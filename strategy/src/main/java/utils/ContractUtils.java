package utils;

import com.ib.client.Contract;

public class ContractUtils {
    public static Contract AUD_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("AUD");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(299701779);
        return contract;
    }

    public static Contract CAD_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("CAD");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20211214");
        contract.conid(259910553);
        return contract;
    }

    public static Contract CHF_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("CHF");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(299701893);
        return contract;
    }

    public static Contract EUR_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("EUR");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(299701833);
        return contract;
    }

    public static Contract GBP_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("GBP");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(299701782);
        return contract;
    }

    public static Contract JPY_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("JPY");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(299701836);
        return contract;
    }

    public static Contract NZD_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("NZD");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(496647000);
        return contract;
    }

    public static Contract ZAR_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("ZAR");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(455429137);
        return contract;
    }


    public static Contract MXP_FXFutureContract() {
        Contract contract = new Contract();
        contract.symbol("MXP");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20221219");
        contract.conid(491421951);
        return contract;
    }


    Contract getContract(String symbol) {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth("20211213");  //
        return contract;
    }

    Contract getContract(String symbol, String lastTradeOrMonth, String contract_ID) {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth(lastTradeOrMonth);
        contract.conid(Integer.parseInt(contract_ID));
        return contract;
    }

    Contract getContract(String symbol, String lastTradeOrMonth, Integer contract_ID) {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth(lastTradeOrMonth);
        contract.conid(contract_ID);
        return contract;
    }

    // TODO: adjust for otherContract
    public static Contract FXFuture(String symbolIn, String currencyStringIn, String dateStringIn) {
        Contract contract = new Contract();
        contract.symbol(symbolIn);
        contract.secType("FUT");
        contract.currency(currencyStringIn);
        contract.exchange("GLOBEX");
        contract.lastTradeDateOrContractMonth(dateStringIn);
        return contract;
    }

}
