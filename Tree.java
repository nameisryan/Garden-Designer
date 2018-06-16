import java.awt.*;
import java.io.Serializable;

public class Tree implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
    private int x, y;			// The coordinates of the top of the tree
    private String type;		// Current tree type
    private static String[] types = {"Standard",
    								 "Pine"};	// Types of available tree
    private Polygon treePoints;	// All the points in the tree's outline
   
    //default tree
    public Tree(int x, int y) {
    	this(x, y, types[0]);
    }
    
    public Tree(int x, int y, String type) {
    	this.x = x;
        this.y = y;
        this.treePoints = getTypePolygon(type);
    	
    }
   
    public void draw(Graphics g) {
      
        g.setColor(Color.green.darker().darker());
        g.fillPolygon(treePoints);
      
    }
    
    public Polygon getTypePolygon(String type) {
    	Polygon newPolygon;
    	int[] xs, ys;
    	
    	switch(type) {
    		
    		case "Pine":
    			y = y - 20;
    			xs = new int[] { x, x+7, x+3, x+10, x+3, x+3, x-3, x-3, x-10, x-3, x-7 };
    	        ys = new int[] { y, y+12, y+12, y+24, y+24, y+30, y+30, y+24, y+24, y+12, y+12 };
    	        newPolygon = new Polygon(xs, ys, 11);
    	        break;
    		case "Standard":
    		default:
    			y = y - 20;
    			xs = new int[] { x, x+10, x+3, x+3, x-3, x-3, x-10 };
    	        ys = new int[] { y, y+20, y+15, y+30, y+30, y+15, y+20 };
    	        newPolygon = new Polygon(xs, ys, 7);
    	        break;
    	}
    	
        return newPolygon;
    }
    
    public String getType() {
    	return this.type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
    public static String[] getTypes() {
    	return types;
    }
    
}
