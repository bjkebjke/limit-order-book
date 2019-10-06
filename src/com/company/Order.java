package com.company;

public class Order {
    private int orderID;
    private int userID;
    private boolean buyOrSell;
    private int quantity;
    private double price;
    private boolean limit;
    private int entryTime;
    private int eventTime;
    Order prevOrder;
    Order nextOrder;
    LimitList parentList;

    public Order(int id, int userID, boolean buyOrSell, int quantity, boolean limit, int entryTime) {
        this.orderID = id;
        this.userID = userID;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
        this.limit = limit;
        this.entryTime = entryTime;
    }

    public void updateQuantity(int qty, int time) {
        parentList.setVolume(parentList.getVolume() - (this.quantity - qty));
        this.quantity = qty;
    }

    // Getter methods
    public double getPrice() {
        return price;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isBuy() {
        return buyOrSell;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isLimit() {
        return limit;
    }

    public int getEntryTime() {
        return entryTime;
    }

    public int getEventTime() {
        return eventTime;
    }

    public Order getPrevOrder() {
        return prevOrder;
    }

    public Order getNextOrder() {
        return nextOrder;
    }

    public LimitList getParentList() {
        return parentList;
    }

    // Setter methods
    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrderId(int id) {
        this.orderID = id;
    }

    public void setUserID(int uid) {
        this.userID = uid;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setEntryTime(int entryTime) {
        this.entryTime = entryTime;
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }

    public void setPrevOrder(Order prevOrder) {
        this.prevOrder = prevOrder;
    }

    public void setNextOrder(Order nextOrder) {
        this.nextOrder = nextOrder;
    }

    public void setParentList(LimitList lst) {
        this.parentList = lst;
    }
}
