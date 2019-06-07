package io.github.bubinimara.gnb;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.bubinimara.gnb.model.Rate;
import io.github.bubinimara.gnb.model.Transaction;

/**
 * Created by davide.
 */
public class Interactor {
    private Repository repository;

    public Interactor(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<String>> getAllTransactionNames(){
        return Transformations.map(repository.getTransactions(),(input)->{
            List<String> transactionNames = new ArrayList<>();
            for (Transaction t:input
            ) {
                if(!transactionNames.contains(t.getSku())){
                    transactionNames.add(t.getSku());
                }
            }
            return transactionNames;
        });
    }

    public LiveData<List<Transaction>> findTransactionByName(@NonNull String name){
        return Transformations.map(repository.getTransactions(),(transactions)->{
            List<Transaction> result = new ArrayList<>();
            for (Transaction t:transactions
            ) {
                if(name.equals(t.getSku())){
                    result.add(t);
                }
            }
           return result;
        });
    }

    public LiveData<List<Rate>> getAllRates(){
        return repository.getRates();
    }

    public LiveData<Rate> findRate(String from,String to){
        return Transformations.map(repository.getRates(),(rates)->{
            return findExchangeRate(rates,from, to);
        });
    }

    //todo: Specify Behavior Logic on error - throw,skip, ... etc
    private Rate findExchangeRate(List<Rate> rates,String from, String to) {
        Rate alternative = new Rate(from,to,"0");
        for (Rate r : rates) {
            if(to.equals(r.getTo())&& from.equals(r.getFrom())){
                return r;
            }else if(to.equals(r.getFrom()) && from.equals(r.getTo())){
                alternative = r;
            }

        }
        return alternative.invertRate();
    }

    public LiveData<String> getTotalTransaction(String currency,List<Transaction> transactions){
        return Transformations.map(repository.getRates(),(rates)->{
            return calculateTotalTransactions(currency,rates,transactions);
        });

    }

    public String calculateTotalTransactions(String currency,List<Rate> rates,List<Transaction> transactions) {
        TransactionOp transactionOp = new TransactionOp(currency,rates);
        transactionOp.sumAll(transactions);
        return String.valueOf(transactionOp.getResult());
    }

    public static class TransactionOp{
        private List<Rate> rates;
        private String currency;
        private float result;

        public TransactionOp(String currency,List<Rate> rates) {
            this.rates = rates;
            this.currency = currency;
            this.result = 0;
        }

        public Transaction convertToCurrency(Transaction transaction){
            if(transaction.getCurrency().equals(currency)){
               return transaction;
            }
            Rate exchangeRate = findExchangeRate(rates, transaction.getCurrency(), currency);

            float amount = Float.parseFloat(transaction.getAmount());
            float rate = Float.parseFloat(exchangeRate.getRate());
            String convertedAmount = String.valueOf(amount*rate);

            return  new Transaction(transaction.getSku(),convertedAmount,currency);
        }

        public void sum(@NonNull Transaction transaction){
            try {
                String tc = convertToCurrency(transaction).getAmount();
                result+= Float.parseFloat(tc);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        public void sumAll(Collection<Transaction> transactions){
            for (Transaction t:transactions) {
                sum(t);
            }
        }
        public float getResult() {
            return result;
        }

        public List<Rate> getRates() {
            return rates;
        }

        public String getCurrency() {
            return currency;
        }

        private Rate findExchangeRate(List<Rate> rates, String from, String to) {
            Rate alternative = new Rate(from,to,"0");
            for (Rate r : rates) {
                if(to.equals(r.getTo())&& from.equals(r.getFrom())){
                    return r;
                }else if(to.equals(r.getFrom()) && from.equals(r.getTo())){
                    alternative = r;
                }
            }
            return alternative.invertRate();
        }
    }
}
