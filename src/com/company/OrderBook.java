package com.company;
import java.util.ArrayList;

public class OrderBook {
    private ArrayList<Trade> trades = new ArrayList<Trade>();
    private LimitTree buys = new LimitTree();
    private LimitTree sells = new LimitTree();

    public OrderBook() {
        this.reset();
    }

    public void reset() {
        trades.clear();
        buys.reset();
        sells.reset();
    }

    //add processing trades

}
