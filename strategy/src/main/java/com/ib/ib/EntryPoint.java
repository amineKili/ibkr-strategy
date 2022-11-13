package com.ib.ib;

/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */


import com.aminekili.ewrapper.EWrapperImplementation;
import com.aminekili.utils.LiveContract;
import com.aminekili.utils.OrderPlacer;
import com.aminekili.utils.OrderTypes;
import com.ib.client.*;
import com.ib.enumerations.DecisionEnum;
import com.ib.helpers.HashMapSynchronizer;
import com.ib.helpers.PositionDisclose;
import com.ib.strategy.BaseStrategy;
import com.ib.strategy.impl.Tesla369Strategy;
import com.ib.utils.ContractUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class EntryPoint {


    public static void main(String[] args) throws InterruptedException {

        EWrapperImplementation wrapper = new EWrapperImplementation();
        HashMapSynchronizer hashMapSynchronizer = new HashMapSynchronizer();
        BaseStrategy strategy = new Tesla369Strategy();

        final EClientSocket m_client = wrapper.getClient();
        final EReaderSignal m_signal = wrapper.getSignal();
        //! [connect]
        m_client.eConnect("127.0.0.1", 7497, 0);
        //! [connect]
        //! [ereader]
        final EReader reader = new EReader(m_client, m_signal);

        //  Ports
        //  7497  paper-trading account - TWS
        //  7496  live account - TWS
        //  4002  paper-trading account - IB Gateway
        //  4000  live account  - IB Gateway


        reader.start();
        //An additional thread is created in this program design to empty the messaging queue
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
        //! [ereader]
        // A pause to give the application time to establish the connection
        // In a production application, it would be best to wait for callbacks to confirm the connection is complete
        Thread.sleep(1000);


        var liveContract00 = new LiveContract(ContractUtils.MXP_FXFutureContract(), "MXP", 491421951, 1852.00, 1481.00, 19.00, 11.00, 3000, 19000);
        var liveContract01 = new LiveContract(ContractUtils.NZD_FXFutureContract(), "NZD", 496647000, 2446.00, 1957.00, 23.00, 16.00, 3001, 19001);
        var liveContract02 = new LiveContract(ContractUtils.CAD_FXFutureContract(), "CAD", 259910553, 3145.00, 2516.00, 23.00, 16.00, 3002, 19002);
        var liveContract03 = new LiveContract(ContractUtils.ZAR_FXFutureContract(), "ZAR", 455429137, 3180.00, 2544.00, 24.00, 17.00, 3003, 19003);
        var liveContract04 = new LiveContract(ContractUtils.AUD_FXFutureContract(), "AUD", 299701779, 3383.00, 2707.00, 33.00, 22.00, 3004, 19004);
        var liveContract05 = new LiveContract(ContractUtils.JPY_FXFutureContract(), "JPY", 299701836, 3438.00, 2751.00, 30.00, 22.00, 3005, 19005);
        var liveContract06 = new LiveContract(ContractUtils.GBP_FXFutureContract(), "GBP", 299701782, 4404.00, 3524.00, 41.00, 30.00, 3006, 19006);
        var liveContract07 = new LiveContract(ContractUtils.EUR_FXFutureContract(), "EUR", 299701833, 4637.00, 3710.00, 36.00, 28.00, 3007, 19007);
        var liveContract08 = new LiveContract(ContractUtils.CHF_FXFutureContract(), "CHF", 299701893, 4825.00, 3860.00, 36.00, 28.00, 3008, 19008);


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

        List<LiveContract> activeContracts = new ArrayList<>();
        activeContracts.add(portfolioVault.put("MXP", liveContract00));
        activeContracts.add(portfolioVault.put("NZD", liveContract01));
        activeContracts.add(portfolioVault.put("AUD", liveContract02));
        activeContracts.add(portfolioVault.put("CAD", liveContract03));
        activeContracts.add(portfolioVault.put("ZAR", liveContract04));
        activeContracts.add(portfolioVault.put("JPY", liveContract05));
        activeContracts.add(portfolioVault.put("CHF", liveContract06));
        activeContracts.add(portfolioVault.put("GBP", liveContract07));
        activeContracts.add(portfolioVault.put("EUR", liveContract08));

        marketDataType(wrapper.getClient());

        OrderPlacer orderPlacer = new OrderPlacer(wrapper.getClient(), wrapper.getCurrentOrderId());

        try {
            while (true) {

                positionStatusOperations(wrapper.getClient());
                var currentPositions = wrapper.getPositions();
                System.out.println("Positions SIZE  ==> : " + currentPositions.size());
                System.out.println("Positions ITSELF    : " + currentPositions);

                Map<String, Double> outgoingPositions = wrapper.getPositions();
                TreeMap<String, Double> doneTreeMap = hashMapSynchronizer.syncMethod(outgoingPositions);

                System.out.println("------- DONE ----  " + doneTreeMap);

                TimeUnit.SECONDS.sleep(4);

                System.out.println("Abs # of contracts [//open//]=   " + PositionDisclose.getAbsolutePositionDisclose(doneTreeMap));

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
                            DecisionEnum determinedDecision = strategy.execute(incomingBarInput);
                            TimeUnit.SECONDS.sleep(6);

                            // TODO: implement both strategies test
                            if (determinedDecision == DecisionEnum.BUY || determinedDecision == DecisionEnum.SELL) {
                                orderPlacer.placeOrder(currentContract, OrderTypes.MarketOrder(determinedDecision.toAction(), 1));
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
            System.out.println("Exception in main: " + e.getMessage());
            e.printStackTrace();
            TimeUnit.SECONDS.sleep(5);
            m_client.eDisconnect();
        }
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
        String ESTNY12 = timeHistDf12.format(nowVerifierToday);
        String EST_NY24 = timeHistDf24.format(nowVerifierToday);

        timeHistDf12.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        timeHistDf24.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        String CSTHour12 = timeHistDf12.format(nowVerifierToday);
        String CSTHour24 = timeHistDf24.format(nowVerifierToday);

        System.out.println("Date in New York Timezone (EST) : " + ESTNY12 + "   ===> " + EST_NY24);
        System.out.println("Date in Houston  Timezone (CST) : " + CSTHour12 + "   ===> " + CSTHour24);
        System.out.println("=====================================================================================================");

        List<TagValue> chartOptions = new ArrayList<>();

        client.reqHistoricalData(reqID_HistData, FXFuture00000, "", "3600 s", "5 mins", "TRADES", 0, 1, false, chartOptions);

        Thread.sleep(4000);
        client.cancelHistoricalData(reqID_HistData);


    }


    private static void marketDataType(EClientSocket client) {
        //! [reqmarketdatatype]
        /*
         * * Switch to live (1) frozen (2) delayed (3) or delayed frozen (4)**
         */
        client.reqMarketDataType(1);
        //! [reqmarketdatatype]

    }

    private static void contractOperations(EClientSocket client) {
        //! [reqcontractdetails]
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

    private static void getTickByTickOperations(EClientSocket client, int requestID, Contract tickSide01234) throws InterruptedException {
        /*
         * * Requesting tick-by-tick data (only refresh) **
         */
        client.reqTickByTickData(requestID, tickSide01234, "Last", 0, false);
        TimeUnit.SECONDS.sleep(5);

        client.cancelTickByTickData(requestID);
    }
}
