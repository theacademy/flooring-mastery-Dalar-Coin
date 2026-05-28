package FlooringMastery;

import FlooringMastery.controller.ClassRosterController;
import FlooringMastery.dao.ClassRosterAuditDao;
import FlooringMastery.dao.ClassRosterAuditDaoFileImpl;
import FlooringMastery.dao.ClassRosterDaoFileImpl;
import FlooringMastery.dao.classRosterDao;
import FlooringMastery.service.ClassRosterServiceLayer;
import FlooringMastery.service.ClassRosterServiceLayerImpl;
import FlooringMastery.ui.ClassRosterView;
import FlooringMastery.ui.UserIO;
import FlooringMastery.ui.UserIOConsoleImpl;
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
        ClassRosterController controller =
                ctx.getBean("controller", ClassRosterController.class);
        controller.run();
    }
}
