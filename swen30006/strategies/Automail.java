package strategies;

import automail.IMailDelivery;
import automail.IMailPool;
import automail.IRobotBehaviour;
import automail.Robot;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    public IMailDelivery delivery;
    
    public Automail() {
    	/* Initialize the MailPool */
    	mailPool = new WeakStrongMailPool();
    	
    	/* Initialise the delivery */
    	delivery = new ReportDelivery();
    	
        /* Initialize the RobotAction */
    	// Can't handle more than 2000 grams
    	boolean weak = false;
    	// Can handle any weight that arrives at the building
    	boolean strong = true; 
    	
    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    	    	
    	/** Initialize robot */
    	robot1 = new Robot(robotBehaviourW, delivery, mailPool, weak); /* shared behaviour because identical and stateless */
    	robot2 = new Robot(robotBehaviourS, delivery, mailPool, strong);
    }
    
}
