package io.github.bubinimara.gnb.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.github.bubinimara.gnb.Interactor;

public class TransactionsListViewModel extends ViewModel {
    LiveData<List<String>> transactionsNames;

    public TransactionsListViewModel(Interactor interactor) {
        transactionsNames = interactor.getAllTransactionNames();
    }
}
