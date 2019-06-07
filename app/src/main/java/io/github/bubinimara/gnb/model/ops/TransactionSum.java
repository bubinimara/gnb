package io.github.bubinimara.gnb.model.ops;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.RateList;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public class TransactionSum {
    private TransactionRateExchange transactionRateExchange;
    private final RateList rates;
    private final String currency;
    private float result;

    public TransactionSum(String currency, RateList rates) {
        this.rates = rates;
        this.transactionRateExchange = new TransactionRateExchange(rates);
        this.currency = currency;
        this.result = 0;
    }

    public void sum(@NonNull Transaction transaction) {
        try {
            String tc =transactionRateExchange
                    .convertToCurrency(transaction,currency)
                    .getAmount();

            result += Float.parseFloat(tc);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void sumAll(Collection<Transaction> transactions) {
        for (Transaction t : transactions) {
            sum(t);
        }
    }

    public float getResult() {
        return result;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public String getCurrency() {
        return currency;
    }
}
