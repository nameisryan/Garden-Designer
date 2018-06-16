import java.awt.*;
import java.io.Serializable;
import java.awt.geom.*;

public class Pond implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int x, y;				// The coordinates of the top left of the pond
	private int width, height;		// Width and height of the pond
	private String size;			// Current pond size
	private static String[] sizes = {"Small", "Large", "Lake", "Ocean"};  // Sizes of ponds available
	
    
	 	/**
	     * Gets x, y coordinates.
	     */
	 
	
	public Pond(int x, int y) {
    	this(x, y, "");
    }
	
    
	 	/**
	     * Sets variable parameters.
	     */
	 
	
	public Pond(int x, int y, String size) {
		this.x = x;
		this.y = y;
		this.setSize(size);
	}
	
    
	 	/**
	     * Paints pond.
	     */
	 
	
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(x,y,width,height);
	}
	
    
	 	/**
	     * Handle getting of size from menu.
	     */
	 
	
	 public String getSize() {
	    	return this.size;
	    }
	    
	 	/**
	     * Handle pond size and position update.
	     */
	 
	 public void setSize(String size) {
	    	
	    switch(size) {
	    	default:
	    	case "Small":
	    		this.width = 12;
	    		this.height = 8;
	    		x = x - 6;
	    		y = y - 4;
	    		break;
	    	case "Large":
	    		this.width = 26;
	    		this.height = 16;
	    		x = x - 13;
	    		y = y - 8;
	    		break;
	    	case "Lake":
	    		this.width = 70;
	    		this.height = 56;
	    		x = x - 35;
	    		y = y - 28;
	    		break;
	    	case "Ocean":
	    		this.width = 300;
	    		this.height = 250;
	    		x = x - 150;
	    		y = y - 125;
	    		break;
	    	}
	    this.size = size;
	 }
	    
	 public static String[] getSizes() {
	   	return sizes;
	 }
	
	
	
}
