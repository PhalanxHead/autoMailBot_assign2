package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.Automail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {

    /** Constant for the mail generator */
    private static int MAIL_TO_CREATE;
    static double totalScore = 0;
    private static IMailDelivery delivery = new ReportDelivery();
    
    public static void main(String[] args) throws IOException {
       
        /* Removed seedMap as was wasteful  */
    	
        Automail automail = new Automail(delivery);
        MAIL_TO_CREATE = Integer.parseInt(MyProps.getProp("Mail_to_Create"));
        MailGenerator generator = new MailGenerator(MAIL_TO_CREATE, automail.mailPool);
        
        /** Initiate all the mail */
        generator.generateAllMail();
        PriorityMailItem priority;
        while(delivery.getDeliveredNum() != generator.MAIL_TO_CREATE) {
        	//System.out.println("-- Step: "+Clock.Time());
            priority = generator.step();
            if (priority != null) {
            	automail.robot1.behaviour.priorityArrival(priority.getPriorityLevel(), priority.weight);
            	automail.robot2.behaviour.priorityArrival(priority.getPriorityLevel(), priority.weight);
            }
            try {
				automail.robot1.step();
				automail.robot2.step();
			} catch (ExcessiveDeliveryException|ItemTooHeavyException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
            Clock.tick();
        }
        printResults();
    }
    
    static double calculateDeliveryScore(MailItem deliveryItem) {
    	// Penalty for longer delivery times
    	final double penalty = Double.parseDouble(MyProps.getProp("Delivery_Penalty"));
    	double priority_weight = 0;
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
    	if(deliveryItem instanceof PriorityMailItem){
    		priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
    	}
        return Math.pow(Clock.time() - deliveryItem.getArrivalTime(),penalty)*(1+Math.sqrt(priority_weight));
    }

    public static void printResults(){
        System.out.println("T: "+Clock.time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.time());
        System.out.printf("Final Score: %.2f%n", totalScore);
    }
}
