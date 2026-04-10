package com.realticker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.realticker.dto.AnalysisResponse;
import com.realticker.model.Stock;

@Service
public class StockService {

    private final Random random = new Random();

    @Value("${huggingface.api.key}")
    private String apiKey;

    // ✅ 1. Top 10 Stocks
    public List<Stock> getTop10Stocks() {
        String[] companies = {
                "Apple", "Microsoft", "Google", "Amazon", "Tesla",
                "Meta", "Nvidia", "Netflix", "Intel", "AMD"
        };

        List<Stock> stocks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            stocks.add(new Stock(
                    "STK" + (i + 1),
                    companies[i],
                    100 + random.nextDouble() * 200,
                    -5 + random.nextDouble() * 10,
                    1000000L * (i + 1)
            ));
        }

        return stocks;
    }

    // ✅ 2. 6 Months History
    public List<Double> getStockHistory(String ticker) {
        List<Double> history = new ArrayList<>();

        double basePrice = 100 + random.nextDouble() * 50;

        for (int i = 0; i < 180; i++) {
            basePrice += random.nextDouble() * 4 - 2;
            history.add(Math.round(basePrice * 100.0) / 100.0);
        }

        return history;
    }

    // ✅ 3. AI Analysis using HuggingFace (Llama 3)
    public AnalysisResponse analyzeStock(String ticker) {

        List<Double> history = getStockHistory(ticker);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Chat Completion Body
        Map<String, Object> body = new HashMap<>();
        body.put("model", "meta-llama/Meta-Llama-3-8B-Instruct");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content",
                "You are a financial expert.\n"
                + "Analyze this 6-month stock data:\n"
                + history + "\n\n"
                + "Give response in this format:\n"
                + "Trend: Uptrend/Downtrend/Sideways\n"
                + "Risk: Low/Medium/High\n"
                + "Suggestion: Buy/Hold/Sell");

        messages.add(message);
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        String responseText = "";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://router.huggingface.co/v1/chat/completions",
                    request,
                    Map.class
            );

            Map result = response.getBody();

            if (result != null) {
                List choices = (List) result.get("choices");

                if (choices != null && !choices.isEmpty()) {
                    Map choice = (Map) choices.get(0);
                    Map msg = (Map) choice.get("message");

                    responseText = msg.get("content").toString().toLowerCase();
                }
            }

        } catch (Exception e) {
            return new AnalysisResponse(
                    "Error",
                    "Error",
                    "Error",
                    "HuggingFace API failed: " + e.getMessage()
            );
        }

        // ✅ Default values
        String trend = "Unknown";
        String risk = "Unknown";
        String suggestion = "Unknown";

        // ✅ Parsing
        if (responseText != null) {

            if (responseText.contains("uptrend"))
                trend = "Uptrend";
            else if (responseText.contains("downtrend"))
                trend = "Downtrend";
            else if (responseText.contains("sideways"))
                trend = "Sideways";

            if (responseText.contains("high"))
                risk = "High";
            else if (responseText.contains("medium"))
                risk = "Medium";
            else if (responseText.contains("low"))
                risk = "Low";

            if (responseText.contains("buy"))
                suggestion = "Buy";
            else if (responseText.contains("sell"))
                suggestion = "Sell";
            else if (responseText.contains("hold"))
                suggestion = "Hold";
        }

        return new AnalysisResponse(
                trend,
                risk,
                suggestion,
                "AI-generated analysis using HuggingFace (Llama 3)"
        );
    }
}