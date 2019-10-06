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

    public void processOrder(Order order) {
        boolean isLimit = order.isLimit();
        if(isLimit) {
            processLimitOrder(order);
        } else {
            processMarketOrder(order);
        }
    }

    public void processLimitOrder(Order order) {
        boolean isBuy = order.isBuy();
        int quantityRemaining = order.getQuantity();
        double price = order.getPrice();

        if(isBuy) { //Is a buy order
            while(this.sells.getNumOrders() > 0 && quantityRemaining > 0 && price >= sells.minPrice()) {
                //process the orders at minimum price
                LimitList minSellLimit = this.sells.getMinLimitList();
                quantityRemaining = transactOrderList(minSellLimit, quantityRemaining, order);
            }
            //add remaining volume to tree
            if(quantityRemaining > 0) {
                order.setQuantity(quantityRemaining);
                this.buys.insertOrder(order);
            }
        } else { // Is a sell order
            while(this.buys.getNumOrders() > 0 && quantityRemaining > 0 && price <= buys.maxPrice()) {
                //process the orders at max price
                LimitList maxBuyLimit = this.buys.getMaxLimitList();
                quantityRemaining = transactOrderList(maxBuyLimit, quantityRemaining, order);
            }
            //add remaining volume to tree
            if(quantityRemaining > 0) {
                order.setQuantity(quantityRemaining);
                this.sells.insertOrder(order);
            }
        }
    }

    public void processMarketOrder(Order order) {
        boolean isBuy = order.isBuy();
        int quantityRemaining = order.getQuantity();

        if(isBuy) { //Is a buy order
            while(quantityRemaining > 0 && this.sells.getNumOrders() > 0) {
                //process the orders at minimum price
                LimitList minSellLimit = this.sells.getMinLimitList();
                quantityRemaining = transactOrderList(minSellLimit, quantityRemaining, order);
            }
        } else { // Is a sell order
            while(quantityRemaining > 0 && this.buys.getNumOrders() > 0) {
                //process the orders at max price
                LimitList maxBuyLimit = this.buys.getMaxLimitList();
                quantityRemaining = transactOrderList(maxBuyLimit, quantityRemaining, order);

            }
        }
    }
    // TODO: need to change trade id
    public int transactOrderList(LimitList ordersAtLimit, int quantityRemaining, Order newOrder) {
        boolean isBuy = newOrder.isBuy();
        int time = newOrder.getEntryTime();

        while(ordersAtLimit.getLength() > 0 && quantityRemaining > 0) {
            int quantityTraded = 0;
            Order headOrder = ordersAtLimit.getHeadOrder();
            Trade newTrade;
            if(isBuy) { //modify sells
                if(quantityRemaining < headOrder.getQuantity()) { //update
                    quantityTraded = quantityRemaining;
                    this.sells.updateOrderQuantity(headOrder.getQuantity() - quantityRemaining, headOrder.getOrderID());

                } else { //remove
                    quantityTraded = headOrder.getQuantity();
                    this.sells.removeOrderByID(headOrder.getOrderID());
                }
                newTrade = new Trade(time, headOrder.getPrice(), quantityTraded, newOrder.getUserID(), headOrder.getUserID(), 0);
            } else { //modify buys
                if(quantityRemaining < headOrder.getQuantity()) { //update
                    quantityTraded = quantityRemaining;
                    this.buys.updateOrderQuantity(headOrder.getQuantity() - quantityRemaining, headOrder.getOrderID());

                } else { //remove
                    quantityTraded = headOrder.getQuantity();
                    this.buys.removeOrderByID(headOrder.getOrderID());
                }
                newTrade = new Trade(time, headOrder.getPrice(), quantityTraded, headOrder.getUserID(), newOrder.getUserID(), 0);
            }
            quantityRemaining -= quantityTraded;
            this.trades.add(newTrade);
        }
        return quantityRemaining;
    }

}
