package com.ib.enumerations;

public enum DecisionEnum {
    BUY, SELL, NO;


    public static DecisionEnum of(String decision) {
        if (decision.equalsIgnoreCase("sell")) {
            return DecisionEnum.SELL;
        }
        if (decision.equalsIgnoreCase("buy")) {
            return DecisionEnum.BUY;
        }
        return DecisionEnum.NO;
    }

    public String toAction() {
        return this.name();
    }
}
