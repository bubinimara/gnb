package io.github.bubinimara.gnb.model;

import java.util.ArrayList;

/**
 * Created by davide.
 */
public class RateList extends ArrayList<Rate> {

    //todo: Specify Behavior Logic on error - throw,skip, ... etc
    public Rate findExchangeRate(String from, String to) {
        Rate alternative = new Rate(from,to,"0");
        for (Rate r : this) {
            if(to.equals(r.getTo())&& from.equals(r.getFrom())){
                return r;
            }else if(to.equals(r.getFrom()) && from.equals(r.getTo())){
                alternative = r;
            }
        }
        return alternative.invertRate();
    }
}
