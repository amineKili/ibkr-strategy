package com.ib.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class AIDataSetUtils {
    private static final Map<String, String> currencyToTrainingFile = new HashMap<>();

    public static String getTrainingFileByCurrency(String currency) {
        if (currencyToTrainingFile.containsKey(currency.toUpperCase())) {
            return currencyToTrainingFile.get(currency.toUpperCase());
        }
        throw new IllegalArgumentException("File not found for currency: " + currency);
    }

    static {
        currencyToTrainingFile.put("AUD", "src/main/resources/AUD_train.csv");
        currencyToTrainingFile.put("CAD", "src/main/resources/CAD_train.csv");
        currencyToTrainingFile.put("CHF", "src/main/resources/CHF_train.csv");
        currencyToTrainingFile.put("EUR", "src/main/resources/EUR_train.csv");
        currencyToTrainingFile.put("GBP", "src/main/resources/GBP_train.csv");
    }

    public static Set<String> getCurrencies() {
        return currencyToTrainingFile.keySet();
    }
}
