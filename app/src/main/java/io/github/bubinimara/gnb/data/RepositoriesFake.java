package io.github.bubinimara.gnb.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.github.bubinimara.gnb.Repository;
import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public class RepositoriesFake implements Repository {
    @Override
    public LiveData<List<Transaction>> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("skuA",null,null));
        transactions.add(new Transaction("skuB",null,null));
        transactions.add(new Transaction("skuA",null,null));
        transactions.add(new Transaction("skuC",null,null));
        transactions.add(new Transaction("skuC",null,null));
        transactions.add(new Transaction("skuA",null,null));
        transactions.add(new Transaction("skuD",null,null));
        transactions.add(new Transaction("skuD",null,null));
        MutableLiveData<List<Transaction>> result = new MutableLiveData<>();
        result.postValue(transactions);
        return result;
    }

    @Override
    public LiveData<Transaction> getTransactionById(String transactionId) {
        return null;
    }

    @Override
    public LiveData<List<Rate>> getRates() {
        return null;
    }

    @Override
    public LiveData<Rate> getRateById(String rateId) {
        return null;
    }
}
