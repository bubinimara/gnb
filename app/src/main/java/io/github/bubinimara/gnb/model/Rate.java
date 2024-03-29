package io.github.bubinimara.gnb.model;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Created by davide.
 */
public class Rate {
    private String from;
    private String to;
    private String rate;

    public Rate(String from, String to, String rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getRate() {
        return rate;
    }

    public Rate invertRate() {
        return new Rate(to,from,exchangeRate());
    }

    @NonNull
    @Override
    public String toString() {
        return to+" "+from+" "+rate;
    }

    protected String exchangeRate(){
        Log.d("MYDEBUG", "exchangeRate: "+toString());
        try {
            float currentRate = Float.parseFloat(rate);
            return String.valueOf(1/currentRate);
        } catch (NumberFormatException e) { // check division by 0
            e.printStackTrace();
            return "NaN";
        }


    }
}
