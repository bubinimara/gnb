package io.github.bubinimara.gnb.model.ops;

import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.RateList;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public class TransactionRateExchange {
    private final RateList rates;

    public TransactionRateExchange(RateList rates) {
        this.rates = rates;
    }

    public Transaction convertToCurrency(Transaction transaction,String currency) {
        if (transaction.getCurrency().equals(currency)) {
            return transaction;
        }
        Rate exchangeRate = rates.findExchangeRate(transaction.getCurrency(), currency);

        float amount = Float.parseFloat(transaction.getAmount());
        float rate = Float.parseFloat(exchangeRate.getRate());
        String convertedAmount = String.valueOf(amount * rate);

        return new Transaction(transaction.getSku(), convertedAmount, currency);
    }
}
