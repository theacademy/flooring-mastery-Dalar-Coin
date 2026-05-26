package flooringmastery.view;

import flooringmastery.dao.FlooringMasteryDao;
import flooringmastery.dao.FlooringMasteryDaoFileImpl;
import flooringmastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FlooringMasteryView {
    private UserIO io = new UserIOConsoleImpl();

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Display orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 5);
    }

    public Order getNewOrderInfo() {
        String customerName = io.readString("Please enter Customer Name");
        String state = io.readString("Please enter the State Abbreviation");
        String productType = io.readString("Please enter Product Type");
        BigDecimal area = new BigDecimal(io.readString("Please enter Area in sq ft"));
        Order currentOrder = new Order();
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);
        return currentOrder;
    }

    public LocalDate getOrderDate() {
        String dateString = io.readString("Please enter Order Date (MM/dd/yyyy)");
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    private FlooringMasteryDao dao = new FlooringMasteryDaoFileImpl();

    public void displayCreateStudentBanner() {
        io.print("=== Create Student ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("Student successfully created.  Please hit enter to continue");
    }
}
