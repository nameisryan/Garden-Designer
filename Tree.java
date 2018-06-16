import java.awt.*;
import java.io.Serializable;

public class Tree implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The coordinates of the top of the tree
	 */
    private int x, y;
    
    /**
     * Current tree type
     */
    private String type;
    
    /**
     * Types of available tree
     */
    private static String[] types = {"Standard",
    								 "Pine"};
    
    /**
     * All the points in the tree's outline
     */
    private Polygon treePoints;
   
    
    /**
     * Specify tree position, set default tree type.
     * @param x Horizontal position
     * @param y Vertical position
     */
    public Tree(int x, int y) {
    	this(x, y, types[0]);
    }
    
    
    /**
     * Specify a type, as well as position.
     * @param x Horizontal position
     * @param y Vertical position
     * @param type String as returned from getTypes()
     */
    public Tree(int x, int y, String type) {
    	this.x = x;
        this.y = y;
        this.treePoints = getTypePolygon(type);
    	
    }
    
    public void draw(Graphics g) {
      
        g.setColor(Color.green.darker().darker());
        g.fillPolygon(treePoints);
      
    }
    
    /**
     * Fetch polygon object for given tree type.
     * @param type String as returned from getTypes()
     * @return Polygon
     */
    
    public Polygon getTypePolygon(String type) {
    	Polygon newPolygon;
    	int[] xs, ys;
    	
    	switch(type) {
    		
    		case "Pine":
    			xs = new int[] { x, x+7, x+3, x+10, x+3, x+3, x-3, x-3, x-10, x-3, x-7 };
    	        ys = new int[] { y, y+12, y+12, y+24, y+24, y+30, y+30, y+24, y+24, y+12, y+12 };
    	        newPolygon = new Polygon(xs, ys, 11);
    	        break;
    		case "Standard":
    		default:
    			xs = new int[] { x, x+10, x+3, x+3, x-3, x-3, x-10 };
    	        ys = new int[] { y, y+20, y+15, y+30, y+30, y+15, y+20 };
    	        newPolygon = new Polygon(xs, ys, 7);
    	        break;
    	}
    	
        return newPolygon;
    }
    
    
    /**
     * Fetch tree type of this instance of Tree.
     * @return String - Type of tree
     */
    
    public String getType() {
    	return this.type;
    }
    
    
    /**
     * Set this instance's tree type.
     * @param type String as returned from getTypes()
     */
    
    public void setType(String type) {
    	this.type = type;
    }
    
    
    /**
     * Fetch all available tree types.
     * @return String[] - Tree types
     */
    
    public static String[] getTypes() {
    	return types;
    }
    
}
