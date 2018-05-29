import java.awt.*;
import java.io.Serializable;

public class Tree implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
    // The coordinates of the top of the tree
    private int x, y, type;
    
    // All the points in the tree's outline
    private Polygon treePoints;
   
    //default tree
    public Tree(int x, int y) {
    	this(x,y,1);
    }
    
    public Tree(int x, int y, int type) {
    	
    	this.x = x;
        this.y = y;
        this.type = type;
    	
    	//tree 1
    	if(type==1) {
            int[] xs = { x, x+10, x+3, x+3, x-3, x-3, x-10 };
            int[] ys = { y, y+20, y+15, y+30, y+30, y+15, y+20 };
            treePoints = new Polygon(xs, ys, 7);
    	}
        
    	//tree 2
    	else if(type==2) {
            int[] xs = { x, x+7, x+3, x+10, x+3, x+3, x-3, x-3, x-10, x-3, x-7 };
            int[] ys = { y, y+12, y+12, y+24, y+24, y+30, y+30, y+24, y+24, y+12, y+12 };
            treePoints = new Polygon(xs, ys, 11);
    	}
    	
   
    }
   
    public void draw(Graphics g) {
      
        g.setColor(Color.green.darker().darker());
        g.fillPolygon(treePoints);
      
    }
    
    public void setType(int type) {
    	this.type = type;
    }
   
}
