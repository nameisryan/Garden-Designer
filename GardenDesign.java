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
   
    private static final Boolean DEBUG = true;
	private final int maxTrees = 10;
    private Tree[] trees = new Tree[maxTrees];
    private int treeCount = 0;
    
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
        setSize(500,500);
        setLocation(200,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        
        window.add(createToolBar(), BorderLayout.NORTH);
        
        JLabel title = new JLabel("My Beautiful Garden", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        //window.add(title, BorderLayout.NORTH);
        
        theGarden = new DrawingPanel(this);
        // Don't need to set the panels' preferred size: 
        // In BorderLayout the component is stretched to fit!
        theGarden.setBackground(new Color(200,255,200));
        window.add(theGarden, BorderLayout.CENTER);
        
        window.add(new JLabel("Click to add a tree (maximum "+maxTrees+")", JLabel.CENTER), BorderLayout.SOUTH);
        
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
    	
    	JMenuItem menuItemExit = new JMenuItem("Exit");
    	menuItemExit.setMnemonic(KeyEvent.VK_X);
    	menuItemExit.addActionListener((ActionEvent event) -> {
    		System.exit(0);
    	});
    	
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
           
        for (int i = 0; i < treeCount; i++) {
            trees[i].draw(g);
        }
      
    }
    
    
    public void mousePressed(MouseEvent e) {
    	// Moved this from mouseClicked because it 
    	// failed if the mouse moved while clicking.
    	
    	if (treeCount < maxTrees) {
            int x = e.getX();
            int y = e.getY();
            trees[treeCount] = new Tree(x, y, 2);
            treeCount++;
            repaint();
        }
    }
    
    public void mouseClicked(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }
   
}

