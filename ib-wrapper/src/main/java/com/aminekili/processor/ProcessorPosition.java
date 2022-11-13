package com.aminekili.processor;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProcessorPosition {

    int length, totalLength;
    String lastFourDigits;
    ArrayList<Double> rtnArrayList = new ArrayList<>();
    HashMap<String, Double> mapPositions = new HashMap<>();
Double positionReturn;
    
   HashMap<String, Double> posArrayProcessor(ArrayList<String> incomingStrArray) {
 //ArrayList<Double> posArrayProcessor(ArrayList<String> incomingStrArray) {
    //   ArrayList<Entry<String, Double>> posArrayProcessor(ArrayList<String> incomingStrArray) {
        System.out.println("incoming arraylist size: " + incomingStrArray.size());
        Collections.sort(incomingStrArray);
        for (int p = 0; p < incomingStrArray.size(); p++) {
            incomingStrArray.get(p);
            totalLength = incomingStrArray.get(p).length();
            System.out.println("TotalLength " + totalLength);
            length = totalLength - 3;
            System.out.println("Length " + length);

            if (totalLength > 3) {
                lastFourDigits = incomingStrArray.get(p).substring(incomingStrArray.get(p).length() - length);
            } else {
                lastFourDigits = incomingStrArray.get(p);
            }
            String conSymbol = incomingStrArray.get(p).substring(0, 3);
            double dum = Double.parseDouble(lastFourDigits);
            mapPositions.put(conSymbol, dum);
            System.out.println(conSymbol + " Pos: " + dum);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        
        
     
        return mapPositions;



        
        
    }


    public double PositionDiscloser(Map<String, Double> passedHashMap, String inputSymbol) {
        for (Map.Entry<String, Double> entry : passedHashMap.entrySet()) {
            String key = entry.getKey();
            if (key == null ? inputSymbol == null : key.equals(inputSymbol)) {
                positionReturn = entry.getValue();
            }
        }
        return positionReturn;
    }

   
   
}

