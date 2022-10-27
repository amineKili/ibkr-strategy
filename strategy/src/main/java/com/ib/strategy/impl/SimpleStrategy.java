package com.ib.strategy.impl;

import com.ib.client.Bar;
import com.ib.ib.LiveBar;
import com.ib.strategy.BaseStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Simple strategy
 */

public class SimpleStrategy extends BaseStrategy {

    private static final DecimalFormat df5 = new DecimalFormat("#.#####");
    private static final DecimalFormat df6 = new DecimalFormat("+#.#####%;-#");
    private static final DecimalFormat df7 = new DecimalFormat("+#.##");

    String decision = "";
    String candleStickDecision = "NO";

    int sizeSimpleBar;
    double pivotPoint0, pivotPoint1;
    double slope;
    double momentum = 1.0;

    int bar1;
    int bar2;


    public String execute(ArrayList<Bar> barInput) throws InterruptedException {

        System.out.println("===================  simple-DecisionDeterminer_NoTick =============================================>  ");
        System.out.println("============          Here's ArrayList<Bar> barInput         ======================================>  " + barInput);
        System.out.println("===================================================================================================>  ");

        System.out.println("===================  simple-DecisionDeterminer_NoTick =============================================>  ");
        System.out.println("===Bar size:  =>  " + barInput.size());

        sizeSimpleBar = barInput.size();

        if (barInput.size() == 2) {
            bar1 = 0;
            bar2 = 1;
        }
        ;
        if (barInput.size() == 3) {
            bar1 = 1;
            bar2 = 2;
        }

        System.out.println(" sizeSimpleBar===>  " + sizeSimpleBar);


        pivotPoint0 = (barInput.get(bar1).low() + barInput.get(bar1).high() + barInput.get(bar1).close()) / 3;
        pivotPoint1 = (barInput.get(bar2).low() + barInput.get(bar2).high() + barInput.get(bar2).close()) / 3;

        System.out.println("pivotpoint_0  = " + pivotPoint0);
        System.out.println("pivotpoint_1  = " + pivotPoint1);

        slope = barInput.get(bar2).close() - pivotPoint1;

        System.out.println("Slope                   :  " + slope);

        if (slope > 0.0) {
            decision = "BUY";
        }
        if (slope < 0.0) {
            decision = "SELL";
        }

        return decision;

    }

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

    public LiveBar getLiveBar() {
        String strResult = getDecision();
        double doubleResult = getMomentum();
        return new LiveBar(strResult, doubleResult);
    }

}
