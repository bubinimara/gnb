package io.github.bubinimara.gnb.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.bubinimara.gnb.R;

/**
 * Created by davide.
 */
public class TransactionNameAdapter extends  RecyclerView.Adapter<TransactionNameAdapter.Holder> {
    public interface ItemListener {
        void onTransactionItemClicked(String transaction);
    }

    private final List<String> data;
    private LayoutInflater inflater;
    private ItemListener itemListener;

    public TransactionNameAdapter(@NonNull Context context) {
        data = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public ItemListener getItemListener() {
        return itemListener;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setData(@NonNull Collection<String> transactionNames){
        data.clear();
        data.addAll(transactionNames);
        // todo: notify insert and remove
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.transaction_listitem, parent, false);
        return new Holder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.set(getItem(position));
    }

    private String getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView textView;

        private final ItemListener listener;

        Holder(@NonNull View view, ItemListener listener) {
            super(view);
            this.listener = listener;
            ButterKnife.bind(this, view);
        }

        void set(String name){
            textView.setText(name);
        }

        private String get() {
            return textView.getText().toString();
        }

        @OnClick(R.id.list_item)
        void onListItemClicked(){
            if (listener!=null) {
                listener.onTransactionItemClicked(get());
            }
        }
    }
}
