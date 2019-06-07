package io.github.bubinimara.gnb.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import io.github.bubinimara.gnb.Repository;
import io.github.bubinimara.gnb.data.cache.InMemoryCache;
import io.github.bubinimara.gnb.data.net.Api;
import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.RateList;
import io.github.bubinimara.gnb.model.Transaction;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by davide.
 */
public class RepositoryImpl implements Repository {
    private Api api;
    private InMemoryCache<List<Transaction>> memoryCacheTransaction;
    private InMemoryCache<RateList> memoryCacheRates;

    private static RepositoryImpl instance;


    public static RepositoryImpl getInstance(){
        if(instance != null)
            return instance;

        synchronized (RepositoryImpl.class){
            if(instance == null){
                instance = new RepositoryImpl();
            }
            return instance;
        }
    }

    RepositoryImpl() {
        memoryCacheTransaction = new InMemoryCache<>();
        memoryCacheRates = new InMemoryCache<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://quiet-stone-2094.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    @Override
    public LiveData<List<Transaction>> getTransactions() {
        MediatorLiveData<List<Transaction>> result = new MediatorLiveData<>();
        if(memoryCacheTransaction.getT()!=null){
            result.postValue(memoryCacheTransaction.getT());
        }else {
            api.getTransactions().enqueue(new Callback<List<Transaction>>() {
                @Override
                public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    List<Transaction> body = response.body();
                    result.postValue(body);
                    memoryCacheTransaction.setT(body);
                }

                @Override
                public void onFailure(Call<List<Transaction>> call, Throwable t) {
                    // threat exceptions
                }
            });
        }
        return result;
    }

    @Override
    public LiveData<RateList> getRates() {
        MediatorLiveData<RateList> result = new MediatorLiveData<>();
        if(memoryCacheRates.getT()!=null){
            result.postValue(memoryCacheRates.getT());

        }else {
            api.getRates().enqueue(new Callback<List<Rate>>() {
                @Override
                public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                    RateList rateList = new RateList(response.body());
                    result.postValue(rateList);
                    memoryCacheRates.setT(rateList);
                }

                @Override
                public void onFailure(Call<List<Rate>> call, Throwable t) {

                }
            });
        }
        return result;
    }
}
