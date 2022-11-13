package com.aminekili.processor;

import com.aminekili.utils.LiveBar;
import com.ib.client.Bar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class ProcessorHistoricalData {

    private static final DecimalFormat df5 = new DecimalFormat("#.#####");
    private static final DecimalFormat df6 = new DecimalFormat("+#.#####%;-#");
    private static final DecimalFormat df7 = new DecimalFormat("+#.##");

    String decision = "";

    int sizeSimpleBar;
    double simpleOPEN, simpleHIGH, simpleLOW, simpleCLOSE;
    double pivotpoint_0, pivotpoint_1, pivotpoint_2;
    double slope;
    double slope_0, slope_1, slope_2;
    double pctChange_0 = 0.0;
    double pctChange, LastTick;
    double momentum_1, momentum_2, momentum_3;
    double momentum_0 = 1.0;
    double momentum = 1.0;

//    TimeDifference TD = new TimeDifference();

//   public static LiveBar myFunction(String strResult,double doubleResult){
//    return new LiveBar(strResult,doubleResult);
//}


    public String simpleDecisionDeterminer_NoTick(ArrayList<Bar> barInput) {  // throws InterruptedException {

        System.out.println("===================  simple-DecisionDeterminer_NoTick =============================================>  ");

        System.out.println("===Bar size:  =>  " + barInput.size());

        sizeSimpleBar = barInput.size();

        if (sizeSimpleBar > 1) {
            System.out.println(" sizeSimpleBar===>  " + sizeSimpleBar);
            simpleOPEN = barInput.get(0).open();

            if (barInput.get(0).high() >= barInput.get(1).high()) {
                simpleHIGH = barInput.get(0).high();
            }
            if (barInput.get(0).high() <= barInput.get(1).high()) {
                simpleHIGH = barInput.get(1).high();
            }
            if (barInput.get(0).low() <= barInput.get(1).low()) {
                simpleLOW = barInput.get(0).low();
            }
            if (barInput.get(0).low() >= barInput.get(1).low()) {
                simpleLOW = barInput.get(1).low();
            }
            simpleCLOSE = barInput.get(1).close();

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("sizeSimpleBar = " + sizeSimpleBar);
            System.out.println("simpleOPEN  = " + simpleOPEN);
            System.out.println("simpleHIGH  = " + simpleHIGH);
            System.out.println("simpleLOW   = " + simpleLOW);
            System.out.println("simpleCLOSE = " + simpleCLOSE);

            pivotpoint_0 = (barInput.get(0).low() + barInput.get(0).high() + barInput.get(0).close()) / 3;
            pivotpoint_1 = (barInput.get(1).low() + barInput.get(1).high() + barInput.get(1).close()) / 3;

            slope = barInput.get(1).close() - pivotpoint_0;

            System.out.println(" ");

            if (slope > 0.0) {
                decision = "BUY";
            }
            if (slope < 0.0) {
                decision = "SELL";
            }

            System.out.println("slope:                 " + df5.format(slope));

        }

        if (sizeSimpleBar > 1) {
            if (slope == 0.0) {
                //           decision = "HOLD";
                Random rand = new Random();
                double rand_dub1 = rand.nextDouble();
                double rand_dub2 = rand.nextDouble();
                double rand_dub3 = rand.nextDouble();
                double rand_dub4 = rand.nextDouble();
                pivotpoint_0 = (barInput.get(0).low() * rand_dub2 + barInput.get(0).high() * rand_dub3 + barInput.get(0).close() * rand_dub4) / 3;
                slope = barInput.get(0).open() * rand_dub1 - pivotpoint_0;
                double adjustedSlope = slope;
                System.out.println("Adjusted Slope=  " + adjustedSlope);
                if (slope > 0.0) {
                    decision = "BUY";
                }
                if (slope < 0.0) {
                    decision = "SELL";
                }
            }
        }

        System.out.println("slope:                 " + df5.format(slope));

        System.out.println();
        return decision;

    }


//    
//    public void simpleDecisionDeterminer_Momentum(ArrayList<Bar> barInput) {  // throws InterruptedException {
//
//        System.out.println("===================  simpleDecisionDeterminer_Momentum =============================================>  ");
//        System.out.println();
//        System.out.println();
//        System.out.println(barInput.toString());
//        System.out.println();
//        System.out.println();
//
//        sizeSimpleBar = barInput.size();
//       
//        System.out.println();
//        System.out.println("|===Size of Bar <Check/verify> ==:  "+barInput.size());
//        System.out.println();
//
//        barInput.get(0).time();
//        barInput.get(1).time();
//        barInput.get(2).time();
//        barInput.get(3).time();
//        TD.FindDifference__MethodYYYYMMDD(barInput.get(0).time(), barInput.get(1).time());
//        TD.FindDifference__MethodYYYYMMDD(barInput.get(1).time(), barInput.get(2).time());
//        TD.FindDifference__MethodYYYYMMDD(barInput.get(2).time(), barInput.get(3).time());
//        
//        
//        
//        
//        if (sizeSimpleBar != 0) {
//        if (sizeSimpleBar > 1) {
////            System.out.println(" sizeSimpleBar===>  " + sizeSimpleBar);
////            simpleOPEN = barInput.get(0).open();
////
////            if (barInput.get(0).high() >= barInput.get(1).high()) {
////                simpleHIGH = barInput.get(0).high();
////            }
////            if (barInput.get(0).high() <= barInput.get(1).high()) {
////                simpleHIGH = barInput.get(1).high();
////            }
////            if (barInput.get(0).low() <= barInput.get(1).low()) {
////                simpleLOW = barInput.get(0).low();
////            }
////            if (barInput.get(0).low() >= barInput.get(1).low()) {
////                simpleLOW = barInput.get(1).low();
////            }
////            simpleCLOSE = barInput.get(1).close();
//
//            System.out.println();
//            System.out.println();
//            System.out.println();
//
////            System.out.println("sizeSimpleBar = " + sizeSimpleBar);
////            System.out.println("simpleOPEN  = " + simpleOPEN);
////            System.out.println("simpleHIGH  = " + simpleHIGH);
////            System.out.println("simpleLOW   = " + simpleLOW);
////            System.out.println("simpleCLOSE = " + simpleCLOSE);
//
//            pivotpoint_0 = (barInput.get(0).low() + barInput.get(0).high() + barInput.get(0).close()) / 3;
//            pivotpoint_1 = (barInput.get(1).low() + barInput.get(1).high() + barInput.get(1).close()) / 3;
//            pivotpoint_2 = (barInput.get(2).low() + barInput.get(2).high() + barInput.get(2).close()) / 3;
//
//            //slope_0 = barInput.get(0).open() - pivotpoint_0;
//            slope_1 = barInput.get(1).open() - pivotpoint_0;
//            slope_2 = barInput.get(2).open() - pivotpoint_1;
//
////            Math.signum(55.6);
//            if (Math.signum(slope_0) == Math.signum(slope_1)) {
//                momentum_1 = momentum_0 + 1.0;
//            }
//            if (Math.signum(slope_0) != Math.signum(slope_1)) {
//                momentum_1 = 1.0;
//            }
//
//            if (Math.signum(slope_1) == Math.signum(slope_2)) {
//                momentum_2 = momentum_1 + 1.0;
//            }
//            if (Math.signum(slope_1) != Math.signum(slope_2)) {
//                momentum_2 = 1.0;
//            }
//
//            slope = slope_2;         //  Indicates BUY or SELL  ==> +Buy/Long     -Short/Sell
//            momentum = momentum_2;   //  Indicates Qty or # of Contracts
//
//            System.out.println(" ");
//
//            if (slope > 0.0) {
//                decision = "BUY";
//            }
//            if (slope < 0.0) {
//                decision = "SELL";
//            }
//
//            System.out.println("-------------------------------------------------------------------------------------------->  ");
//            System.out.println("slope:                 " + df5.format(slope));
//            System.out.println("Momentum:     -------> " + df7.format(momentum));
//
//        }
//    }
//     
//        
// if (sizeSimpleBar != 0)       {   
//        if (slope == 0.0) {
//            //           decision = "HOLD";
//            Random rand = new Random();
//            double rand_dub1 = rand.nextDouble();
//            double rand_dub2 = rand.nextDouble();
//            double rand_dub3 = rand.nextDouble();
//            double rand_dub4 = rand.nextDouble();
//            pivotpoint_0 = (barInput.get(0).low() * rand_dub2 + barInput.get(0).high() * rand_dub3 + barInput.get(0).close() * rand_dub4) / 3;
//            slope = barInput.get(0).open() * rand_dub1 - pivotpoint_0;
//            double adjustedSlope = slope;
//            System.out.println("Adjusted Slope=  " + adjustedSlope);
//            if (slope > 0.0) {
//                decision = "BUY";
//            }
//            if (slope < 0.0) {
//                decision = "SELL";
//            }
//        }
//        System.out.println("slope:                 " + df5.format(slope));
//        System.out.println();
//    }
//        
//        
//    }
//


    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    LiveBar getLiveBar() {
        String strResult = getDecision();
        double doubleResult = getMomentum();
        return new LiveBar(strResult, doubleResult);
    }


}
