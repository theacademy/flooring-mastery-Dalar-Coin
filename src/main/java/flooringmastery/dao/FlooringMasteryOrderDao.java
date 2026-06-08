package flooringmastery.dao;

import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryOrderDao {

    /**
     * Adds the given Order to the roster and associates it with the given
     * order number. If there is already a order associated with the given
     * order id it will return that order object, otherwise it will
     * return null.
     *
     * @param orderNumber number with which order is to be associated
     * @param order order to be added to the roster
     * @return the Order object previously associated with the given
     * order number if it exists, null otherwise
     * @throws FlooringMasteryPersistenceException
     */
    Order addOrder(int orderNumber, Order order)
            throws FlooringMasteryPersistenceException;

    /**
     * Returns a List of all Orders.
     *
     * @return Order List containing all orders on the roster.
     * @throws FlooringMasteryPersistenceException
     */
    List<Order> getAllOrders(LocalDate date)
            throws FlooringMasteryPersistenceException;

    /**
     * Returns the order object associated with the given date and order number.
     * Returns null if no such order exists
     *
     * @param date date of the order to retrieve
     * @param orderNumber ID of the order to retrieve
     * @return the Order object associated with the given date and orderNumber.
     * null if no such order exists
     * @throws FlooringMasteryPersistenceException
     */
    Order getOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException;

    Order editOrder(Order order)
            throws FlooringMasteryPersistenceException;

    /**
     * Removes from the roster the order associated with the given id.
     * Returns the order object that is being removed or null if
     * there is no order associated with the given id
     *
     * @param date date of order to be removed
     * @param orderNumber number of order to be removed
     * @return Order object that was removed or null if no order
     * was associated with the given order id
     * @throws FlooringMasteryPersistenceException
     */
    Order removeOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException;

    int getNextOrderNumber()
            throws FlooringMasteryPersistenceException;

    void exportAllData()
            throws FlooringMasteryPersistenceException;

}
