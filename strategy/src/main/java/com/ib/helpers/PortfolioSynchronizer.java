package com.ib.helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioSynchronizer {
    
    Map<String, List<Double>> criteriaMatch = new HashMap<>();
    Map<String, Double> positionsToClose = new HashMap<>();

    public Map<String, Double> portfolioSyncMethod(Map<String, List<Double>> incomingPortfolio) {

        /*        NORMAL      */

        criteriaMatch.put("MXP", Arrays.asList(-30.53, 40.47));
        criteriaMatch.put("NZD", Arrays.asList(-46.03, 55.97));
        criteriaMatch.put("AUD", Arrays.asList(-46.66, 56.60));
        criteriaMatch.put("CAD", Arrays.asList(-50.48, 60.42));
        criteriaMatch.put("ZAR", Arrays.asList(-56.28, 73.72));
        criteriaMatch.put("JPY", Arrays.asList(-68.48, 79.67));
        criteriaMatch.put("CHF", Arrays.asList(-85.16, 102.6));
        criteriaMatch.put("GBP", Arrays.asList(-89.73, 100.9));
        criteriaMatch.put("EUR", Arrays.asList(-90.89, 102.0));
        criteriaMatch.put("RUR", Arrays.asList(-108.6, 126.1));


        for (Map.Entry<String, List<Double>> entry1 : incomingPortfolio.entrySet()) {
            String key = entry1.getKey();
            double value1_POS = entry1.getValue().get(0);
            double value1_mktPrice = entry1.getValue().get(1);
            double value1_mktValue = entry1.getValue().get(2);
            double value1_avgCost = entry1.getValue().get(3);
            double value1_uPNL = entry1.getValue().get(4);
            double value1_rPNL = entry1.getValue().get(5);


            System.out.println("Symbol           = " + key);
            System.out.println("Position         = " + value1_POS);
            System.out.println("Market_Price     = " + value1_mktPrice);
            System.out.println("Market_Value     = " + value1_mktValue);
            System.out.println("AverageCost      = " + value1_avgCost);
            System.out.println("UnrealizedPNL    = " + value1_uPNL);
            System.out.println("RealizedPNL      = " + value1_rPNL);


            double value2_min = criteriaMatch.get(key).get(0);
            double value2_max = criteriaMatch.get(key).get(1);

            System.out.println("Minimum          = " + value2_min);
            System.out.println("Maximum          = " + value2_max);


            if (value1_uPNL / Math.abs(value1_POS) <= value2_min || value1_uPNL / Math.abs(value1_POS) >= value2_max) {
                positionsToClose.put(key, value1_POS);
            }

        }

        System.out.println("Here's answer_Box:===> PositionsToClose =======================");
        System.out.println("Here's answer_Box:===>  " + positionsToClose);
        System.out.println("Here's answer_Box:===> PositionsToClose =======================");

        return positionsToClose;

    }
}
