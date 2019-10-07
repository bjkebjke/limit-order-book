package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimitTreeTest {

    @Test
    void getLimitList() {
    }

    @Test
    void createPrice() {
    }

    @Test
    void removePrice() {
    }

    @Test
    void insertConsecutiveOrders() {
        LimitTree lt = new LimitTree();
        for(int i = 0; i < 10; i++) {
            Order o1 = new Order(i,i,true,i,21.11,true,0);
            lt.insertOrder(o1);
        }
        assertEquals(lt.getNumOrders(), 10);
    }

    @Test
    void insertMultipleOrdersAndRemoveTwoOrdersByID() {
        LimitTree lt = new LimitTree();
        for(int i = 0; i < 10; i++) {
            Order o1 = new Order(i,i,true,i,21.11,true,0);
            lt.insertOrder(o1);
        }

        lt.removeOrderByID(2);
        lt.removeOrderByID(6);

        assertFalse(lt.orderExists(2));
        assertFalse(lt.orderExists(6));
        assertEquals(lt.getNumOrders(), 8);

    }

    @Test
    void updateOrderQuantity() {
    }

    @Test
    void getMaxLimitList() {
        // Maximum price should be 9 with volume 9+10=19
        LimitTree lt = new LimitTree();
        for(int i = 0; i < 10; i++) {
            Order o1 = new Order(i,i,true,i,i,true,0);
            lt.insertOrder(o1);
        }
        lt.insertOrder(new Order(10,10,true,10, 9, true, 0));

        LimitList ll = lt.getMaxLimitList();
        assertEquals(19, ll.getVolume());
        assertEquals(9, lt.maxPrice());
    }

    @Test
    void getMinLimitList() {
        // Minimum price should be 1 with volume 1+10=11
        LimitTree lt = new LimitTree();
        for(int i = 1; i < 10; i++) {
            Order o1 = new Order(i,i,true,i,i,true,0);
            lt.insertOrder(o1);
        }
        lt.insertOrder(new Order(10,10,true,10, 1, true, 0));

        LimitList ll = lt.getMinLimitList();
        assertEquals(11, ll.getVolume());
        assertEquals(1, lt.minPrice());
    }
}