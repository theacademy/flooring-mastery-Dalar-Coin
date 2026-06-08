package flooringmastery.dao;

import flooringmastery.model.Order;
import org.junit.jupiter.api.*;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryOrderDaoFileImplTest {

    private FlooringMasteryOrderDao testDao;
    private final String TEST_FOLDER = "TestOrders/";
    private final LocalDate TEST_DATE = LocalDate.of(2025, 6, 1);

    public FlooringMasteryOrderDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        File folder = new File(TEST_FOLDER);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            folder.mkdirs();
        }
        testDao = new FlooringMasteryOrderDaoFileImpl(TEST_FOLDER);
    }

    private Order buildOrder(int number, String name) {
        Order order = new Order(number);
        order.setOrderDate(TEST_DATE);
        order.setCustomerName(name);
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));
        return order;
    }

    @Test
    public void testAddGetOrder() throws Exception {
        Order order = buildOrder(1, "Ada Lovelace");
        testDao.addOrder(1, order);

        Order retrieved = testDao.getOrder(TEST_DATE, 1);
        assertNotNull(retrieved, "Retrieved order should not be null.");
        assertEquals(1, retrieved.getOrderNumber(), "Order number should match.");
        assertEquals("Ada Lovelace", retrieved.getCustomerName(), "Name should match.");
        assertEquals("CA", retrieved.getState(), "State should match.");
        assertEquals(0, new BigDecimal("2381.06").compareTo(retrieved.getTotal()),
                "Total should match.");
    }

    @Test
    public void testGetAllOrders() throws Exception {
        testDao.addOrder(1, buildOrder(1, "Ada Lovelace"));
        testDao.addOrder(2, buildOrder(2, "Charles Babbage"));

        List<Order> all = testDao.getAllOrders(TEST_DATE);
        assertNotNull(all, "Order list should not be null.");
        assertEquals(2, all.size(), "There should be two orders for the date.");
    }

    @Test
    public void testEditOrder() throws Exception {
        testDao.addOrder(1, buildOrder(1, "Ada Lovelace"));

        Order edited = buildOrder(1, "Ada L. Updated");
        testDao.editOrder(edited);

        Order retrieved = testDao.getOrder(TEST_DATE, 1);
        assertEquals("Ada L. Updated", retrieved.getCustomerName(),
                "Edited name should be persisted.");
    }

    @Test
    public void testRemoveOrder() throws Exception {
        testDao.addOrder(1, buildOrder(1, "Ada Lovelace"));
        testDao.addOrder(2, buildOrder(2, "Charles Babbage"));

        Order removed = testDao.removeOrder(TEST_DATE, 1);
        assertNotNull(removed, "Removed order should not be null.");
        assertEquals("Ada Lovelace", removed.getCustomerName(),
                "Removed order should be Ada.");

        assertNull(testDao.getOrder(TEST_DATE, 1), "Order 1 should be gone.");
        assertEquals(1, testDao.getAllOrders(TEST_DATE).size(),
                "One order should remain.");
    }

    @AfterAll
    public static void cleanUp() {
        File folder = new File("TestOrders/");
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            folder.delete();
        }
    }
}