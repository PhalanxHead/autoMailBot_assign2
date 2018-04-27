/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

import java.util.*;

/**
 * This class generates the mail.
 * Constants in this class are based on observations of typical mail arrivals
 */
public class MailGenerator {

    public final int MAIL_TO_CREATE;

    private int mailCreated;

    private Random random;
    /** This seed is used to make the behaviour deterministic */

    private boolean complete;
    private IMailPool mailPool;

    private HashMap<Integer,ArrayList<MailItem>> allMail;

    /**
     * Constructor for mail generation
     * @param mailToCreate roughly how many mail items to create
     * @param mailPool where mail items go on arrival
     * @param seed random seed for generating mail
     */
    public MailGenerator(int mailToCreate, IMailPool mailPool){
    	/* I don't particularly like this solution, but it works. */
    	try {
    		this.random = new Random(MyProps.getLongProp("Seed"));

    	} catch(Exception e) {

    		this.random = new Random();
    	}

        // Vary arriving mail by +/-20%. I don't like this
        MAIL_TO_CREATE = mailToCreate*4/5 + random.nextInt(mailToCreate*2/5);
        // System.out.println("Num Mail Items: "+MAIL_TO_CREATE);
        mailCreated = 0;
        complete = false;
        allMail = new HashMap<Integer,ArrayList<MailItem>>();
        this.mailPool = mailPool;
    }

    /**
     * @return a new mail item that needs to be delivered
     */
    private MailItem generateMail(){
        int destFloor = generateDestinationFloor();
        int priorityLevel = generatePriorityLevel();
        int arrivalTime = generateArrivalTime();
        int weight = generateWeight();
        // Check if arrival time has a priority mail
        if(	(random.nextInt(6) > 0) ||  // Skew towards non priority mail
        	(allMail.containsKey(arrivalTime) &&
        	allMail.get(arrivalTime).stream().anyMatch(e -> PriorityMailItem.class.isInstance(e))))
        {
        	return new StdMailItem(destFloor,arrivalTime,weight);
        } else {
        	return new PriorityMailItem(destFloor,arrivalTime,weight,priorityLevel);
        }
    }

    /**
     * @return a destination floor between the ranges of GROUND_FLOOR to FLOOR
     */
    private int generateDestinationFloor(){
        return Building.LOWEST_FLOOR + random.nextInt(Building.FLOORS);
    }

    /**
     * @return a random priority level selected from 10 and 100
     */
    private int generatePriorityLevel(){
    	final Integer LOW_PRI = MyProps.LOW_PRIORITY;
    	final Integer HI_PRI = MyProps.HIGH_PRIORITY;

        return random.nextInt(4) > 0 ? LOW_PRI : HI_PRI;
    }

    /**
     * @return a random weight
     */
    private int generateWeight(){
    	final double mean = MyProps.NORMAL_WEIGHT;
    	final double stddev = MyProps.STDEV_WEIGHT;
    	final int MAX = MyProps.WEIGHT_MAX;
    	double base = random.nextGaussian();
    	if (base < 0) base = -base;
    	int weight = (int) (mean + base * stddev);
        return weight > MAX ? MAX : weight;
    }

    /**
     * @return a random arrival time before the last delivery time
     */
    private int generateArrivalTime(){
        return 1 + random.nextInt(Clock.LAST_DELIVERY_TIME);
    }

    /**
     * Returns a random element from an array
     * @param array of objects
     */
    private Object getRandom(Object[] array){
        return array[random.nextInt(array.length)];
    }

    /**
     * This class initializes all mail and sets their corresponding values,
     */
    public void generateAllMail(){
        while(!complete){
            MailItem newMail =  generateMail();
            int timeToDeliver = newMail.getArrivalTime();
            /** Check if key exists for this time **/
            if(allMail.containsKey(timeToDeliver)){
                /** Add to existing array */
                allMail.get(timeToDeliver).add(newMail);
            }
            else{
                /** If the key doesn't exist then set a new key along with the array of MailItems to add during
                 * that time step.
                 */
                ArrayList<MailItem> newMailList = new ArrayList<MailItem>();
                newMailList.add(newMail);
                allMail.put(timeToDeliver,newMailList);
            }
            /** Mark the mail as created */
            mailCreated++;

            /** Once we have satisfied the amount of mail to create, we're done!*/
            if(mailCreated == MAIL_TO_CREATE){
                complete = true;
            }
        }

    }

    /**
     * While there are steps left, create a new mail item to deliver
     * @return Priority
     */
    public PriorityMailItem step(){
    	PriorityMailItem priority = null;
    	// Check if there are any mail to create
        if(this.allMail.containsKey(Clock.time())){
            for(MailItem mailItem : allMail.get(Clock.time())){
            	if (mailItem instanceof PriorityMailItem) priority = ((PriorityMailItem) mailItem);
                System.out.printf("T: %3d > new addToPool [%s]%n", Clock.time(), mailItem.toString());
                mailPool.addToPool(mailItem);
            }
        }
        return priority;
    }

}
