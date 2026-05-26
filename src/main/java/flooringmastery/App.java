package flooringmastery;//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import flooringmastery.controller.FlooringMasteryController;

public class App {

    public static void main(String[] args) {
        FlooringMasteryController controller = new FlooringMasteryController();
        controller.run();
    }
}
