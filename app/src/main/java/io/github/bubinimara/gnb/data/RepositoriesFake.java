package io.github.bubinimara.gnb.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.github.bubinimara.gnb.Repository;
import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.RateList;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public class RepositoriesFake implements Repository {
    @Override
    public LiveData<List<Transaction>> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("skuA","1.33","EUR"));
        transactions.add(new Transaction("skuB","4.44","CUD"));
        transactions.add(new Transaction("skuA","3.12","EUR"));
        transactions.add(new Transaction("skuC","1.5","EUR"));
        transactions.add(new Transaction("skuC","11.2","EUR"));
        transactions.add(new Transaction("skuA","1.2","EUR"));
        transactions.add(new Transaction("skuD","2.2","USD"));
        transactions.add(new Transaction("skuD","1.2","CUD"));
        MutableLiveData<List<Transaction>> result = new MutableLiveData<>();
        result.postValue(transactions);
        return result;
    }

    @Override
    public LiveData<Transaction> getTransactionById(String transactionId) {
        return null;
    }

    @Override
    public LiveData<RateList> getRates() {
        MutableLiveData<RateList> result = new MutableLiveData<>();
        RateList rates = new RateList();
        rates.add(new Rate("EUR","CUD","2"));
        rates.add(new Rate("CUD","EUR","1"));
        rates.add(new Rate("EUR","USD","2"));

        result.postValue(rates);
        return result;
    }

    @Override
    public LiveData<Rate> getRateById(String rateId) {
        return null;
    }
}
