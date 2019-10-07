package com.company;

import java.awt.*;       // Using layouts
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.util.ArrayList;
import javax.swing.*;

public class OrderBookGUI extends JFrame {
    private JTextField userID, buyOrSell, quantity, price, limitOrMarket;
    private JList<Order> bids, asks;
    private OrderBook ob = new OrderBook();
    private static int orderNum = 0;


    public OrderBookGUI() {
        //OrderBook ob = new OrderBook();
        Container cp = getContentPane();

        Panel orderInput = new Panel(new FlowLayout());
        //adding order input buttons
        Panel userIDInput = new Panel(new FlowLayout());
        JLabel uidLabel = new JLabel("User ID");
        userID = new JTextField(10);
        userIDInput.add(uidLabel);
        userIDInput.add(userID);

        Panel buyOrSellInput = new Panel(new FlowLayout());
        JLabel bosLabel = new JLabel("Buy or Sell?");
        buyOrSell = new JTextField(10);
        buyOrSellInput.add(bosLabel);
        buyOrSellInput.add(buyOrSell);

        Panel quantityInput = new Panel(new FlowLayout());
        JLabel qLabel = new JLabel("Quantity");
        quantity = new JTextField(10);
        quantityInput.add(qLabel);
        quantityInput.add(quantity);

        Panel priceInput = new Panel(new FlowLayout());
        JLabel priceLabel = new JLabel("Price");
        price = new JTextField(10);
        priceInput.add(priceLabel);
        priceInput.add(price);

        Panel lomInput = new Panel(new FlowLayout());
        JLabel lomLabel = new JLabel("Limit or Market?");
        limitOrMarket = new JTextField(10);
        lomInput.add(lomLabel);
        lomInput.add(limitOrMarket);

        Button submitButton = new Button("Submit");

        //add inputs to North component
        orderInput.add(userIDInput);
        orderInput.add(buyOrSellInput);
        orderInput.add(quantityInput);
        orderInput.add(priceInput);
        orderInput.add(lomInput);
        orderInput.add(submitButton);

        /*
        *
        *   Render orderbook buys and sells here
        *
         */
        Panel orderBookDisplay = new Panel(new FlowLayout());

        Panel bidsBox = new Panel();
        bidsBox.setLayout(new BoxLayout(bidsBox, BoxLayout.PAGE_AXIS));
        JLabel bLabel = new JLabel("Bids");
        bids = new JList<Order>();
        bidsBox.add(bLabel);
        bidsBox.add(bids);

        Panel asksBox = new Panel();
        asksBox.setLayout(new BoxLayout(asksBox, BoxLayout.PAGE_AXIS));
        JLabel aLabel = new JLabel("Asks");
        asks = new JList<Order>();
        asksBox.add(aLabel);
        asksBox.add(asks);

        orderBookDisplay.add(bidsBox);
        orderBookDisplay.add(asksBox);

        Panel trades = new Panel(new FlowLayout());

        cp.setLayout(new BorderLayout());
        cp.add(orderInput, BorderLayout.NORTH);
        cp.add(orderBookDisplay, BorderLayout.CENTER);
        cp.add(trades, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Exit the program when the close-window button clicked
        setTitle("OrderBookGUI");  // "super" JFrame sets title
        setSize(1100, 750);   // "super" JFrame sets initial size
        setVisible(true);    // "super" JFrame shows

    }
    //TODO: fix entrytime
    private class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            int userId = Integer.parseInt(userID.getText());

            String bosValue = buyOrSell.getText();
            boolean bosBool;
            if(bosValue.equals("buy")) {
                bosBool = true;
            } else {
                bosBool = false;
            }

            int qty = Integer.parseInt(quantity.getText());

            double prc = Double.parseDouble(price.getText());

            boolean lomBool;
            if(bosValue.equals("limit")) {
                lomBool = true;
            } else {
                lomBool = false;
            }

            Order o = new Order(orderNum, userId, bosBool, qty, prc, lomBool, 0);
            orderNum++;

            ob.processOrder(o);

            ArrayList<Order> topAsks = ob.getSells().getMinimumNOrders(20);
            ArrayList<Order> topBids = ob.getBuys().getMinimumNOrders(20);

            Order[] tAsksArray = new Order[topAsks.size()];
            Order[] tBidsArray = new Order[topBids.size()];

            bids.setListData(topBids.toArray(tBidsArray));
            asks.setListData(topAsks.toArray(tAsksArray));
        }
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new OrderBookGUI(); // Let the constructor do the job
        }
    });
    }
}
