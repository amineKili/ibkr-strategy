package com.ib.strategy.impl;

import com.ib.client.Bar;
import com.ib.enumerations.DecisionEnum;
import com.ib.ib.LiveBar;
import com.ib.strategy.BaseStrategy;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Simple strategy
 */
@Slf4j
public class SimpleStrategy extends BaseStrategy {

    private static final DecimalFormat df5 = new DecimalFormat("#.#####");
    private static final DecimalFormat df6 = new DecimalFormat("+#.#####%;-#");
    private static final DecimalFormat df7 = new DecimalFormat("+#.##");

    int sizeSimpleBar;
    double pivotPoint0, pivotPoint1;
    double slope;
    double momentum = 1.0;

    int bar1;
    int bar2;


    public DecisionEnum execute(ArrayList<Bar> barInput) throws InterruptedException {

        DecisionEnum decision = DecisionEnum.NO;

        log.info("Bar size: {}", barInput.size());
        log.info("Modulo: {}", barInput.size() % 3);

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

        log.info("sizeSimpleBar: " + sizeSimpleBar);

        pivotPoint0 = (barInput.get(bar1).low() + barInput.get(bar1).high() + barInput.get(bar1).close()) / 3;
        pivotPoint1 = (barInput.get(bar2).low() + barInput.get(bar2).high() + barInput.get(bar2).close()) / 3;

        log.info("pivotPoint0: " + pivotPoint0);
        log.info("pivotPoint1: " + pivotPoint1);

        slope = barInput.get(bar2).close() - pivotPoint1;

        log.info("Slope:  " + slope);

        if (slope > 0.0) {
            decision = DecisionEnum.BUY;
        }
        if (slope < 0.0) {
            decision = DecisionEnum.SELL;
        }

        return decision;

    }
}
