package io.github.bubinimara.gnb.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import io.github.bubinimara.gnb.Interactor;
import io.github.bubinimara.gnb.R;
import io.github.bubinimara.gnb.data.RepositoriesFake;
import io.github.bubinimara.gnb.model.Transaction;

public class TransactionsListFragment extends BaseFragment {

    private TransactionAdapter adapter;
    private Interactor interactor;

    public interface Listener{
        void onTransactionSelected(Transaction transaction);
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private TransactionsListViewModel mViewModel;
    private TransactionsListViewModel.Factory factory;

    public static TransactionsListFragment newInstance() {
        return new TransactionsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new TransactionAdapter(context);
        interactor = new Interactor(new RepositoriesFake());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transactions_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        factory = new TransactionsListViewModel.Factory(interactor);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //adapter.setItemListener((TransactionAdapter.ItemListener) getActivity());
        mViewModel = ViewModelProviders.of(this,factory)
                .get(TransactionsListViewModel.class);

        mViewModel.transactionsNames.observe(this,this::onDataChanged);

    }

    private void onDataChanged(List<String> transactionNames) {
        adapter.setData(transactionNames);
    }

    public void setItemListener(TransactionAdapter.ItemListener listener){
        adapter.setItemListener(listener);
    }

}
