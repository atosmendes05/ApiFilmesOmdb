package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ConsumirApi {

   public String ApiOmdb(String endereco) {

       HttpClient client = HttpClient.newHttpClient();
       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(endereco))
               .build();
       HttpResponse<String> response = null;
       try {
           response = client
                   .send(request, HttpResponse.BodyHandlers.ofString());

           if(response.statusCode() == 200){
               return  response.body();
           }else {
               System.out.println("Erro na resposta da Api" + response.statusCode());
           }


       } catch (IOException e) {
           System.out.println("Erro na comunicaçao da Api" + e.getMessage());
       } catch (InterruptedException e){
           System.out.println("Requisiçao interrompido" + e.getMessage());
       }catch (NullPointerException e){
           System.out.println("Filme nao encontrado" + e.getMessage());
       }
       catch (Exception e ){
           System.out.println("Ocorreu um erro insesperado" + e.getMessage());
       }


       return response.body();
   }
}
