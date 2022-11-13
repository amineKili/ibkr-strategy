package com.ib.strategy.impl;


import com.ib.ai.model.BaseModel;
import com.ib.ai.model.implementation.GradientBoostedTreesImpl;
import com.ib.ai.utils.Pair;
import com.ib.client.Bar;
import com.ib.enumerations.DecisionEnum;
import com.ib.strategy.BaseStrategy;
import com.ib.utils.AIDataSetUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Slf4j
public class Tesla369BoostedTreeStrategy extends BaseStrategy {
    private static final DecimalFormat df5 = new DecimalFormat("#.#####");
    private static final DecimalFormat df6 = new DecimalFormat("+#.#####%;-#");
    private static final DecimalFormat df7 = new DecimalFormat("+#.##");

    double teslaValue9 = 0.484827661;
    double teslaValue6 = 0.32321844;
    double teslaValue3 = 0.16160922;

    BaseModel model;

    public Tesla369BoostedTreeStrategy(String currency) {
        Predicate<Double> filterMinute = val -> val == 20 || val == 40 || val == 0;
        try {
            model = new GradientBoostedTreesImpl(AIDataSetUtils.getTrainingFileByCurrency(currency), "", "", List.of(new Pair<>("Minute", filterMinute)));
            model.train();
            model.test();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public DecisionEnum execute(ArrayList<Bar> barInput) throws InterruptedException {

        DecisionEnum decision = DecisionEnum.NO;

        if (barInput.size() > 1) {

            log.info("Bar size: {}", barInput.size());
            log.info("Modulo: {}", barInput.size() % 3);

            double tesla3 = 0.0;
            double tesla6 = 0.0;
            double tesla9 = 0.0;

            int barInputSize1 = barInput.size() - 1;
            int barInputSize2 = barInput.size() - 2;

            log.info("Volume, barInput1 {}, barInput2 {} ", barInput.get(barInputSize1).volume(), barInput.get(barInputSize2).volume());

            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) > 41) {

                int div3 = barInput.size() / 3;

                log.info("div3 {} ", div3);

                log.info("loop3Div3 @ 0 {}", barInput.get(0).wap());
                log.info("loop3Div3 @ 1 {}", barInput.get(1).wap());

                TimeUnit.SECONDS.sleep(2);
                int loop3Div3 = (div3 * 3);
                log.info("loop3Div3 {} ", loop3Div3);

                TimeUnit.SECONDS.sleep(2);

                for (int loop3 = barInput.size() - 1; loop3 >= (barInput.size() - div3); loop3--) {
                    log.debug("barInput.get(loop3).wap()=  " + barInput.get(loop3).wap() + "   Time:  " + barInput.get(loop3).time());
                    tesla9 = barInput.get(loop3).wap() + tesla9;
                }
                tesla9 = (tesla9 / div3) * teslaValue9;   //0.9


                for (int loop2 = ((barInput.size() - 1) - div3); loop2 >= (barInput.size() - (2 * div3)); loop2--) {
                    log.debug("barInput.get(loop2).wap()=  " + barInput.get(loop2).wap() + "   Time:  " + barInput.get(loop2).time());
                    tesla6 = barInput.get(loop2).wap() + tesla6;
                }
                tesla6 = (tesla6 / div3) * teslaValue6; // 0.6

                for (int loop1 = ((barInput.size() - 1) - (2 * div3)); loop1 >= (barInput.size() - (3 * div3)); loop1--) {
                    log.info("barInput.get(loop1).wap()=  " + barInput.get(loop1).wap() + "   Time:  " + barInput.get(loop1).time());
                    tesla3 = barInput.get(loop1).wap() + tesla3;
                }
                tesla3 = (tesla3 / div3) * teslaValue3;  ///0.3
                log.info("tesla3=  " + tesla3);


                log.info("Tesla 3: {}", df5.format(tesla3));
                log.info("Tesla 6: {}", df5.format(tesla6));
                log.info("Tesla 9: {}", df5.format(tesla9));

                TimeUnit.SECONDS.sleep(2);

                if (tesla3 < (tesla9 - tesla6) && tesla9 > (tesla3 + tesla6)) {

                    if (tesla3 <= tesla6 && tesla6 <= tesla9) {
                        decision = DecisionEnum.BUY;
                    }
                    if (tesla3 >= tesla6 && tesla6 >= tesla9) {
                        decision = DecisionEnum.SELL;
                    }
                }

                if (tesla3 > (tesla9 - tesla6) && tesla9 < (tesla3 + tesla6)) {
                    log.info("Tesla369, Condition not met for opening a position");
                    decision = DecisionEnum.NO;
                }
            }

            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) < 41) {
                decision = DecisionEnum.NO;
                log.info("Volume Condition not met for opening a position");
            }

            if (this.model != null && decision != DecisionEnum.NO) {
                var bar = barInput.get(0);
                log.info("Parsing Time {}", bar.time());
                LocalDateTime localDateTime = LocalDateTime.parse(bar.time());
                String newDecision = this.model.predict(bar.open(), bar.high(), bar.low(), bar.close(), bar.wap(), bar.volume(), bar.count(), localDateTime.getMinute(), tesla3, tesla6, tesla9, decision.name());
                if (!newDecision.equalsIgnoreCase("EXECUTE")) {
                    decision = DecisionEnum.NO;
                }
            }
        }
        return decision;
    }
}

