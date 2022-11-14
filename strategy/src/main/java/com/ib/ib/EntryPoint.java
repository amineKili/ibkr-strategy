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
import com.ib.strategy.impl.Tesla369BoostedTreeStrategy;
import com.ib.strategy.impl.Tesla369Strategy;
import com.ib.utils.ContractUtils;
import com.ib.utils.LiveContractUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EntryPoint {

    public static void main(String[] args) throws InterruptedException {

        EWrapperImplementation wrapper = new EWrapperImplementation();
        HashMapSynchronizer hashMapSynchronizer = new HashMapSynchronizer();
        BaseStrategy strategy = new Tesla369BoostedTreeStrategy();

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


        List<LiveContract> activeContracts = new ArrayList<>();
        activeContracts.add(LiveContractUtils.getLiveContract("MXP"));
        activeContracts.add(LiveContractUtils.getLiveContract("MXN"));
        activeContracts.add(LiveContractUtils.getLiveContract("AUD"));
        activeContracts.add(LiveContractUtils.getLiveContract("CAD"));
        activeContracts.add(LiveContractUtils.getLiveContract("ZAR"));
        activeContracts.add(LiveContractUtils.getLiveContract("CHF"));
        activeContracts.add(LiveContractUtils.getLiveContract("GBP"));
        activeContracts.add(LiveContractUtils.getLiveContract("EUR"));
        activeContracts.add(LiveContractUtils.getLiveContract("JPY"));
        activeContracts.add(LiveContractUtils.getLiveContract("NZD"));

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
                // TODO: adjust this work in parallel with other contracts
                // TODO: react each 20 minutes
                for (LiveContract activeContract : activeContracts) {
                    Contract currentContract = activeContract.getContract();
                    String currentSymbolFUT = activeContract.getSymbol();
                    int reqID_HistoricalData = activeContract.getHistData_ReqID();

                    Thread.sleep(10000);

                    log.info("Checking if position for *=> " + currentSymbolFUT + " equals zero then OPN ~> However current open: " + PositionDisclose.getPositionDisclose(doneTreeMap, currentSymbolFUT));

                    if (PositionDisclose.getPositionDisclose(doneTreeMap, currentSymbolFUT) == 0.00) {

                        historicalDataMainOPERATIONS(reqID_HistoricalData, wrapper.getClient(), currentContract);
                        TimeUnit.SECONDS.sleep(10);

                        if (wrapper.getBarsHistDataArrayList().size() != 0) {
                            TimeUnit.SECONDS.sleep(8);
                            ArrayList<Bar> incomingBarInput = wrapper.getBarsHistDataArrayList();
                            DecisionEnum determinedDecision = strategy.execute(activeContract.getSymbol(), incomingBarInput);
                            TimeUnit.SECONDS.sleep(6);

                            if (determinedDecision == DecisionEnum.BUY || determinedDecision == DecisionEnum.SELL) {
                                orderPlacer.placeOrder(currentContract, OrderTypes.MarketOrder(determinedDecision.toAction(), 1));
                            }
                            incomingBarInput.clear();
                        }
                    }
                }

                log.info("Open completed");


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
