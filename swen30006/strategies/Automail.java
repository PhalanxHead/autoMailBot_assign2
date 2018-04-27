/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package strategies;

import automail.IMailDelivery;
import automail.IMailPool;
import automail.IRobotBehaviour;
import automail.MyProps;
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

    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(MyProps.WEAK_WEIGHT_MAX);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(MyProps.WEIGHT_MAX);

    	/** Initialize robot */
    	/* shared behaviour because identical and stateless */
    	robot1 = new WeakRobot(robotBehaviourW, delivery, mailPool, MyProps.WEAK_WEIGHT_MAX);
    	robot2 = new StrongRobot(robotBehaviourS, delivery, mailPool, MyProps.WEIGHT_MAX);
    }

}
