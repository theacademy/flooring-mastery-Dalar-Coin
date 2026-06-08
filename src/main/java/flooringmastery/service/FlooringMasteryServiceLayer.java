package flooringmastery.service;

import flooringmastery.dao.FlooringMasteryPersistenceException;
import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryServiceLayer {

    void createOrder(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException;

    void calculateOrder(Order order) throws
            FlooringMasteryPersistenceException, FlooringMasteryDataValidationException;

    List<Order> getAllOrders(LocalDate date) throws
            FlooringMasteryPersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws
            FlooringMasteryPersistenceException;

    void editOrder(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException;

    void removeOrder(Order order) throws
            FlooringMasteryPersistenceException;

    void exportAllData() throws
            FlooringMasteryPersistenceException;

    List<Product> getAllProducts() throws
            FlooringMasteryPersistenceException;

    List<Tax> getAllTaxes() throws
            FlooringMasteryPersistenceException;
}