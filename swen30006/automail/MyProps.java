/*
 *  Authors: Group 62
 *  Luke Hedt, Marzuk Amin, William Dean
 *  Date: 20/04/2018
 *
 *  Solution to Part B of the Software Modelling and Design 2018 Project
 */

package automail;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Implements Properties as a Singleton Information Expert
 * (Reduces cohesion to the Simulation Class)
 * Based on:
 * https://stackoverflow.com/questions/4750131/make-java-properties-available-across-classes
 */
public class MyProps extends Properties {

	/* Normally you'd put these in the property file but apprently we can't
		modify the one provided. */
	public static final int LOWEST_FLOOR = 1;
	public static final int MAILROOM_FLOOR = 1;
	// Weights in Grams
	public static final double NORMAL_WEIGHT = 200.0;
	public static final double STDEV_WEIGHT = 700.0;
	public static final int WEIGHT_MAX = 5000;
	public static final int WEAK_WEIGHT_MAX = 2000;
	public static final int LOW_PRIORITY = 10;
	public static final int HIGH_PRIORITY = 100;;
	public static final int STD_TUBESIZE = 4;

	private static final long serialVersionUID = 1L;
	private static MyProps instance = null;

    private MyProps() {
    }

    private static MyProps getInstance() {
        if (instance == null) {
            try {
                instance = new MyProps();
                FileInputStream in = new FileInputStream("automail.properties");
                instance.load(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return instance;
    }

    public static Integer getIntProp(String s) {
    	return Integer.parseInt(MyProps.getInstance().getProperty(s));
    }

    public static Double getDoubleProp(String s) {
    	return Double.parseDouble(MyProps.getInstance().getProperty(s));
    }

    public static Long getLongProp(String s) {
    	return Long.parseLong(MyProps.getInstance().getProperty(s));
    }

    public static String getStrProp(String s) {
    	return MyProps.getInstance().getProperty(s);
    }
}
