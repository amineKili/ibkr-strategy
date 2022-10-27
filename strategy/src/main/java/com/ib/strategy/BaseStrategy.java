package com.ib.strategy;

import com.ib.client.Bar;

import java.util.ArrayList;

public abstract class BaseStrategy {
    public abstract String execute(ArrayList<Bar> barInput) throws InterruptedException;
}
