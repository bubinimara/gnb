package io.github.bubinimara.gnb;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import io.github.bubinimara.gnb.model.RateList;
import io.github.bubinimara.gnb.model.Transaction;
import io.github.bubinimara.gnb.model.ops.TransactionSum;

/**
 * Created by davide.
 */
public class Interactor {
    private Repository repository;

    public Interactor(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<String>> getAllTransactionNames(){
        return Transformations.map(repository.getTransactions(),(input)->{
            List<String> transactionNames = new ArrayList<>();
            for (Transaction t:input
            ) {
                if(!transactionNames.contains(t.getSku())){
                    transactionNames.add(t.getSku());
                }
            }
            return transactionNames;
        });
    }

    public LiveData<List<Transaction>> findTransactionByName(@NonNull String name){
        return Transformations.map(repository.getTransactions(),(transactions)->{
            List<Transaction> result = new ArrayList<>();
            for (Transaction t:transactions
            ) {
                if(name.equals(t.getSku())){
                    result.add(t);
                }
            }
           return result;
        });
    }

    public LiveData<RateList> getAllRates(){
        return repository.getRates();
    }

    public LiveData<String> getTotalTransaction(String currency,List<Transaction> transactions){
        return Transformations.map(repository.getRates(),(rates)-> calculateTotalTransactions(currency,rates,transactions));

    }

    private String calculateTotalTransactions(String currency, RateList rates, List<Transaction> transactions) {
        TransactionSum transactionSum = new TransactionSum(currency,rates);
        transactionSum.sumAll(transactions);
        return String.valueOf(transactionSum.getResult());
    }

}
