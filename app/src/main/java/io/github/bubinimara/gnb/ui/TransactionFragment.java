package io.github.bubinimara.gnb.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import io.github.bubinimara.gnb.Interactor;
import io.github.bubinimara.gnb.R;
import io.github.bubinimara.gnb.data.RepositoriesFake;
import io.github.bubinimara.gnb.data.RepositoryImpl;
import io.github.bubinimara.gnb.model.Transaction;

public class TransactionFragment extends BaseFragment {
    private static final String ARG_NAME = "ARG_TRANSACTION_NAME";

    private ViewModelProvider.Factory viemodelFactory;
    private TransactionViewModel mViewModel;
    private String transactionName;

    @BindView(R.id.text_title)
    TextView title;
    @BindView(R.id.text_amount)
    TextView amount;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Interactor interactor;
    private TransactionAdapter adapter;

    public static TransactionFragment newInstance(String transactionName) {
        Bundle args = new Bundle();
        args.putString(ARG_NAME,transactionName);
        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new TransactionAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = new Interactor(RepositoryImpl.getInstance());
        transactionName = getArguments().getString(ARG_NAME);
        viemodelFactory = new TransactionViewModel.Factory(interactor,transactionName);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viemodelFactory).get(TransactionViewModel.class);
        mViewModel.transactionsLiveData.observe(this,this::onDataChanged);
        mViewModel.totalLiveData.observe(this,this::onDataChanged);

    }

    private void onDataChanged(String total) {
        amount.setText(total);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(transactionName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void onDataChanged(List<Transaction> transactions) {
        adapter.setData(transactions);
    }
}
