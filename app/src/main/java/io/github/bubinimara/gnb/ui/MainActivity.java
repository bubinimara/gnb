package io.github.bubinimara.gnb.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import io.github.bubinimara.gnb.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content,TransactionsListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof TransactionsListFragment){
            ((TransactionsListFragment)fragment).setItemListener(this::onTransactionItemClicked);
        }
    }

    public void onTransactionItemClicked(String transaction) {
        Toast.makeText(this,"Main Item Clicked "+transaction,Toast.LENGTH_SHORT)
                .show();
    }
}
