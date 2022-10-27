package com.ib.helpers;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PositionDisclose {

    public static double getPositionDisclose(TreeMap<String, Double> incomingTreeMap, String incomingSymbol) {
        var positionQtyToClose = 0.0;

        Set<Map.Entry<String, Double>> entrySet = incomingTreeMap.entrySet();
        int index = 0;
        for (Map.Entry<String, Double> currentEntry : entrySet) {
            if (currentEntry.getKey() == null ? incomingSymbol == null : currentEntry.getKey().equals(incomingSymbol)) {

                if (currentEntry.getValue() != 0.0) {
                    positionQtyToClose = currentEntry.getValue();
                }
                System.out.println("Counter [index]     = " + index);
            }
        }

        return positionQtyToClose;
    }

    public static double getAbsolutePositionDisclose(TreeMap<String, Double> incomingTreeMap) {
        var absoluteOpenPositions = 0.0;

        Set<Map.Entry<String, Double>> entrySet = incomingTreeMap.entrySet();
        for (Map.Entry<String, Double> currentEntry : entrySet) {
            if (currentEntry.getValue() != 0.0) {
                absoluteOpenPositions = Math.abs(currentEntry.getValue()) + absoluteOpenPositions;
            }
        }

        return absoluteOpenPositions;
    }

}
