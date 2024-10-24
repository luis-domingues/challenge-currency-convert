package com.currencyConvert.services;

import com.google.gson.Gson;
import com.currencyConvert.models.ApiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyServices {
    private static final String API_KEY = "73d4a73c72753f3d68f73b93";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static double getExchangeRate (String sourceCurrency, String destinationCurrency) throws IOException, InterruptedException {
        String url = API_URL + sourceCurrency;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode () == 200) {
            Gson gson = new Gson ();
            ApiResponse apiResponse = gson.fromJson (response.body (), ApiResponse.class);
            return apiResponse.getConversionRates().get(destinationCurrency);
        } else {
            System.out.println ("Error " + response.statusCode ());
            return -1;
        }
    }
}
