package com.example.vrteste.front.Pedido.Controller;

import com.example.vrteste.front.Pedido.Model.BaixaEstoqueRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProdutoBaixaApiController {
    private static final String API_URL = "http://localhost:3000/produtos/baixa";

    public static void baixarEstoqueEmLote(List<BaixaEstoqueRequest> requests) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        Gson gson = new Gson();
        String input = gson.toJson(requests);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != 207) {
            throw new RuntimeException("Erro ao baixar estoque: HTTP " + conn.getResponseCode());
        }
        conn.disconnect();
    }
}
