package com.ib.helpers;

import java.util.*;

public class ProcessorPosition {


    public static Map<String, Double> getPosArrayProcessor(ArrayList<String> incomingStrArray) {
        Map<String, Double> mapPositions = new HashMap<>();
        System.out.println("incoming arraylist size: " + incomingStrArray.size());
        Collections.sort(incomingStrArray);
        for (String pArray : incomingStrArray) {
            var totalLength = pArray.length();
            System.out.println("TotalLength " + totalLength);
            var length = totalLength - 3;
            System.out.println("Length " + length);
            var lastFourDigits = "";
            if (totalLength > 3) {
                lastFourDigits = pArray.substring(pArray.length() - length);
            } else {
                lastFourDigits = pArray;
            }
            String conSymbol = pArray.substring(0, 3);
            double dum = Double.parseDouble(lastFourDigits);
            mapPositions.put(conSymbol, dum);
            System.out.println(conSymbol + " Pos: " + dum);
        }
        return mapPositions;

    }

    public static Double getPositionDisclose(Map<String, Double> passedHashMap, String inputSymbol) {
        for (Map.Entry<String, Double> entry : passedHashMap.entrySet()) {
            String key = entry.getKey();
            if (Objects.equals(key, inputSymbol)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
