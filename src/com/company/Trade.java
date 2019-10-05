package com.company;

public class Trade {
    private int timestamp;
    private double price;
    private int quantity;
    private int buyerID;
    private int sellerID;
    private int orderID;

    public Trade(int timestamp, double price, int quantity, int buyerID, int sellerID, int orderID) {
        this.timestamp = timestamp;
        this.price = price;
        this.quantity = quantity;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.orderID = orderID;
    }
}
