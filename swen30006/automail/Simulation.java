package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
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
    private static ArrayList<MailItem> MAIL_DELIVERED;
    private static double total_score = 0;

	public static Properties amProperties;
    
    public static void main(String[] args) throws IOException {

    	/* Instantiate Properties */
    	amProperties = new Properties();
		FileReader inStream = null;
		
		try {
			inStream = new FileReader("automail.properties");
			amProperties.load(inStream);
			
		/* No File? */
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.err.printf("ERROR: Properties File not found!");
			
		/* Something worrisome this way comes*/
		} catch(Exception e) {
			e.printStackTrace();
			System.err.printf("ERROR: Something is wrong");
			
		} finally {
			 if (inStream != null) {
	                inStream.close();
            }
		}


        MAIL_DELIVERED = new ArrayList<MailItem>();
       
        /* Removed seedMap as was wasteful  */
    	
        Automail automail = new Automail(new ReportDelivery());
        MAIL_TO_CREATE = Integer.parseInt(amProperties.getProperty("Mail_to_Create"));
        MailGenerator generator = new MailGenerator(MAIL_TO_CREATE, automail.mailPool);
        
        /** Initiate all the mail */
        generator.generateAllMail();
        PriorityMailItem priority;
        while(MAIL_DELIVERED.size() != generator.MAIL_TO_CREATE) {
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
            Clock.Tick();
        }
        printResults();
    }
    
    static class ReportDelivery implements IMailDelivery {
    	
    	/** Confirm the delivery and calculate the total score */
    	public void deliver(MailItem deliveryItem){
    		if(!MAIL_DELIVERED.contains(deliveryItem)){
                System.out.printf("T: %3d > Delivered     [%s]%n", Clock.Time(), deliveryItem.toString());
    			MAIL_DELIVERED.add(deliveryItem);
    			// Calculate delivery score
    			total_score += calculateDeliveryScore(deliveryItem);
    		}
    		else{
    			try {
    				throw new MailAlreadyDeliveredException();
    			} catch (MailAlreadyDeliveredException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    }
    
    private static double calculateDeliveryScore(MailItem deliveryItem) {
    	// Penalty for longer delivery times
    	final double penalty = Double.parseDouble(amProperties.getProperty("Delivery_Penalty"));
    	double priority_weight = 0;
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
    	if(deliveryItem instanceof PriorityMailItem){
    		priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
    	}
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),penalty)*(1+Math.sqrt(priority_weight));
    }

    public static void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", total_score);
    }
}
