package flooringmastery.dao;

import flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryDao {

    List<Order> getOrderByDate(LocalDate date);
    Order addOrder(LocalDate date, Order order);
    Order editOrder(LocalDate date, Order order);
    Order removeOrder(LocalDate date, Order order);
    Order exportAllData();
}
