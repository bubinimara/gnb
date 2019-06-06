package io.github.bubinimara.gnb.ui;

import android.os.Bundle;

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
}
