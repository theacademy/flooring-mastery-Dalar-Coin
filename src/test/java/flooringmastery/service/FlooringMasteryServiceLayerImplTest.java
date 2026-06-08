package flooringmastery.service;

import flooringmastery.dao.*;
import flooringmastery.model.Order;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryServiceLayerImplTest {

    private FlooringMasteryServiceLayer service;

    public FlooringMasteryServiceLayerImplTest() {
        // Hand-wire the service with in-memory stub DAOs (no Spring, no files).
        FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoStubImpl();
        FlooringMasteryTaxesDao taxesDao = new FlooringMasteryTaxesDaoStubImpl();
        FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoStubImpl();
        service = new FlooringMasteryServiceLayerImpl(orderDao, taxesDao, productDao);
    }

    private LocalDate futureDate() {
        return LocalDate.now().plusDays(10);
    }

    private Order validOrder() {
        Order order = new Order();
        order.setOrderDate(futureDate());
        order.setCustomerName("Charles Babbage");
        order.setState("CA");
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        return order;
    }

    @Test
    public void testCreateValidOrder() {
        Order order = validOrder();
        try {
            service.createOrder(order);
        } catch (FlooringMasteryDataValidationException
                 | FlooringMasteryPersistenceException e) {
            fail("Order was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testCalculateOrderMath() throws Exception {
        // Spec sample: CA (25%), Tile (3.50 / 4.15), area 249
        // Material = 249 * 3.50 = 871.50
        // Labor    = 249 * 4.15 = 1033.35
        // Tax      = (871.50 + 1033.35) * 0.25 = 476.21
        // Total    = 871.50 + 1033.35 + 476.21 = 2381.06
        Order order = validOrder();
        service.calculateOrder(order);

        assertEquals(0, new BigDecimal("871.50").compareTo(order.getMaterialCost()),
                "Material cost should be 871.50.");
        assertEquals(0, new BigDecimal("1033.35").compareTo(order.getLaborCost()),
                "Labor cost should be 1033.35.");
        assertEquals(0, new BigDecimal("476.21").compareTo(order.getTax()),
                "Tax should be 476.21.");
        assertEquals(0, new BigDecimal("2381.06").compareTo(order.getTotal()),
                "Total should be 2381.06.");
    }

    @Test
    public void testCreateOrderInvalidState() {
        Order order = validOrder();
        order.setState("ZZ"); // not in the stub tax data
        try {
            service.createOrder(order);
            fail("Expected ValidationException was not thrown for bad state.");
        } catch (FlooringMasteryPersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryDataValidationException e) {
            return; // expected
        }
    }

    @Test
    public void testCreateOrderAreaTooSmall() {
        Order order = validOrder();
        order.setArea(new BigDecimal("50")); // below 100 minimum
        try {
            service.createOrder(order);
            fail("Expected ValidationException was not thrown for small area.");
        } catch (FlooringMasteryPersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryDataValidationException e) {
            return; // expected
        }
    }

    @Test
    public void testGetOrder() throws Exception {
        // The stub holds order #1 on 2025-06-01.
        LocalDate stubDate = LocalDate.of(2025, 6, 1);
        Order found = service.getOrder(stubDate, 1);
        assertNotNull(found, "Order #1 on 2025-06-01 should exist in the stub.");
        assertEquals("Ada Lovelace", found.getCustomerName(),
                "Stub order should belong to Ada Lovelace.");

        Order missing = service.getOrder(stubDate, 99);
        assertNull(missing, "Order #99 should not exist.");
    }
}