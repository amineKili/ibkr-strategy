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
 * Different strategies
 * JapaneseHaramiStrategy
 */
@Slf4j
public class JapaneseHaramiStrategy extends BaseStrategy {

    private static final DecimalFormat df5 = new DecimalFormat("#.#####");

    String candleStickDecision = "NO";
    int loop1Div3 = 0;
    int loop2Div3 = 0;
    double haramiValue9 = 1;
    double haramiValue6 = 1;
    double haramiValue3 = 1;
    int volumeLimit = 41;

    double head9, tail9, openBody9, closeBody9, body9;
    double head6, tail6, openBody6, closeBody6, body6;
    double head3, tail3, openBody3, closeBody3, body3;


    public DecisionEnum execute(ArrayList<Bar> barInput) throws InterruptedException {

        DecisionEnum decision = DecisionEnum.NO;

        if (barInput.size() > 1) {

            log.info("Bar size: {}", barInput.size());
            log.info("Modulo: {}", barInput.size() % 3);

            var harami3 = 0.0;
            var harami6 = 0.0;
            var harami9 = 0.0;
            var barInputSize1 = barInput.size() - 1;
            var barInputSize2 = barInput.size() - 2;

            var moduloBar = barInput.size() % 3;

            log.info("Volume, barInput1 {}, barInput2 {} ", barInput.get(barInputSize1).volume(), barInput.get(barInputSize2).volume());

            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) > volumeLimit) {

                var div3 = barInput.size() / 3;

                log.info("div3=  " + div3);

                log.info("loop3Div3 @ 0=  " + barInput.get(0).wap());
                log.info("loop3Div3 @ 1=  " + barInput.get(1).wap());

                TimeUnit.SECONDS.sleep(2);
                var loop3Div3 = (div3 * 3);
                log.info("loop3Div3=  " + loop3Div3);


                TimeUnit.SECONDS.sleep(2);

                for (int loop3 = barInput.size() - 1; loop3 >= (barInput.size() - div3); loop3--) {
                    log.info("loop3=  " + loop3);
                    log.info("barInput.get(loop3).wap()=  " + barInput.get(loop3).wap() + "   Time:  " + barInput.get(loop3).time());

                    harami9 = barInput.get(loop3).wap() + harami9;
                    head9 = barInput.get(loop3).high() + head9;
                    tail9 = barInput.get(loop3).low() + tail9;
                    openBody9 = barInput.get(loop3).open() + openBody9;
                    closeBody9 = barInput.get(loop3).close() + closeBody9;
                }
                harami9 = (harami9 / div3) * haramiValue9;   //0.9
                head9 = (head9 / div3) * haramiValue9;
                tail9 = (tail9 / div3) * haramiValue9;
                openBody9 = (openBody9 / div3) * haramiValue9;
                closeBody9 = (closeBody9 / div3) * haramiValue9;
                body9 = openBody9 - closeBody9;
                log.info("harami9=  " + harami9);


                loop2Div3 = barInput.size() - (2 * div3);
                for (int loop2 = ((barInput.size() - 1) - div3); loop2 >= (barInput.size() - (2 * div3)); loop2--) {
                    log.info("loop2=  " + loop2);
                    log.info("barInput.get(loop2).wap()=  " + barInput.get(loop2).wap() + "   Time:  " + barInput.get(loop2).time());
                    harami6 = barInput.get(loop2).wap() + harami6;
                    head6 = barInput.get(loop2).high() + head6;
                    tail6 = barInput.get(loop2).low() + tail6;
                    openBody6 = barInput.get(loop2).open() + openBody6;
                    closeBody6 = barInput.get(loop2).close() + closeBody6;
                }
                harami6 = (harami6 / div3) * haramiValue6; // 0.6 
                head6 = (head6 / div3) * haramiValue6;
                tail6 = (tail6 / div3) * haramiValue6;
                openBody6 = (openBody6 / div3) * haramiValue6;
                closeBody6 = (closeBody6 / div3) * haramiValue6;
                body6 = openBody6 - closeBody6;
                log.info("harami6=  " + harami6);


                loop1Div3 = barInput.size() - (3 * div3);
                for (int loop1 = ((barInput.size() - 1) - (2 * div3)); loop1 >= (barInput.size() - (3 * div3)); loop1--) {
                    log.info("loop1=  " + loop1);
                    log.info("barInput.get(loop1).wap()=  " + barInput.get(loop1).wap() + "   Time:  " + barInput.get(loop1).time());
                    harami3 = barInput.get(loop1).wap() + harami3;
                    head3 = barInput.get(loop1).high() + head3;
                    tail3 = barInput.get(loop1).low() + tail3;
                    openBody3 = barInput.get(loop1).open() + openBody3;
                    closeBody3 = barInput.get(loop1).close() + closeBody3;
                }
                harami3 = (harami3 / div3) * haramiValue3;  ///0.3
                head3 = (head3 / div3) * haramiValue3;
                tail3 = (tail3 / div3) * haramiValue3;
                openBody3 = (openBody3 / div3) * haramiValue3;
                closeBody3 = (closeBody3 / div3) * haramiValue3;
                body3 = openBody3 - closeBody3;
                log.info("harami3=  " + harami3);

                log.info("Harami-3:  " + df5.format(harami3));
                log.info("Harami-6:  " + df5.format(harami6));
                log.info("Harami-9:  " + df5.format(harami9));

                TimeUnit.SECONDS.sleep(2);

                log.info("harami3=  " + df5.format(harami3) + "   ___ PRIOR (raw/Original): " + df5.format(harami3 / 0.3));
                log.info("harami6=  " + df5.format(harami6) + "   ___ PRIOR (raw/Original): " + df5.format(harami6 / 0.6));
                log.info("harami9=  " + df5.format(harami9) + "   ___ PRIOR (raw/Original): " + df5.format(harami9 / 0.9));

                if (body3 > body9 && body6 > body9) {
                    decision = DecisionEnum.SELL;
                }

                if (body3 < body9 && body6 < body9) {
                    decision = DecisionEnum.BUY;
                }

                if (!candleStickDecision.equals("SELL") && !candleStickDecision.equals("BUY")) {
                    log.info("Japanese candlestick Harami  >> Condition not met for opening a position");
                }
            }
            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) < volumeLimit) {
                decision = DecisionEnum.NO;
                log.info("Volume Condition not met for opening a position");
            }

        }

        if (barInput.size() <= 1) {
            decision = DecisionEnum.NO;
        }
        return decision;
    }

}
