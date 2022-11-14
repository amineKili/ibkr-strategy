package com.ib.utils;

import com.aminekili.utils.LiveContract;

import java.util.HashMap;
import java.util.Map;

public class LiveContractUtils {
    private static Map<String, LiveContract> liveContractMap = new HashMap<>();

    public static LiveContract getLiveContract(String symbol) {
        return liveContractMap.get(symbol);
    }

    public static void addLiveContract(String symbol, LiveContract liveContract) {
        liveContractMap.put(symbol, liveContract);
    }

    public static void removeLiveContract(String symbol) {
        liveContractMap.remove(symbol);
    }

    public static void clearLiveContractMap() {
        liveContractMap.clear();
    }

    public static Map<String, LiveContract> getLiveContractMap() {
        return liveContractMap;
    }

    public static void setLiveContractMap(Map<String, LiveContract> liveContractMap) {
        LiveContractUtils.liveContractMap = liveContractMap;
    }

    public static void printLiveContractMap() {
        liveContractMap.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v));
    }

    static{
        Map<String, LiveContract> portfolioVault = new HashMap<>();
        portfolioVault.put("MXP", new LiveContract(ContractUtils.MXP_FXFutureContract(), "MXP", 491421951, 1852.00, 1481.00, 19.00, 11.00, 3000, 19000));
        portfolioVault.put("NZD", new LiveContract(ContractUtils.NZD_FXFutureContract(), "NZD", 496647000, 2446.00, 1957.00, 23.00, 16.00, 3001, 19001));
        portfolioVault.put("CAD", new LiveContract(ContractUtils.CAD_FXFutureContract(), "CAD", 259910553, 3145.00, 2516.00, 23.00, 16.00, 3002, 19002));
        portfolioVault.put("ZAR", new LiveContract(ContractUtils.ZAR_FXFutureContract(), "ZAR", 455429137, 3180.00, 2544.00, 24.00, 17.00, 3003, 19003));
        portfolioVault.put("AUD", new LiveContract(ContractUtils.AUD_FXFutureContract(), "AUD", 299701779, 3383.00, 2707.00, 33.00, 22.00, 3004, 19004));
        portfolioVault.put("JPY", new LiveContract(ContractUtils.JPY_FXFutureContract(), "JPY", 299701836, 3438.00, 2751.00, 30.00, 22.00, 3005, 19005));
        portfolioVault.put("CHF", new LiveContract(ContractUtils.CHF_FXFutureContract(), "CHF", 299701893, 4825.00, 3860.00, 36.00, 28.00, 3008, 19008));
        portfolioVault.put("GBP", new LiveContract(ContractUtils.GBP_FXFutureContract(), "GBP", 299701782, 4404.00, 3524.00, 41.00, 30.00, 3006, 19006));
        portfolioVault.put("EUR", new LiveContract(ContractUtils.EUR_FXFutureContract(), "EUR", 299701833, 4637.00, 3710.00, 36.00, 28.00, 3007, 19007));
    }
}
