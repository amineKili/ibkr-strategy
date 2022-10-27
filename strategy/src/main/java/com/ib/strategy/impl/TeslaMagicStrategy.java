package com.ib.strategy.impl;

import com.ib.client.Bar;
import com.ib.ib.LiveBar;
import com.ib.strategy.BaseStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Different strategies
 * Tesla369 strategy
 */

public class TeslaMagicStrategy extends BaseStrategy {

    private static final DecimalFormat df5 = new DecimalFormat("#.#####");
    private static final DecimalFormat df6 = new DecimalFormat("+#.#####%;-#");
    private static final DecimalFormat df7 = new DecimalFormat("+#.##");

    String decision = "";
    String candleStickDecision = "NO";

    int sizeSimpleBar;
    double pivotPoint0, pivotPoint1;
    double slope;
    double momentum = 1.0;

    double tesla3, tesla6, tesla9;

    int bar1;
    int bar2;
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

    double harami3, harami6, harami9;
    double haramiValue9 = 1;
    double haramiValue6 = 1;
    double haramiValue3 = 1;
    int volumeLimit = 41;

    double head9, tail9, openBody9, closeBody9, body9;
    double head6, tail6, openBody6, closeBody6, body6;
    double head3, tail3, openBody3, closeBody3, body3;

    public String execute(ArrayList<Bar> barInput) throws InterruptedException {

        if (barInput.size() > 1) {
            System.out.println("===================         Tesla369DecisionDeterminer        =============================================>");
            System.out.println("===================         Here's ArrayList<Bar> barInput    =============================================>");
            System.out.println("===================         Tesla369DecisionDeterminer        =============================================>");

            System.out.println("===Bar size:  =>  " + barInput.size());
            System.out.println("===Bar size:  =>  " + barInput.size());
            System.out.println("===Bar size:  =>  " + barInput.size());
            System.out.println("===Modulo  :  =>  " + barInput.size() % 3);

            tesla3 = 0.0;
            tesla6 = 0.0;
            tesla9 = 0.0;
            barInputSize1 = barInput.size() - 1;
            barInputSize2 = barInput.size() - 2;

            moduloBar = barInput.size() % 3;


            System.out.println("Volume ---check =:    " + barInput.get(barInputSize1).volume());
            System.out.println("Volume ---check =:    " + barInput.get(barInputSize2).volume());


            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) > 41) {

                div3 = barInput.size() / 3;

                System.out.println("div3=  " + div3);

                System.out.println("loop3Div3 @ 0=  " + barInput.get(0).wap());
                System.out.println("loop3Div3 @ 1=  " + barInput.get(1).wap());

                TimeUnit.SECONDS.sleep(2);
                loop3Div3 = (div3 * 3);
                System.out.println("loop3Div3=  " + loop3Div3);


                TimeUnit.SECONDS.sleep(2);

                for (int loop3 = barInput.size() - 1; loop3 >= (barInput.size() - div3); loop3--) {
                    System.out.println("loop3=  " + loop3);
                    System.out.println("barInput.get(loop3).wap()=  " + barInput.get(loop3).wap() + "   Time:  " + barInput.get(loop3).time());
                    tesla9 = barInput.get(loop3).wap() + tesla9;
                }
                tesla9 = (tesla9 / div3) * teslaValue9;   //0.9
                System.out.println("tesla9=  " + tesla9);


                loop2Div3 = barInput.size() - (2 * div3);
                for (int loop2 = ((barInput.size() - 1) - div3); loop2 >= (barInput.size() - (2 * div3)); loop2--) {
                    System.out.println("loop2=  " + loop2);
                    System.out.println("barInput.get(loop2).wap()=  " + barInput.get(loop2).wap() + "   Time:  " + barInput.get(loop2).time());
                    tesla6 = barInput.get(loop2).wap() + tesla6;
                }
                tesla6 = (tesla6 / div3) * teslaValue6; // 0.6 
                System.out.println("tesla6=  " + tesla6);


                loop1Div3 = barInput.size() - (3 * div3);
                for (int loop1 = ((barInput.size() - 1) - (2 * div3)); loop1 >= (barInput.size() - (3 * div3)); loop1--) {
                    System.out.println("loop1=  " + loop1);
                    System.out.println("barInput.get(loop1).wap()=  " + barInput.get(loop1).wap() + "   Time:  " + barInput.get(loop1).time());
                    tesla3 = barInput.get(loop1).wap() + tesla3;
                }
                tesla3 = (tesla3 / div3) * teslaValue3;  ///0.3
                System.out.println("tesla3=  " + tesla3);


                System.out.println("Tesla-3:  " + df5.format(tesla3));
                System.out.println("Tesla-6:  " + df5.format(tesla6));
                System.out.println("Tesla-9:  " + df5.format(tesla9));


                TimeUnit.SECONDS.sleep(2);


                System.out.println("tesla3=  " + df5.format(tesla3) + "   ___ PRIOR (raw/Original): " + df5.format(tesla3 / 0.3));
                System.out.println("tesla6=  " + df5.format(tesla6) + "   ___ PRIOR (raw/Original): " + df5.format(tesla6 / 0.6));
                System.out.println("tesla9=  " + df5.format(tesla9) + "   ___ PRIOR (raw/Original): " + df5.format(tesla9 / 0.9));


                if (tesla3 < (tesla9 - tesla6) && tesla9 > (tesla3 + tesla6)) {

                    if (tesla3 <= tesla6 && tesla6 <= tesla9) {
                        decision = "BUY";
                    }
                    if (tesla3 >= tesla6 && tesla6 >= tesla9) {
                        decision = "SELL";
                    }
                }

                if (tesla3 > (tesla9 - tesla6) && tesla9 < (tesla3 + tesla6)) {

                    System.out.println("    *********************  << tesla 3 6 9 >> Condition not met for opening a position     ********        ");


                }

            }


            if ((barInput.get(barInputSize1).volume() + barInput.get(barInputSize2).volume()) < 41) {
                decision = "DO NOTHING";

                System.out.println("    ********" + "*************  Volume Condition not met for opening a position     ********        ");

            }

        }

        return decision;
    }

}
