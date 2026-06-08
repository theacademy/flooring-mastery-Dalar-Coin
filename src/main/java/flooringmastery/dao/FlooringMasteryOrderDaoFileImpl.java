package flooringmastery.dao;

import flooringmastery.model.Order;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao {

    public final String ORDERS_FOLDER;
    public static final String DELIMITER = ",";
    private final DateTimeFormatter FILE_DATE =
            DateTimeFormatter.ofPattern("MMddyyyy");

    private Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

    public FlooringMasteryOrderDaoFileImpl() {
        ORDERS_FOLDER = "src/main/java/flooringmastery/Orders/";
    }

    public FlooringMasteryOrderDaoFileImpl(String ordersFolder) {
        ORDERS_FOLDER = ordersFolder;
    }

    // ---- Order impls ----

    @Override
    public Order addOrder(int orderNumber, Order order)
            throws FlooringMasteryPersistenceException {
        loadOrders(order.getOrderDate());
        Map<Integer, Order> ordersForDate =
                orders.computeIfAbsent(order.getOrderDate(), d -> new HashMap<>());
        Order previous = ordersForDate.put(orderNumber, order);
        writeOrders(order.getOrderDate());
        return previous;
    }

    @Override
    public List<Order> getAllOrders(LocalDate date)
            throws FlooringMasteryPersistenceException {
        loadOrders(date);
        Map<Integer, Order> ordersFromDate = orders.get(date);
        if (ordersFromDate == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(ordersFromDate.values());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException {
        loadOrders(date);
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) {
            return null;
        }
        return ordersForDate.get(orderNumber);
    }

    @Override
    public Order editOrder(Order order)
            throws FlooringMasteryPersistenceException {
        loadOrders(order.getOrderDate());
        Map<Integer, Order> ordersForDate = orders.get(order.getOrderDate());
        if (ordersForDate == null) {
            return null;
        }
        Order edited = ordersForDate.put(order.getOrderNumber(), order);
        writeOrders(order.getOrderDate());
        return edited;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber)
            throws FlooringMasteryPersistenceException {
        loadOrders(date);
        Map<Integer, Order> ordersForTheDate = orders.get(date);
        if (ordersForTheDate == null) {
            return null;
        }
        Order removedOrder = ordersForTheDate.remove(orderNumber);
        writeOrders(date);
        return removedOrder;
    }

    @Override
    public int getNextOrderNumber()
            throws FlooringMasteryPersistenceException {
        loadAllOrders();
        int max = 0;
        for (Map<Integer, Order> forDate : orders.values()) {
            for (int num : forDate.keySet()) {
                if (num > max) {
                    max = num;
                }
            }
        }
        return max + 1;
    }

    @Override
    public void exportAllData()
            throws FlooringMasteryPersistenceException {
        loadAllOrders();

        String backupFolder = "Backup/";
        File folder = new File(backupFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = backupFolder + "DataExport.txt";

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not export data.", e);
        }

        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
                + "LaborCost,Tax,Total,OrderDate");

        // Sort by date, then order number, for a stable export
        List<LocalDate> dates = new ArrayList<>(orders.keySet());
        Collections.sort(dates);
        for (LocalDate date : dates) {
            List<Integer> nums = new ArrayList<>(orders.get(date).keySet());
            Collections.sort(nums);
            for (int num : nums) {
                Order o = orders.get(date).get(num);
                out.println(marshallOrder(o) + DELIMITER + o.getOrderDate().format(FILE_DATE));
            }
        }
        out.flush();
        out.close();
    }

    // ---- marshall / unmarshall ----

    private Order unmarshallOrder(String orderAsText) {
        // Only CustomerName may contain commas, so parse fixed fields from
        // both ends and rejoin the middle as the name.
        // Layout: [0]=orderNumber, [1..n-11]=customerName,
        //         then State, TaxRate, ProductType, Area, CostPerSqFt,
        //         LaborCostPerSqFt, MaterialCost, LaborCost, Tax, Total (10 trailing).
        String[] t = orderAsText.split(DELIMITER, -1);
        Order order = new Order();
        order.setOrderNumber(Integer.parseInt(t[0].trim()));

        int trailing = 10;
        int nameEnd = t.length - trailing; // exclusive
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < nameEnd; i++) {
            if (i > 1) name.append(DELIMITER);
            name.append(t[i]);
        }
        order.setCustomerName(name.toString());

        int idx = nameEnd;
        order.setState(t[idx++]);
        order.setTaxRate(new BigDecimal(t[idx++]));
        order.setProductType(t[idx++]);
        order.setArea(new BigDecimal(t[idx++]));
        order.setCostPerSquareFoot(new BigDecimal(t[idx++]));
        order.setLaborCostPerSquareFoot(new BigDecimal(t[idx++]));
        order.setMaterialCost(new BigDecimal(t[idx++]));
        order.setLaborCost(new BigDecimal(t[idx++]));
        order.setTax(new BigDecimal(t[idx++]));
        order.setTotal(new BigDecimal(t[idx]));
        return order;
    }

    private String marshallOrder(Order order) {
        return order.getOrderNumber() + DELIMITER
                + order.getCustomerName() + DELIMITER
                + order.getState() + DELIMITER
                + order.getTaxRate() + DELIMITER
                + order.getProductType() + DELIMITER
                + order.getArea() + DELIMITER
                + order.getCostPerSquareFoot() + DELIMITER
                + order.getLaborCostPerSquareFoot() + DELIMITER
                + order.getMaterialCost() + DELIMITER
                + order.getLaborCost() + DELIMITER
                + order.getTax() + DELIMITER
                + order.getTotal();
    }

    // ---- load / write ----

    private void loadOrders(LocalDate date)
            throws FlooringMasteryPersistenceException {
        String fileName = ORDERS_FOLDER + "Orders_" + date.format(FILE_DATE) + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            orders.remove(date);
            return;
        }

        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load order data into memory.", e);
        }

        Map<Integer, Order> ordersForDate = new HashMap<>();
        if (sc.hasNextLine()) {
            sc.nextLine(); // header
        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) continue;
            Order currentOrder = unmarshallOrder(line);
            currentOrder.setOrderDate(date);
            ordersForDate.put(currentOrder.getOrderNumber(), currentOrder);
        }
        sc.close();
        orders.put(date, ordersForDate);
    }

    // Loads every Orders_*.txt found in the Orders folder.
    private void loadAllOrders()
            throws FlooringMasteryPersistenceException {
        File folder = new File(ORDERS_FOLDER);
        File[] files = folder.listFiles(
                (dir, fname) -> fname.startsWith("Orders_") && fname.endsWith(".txt"));
        if (files == null) {
            return;
        }
        for (File f : files) {
            String fname = f.getName(); // Orders_MMDDYYYY.txt
            String datePart = fname.substring("Orders_".length(), fname.length() - ".txt".length());
            try {
                LocalDate date = LocalDate.parse(datePart, FILE_DATE);
                loadOrders(date);
            } catch (Exception e) {
                // skip files that don't match the expected name pattern
            }
        }
    }

    private void writeOrders(LocalDate date)
            throws FlooringMasteryPersistenceException {
        String fileName = ORDERS_FOLDER + "Orders_" + date.format(FILE_DATE) + ".txt";

        File folder = new File(ORDERS_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null || ordersForDate.isEmpty()) {
            File f = new File(fileName);
            if (f.exists()) {
                f.delete();
            }
            return;
        }

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not save order data.", e);
        }

        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
                + "LaborCost,Tax,Total");
        for (Order currentOrder : ordersForDate.values()) {
            out.println(marshallOrder(currentOrder));
        }
        out.flush();
        out.close();
    }
}