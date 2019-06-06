package io.github.bubinimara.gnb.model;

/**
 * Created by davide.
 */
public class Transaction {
    private String sku;
    private String amount;
    private String currency;

    public Transaction(String sku, String amount, String currency) {
        this.sku = sku;
        this.amount = amount;
        this.currency = currency;
    }

    public String getSku() {
        return sku;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
