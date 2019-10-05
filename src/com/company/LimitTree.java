package com.company;

import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.HashMap;

public class LimitTree {
    TreeMap<Double, LimitList> limitTree = new TreeMap<Double, LimitList>();
    HashMap<Double, LimitList> limitMap = new HashMap<Double, LimitList>();
    HashMap<Integer, Order> orderMap = new HashMap<Integer, Order>();
    int volume;

    public LimitTree() {
        reset();
    }

    public void reset() {
        limitTree.clear();
        limitMap.clear();
        orderMap.clear();
        volume = 0;
    }

    public LimitList getLimitList(double price) {
        return limitMap.get(price);
    }

    public Order getOrder(int id) {
        return orderMap.get(id);
    }

    public void createPrice(double price) {
        LimitList newPriceList = new LimitList();
        limitTree.put(price, newPriceList);
        limitMap.put(price, newPriceList);
    }

    public void removePrice(double price) {
        limitTree.remove(price);
        limitMap.remove(price);
    }

    public boolean priceExists(double price) {
        return limitMap.containsKey(price);
    }

    public boolean orderExists(int id) {
        return orderMap.containsKey(id);
    }

    public void insertOrder(Order newOrder) {
        int newId = newOrder.getOrderID();
        double newPrice = newOrder.getPrice();

        if(!priceExists(newPrice)) {
            createPrice(newPrice);
        }
        newOrder.setParentList(limitMap.get(newPrice));
        limitMap.get(newPrice).appendOrder(newOrder);
        orderMap.put(newId, newOrder);
        volume += newOrder.getQuantity();
    }

    public void removeOrderByID(int id) {
        Order toRemove = orderMap.get(id);
        this.volume -= toRemove.getQuantity();
        toRemove.getParentList().removeOrder(toRemove);
        if(toRemove.getParentList().getLength() == 0) {
            this.removePrice(toRemove.getPrice());
        }
        this.orderMap.remove(id);
    }

    //public void updateOrder()

    public double maxPrice() {
        if(limitTree.size() > 0) {
            return limitTree.lastKey();
        } else {
            throw new NoSuchElementException();
        }
    }

    public double minPrice() {
        if(limitTree.size() > 0) {
            return limitTree.firstKey();
        } else {
            throw new NoSuchElementException();
        }
    }

    public LimitList getMaxLimitList() {
        if(limitTree.size() > 0) {
            return this.getLimitList(maxPrice());
        } else {
            throw new NoSuchElementException();
        }
    }

    public LimitList getMinLimitList() {
        if(limitTree.size() > 0) {
            return this.getLimitList(minPrice());
        } else {
            throw new NoSuchElementException();
        }
    }

    public int getVolume() {
        return this.volume;
    }


}
