package com.medimanage.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappService {

    @Value("{whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token")
    private String apiToken;

    private final WebClient webClient = WebClient.builder().build();

    public void enviarRecordatorioCita(String telefonoPaciente, String nombrePaciente, String fechaHora, String motivo) {
        String numeroDestino = limpiarNumero(telefonoPaciente);

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", numeroDestino,
                "type", "template",
                "template", Map.of(
                        "name", "confirmacion_cita",
                        "language", Map.of("code", "es"),
                        "components", List.of(
                                Map.of(
                                        "type", "body",
                                        "parameters", List.of(
                                                Map.of("type", "text", "text", nombrePaciente),
                                                Map.of("type", "text", "text", fechaHora),
                                                Map.of("type", "text", "text", motivo)
                                        )
                                )
                        )
                )
        );

        this.webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + apiToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        response -> System.out.println("Whatsapp enviado con éxito a: " + numeroDestino),
                        error -> System.err.println("Error al enviar WhatsApp: " + error.getMessage())
                );
    }

    private String limpiarNumero(String telefono) {
        String limpio = telefono.replaceAll("[^0-9]", "");
        if (!limpio.startsWith("52")) {
            limpio = "52" + limpio;
        }
        return limpio;
    }
}
