package strategies;

import java.util.ArrayList;

import automail.Clock;
import automail.IMailDelivery;
import automail.MailItem;
import automail.Simulation;
import exceptions.MailAlreadyDeliveredException;


/**
 * Mail Delivery Implementation
 */
class ReportDelivery implements IMailDelivery {
	
	private ArrayList<MailItem> deliveredMail;
	
	public ReportDelivery() {
		this.deliveredMail = new ArrayList<>();
	}
	
	
	/** 
	 * Confirm the delivery and calculate the total score 
	 */
	@Override
	public void deliver(MailItem deliveryItem){
		if(!deliveredMail.contains(deliveryItem)){
            System.out.printf("T: %3d > Delivered     [%s]%n", Clock.time(), deliveryItem.toString());
            deliveredMail.add(deliveryItem);
			// Calculate delivery score
            updateTotalScore(deliveryItem);
		}
		else{
			try {
				throw new MailAlreadyDeliveredException();
			} catch (MailAlreadyDeliveredException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int getDeliveredNum() {
		return deliveredMail.size();
	}
	
	/**
	 * Updates the total score of the system
	 * @param deliveryItem The MailItem that was delivered
	 */
	private void updateTotalScore(MailItem deliveryItem) {
		Simulation.totalScore += Simulation.calculateDeliveryScore(deliveryItem);
	}

}