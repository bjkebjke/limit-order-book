package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {

    @Test
    void processConsecutiveBuyOrdersSamePrice() {
        OrderBook ob = new OrderBook();
        for(int i = 1; i < 11; i++) {
            Order o1 = new Order(i,i,true,i,21.11,true,0);
            ob.processOrder(o1);
        }

        assertEquals(10, ob.getBuys().getNumOrders());
        assertEquals(55,ob.getBuys().getVolume());
    }

    @Test
    void processBidsAndAsksSamePrice() {
        OrderBook ob = new OrderBook();
        for(int i = 1; i < 11; i++) {
            Order o1 = new Order(i,i,true,i,21.11,true,0);
            ob.processOrder(o1);
        }
        assertEquals(10, ob.getBuys().getNumOrders());
        assertEquals(55,ob.getBuys().getVolume());

        for(int i = 10; i >= 1; i--) {
            Order o2 = new Order(i+20, i+20, false, i, 21.11, true, 0);
            ob.processOrder(o2);
        }
        assertEquals(0, ob.getBuys().getVolume());
    }

    @Test
    void processLimitOrder() {
    }

    @Test
    void processMarketOrder() {
    }

    @Test
    void transactOrderList() {
    }
}