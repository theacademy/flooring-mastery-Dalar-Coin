package flooringmastery.dao;

import flooringmastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory stub of the Order DAO for service-layer testing.
 * Holds a single known order so tests can assert against fixed data
 * without touching the file system.
 */
public class FlooringMasteryOrderDaoStubImpl implements FlooringMasteryOrderDao {

    public Order onlyOrder;
    public LocalDate onlyDate;

    public FlooringMasteryOrderDaoStubImpl() {
        onlyDate = LocalDate.of(2025, 6, 1);
        onlyOrder = new Order(1);
        onlyOrder.setOrderDate(onlyDate);
        onlyOrder.setCustomerName("Ada Lovelace");
        onlyOrder.setState("CA");
        onlyOrder.setTaxRate(new BigDecimal("25.00"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("249.00"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        onlyOrder.setMaterialCost(new BigDecimal("871.50"));
        onlyOrder.setLaborCost(new BigDecimal("1033.35"));
        onlyOrder.setTax(new BigDecimal("476.21"));
        onlyOrder.setTotal(new BigDecimal("2381.06"));
    }

    public FlooringMasteryOrderDaoStubImpl(Order testOrder) {
        this.onlyOrder = testOrder;
        this.onlyDate = testOrder.getOrderDate();
    }

    @Override
    public Order addOrder(int orderNumber, Order order)
            throws FlooringMasteryPersistenceException {
        if (order.getOrderDate().equals(onlyDate)
                && orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders(LocalDate date)
            throws FlooringMasteryPersistenceException {
        List<Order> orders = new ArrayList<>();
        if (date.equals(onlyDate)) {
            orders.add(onlyOrder);
        }
        return orders;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException {
        if (date.equals(onlyDate) && orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        }
        return null;
    }

    @Override
    public Order editOrder(Order order)
            throws FlooringMasteryPersistenceException {
        if (order.getOrderDate().equals(onlyDate)
                && order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        }
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException {
        if (date.equals(onlyDate) && orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        }
        return null;
    }

    @Override
    public int getNextOrderNumber()
            throws FlooringMasteryPersistenceException {
        return onlyOrder.getOrderNumber() + 1;
    }

    @Override
    public void exportAllData()
            throws FlooringMasteryPersistenceException {
        // no-op for the stub
    }
}
