package io.github.bubinimara.gnb.model.ops;

import android.util.Log;

import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.RateList;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public class TransactionRateExchange {
    private static final String TAG = TransactionRateExchange.class.getSimpleName();

    private final RateList rates;

    public TransactionRateExchange(RateList rates) {
        this.rates = rates;
    }

    public Transaction convertToCurrency(Transaction transaction,String currency) {
        Log.d(TAG, "convertToCurrency: TRANSACTION "+transaction.toString());
        if (transaction.getCurrency().equals(currency)) {
            Log.d(TAG, "convertToCurrency: Skip");
            return transaction;
        }
        Rate exchangeRate = rates.findExchangeRate(transaction.getCurrency(), currency);

        float amount = Float.parseFloat(transaction.getAmount());
        float rate = Float.parseFloat(exchangeRate.getRate());

        String convertedAmount = String.valueOf(amount * rate);
        Log.d(TAG, "convertToCurrency: amount [ "+amount+" ] rate [ "+rate+" ] => "+convertedAmount);
        return new Transaction(transaction.getSku(), convertedAmount, currency);
    }
}
