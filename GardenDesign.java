import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * <h1>Garden Designer</h1>
 * 
 * 
 * 
 * @author Chris Hodgen, Ryan Ritchie
 *
 */



//http://www.cs.stir.ac.uk/~sbj/examples/Java-applications/SimpleGardenDesign/

public class GardenDesign extends JFrame
                          implements MouseListener {
	
	/* Removed array and replaced with ArrayList object
		This enables an "endless" amount of trees to be added
	*/
	
    private static final Boolean DEBUG = true;
    private ArrayList<Tree> trees = new ArrayList<Tree>();
    
    private DrawingPanel theGarden;
    
    /**
     * Main method which invokes the class.
     * @param args Not used.
     * 
     */
    
    public static void main(String[] args) {
        
        GardenDesign designer = new GardenDesign();
        designer.createGUI();
        designer.setVisible(true);
        
    }
    
    
    /**
     * Used to set up the window properties.
     */
    
    public void createGUI() {
    	createMenu();
    	
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch(Exception e) {
    		System.out.println("Could not set look and feel.\nError: " + e + "\n");
    	}
    	
        setTitle("Garden designer");
        setSize(800,600);
        setLocation(200,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        
        window.add(createToolBar(), BorderLayout.EAST);
        
        JLabel title = new JLabel("My Beautiful Garden", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        //window.add(title, BorderLayout.NORTH);
        
        theGarden = new DrawingPanel(this);
        // Don't need to set the panels' preferred size: 
        // In BorderLayout the component is stretched to fit!
        theGarden.setBackground(new Color(200,255,200));
        window.add(theGarden, BorderLayout.CENTER);
        
        window.add(new JLabel("Click to add a tree.", JLabel.CENTER), BorderLayout.SOUTH);
        
        // Register to be notified of mouse clicks on the drawing panel,
        // and nowhere else in the window. Coordinates will be relative to the panel.
        theGarden.addMouseListener(this);
      
    }
    
    
    /**
     * Used to create the main menu.
     */
    
    public void createMenu() {
    	JMenuBar menuBar = new JMenuBar();
    	
    	JMenu fileMenu = new JMenu("File");
    	fileMenu.setMnemonic(KeyEvent.VK_F);
    	
    	JMenuItem menuItemNew = new JMenuItem("New");
    	menuItemNew.setMnemonic(KeyEvent.VK_N);
    	menuItemNew.addActionListener((ActionEvent event) -> {
    		this.newGarden();
    	});
    	
    	JMenuItem menuItemExit = new JMenuItem("Exit");
    	menuItemExit.setMnemonic(KeyEvent.VK_X);
    	menuItemExit.addActionListener((ActionEvent event) -> {
    		System.exit(0);
    	});
    	
    	fileMenu.add(menuItemNew);
    	fileMenu.add(menuItemExit);
    	menuBar.add(fileMenu);
    	
    	setJMenuBar(menuBar);
    	
    }
    
    
    /**
     * Used to create the toolbar.
     * @return Instance of JToolBar.
     * @see  javax.swing.JToolBar
     */
    
    private JToolBar createToolBar() {
    	JToolBar toolBar = new JToolBar();
    	
    	JButton buttonSave = new JButton(UIManager.getIcon("FileView.floppyDriveIcon"));
    	toolBar.add(buttonSave);
    	
    	buttonSave.addActionListener((ActionEvent event) -> {
    		System.exit(0);
    	});
    	
    	return toolBar;
    	
    }
    
    public void paintGarden(Graphics g) {
        
        for(Tree tree:trees) {
        	tree.draw(g);
        }
      
    }
    
    public void newGarden() {
    	this.trees = new ArrayList<Tree>();
    	repaint();
    }
    
    
    public void mousePressed(MouseEvent e) {
    	// Moved this from mouseClicked because it 
    	// failed if the mouse moved while clicking.
    	
        int x = e.getX();
        int y = e.getY();
        trees.add(new Tree(x, y));
        repaint();
        
    }
    
    public void mouseClicked(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }
   
}

