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
    
    public static String getProp(String s) {
    	return MyProps.getInstance().getProperty(s);
    }
}
