package io.github.bubinimara.gnb;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import io.github.bubinimara.gnb.model.Transaction;

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
}
