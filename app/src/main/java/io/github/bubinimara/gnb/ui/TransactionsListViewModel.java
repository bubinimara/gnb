package io.github.bubinimara.gnb.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.github.bubinimara.gnb.Interactor;

public class TransactionsListViewModel extends ViewModel {
    LiveData<List<String>> transactionsNames;

    public TransactionsListViewModel(Interactor interactor) {
        transactionsNames = interactor.getAllTransactionNames();
    }
    
    public static class Factory implements ViewModelProvider.Factory {
        Interactor interactor;

        public Factory(Interactor interactor) {
            this.interactor = interactor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TransactionsListViewModel(interactor);
        }
    }
}
