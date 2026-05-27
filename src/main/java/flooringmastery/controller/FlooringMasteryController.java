package flooringmastery.controller;


import flooringmastery.dao.FlooringMasteryDao;
import flooringmastery.dao.FlooringMasteryDaoFileImpl;
import flooringmastery.model.Order;
import flooringmastery.view.FlooringMasteryView;
import flooringmastery.view.UserIO;
import flooringmastery.view.UserIOConsoleImpl;

public class FlooringMasteryController {

    private FlooringMasteryView view = new FlooringMasteryView();
    private FlooringMasteryDao dao = new FlooringMasteryDaoFileImpl();
    private UserIO io = new UserIOConsoleImpl();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    io.print("DISPLAY ORDERS");
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    io.print("EDIT AN ORDER");
                    break;
                case 4:
                    io.print("REMOVE AN ORDER");
                    break;
                case 5:
                    io.print("EXPORT ALL ORDERS");
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    io.print("UNKNOWN COMMAND");
            }

        }
        io.print("GOOD BYE");
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createOrder() {
        view.displayCreateOrderBanner();
        Order newOrder = view.getNewOrderInfo();
        dao.addOrder(newOrder.getOrderDate(), newOrder);
        view.displayCreateSuccessBanner();
    }
}
