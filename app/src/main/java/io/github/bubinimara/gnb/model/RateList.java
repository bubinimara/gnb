package io.github.bubinimara.gnb.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by davide.
 */
public class RateList extends ArrayList<Rate> {
    private static final String TAG = RateList.class.getSimpleName();

    public RateList(List<Rate> rates) {
        addAll(rates);
    }
    public RateList() {

    }

    //todo: Specify Behavior Logic on error - throw,skip, ... etc
    public Rate findExchangeRate(String from, String to) {
        Log.d(TAG, "findExchangeRate: to "+ to + "from "+from);
        Rate alternative = null;
        for (Rate r : this) {
            Log.d(TAG, "findExchangeRate: "+r.toString());
            if(to.equals(r.getTo())&& from.equals(r.getFrom())){
                Log.d(TAG, "findExchangeRate: FOUND "+r.toString());
                return r;
            }else if(to.equals(r.getFrom()) && from.equals(r.getTo())){
                alternative = r;
            }
        }
        if(alternative == null) {
            Log.w(TAG, "findExchangeRate: NOT FOUND " );
            return new Rate(from, to, "0");
        }
        Log.d(TAG, "findExchangeRate: INVERT ");
        return alternative.invertRate();
    }
}
