package com.aminekili.processor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessorPNLPortfolio {

//Map<String, List<Double>> portfolio_Positions = new HashMap<>();
    Map<String, List<Double>> criteria_Match = new HashMap<>();
    Map<String, Double> PositionsToClose = new HashMap<>();

    public Map<String, Double> portfolioSyncMethod(Map<String, List<Double>> incomingPortfolio) {

        /*        NORMAL      */


                /*        GOLDEN RATIO DERIVED      */
//Golden_Ratio_Min, Golden_Ratio_Max, Cost, Benefit
criteria_Match.put("MXP", Arrays.asList(-69.97,69.97,-30.53,40.47));
criteria_Match.put("NZD", Arrays.asList(-105.50,105.50,-46.03,55.97));
criteria_Match.put("AUD", Arrays.asList(-106.90,106.90,-46.66,56.60));
criteria_Match.put("CAD", Arrays.asList(-115.60,115.60,-50.48,60.42));
criteria_Match.put("ZAR", Arrays.asList(-128.90,128.90,-56.28,73.72));
criteria_Match.put("JPY", Arrays.asList(-156.90,156.90,-68.48,79.67));
criteria_Match.put("CHF", Arrays.asList(-195.10,195.10,-85.16,102.60));
criteria_Match.put("GBP", Arrays.asList(-205.60,205.60,-89.73,100.90));
criteria_Match.put("EUR", Arrays.asList(-208.30,208.30,-90.89,102.00));
criteria_Match.put("RUR", Arrays.asList(-249.10,249.10,-108.60,126.10));
        
//        criteria_Match.put("MXP", Arrays.asList(-18.45, 11.01));
//        criteria_Match.put("NZD", Arrays.asList(-26.93, 16.99));
//        criteria_Match.put("AUD", Arrays.asList(-22.50, 15.06));
//        criteria_Match.put("CAD", Arrays.asList(-23.56, 16.12));
//        criteria_Match.put("ZAR", Arrays.asList(-33.78, 22.59));
//        criteria_Match.put("JPY", Arrays.asList(-30.50, 22.44));
//        criteria_Match.put("CHF", Arrays.asList(-40.57, 29.38));
//        criteria_Match.put("GBP", Arrays.asList(-35.81, 27.74));
//        criteria_Match.put("EUR", Arrays.asList(-36.21, 28.15));
//        criteria_Match.put("RUR", Arrays.asList(-46.26, 35.07));
//



/*     45  50 55  60  65  */
//        criteria_Match.put("MXP", Arrays.asList(-45.45, 11.01));
//        criteria_Match.put("NZD", Arrays.asList(-45.93, 16.99));
//        criteria_Match.put("AUD", Arrays.asList(-50.50, 15.06));
//        criteria_Match.put("CAD", Arrays.asList(-50.56, 16.12));
//        criteria_Match.put("ZAR", Arrays.asList(-55.78, 22.59));
//        criteria_Match.put("JPY", Arrays.asList(-55.50, 22.44));
//        criteria_Match.put("CHF", Arrays.asList(-60.57, 29.38));
//        criteria_Match.put("GBP", Arrays.asList(-60.81, 27.74));
//        criteria_Match.put("EUR", Arrays.asList(-65.21, 28.15));
//        criteria_Match.put("RUR", Arrays.asList(-65.26, 35.07));










////////   BIZARRO SUPERMAN PLAN        
////////        criteria_Match.put("MXP", Arrays.asList(-11.01, 18.45));
////////        criteria_Match.put("NZD", Arrays.asList(-16.99, 26.93));
////////        criteria_Match.put("AUD", Arrays.asList(-15.06, 22.50));
////////        criteria_Match.put("CAD", Arrays.asList(-16.12, 23.56));
////////        criteria_Match.put("ZAR", Arrays.asList(-22.59, 33.78));
////////        criteria_Match.put("JPY", Arrays.asList(-22.44, 30.50));
////////        criteria_Match.put("CHF", Arrays.asList(-29.38, 40.57));
////////        criteria_Match.put("GBP", Arrays.asList(-27.74, 35.81));
////////        criteria_Match.put("EUR", Arrays.asList(-28.15, 36.21));
////////        criteria_Match.put("RUR", Arrays.asList(-35.07, 46.26));




        
        
        for (Map.Entry<String, List<Double>> entry1 : incomingPortfolio.entrySet()) {
            String key = entry1.getKey();
            double value1_POS = entry1.getValue().get(0);
            double value1_mktPrice = entry1.getValue().get(1);
            double value1_mktValue = entry1.getValue().get(2);
            double value1_avgCost = entry1.getValue().get(3);
            double value1_uPNL = entry1.getValue().get(4);
            double value1_rPNL = entry1.getValue().get(5);

            System.out.println();
            System.out.println("Symbol           = " + key);
            System.out.println("Position         = " + value1_POS);
            System.out.println("Market_Price     = " + value1_mktPrice);
            System.out.println("Market_Value     = " + value1_mktValue);
            System.out.println("AverageCost      = " + value1_avgCost);
            System.out.println("UnrealizedPNL    = " + value1_uPNL);
            System.out.println("RealizedPNL      = " + value1_rPNL);

            
            double value2_min = criteria_Match.get(key).get(0);
            double value2_max = criteria_Match.get(key).get(1);

            System.out.println("Minimum          = " + value2_min);
            System.out.println("Maximum          = " + value2_max);

            
            if (value1_uPNL/Math.abs(value1_POS) <= value2_min || value1_uPNL/Math.abs(value1_POS) >= value2_max) {
                PositionsToClose.put(key, value1_POS);
            }

        }

        System.out.println("Here's answer_Box:===> PositionsToClose =======================");
        System.out.println("Here's answer_Box:===>  " + PositionsToClose);
        System.out.println("Here's answer_Box:===> PositionsToClose =======================");

        return PositionsToClose;

    }
}
