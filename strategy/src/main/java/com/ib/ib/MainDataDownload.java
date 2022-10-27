/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */
package com.ib.ib;

import com.ib.client.*;
import com.ib.helpers.HashMapSynchronizer;
import com.ib.helpers.PositionDisclose;
import com.ib.strategy.BaseStrategy;
import com.ib.strategy.impl.TeslaMagicStrategy;
import utils.ContractUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/*
 * Class used to download historical data and contract paraments at contract changes every quarter, at expiration.
 * Disregard this class.
 */
public class MainDataDownload {

    public static void main(String[] args) throws InterruptedException {

        EWrapperImplementation wrapper = new EWrapperImplementation();
        HashMapSynchronizer hashMapSynchronizer = new HashMapSynchronizer();
        BaseStrategy teslaStrategy = new TeslaMagicStrategy();

        final EClientSocket m_client = wrapper.getClient();
        final EReaderSignal m_signal = wrapper.getSignal();
        //! [connect]
        m_client.eConnect("127.0.0.1", 7497, 0);
        //! [connect]
        //! [ereader]
        final EReader reader = new EReader(m_client, m_signal);

        reader.start();
        new Thread(() -> {
            while (m_client.isConnected()) {
                m_signal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }).start();
        sleep(1000);

        contractOperations(wrapper.getClient());
        TimeUnit.MINUTES.sleep(3);

        LiveContract liveContract00 = new LiveContract(ContractUtils.MXP_FXFutureContract(), "MXP", 491421951, 1852.00, 1481.00, 19.00, 11.00, 3000, 19000);
        LiveContract liveContract01 = new LiveContract(ContractUtils.NZD_FXFutureContract(), "NZD", 496647000, 2446.00, 1957.00, 23.00, 16.00, 3001, 19001);
        LiveContract liveContract02 = new LiveContract(ContractUtils.CAD_FXFutureContract(), "CAD", 259910553, 3145.00, 2516.00, 23.00, 16.00, 3002, 19002);
        LiveContract liveContract03 = new LiveContract(ContractUtils.ZAR_FXFutureContract(), "ZAR", 455429137, 3180.00, 2544.00, 24.00, 17.00, 3003, 19003);
        LiveContract liveContract04 = new LiveContract(ContractUtils.AUD_FXFutureContract(), "AUD", 299701779, 3383.00, 2707.00, 33.00, 22.00, 3004, 19004);
        LiveContract liveContract05 = new LiveContract(ContractUtils.JPY_FXFutureContract(), "JPY", 299701836, 3438.00, 2751.00, 30.00, 22.00, 3005, 19005);
        LiveContract liveContract06 = new LiveContract(ContractUtils.GBP_FXFutureContract(), "GBP", 299701782, 4404.00, 3524.00, 41.00, 30.00, 3006, 19006);
        LiveContract liveContract07 = new LiveContract(ContractUtils.EUR_FXFutureContract(), "EUR", 299701833, 4637.00, 3710.00, 36.00, 28.00, 3007, 19007);
        LiveContract liveContract08 = new LiveContract(ContractUtils.CHF_FXFutureContract(), "CHF", 299701893, 4825.00, 3860.00, 36.00, 28.00, 3008, 19008);

        Map<String, LiveContract> portfolioVault = new HashMap<>();
        portfolioVault.put("MXP", liveContract00);
        portfolioVault.put("NZD", liveContract01);
        portfolioVault.put("AUD", liveContract02);
        portfolioVault.put("CAD", liveContract03);
        portfolioVault.put("ZAR", liveContract04);
        portfolioVault.put("JPY", liveContract05);
        portfolioVault.put("CHF", liveContract06);
        portfolioVault.put("GBP", liveContract07);
        portfolioVault.put("EUR", liveContract08);

        ArrayList<LiveContract> activeContracts = new ArrayList<>();
        activeContracts.add(portfolioVault.put("MXP", liveContract00));
        activeContracts.add(portfolioVault.put("NZD", liveContract01));
        activeContracts.add(portfolioVault.put("AUD", liveContract02));
        activeContracts.add(portfolioVault.put("CAD", liveContract03));
        activeContracts.add(portfolioVault.put("ZAR", liveContract04));
        activeContracts.add(portfolioVault.put("JPY", liveContract05));
        activeContracts.add(portfolioVault.put("CHF", liveContract06));
        activeContracts.add(portfolioVault.put("GBP", liveContract07));
        activeContracts.add(portfolioVault.put("EUR", liveContract08));


        historicalDataMainOPERATIONS_Clean(501, wrapper.getClient(), ContractUtils.NZD_FXFutureContract());
        TimeUnit.SECONDS.sleep(30);
        historicalDataMainOPERATIONS_Clean(502, wrapper.getClient(), ContractUtils.CAD_FXFutureContract());
        TimeUnit.SECONDS.sleep(30);
//historicalDataMainOPERATIONS_Clean(503,wrapper.getClient(),Contracts_202212_DEC.ZAR_FXFutureContract());
        historicalDataMainOPERATIONS_Clean(504, wrapper.getClient(), ContractUtils.AUD_FXFutureContract());
        TimeUnit.SECONDS.sleep(30);
        historicalDataMainOPERATIONS_Clean(505, wrapper.getClient(), ContractUtils.JPY_FXFutureContract());
        TimeUnit.SECONDS.sleep(30);
        historicalDataMainOPERATIONS_Clean(506, wrapper.getClient(), ContractUtils.GBP_FXFutureContract());
        TimeUnit.SECONDS.sleep(30);
        historicalDataMainOPERATIONS_Clean(507, wrapper.getClient(), ContractUtils.EUR_FXFutureContract());
        TimeUnit.SECONDS.sleep(30);
        historicalDataMainOPERATIONS_Clean(508, wrapper.getClient(), ContractUtils.CHF_FXFutureContract());

        TimeUnit.MINUTES.sleep(1);
        TimeUnit.MINUTES.sleep(1);
        TimeUnit.MINUTES.sleep(1);


        TimeUnit.MINUTES.sleep(3);

        System.out.println("  ");

        marketDataType(wrapper.getClient());

        OrderPlacer OOP = new OrderPlacer(wrapper.getClient(), wrapper.getCurrentOrderId());
        try {
            while (true) {

                positionStatusOperations(wrapper.getClient());
                var positions = wrapper.getPositions();
                System.out.println("Positions SIZE  ==> : " + positions.size());
                System.out.println("Positions ITSELF    : " + positions);

                System.out.println("  ");

                Map<String, Double> outgoingPositions = wrapper.getPositions();
                TreeMap<String, Double> doneTreeMap = hashMapSynchronizer.syncMethod(outgoingPositions);

                System.out.println("|------- DONE ----  " + doneTreeMap);

                TimeUnit.SECONDS.sleep(4);

                System.out.println("==============================================================");

                System.out.println("Abs # of contracts [//open//]=   " + PositionDisclose.getAbsolutePositionDisclose(doneTreeMap));

                System.out.println("==============================================================");


                for (LiveContract activeContract : activeContracts) {
                    Contract currentContract = activeContract.getContract();
                    String currentSymbolFUT = activeContract.getSymbol();
                    int reqID_HistoricalData = activeContract.getHistData_ReqID();

                    Thread.sleep(10000);

                    System.out.println("** Checking if position for *=> " + currentSymbolFUT + " equals zero then OPN ~> However current open: " + PositionDisclose.getPositionDisclose(doneTreeMap, currentSymbolFUT));

                    if (PositionDisclose.getPositionDisclose(doneTreeMap, currentSymbolFUT) == 0.00) {

                        historicalDataMainOPERATIONS(reqID_HistoricalData, wrapper.getClient(), currentContract);
                        TimeUnit.SECONDS.sleep(10);

                        if (wrapper.getBarsHistDataArrayList().size() != 0) {
                            TimeUnit.SECONDS.sleep(8);
                            ArrayList<Bar> incomingBarInput = wrapper.getBarsHistDataArrayList();
                            String determinedDecision = teslaStrategy.execute(incomingBarInput);
                            TimeUnit.SECONDS.sleep(6);

                            System.out.println(" ");

                            if (determinedDecision.equals("BUY") || determinedDecision.equals("SELL")) {
                                OOP.placeOrder(currentContract, OrderTypes.MarketOrder(determinedDecision, 1));
                            }

                            incomingBarInput.clear();

                        }
                    }
                }

                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.println("  === Open completed === Open completed === Open completed === Open completed === ");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" End == End == End == End == End == End == End == End == End == End == End == End == End == End ");

            TimeUnit.SECONDS.sleep(5);
            m_client.eDisconnect();
        }

    }

    //
    private static void updatePortfolioOperations(EClientSocket client) throws InterruptedException {
        client.reqAccountUpdates(true, "DU999999");
        //! [reqaaccountupdates]
        sleep(15000);
        //! [cancelaaccountupdates]
        client.reqAccountUpdates(false, "DU999999");
        //! [cancelaaccountupdates]
        sleep(1000);
    }

    private static void positionStatusOperations(EClientSocket client) throws InterruptedException {
        client.reqPositions();
        TimeUnit.SECONDS.sleep(5);
        client.cancelPositions();
    }

    public static void historicalDataMainOPERATIONS(int reqID_HistData, EClientSocket client, Contract FXFuture00000) throws InterruptedException {
        System.out.println("Futures Historical Data Main-- OPERATION    --->  historicalDataMainOPERATIONS    ***   ---    " + FXFuture00000.description() + "  ");

        Date nowVerifierToday = new Date();
        SimpleDateFormat timeHistDf12 = new SimpleDateFormat("MM-dd-yy hh:mm a");
        SimpleDateFormat timeHistDf24 = new SimpleDateFormat("MM-dd-yy HH:mm");
        timeHistDf12.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        timeHistDf24.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String estNY12 = timeHistDf12.format(nowVerifierToday);
        String estNY24 = timeHistDf24.format(nowVerifierToday);

        timeHistDf12.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        timeHistDf24.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        String cstHour12 = timeHistDf12.format(nowVerifierToday);
        String cstHour24 = timeHistDf24.format(nowVerifierToday);

        System.out.println("Date in New York Timezone (EST) : " + estNY12 + "   ===> " + estNY24);
        System.out.println("Date in Houston  Timezone (CST) : " + cstHour12 + "   ===> " + cstHour24);
        System.out.println("=====================================================================================================");

        List<TagValue> chartOptions = new ArrayList<>();

        client.reqHistoricalData(reqID_HistData, FXFuture00000, "", "1 M", "5 mins", "TRADES", 0, 1, false, chartOptions);

        sleep(4000);
        client.cancelHistoricalData(reqID_HistData);


    }

    // TODO: clean
    public static void historicalDataMainOPERATIONS_Clean(int reqID_HistData, EClientSocket client, Contract FXFuture00000) throws InterruptedException {

        System.out.println("Futures Historical Data Main-- OPERATION    --->  historicalDataMainOPERATIONS    ***   ---    " + FXFuture00000.description() + "  ");

        Date nowVerifier_Today = new Date();
        SimpleDateFormat timeHistDf12 = new SimpleDateFormat("MM-dd-yy hh:mm a");
        SimpleDateFormat timeHistDf24 = new SimpleDateFormat("MM-dd-yy HH:mm");
        timeHistDf12.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        timeHistDf24.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String estNY12 = timeHistDf12.format(nowVerifier_Today);
        String estNY24 = timeHistDf24.format(nowVerifier_Today);

        timeHistDf12.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        timeHistDf24.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        String cstHour12 = timeHistDf12.format(nowVerifier_Today);
        String cstHour24 = timeHistDf24.format(nowVerifier_Today);

        System.out.println("Date in New York Timezone (EST) : " + estNY12 + "   ===> " + estNY24);
        System.out.println("Date in Houston  Timezone (CST) : " + cstHour12 + "   ===> " + cstHour24);
        System.out.println("=====================================================================================================");

        List<TagValue> chartOptions = new ArrayList<>();

        client.reqHistoricalData(reqID_HistData, FXFuture00000, "", "1 M", "5 mins", "TRADES", 0, 1, false, chartOptions);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.MONTH, -3);
        SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
        String formatted = form.format(cal.getTime());
        client.reqHistoricalData(4001, ContractUtils.AUD_FXFutureContract(), formatted, "1 M", "5 mins", "TRADES", 0, 1, false, null);
        client.reqHistoricalData(4002, ContractUtils.EUR_FXFutureContract(), formatted, "1 M", "5 mins", "TRADES", 0, 1, false, null);
        client.reqHistoricalData(4003, ContractUtils.CHF_FXFutureContract(), formatted, "1 M", "5 mins", "TRADES", 0, 1, false, null);
        sleep(2000);
        /*
         * Canceling historical data requests **
         */
        client.cancelHistoricalData(4001);
        client.cancelHistoricalData(4002);
        client.cancelHistoricalData(4003);

        sleep(4000);
        client.cancelHistoricalData(reqID_HistData);


    }

    private static void marketDataType(EClientSocket client) {
        client.reqMarketDataType(1);
    }

    private static void contractOperations(EClientSocket client) {
        client.reqContractDetails(211, ContractUtils.FXFuture("AUD", "USD", "20221219"));
        client.reqContractDetails(212, ContractUtils.FXFuture("CHF", "USD", "20221219"));
        client.reqContractDetails(213, ContractUtils.FXFuture("EUR", "USD", "20221219"));
        client.reqContractDetails(214, ContractUtils.FXFuture("GBP", "USD", "20221219"));
        client.reqContractDetails(215, ContractUtils.FXFuture("JPY", "USD", "20221219"));
        client.reqContractDetails(216, ContractUtils.FXFuture("MXP", "USD", "20221219"));
        client.reqContractDetails(217, ContractUtils.FXFuture("NZD", "USD", "20221219"));
        client.reqContractDetails(218, ContractUtils.FXFuture("BRE", "USD", "20221219"));
        client.reqContractDetails(219, ContractUtils.FXFuture("ZAR", "USD", "20221219"));
        client.reqContractDetails(220, ContractUtils.FXFuture("RUR", "USD", "202206"));
        client.reqContractDetails(221, ContractUtils.FXFuture("CAD", "USD", "202206"));
    }

}
