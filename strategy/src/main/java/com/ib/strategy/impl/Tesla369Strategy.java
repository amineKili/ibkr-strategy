package com.ib.strategy.impl;

import com.ib.client.Bar;
import com.ib.enumerations.DecisionEnum;
import com.ib.strategy.BaseStrategy;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Different strategies
 * Tesla369 strategy
 */
@Slf4j
public class Tesla369Strategy extends BaseStrategy {

    private static final DecimalFormat df5 = new DecimalFormat("#.#####");
    private static final DecimalFormat df6 = new DecimalFormat("+#.#####%;-#");
    private static final DecimalFormat df7 = new DecimalFormat("+#.##");

    double tesla3, tesla6, tesla9;
    int div3;
    double Tick;
    int loop1Div3 = 0;
    int loop2Div3 = 0;
    int loop3Div3 = 0;
    int moduloBar = 0;
    int barInputSize1, barInputSize2;
    double teslaValue9 = 0.484827661;
    double teslaValue6 = 0.32321844;
    double teslaValue3 = 0.16160922;

    public DecisionEnum execute(ArrayList<Bar> barInput) throws InterruptedException {

        DecisionEnum decision = DecisionEnum.NO;

        if (barInput.size() > 1) {

            log.info("Bar size: {}", barInput.size());
            log.info("Modulo: {}", barInput.size() % 3);

            tesla3 = 0.0;
            tesla6 = 0.0;
            tesla9 = 0.0;
            barInputSize1 = barInput.size() - 1;
            barInputSize2 = barInput.size() - 2;

            moduloBar = barInput.size() % 3;

            log.info("Volume, barInput1 {}, barInput2 {} ", barInput.get(barInputSize1).volume(), barInput.get(barInputSize2).volume());

            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) > 41) {

                div3 = barInput.size() / 3;

                log.info("div3=  " + div3);

                log.info("loop3Div3 @ 0=  " + barInput.get(0).wap());
                log.info("loop3Div3 @ 1=  " + barInput.get(1).wap());

                TimeUnit.SECONDS.sleep(2);
                loop3Div3 = (div3 * 3);
                log.info("loop3Div3=  " + loop3Div3);


                TimeUnit.SECONDS.sleep(2);

                for (int loop3 = barInput.size() - 1; loop3 >= (barInput.size() - div3); loop3--) {
                    log.info("loop3=  " + loop3);
                    log.info("barInput.get(loop3).wap()=  " + barInput.get(loop3).wap() + "   Time:  " + barInput.get(loop3).time());
                    tesla9 = barInput.get(loop3).wap() + tesla9;
                }
                tesla9 = (tesla9 / div3) * teslaValue9;   //0.9
                log.info("tesla9=  " + tesla9);


                loop2Div3 = barInput.size() - (2 * div3);
                for (int loop2 = ((barInput.size() - 1) - div3); loop2 >= (barInput.size() - (2 * div3)); loop2--) {
                    log.info("loop2=  " + loop2);
                    log.info("barInput.get(loop2).wap()=  " + barInput.get(loop2).wap() + "   Time:  " + barInput.get(loop2).time());
                    tesla6 = barInput.get(loop2).wap() + tesla6;
                }
                tesla6 = (tesla6 / div3) * teslaValue6; // 0.6 
                log.info("tesla6=  " + tesla6);


                loop1Div3 = barInput.size() - (3 * div3);
                for (int loop1 = ((barInput.size() - 1) - (2 * div3)); loop1 >= (barInput.size() - (3 * div3)); loop1--) {
                    log.info("loop1=  " + loop1);
                    log.info("barInput.get(loop1).wap()=  " + barInput.get(loop1).wap() + "   Time:  " + barInput.get(loop1).time());
                    tesla3 = barInput.get(loop1).wap() + tesla3;
                }
                tesla3 = (tesla3 / div3) * teslaValue3;  ///0.3
                log.info("tesla3=  " + tesla3);


                log.info("Tesla-3:  " + df5.format(tesla3));
                log.info("Tesla-6:  " + df5.format(tesla6));
                log.info("Tesla-9:  " + df5.format(tesla9));

                TimeUnit.SECONDS.sleep(2);

                log.info("tesla3=  " + df5.format(tesla3) + "   ___ PRIOR (raw/Original): " + df5.format(tesla3 / 0.3));
                log.info("tesla6=  " + df5.format(tesla6) + "   ___ PRIOR (raw/Original): " + df5.format(tesla6 / 0.6));
                log.info("tesla9=  " + df5.format(tesla9) + "   ___ PRIOR (raw/Original): " + df5.format(tesla9 / 0.9));

                if (tesla3 < (tesla9 - tesla6) && tesla9 > (tesla3 + tesla6)) {

                    if (tesla3 <= tesla6 && tesla6 <= tesla9) {
                        decision = DecisionEnum.BUY;
                    }
                    if (tesla3 >= tesla6 && tesla6 >= tesla9) {
                        decision = DecisionEnum.SELL;
                    }
                }

                if (tesla3 > (tesla9 - tesla6) && tesla9 < (tesla3 + tesla6)) {
                    log.info("Tesla369 >> Condition not met for opening a position");
                }
            }


            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) < 41) {
                decision = DecisionEnum.NO;

                log.info("Volume Condition not met for opening a position");
            }

        }

        return decision;
    }

    @Override
    public DecisionEnum execute(String symbol, ArrayList<Bar> barInput) throws InterruptedException {
        return null;
    }

}
