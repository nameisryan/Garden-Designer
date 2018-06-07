import java.awt.*;
import java.io.Serializable;
import java.awt.geom.*;

// TODO: Change each item to draw lake around x and y coordinate.

/*
 * Create constructor(s)
 * Create types of pond
 * Create "getters and setters" for type
 * 
 */

public class Pond implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int x, y;		//pond x/y coordinates.
	private int width, height;
	private String size;	//Current pond size
	private static String[] sizes = {"Small", "Large", "Lake", "Ocean"};
	
	
	
	public Pond(int x, int y) {
    	this(x, y, "");
    }
	
	
	public Pond(int x, int y, String size) {
		this.x = x;
		this.y = y;
		this.setSize(size);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(x,y,width,height);
	}
	
	
	
	 public String getSize() {
	    	return this.size;
	    }
	    
	 public void setSize(String size) {
	    	
	    switch(size) {
	    	default:
	    	case "Small":
	    		this.width = 12;
	    		this.height = 7;
	    		break;
	    	case "Large":
	    		this.width = 25;
	    		this.height = 15;
	    		break;
	    	case "Lake":
	    		this.width = 70;
	    		this.height = 55;
	    		break;
	    	case "Ocean":
	    		this.width = 300;
	    		this.height = 250;
	    		break;
	    	}
	    this.size = size;
	 }
	    
	 public static String[] getSizes() {
	   	return sizes;
	 }
	
	
	
}
