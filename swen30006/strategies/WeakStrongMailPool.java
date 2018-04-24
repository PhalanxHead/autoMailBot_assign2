package strategies;

import java.util.*;

import automail.Building;
import automail.IMailPool;
import automail.MailItem;
import automail.StdMailItem;
import automail.MyProps;
import automail.PriorityMailItem;
import automail.Simulation;
import automail.StorageTube;
import exceptions.TubeFullException;

public class WeakStrongMailPool implements IMailPool{
	private LinkedList<MailItem> lighter;  // weak robot will take this set
	private LinkedList<MailItem> heavier;  // strong robot will take this set
	private int divider;
	private static final int WEAK_MAX_WEIGHT = Integer.parseInt(MyProps.getProp("Weak_Weight_Max"));

	public WeakStrongMailPool(){
		// Start empty
		lighter = new LinkedList<MailItem>();
		heavier = new LinkedList<MailItem>();
		divider = Building.FLOORS / 2;  // Top normal floor for strong robot
	}

	private int priority(MailItem m) {
		return (m instanceof PriorityMailItem) ? ((PriorityMailItem) m).getPriorityLevel() : 0;
	}
	
	public void addToPool(MailItem mailItem) {
		// This doesn't attempt to put the re-add items back in time order but there will be relatively few of them,
		// from the strong robot only, and only when it is recalled with undelivered items.
		// Check whether mailItem is for strong robot
		if (mailItem instanceof PriorityMailItem || mailItem.getWeight() > WEAK_MAX_WEIGHT || mailItem.getDestFloor() <= divider) {
			if (mailItem instanceof PriorityMailItem) {  // Add in priority order
				int priority = ((PriorityMailItem) mailItem).getPriorityLevel();
				ListIterator<MailItem> i = heavier.listIterator();
				while (i.hasNext()) {
					if (priority(i.next()) < priority) {
						i.previous();
						i.add(mailItem);
						return; // Added it - done
					}
				}
			}
			
			heavier.addLast(mailItem); // Just add it on the end of the lower (strong robot) list
			
		} else{
			lighter.addLast(mailItem); // Just add it on the end of the upper (weak robot) list
		}
	}
	
	@Override
	public MailItem getMail(int maxWeight) {
		
		try {
			if((maxWeight > WEAK_MAX_WEIGHT) && (heavier.size() > 0)) {
				return heavier.remove();
				
			} else if(lighter.size() > 0) {
				return lighter.remove();
				
			} else {
				return null;
			}
		} catch(Exception e) {
			/* Something weird went wrong */
			e.printStackTrace();
			return null;
		}
	}
}
