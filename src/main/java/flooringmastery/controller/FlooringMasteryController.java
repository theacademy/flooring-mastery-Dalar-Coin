package flooringmastery.controller;

import flooringmastery.dao.FlooringMasteryPersistenceException;
import flooringmastery.model.Order;
import flooringmastery.service.FlooringMasteryDataValidationException;
import flooringmastery.service.FlooringMasteryServiceLayer;
import flooringmastery.ui.FlooringMasteryView;

import java.time.LocalDate;
import java.util.List;

public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection;
        try {
            while (keepGoing) {
                menuSelection = view.printMenuAndGetSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        view.displayUnknownCommandBanner();
                }
            }
            view.displayExitBanner();
        } catch (FlooringMasteryPersistenceException | FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void displayOrders()
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {
        view.displayOrdersBanner();
        LocalDate date = view.getOrderDate();
        List<Order> orders = service.getAllOrders(date);
        view.displayOrderList(orders);
    }

    private void addOrder() throws FlooringMasteryPersistenceException {
        view.displayCreateOrderBanner();
        boolean hasErrors;
        do {
            hasErrors = false;
            try {
                Order currentOrder = view.getNewOrderInfo(service.getAllProducts());
                service.calculateOrder(currentOrder); // validates + computes preview
                boolean confirm = view.confirmNewOrder(currentOrder);
                if (confirm) {
                    service.createOrder(currentOrder);
                    view.displayCreateSuccessBanner();
                }
            } catch (FlooringMasteryDataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
    }

    private void editOrder()
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {
        view.displayEditOrderBanner();
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();

        Order existing = service.getOrder(date, orderNumber);
        if (existing == null) {
            view.displayErrorMessage("No order found for that date and order number.");
            return;
        }

        boolean hasErrors;
        do {
            hasErrors = false;
            try {
                Order edited = view.getEditOrderInfo(existing);
                boolean save = view.confirmSaveEdit(edited);
                if (save) {
                    service.editOrder(edited);
                    view.displayEditSuccessBanner();
                }
            } catch (FlooringMasteryDataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
    }

    private void removeOrder()
            throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {
        view.displayRemoveOrderBanner();
        LocalDate date = view.getOrderDate();
        int remOrderNumber = view.getOrderNumber();

        Order orderToRem = service.getOrder(date, remOrderNumber);
        if (orderToRem == null) {
            view.displayErrorMessage("No order found for that date and order number.");
            return;
        }
        boolean confirm = view.confirmRemove(orderToRem);
        if (confirm) {
            service.removeOrder(orderToRem);
            view.displayRemoveSuccessBanner();
        }
    }

    private void exportData() throws FlooringMasteryPersistenceException {
        service.exportAllData();
        view.displayExportSuccessBanner();
    }
}