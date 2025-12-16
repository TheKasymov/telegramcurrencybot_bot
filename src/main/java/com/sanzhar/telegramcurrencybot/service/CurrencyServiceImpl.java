package com.sanzhar.telegramcurrencybot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sanzhar.telegramcurrencybot.model.ConversionHistory;
import com.sanzhar.telegramcurrencybot.repository.ConversionHistoryRepository;

import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final String API_URL =
            "https://api.exchangerate.host/convert?from={from}&to={to}&amount={amount}";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ConversionHistoryRepository repository;

    public CurrencyServiceImpl(ConversionHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public double convert(String from, String to, double amount) {
        Map<String, Object> response =
                restTemplate.getForObject(API_URL, Map.class, from, to, amount);

        double result = ((Number) response.get("result")).doubleValue();

        ConversionHistory history = new ConversionHistory();
        history.setFromCurrency(from);
        history.setToCurrency(to);
        history.setAmount(amount);
        history.setResult(result);

        repository.save(history);

        return result;
    }
}
