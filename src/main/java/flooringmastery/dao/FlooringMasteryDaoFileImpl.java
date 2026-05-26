package flooringmastery.dao;

import flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {

    private Map<Integer, Order> orders = new HashMap<>();

    @Override
    public List<Order> getOrderByDate(LocalDate date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        Order prevOrder = orders.put(order.getOrderNumber(), order);
        return prevOrder;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Order removeOrder(LocalDate date, Order order) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Order exportAllData() {
        return null;
    }
}
