package flooringmastery.service;

import flooringmastery.dao.*;
import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private FlooringMasteryOrderDao orderDao;
    private FlooringMasteryTaxesDao taxesDao;
    private FlooringMasteryProductDao productDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao dao,
                                           FlooringMasteryTaxesDao taxesDao,
                                           FlooringMasteryProductDao productDao) {
        this.orderDao = dao;
        this.taxesDao = taxesDao;
        this.productDao = productDao;
    }

    @Override
    public void createOrder(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        if (order.getOrderNumber() <= 0) {
            order.setOrderNumber(orderDao.getNextOrderNumber());
        }
        calculateOrder(order); // validates, then computes
        orderDao.addOrder(order.getOrderNumber(), order);
    }

    // Validates the order, then fills rate/cost fields from the file data and
    // computes material, labor, tax, and total. Throwing here lets the
    // controller show the summary only for a valid order (spec: validate before summary).
    @Override
    public void calculateOrder(Order order)
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {
        validateOrderData(order);
        Tax tax = taxesDao.getTax(order.getState());
        Product product = productDao.getProduct(order.getProductType());

        order.setTaxRate(tax.getTaxRate());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());

        BigDecimal materialCost = order.getArea().multiply(order.getCostPerSquareFoot())
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = order.getArea().multiply(order.getLaborCostPerSquareFoot())
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal divisor = new BigDecimal("100");
        BigDecimal taxRes = materialCost.add(laborCost).multiply(order.getTaxRate().divide(divisor, 10, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost).add(taxRes);

        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(taxRes);
        order.setTotal(total);
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException {
        return orderDao.getAllOrders(date);
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException {
        return orderDao.getOrder(orderDate, orderNumber);
    }

    @Override
    public void editOrder(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        calculateOrder(order); // validates, then recomputes
        orderDao.editOrder(order);
    }

    @Override
    public void removeOrder(Order order) throws FlooringMasteryPersistenceException {
        orderDao.removeOrder(order.getOrderDate(), order.getOrderNumber());
    }

    @Override
    public void exportAllData() throws FlooringMasteryPersistenceException {
        orderDao.exportAllData();
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        return taxesDao.getAllTaxes();
    }

    // ---- validation ----

    private void validateOrderData(Order order) throws
            FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {

        if (order.getOrderDate() == null
                || order.getCustomerName() == null
                || order.getCustomerName().trim().isEmpty()
                || order.getState() == null
                || order.getState().trim().isEmpty()
                || order.getProductType() == null
                || order.getProductType().trim().isEmpty()
                || order.getArea() == null) {
            throw new FlooringMasteryDataValidationException(
                    "ERROR: All fields [date, customerName, state, productType, area] are required.");
        }

        validateOrderDate(order.getOrderDate());
        validateOrderCustomerName(order.getCustomerName());
        validateOrderState(order.getState());
        validateOrderProductType(order.getProductType());
        validateOrderArea(order.getArea());
    }

    private void validateOrderDate(LocalDate orderDate) throws FlooringMasteryDataValidationException {
        if (!orderDate.isAfter(LocalDate.now())) {
            throw new FlooringMasteryDataValidationException(
                    "The order date must be in the future. Please try again.");
        }
    }

    private void validateOrderCustomerName(String customerName) throws FlooringMasteryDataValidationException {
        // Spec: letters, numbers, periods, and commas allowed ("Acme, Inc." is valid).
        if (!customerName.matches("[A-Za-z0-9., ]*") || customerName.trim().isEmpty()) {
            throw new FlooringMasteryDataValidationException(
                    "Customer name must only be made up of letters, numbers, periods, commas, and spaces.");
        }
    }

    private void validateOrderState(String stateAbbr)
            throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        Tax tax = taxesDao.getTax(stateAbbr);
        if (tax == null) {
            throw new FlooringMasteryDataValidationException(stateAbbr + " is not a valid state. Please try again.");
        }
    }

    private void validateOrderProductType(String productType)
            throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {
        Product product = productDao.getProduct(productType);
        if (product == null) {
            throw new FlooringMasteryDataValidationException(
                    productType + " is not an available product. Please try again.");
        }
    }

    private void validateOrderArea(BigDecimal area) throws FlooringMasteryDataValidationException {
        BigDecimal minimum = new BigDecimal("100");
        if (area.compareTo(minimum) < 0) {
            throw new FlooringMasteryDataValidationException(
                    "Area must be at least 100 sq ft. Please try again.");
        }
    }
}