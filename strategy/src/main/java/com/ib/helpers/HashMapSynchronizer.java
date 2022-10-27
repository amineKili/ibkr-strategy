package com.ib.helpers;

import java.util.*;
import java.util.stream.Collectors;

//Synchronize hashmap containing positions of securities- then for continous trading

public class HashMapSynchronizer {

    HashMap<String, Double> portfolioMap = new HashMap<>();

    public TreeMap<String, Double> syncMethod(Map<String, Double> unsortedIncomingMap) {
        System.out.println("unsortedIncomingMap.size= " + unsortedIncomingMap.size());


        portfolioMap.put("NZD", 0.0);
        portfolioMap.put("RUR", 0.0);
        portfolioMap.put("ZAR", 0.0);
        portfolioMap.put("MXP", 0.0);
        portfolioMap.put("AUD", 0.0);
        portfolioMap.put("CAD", 0.0);
        portfolioMap.put("CHF", 0.0);
        portfolioMap.put("EUR", 0.0);
        portfolioMap.put("GBP", 0.0);
        portfolioMap.put("JPY", 0.0);


        Set<Map.Entry<String, Double>> entrySet = unsortedIncomingMap.entrySet();
        for (Map.Entry<String, Double> e : entrySet) {
            if (unsortedIncomingMap.containsKey(e.getKey())) {
                portfolioMap.put(e.getKey(), e.getValue());
            } else {
                portfolioMap.put(e.getKey(), e.getValue());
            }
        }

        System.out.println("Before Sorting:");
        var set = portfolioMap.entrySet();
        for (Map.Entry<String, Double> stringDoubleEntry : set) {
            System.out.print(stringDoubleEntry.getKey() + ": ");
            System.out.println(stringDoubleEntry.getValue());
        }

        TreeMap<String, Double> map = new TreeMap<>(portfolioMap);
        System.out.println("After Sorting:");
        var set2 = map.entrySet();
        for (Map.Entry<String, Double> me2 : set2) {
            System.out.print(me2.getKey() + ": ");
            System.out.println(me2.getValue());
        }


        System.out.println("Here's Merged map = :" + map);

        System.out.println("Here's AFTER -- Taking out 0.0 positions:");

        Map<String, Double> result = map.entrySet().stream().filter(e -> Math.abs(e.getValue()) != 0.0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new TreeMap<>(result);

    }
}
