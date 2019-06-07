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
public class TransactionOp {
    private final RateList rates;
    private final String currency;
    private float result;

    public TransactionOp(String currency, RateList rates) {
        this.rates = rates;
        this.currency = currency;
        this.result = 0;
    }

    public Transaction convertToCurrency(Transaction transaction) {
        if (transaction.getCurrency().equals(currency)) {
            return transaction;
        }
        Rate exchangeRate = rates.findExchangeRate(transaction.getCurrency(), currency);

        float amount = Float.parseFloat(transaction.getAmount());
        float rate = Float.parseFloat(exchangeRate.getRate());
        String convertedAmount = String.valueOf(amount * rate);

        return new Transaction(transaction.getSku(), convertedAmount, currency);
    }

    public void sum(@NonNull Transaction transaction) {
        try {
            String tc = convertToCurrency(transaction).getAmount();
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
