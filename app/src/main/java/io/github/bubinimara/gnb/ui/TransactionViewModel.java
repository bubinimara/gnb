package io.github.bubinimara.gnb.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.github.bubinimara.gnb.Interactor;
import io.github.bubinimara.gnb.model.Transaction;

public class TransactionViewModel extends ViewModel {
    MediatorLiveData<List<Transaction>> transactionsLiveData;
    MediatorLiveData<String> totalLiveData;


    public TransactionViewModel(Interactor interactor,String name) {
        transactionsLiveData = new MediatorLiveData<>();
        totalLiveData = new MediatorLiveData<>();
        transactionsLiveData.addSource(interactor.findTransactionByName(name), transactions -> {
            transactionsLiveData.postValue(transactions);
            LiveData<String> totalTransaction = interactor.getTotalTransaction("EUR", transactions);
            totalLiveData.addSource(totalTransaction
                    , s -> {
                        totalLiveData.removeSource(totalTransaction);
                        totalLiveData.postValue(s);
                    });



        });
    }

    public static class Factory implements ViewModelProvider.Factory{

        private Interactor interactor;
        private String name;

        public Factory(Interactor interactor, String name) {
            this.interactor = interactor;
            this.name = name;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TransactionViewModel(interactor,name);
        }
    }
}
