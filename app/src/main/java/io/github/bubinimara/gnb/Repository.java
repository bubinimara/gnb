package io.github.bubinimara.gnb;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public interface Repository {
    LiveData<List<Transaction>> getTransactions();
    LiveData<Transaction> getTransactionById(String transactionId);

    LiveData<List<Rate>> getRates();
    LiveData<Rate> getRateById(String rateId);
}
