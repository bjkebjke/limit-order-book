package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LimitList implements Iterable<Order>, Iterator<Order> {
    private int limitPrice;
    private int length = 0;
    private int volume = 0;
    private Order headOrder = null;
    private Order tailOrder = null;
    private Order last = null;

    public boolean hasNext() {
        if(this.last == null) {
            return false;
        }
        return true;
    }

    public Order next() {
        if(this.last == null) {
            throw new NoSuchElementException();
        }
        Order returnOrd = this.last;
        this.last = this.last.getNextOrder();
        return returnOrd;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public Iterator<Order> iterator() {
        this.last = headOrder;
        return this;
    }

    public void appendOrder(Order newOrder) {
        if(length == 0) {
            newOrder.setPrevOrder(null);
            newOrder.setNextOrder(null);
            headOrder = newOrder;
            tailOrder = newOrder;
        } else {
            newOrder.setPrevOrder(tailOrder);
            newOrder.setNextOrder(null);
            tailOrder.setNextOrder(newOrder);
            tailOrder = newOrder;
        }
        length += 1;
        volume += newOrder.getQuantity();
    }

    public void removeOrder(Order order) {
        if(length == 0) {
            throw new NoSuchElementException("empty List, cannot remove");
        }
        Order tempPrevOrder = order.getPrevOrder();
        Order tempNextOrder = order.getNextOrder();

        if((tempPrevOrder != null) && (tempNextOrder != null)) {
            tempPrevOrder.setNextOrder(tempNextOrder);
            tempNextOrder.setPrevOrder(tempPrevOrder);
        } else if(tempNextOrder != null) {
            tempNextOrder.setPrevOrder(null);
            this.headOrder = tempNextOrder;
        } else if(tempPrevOrder != null) {
            tempPrevOrder.setNextOrder(null);
            this.tailOrder = tempPrevOrder;
        }
        volume -= order.getQuantity();
        length -= 1;
    }

    public Order getHeadOrder() {
        return headOrder;
    }

    public Order getTailOrder() {
        return tailOrder;
    }

    public int getLength() {
        return length;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }
}
