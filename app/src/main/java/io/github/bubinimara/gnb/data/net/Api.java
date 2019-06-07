package io.github.bubinimara.gnb.data.net;

import java.util.List;

import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.Transaction;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by davide.
 */
public interface Api {

    @GET("/transactions.json")
    Call<List<Transaction>> getTransactions();

    @GET("/rates.json")
    Call<List<Rate>> getRates();
}
