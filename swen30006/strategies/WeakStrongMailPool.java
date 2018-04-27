/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

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
	private LinkedList<MailItem> upper;  // weak robot will take this set
	private LinkedList<MailItem> lower;  // strong robot will take this set
	private int divider;
	private static final int WEAK_WEIGHT_MAX = MyProps.WEAK_WEIGHT_MAX;

	public WeakStrongMailPool(){
		// Start empty
		upper = new LinkedList<MailItem>();
		lower = new LinkedList<MailItem>();
		divider = Building.FLOORS / 2;  // Top normal floor for strong robot
	}

	private int priority(MailItem m) {
		return (m instanceof PriorityMailItem) ? ((PriorityMailItem) m).getPriorityLevel() : 0;
	}

	@Override
	public void addToPool(MailItem mailItem) {
		// This doesn't attempt to put the re-add items back in time order but there will be relatively few of them,
		// from the strong robot only, and only when it is recalled with undelivered items.

		// Check whether mailItem is for strong robot
		if (mailItem instanceof PriorityMailItem || mailItem.getWeight() > WEAK_WEIGHT_MAX || mailItem.getDestFloor() <= divider) {
			if (mailItem instanceof PriorityMailItem) {  // Add in priority order
				int priority = ((PriorityMailItem) mailItem).getPriorityLevel();
				ListIterator<MailItem> i = lower.listIterator();
				while (i.hasNext()) {
					if (priority(i.next()) < priority) {
						i.previous();
						i.add(mailItem);
						return; // Added it - done
					}
				}
			}

			lower.addLast(mailItem); // Just add it on the end of the lower (strong robot) list

		} else{
			upper.addLast(mailItem); // Just add it on the end of the upper (weak robot) list
		}
	}

	@Override
	public MailItem getMail(int maxWeight) {

		try {
			if((maxWeight > WEAK_WEIGHT_MAX) && (lower.size() > 0)) {
				return lower.remove();

			} else if(upper.size() > 0) {
				return upper.remove();

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
