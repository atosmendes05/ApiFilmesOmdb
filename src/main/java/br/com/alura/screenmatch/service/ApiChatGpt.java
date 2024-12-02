package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiChatGpt {

    public void obterTraducao() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.apyhub.com//sharpapi/api/v1/content/translate"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"content\":\"nenhuma rota corresponde a esses valores\",\"context\":\"Inglês\"}"))
                .setHeader("Content-Type", "application/json")
                .setHeader("apy-token", "APY0OWThTVFl0Xmw7XjVRMiLQrKr0ACbWGRAUBw0jPhvEySfEI8LSF7n7NlAACa4vUAGeUY6c")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());



    }


}
