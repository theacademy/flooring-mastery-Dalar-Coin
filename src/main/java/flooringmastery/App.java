package flooringmastery;

import flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        // UserIO myIo = new UserIOConsoleImpl();
        // ClassRosterView myView = new ClassRosterView(myIo);
        // ClassRosterDao myDao = new ClassRosterDaoFileImpl();
        // ClassRosterAuditDao myAuditDao =
        //       new ClassRosterAuditDaoFileImpl();
        // ClassRosterServiceLayer myService =
        //       new ClassRosterServiceLayerImpl(myDao, myAuditDao);
        // ClassRosterController controller =
        //       new ClassRosterController(myService, myView);
        // controller.run();

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller =
                ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}
