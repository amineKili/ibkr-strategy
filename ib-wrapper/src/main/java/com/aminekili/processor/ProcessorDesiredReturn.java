package com.aminekili.processor;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ProcessorDesiredReturn {

    HashMap<String, Double> Gains_per_Trade = new HashMap<>();
    HashMap<String, Double> DesiredGains_per_Trade = new HashMap<>();

    public double getReturnPerTradeMethod(Map<String, Double> unsortedIncomingMap) {
        unsortedIncomingMap.size();
        System.out.println("unsortedIncomingMap.size= " + unsortedIncomingMap.size());
        System.out.println("unsortedIncomingMap HashMap= " + unsortedIncomingMap.size());
        System.out.println();

        Gains_per_Trade.put("MXP", 45.50); //19*2

        Gains_per_Trade.put("NZD", 28.0);
        Gains_per_Trade.put("AUD", 24.0);
        Gains_per_Trade.put("CAD", 24.0);
        Gains_per_Trade.put("ZAR", 60.0);
        Gains_per_Trade.put("JPY", 32.0);
        Gains_per_Trade.put("CHF", 42.0);
        Gains_per_Trade.put("GBP", 37.0);
        Gains_per_Trade.put("EUR", 38.0);
        Gains_per_Trade.put("RUR", 47.0);


        Set<Map.Entry<String, Double>> entrySet = unsortedIncomingMap.entrySet();
        for (Map.Entry<String, Double> e : entrySet) {
            if (unsortedIncomingMap.containsKey(e.getKey())) {

                if (Math.abs(unsortedIncomingMap.get(e.getKey())) != 0.00) {
                    DesiredGains_per_Trade.put(e.getKey(), Gains_per_Trade.get(e.getKey()));
                }
            } else {
                DesiredGains_per_Trade.put(e.getKey(), 0.0);
            }
        }

        System.out.println("Here's DesiredGains_Per_Trade BEFORE___Summing out Total $ Desired_per_Trade:");
        System.out.println();
        System.out.println(DesiredGains_per_Trade);
        System.out.println();

        double sum = DesiredGains_per_Trade.values().stream().mapToDouble(w -> w).sum();

//     System.out.println("Desired Return/Gains for current positions:  $"+sum);

        return sum;

    }


}
