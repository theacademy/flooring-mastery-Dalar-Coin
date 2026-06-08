package flooringmastery.ui;

import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.service.FlooringMasteryDataValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        return io.readInt("Please select from the above choices.", 1, 6);
    }

    // **** Shows the available products so the user can pick a valid product type.
    public Order getNewOrderInfo(List<Product> products) throws FlooringMasteryDataValidationException {
        LocalDate orderDate = io.readLocalDate("Please enter the order date (MM/dd/yyyy):");
        String customerName = io.readString("Please enter the customer name:");
        String state = io.readString("Please enter the state abbreviation:");

        io.print("Available products:");
        if (products != null) {
            for (Product p : products) {
                io.print("  " + p.getProductType()
                        + " | cost/sqft: " + p.getCostPerSquareFoot()
                        + " | labor/sqft: " + p.getLaborCostPerSquareFoot());
            }
        }
        String productType = io.readString("Please enter the product type:");
        BigDecimal area = io.readBigDecimal("Please enter the area (sq ft, min 100):");

        Order currentOrder = new Order();
        currentOrder.setOrderDate(orderDate);
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);
        return currentOrder;
    }

    public LocalDate getOrderDate() throws FlooringMasteryDataValidationException {
        return io.readLocalDate("Please enter the order date (MM/dd/yyyy):");
    }

    public int getOrderNumber() {
        return io.readInt("Please enter the order number:");
    }

    public void displayCreateOrderBanner() { io.print("=== Add Order ==="); }
    public void displayEditOrderBanner()   { io.print("=== Edit Order ==="); }
    public void displayOrdersBanner()      { io.print("=== Display Orders ==="); }
    public void displayRemoveOrderBanner() { io.print("=== Remove Order ==="); }
    public void displaySummaryBanner()     { io.print("=== Summary ==="); }

    public void displayCreateSuccessBanner() {
        io.readString("Order successfully created. Press enter to continue.");
    }
    public void displayEditSuccessBanner() {
        io.readString("Order successfully updated. Press enter to continue.");
    }
    public void displayRemoveSuccessBanner() {
        io.readString("Order successfully removed. Press enter to continue.");
    }
    public void displayExportSuccessBanner() {
        io.readString("All data exported to Backup/DataExport.txt. Press enter to continue.");
    }
    public void displayExitBanner() { io.print("Good Bye!!!"); }
    public void displayUnknownCommandBanner() { io.print("Unknown Command!!!"); }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayOrderList(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            io.print("No orders found for that date.");
        } else {
            for (Order order : orders) {
                io.print("---------------------------------------");
                io.print("Order number: " + order.getOrderNumber());
                io.print("Customer name: " + order.getCustomerName());
                io.print("State: " + order.getState() + "  Tax Rate: " + order.getTaxRate());
                io.print("Product type: " + order.getProductType()
                        + "  cost/sqft: " + order.getCostPerSquareFoot()
                        + "  labor/sqft: " + order.getLaborCostPerSquareFoot());
                io.print("Area: " + order.getArea());
                io.print("Material: " + order.getMaterialCost()
                        + "  Labor: " + order.getLaborCost()
                        + "  Tax: " + order.getTax());
                io.print("Total: " + order.getTotal());
            }
            io.print("---------------------------------------");
        }
        io.readString("Press enter to continue.");
    }

    public Order getEditOrderInfo(Order existing) throws FlooringMasteryDataValidationException {
        String customerName = io.readString("Enter customer name (" + existing.getCustomerName() + "):");
        if (!customerName.isEmpty()) existing.setCustomerName(customerName);

        String state = io.readString("Enter state (" + existing.getState() + "):");
        if (!state.isEmpty()) existing.setState(state);

        String productType = io.readString("Enter product type (" + existing.getProductType() + "):");
        if (!productType.isEmpty()) existing.setProductType(productType);

        String area = io.readString("Enter area (" + existing.getArea() + "):");
        if (!area.isEmpty()) {
            try {
                existing.setArea(new BigDecimal(area.trim()));
            } catch (NumberFormatException e) {
                throw new FlooringMasteryDataValidationException("Area must be a number. Please try again.");
            }
        }
        return existing;
    }

    public boolean confirmNewOrder(Order o) {
        displaySummaryBanner();
        printOrderSummary(o);
        String res = io.readString("Place this order? (y/n)");
        return res.equalsIgnoreCase("y");
    }

    public boolean confirmSaveEdit(Order o) {
        displaySummaryBanner();
        printOrderSummary(o);
        String res = io.readString("Save these changes? (y/n)");
        return res.equalsIgnoreCase("y");
    }

    public boolean confirmRemove(Order o) {
        displaySummaryBanner();
        printOrderSummary(o);
        String res = io.readString("Remove this order? (y/n)");
        return res.equalsIgnoreCase("y");
    }

    private void printOrderSummary(Order order) {
        io.print("Order #: " + order.getOrderNumber());
        io.print("Customer: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate());
        io.print("Product: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Cost/sqft: " + order.getCostPerSquareFoot());
        io.print("Labor/sqft: " + order.getLaborCostPerSquareFoot());
        io.print("Material Cost: " + order.getMaterialCost());
        io.print("Labor Cost: " + order.getLaborCost());
        io.print("Tax: " + order.getTax());
        io.print("Total: " + order.getTotal());
    }
}