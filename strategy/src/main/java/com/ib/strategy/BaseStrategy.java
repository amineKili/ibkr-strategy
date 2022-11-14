package com.ib.strategy;

import com.ib.client.Bar;
import com.ib.enumerations.DecisionEnum;

import java.util.ArrayList;

public abstract class BaseStrategy {
    public abstract DecisionEnum execute(ArrayList<Bar> barInput) throws InterruptedException;

    public abstract DecisionEnum execute(String symbol, ArrayList<Bar> barInput) throws InterruptedException;

}
