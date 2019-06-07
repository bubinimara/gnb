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
    MutableLiveData<String> totalLiveData;


    public TransactionViewModel(Interactor interactor,String name) {
        transactionsLiveData = new MediatorLiveData<>();
        totalLiveData = new MutableLiveData<>();
        transactionsLiveData.addSource(interactor.findTransactionByName(name), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                transactionsLiveData.postValue(transactions);
                totalLiveData.postValue(interactor.calculateTotalTransactions(transactions));
            }
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
