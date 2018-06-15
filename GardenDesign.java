import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * <h1>Garden Designer</h1>
 * 
 * A simple drawing program.<br />
 * 
 * Add trees and water features to a layout.<br />
 * 
 * Original application can be found <a href="http://www.cs.stir.ac.uk/~sbj/examples/Java-applications/SimpleGardenDesign/">here.</a>
 * 
 * @author Chris Hodgen, Ryan Ritchie
 *
 */

// TODO: Toolbar stacked vertically.


public class GardenDesign extends JFrame
                          implements MouseListener, ActionListener {
	
	/* Removed array and replaced with ArrayList object
		This enables an "endless" amount of trees to be added
	*/
	
    private ArrayList<Tree> trees = new ArrayList<Tree>();
    private ArrayList<Pond> ponds = new ArrayList<Pond>();
    
    
    // Toolbar elements
    private JComboBox<String> selectType;
    private JComboBox<String> selectShape;
    private JComboBox<String> selectSize;
    private JLabel labelType;
    private JLabel labelSize;
    
    private DrawingPanel theGarden;
    
    private JFileChooser fileChooser;
    
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
     * Setup look and feel and file chooser settings.
     */
    
    public GardenDesign() {
    	
    	// Change the JFrame and dialogs to match the system themes, instead of default Java
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch(Exception e) {
    		System.out.println("Could not set look and feel.\nError: " + e + "\n");
    	}
    	
    	// Setup behaviour for the file open/save dialog
        // Only allow for .dat files
        this.fileChooser = new JFileChooser();
    	fileChooser.setAcceptAllFileFilterUsed(false);
    	fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Garden Design Files", "dat"));
    }
    
    
    /**
     * Set up the window properties.
     */
    
    public void createGUI() {
    	createMenu();
    	
        setTitle("Garden designer");
        setSize(800,600);
        setLocation(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        
        window.add(createToolBar(), BorderLayout.EAST);
        
        JLabel title = new JLabel("My Beautiful Garden", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        window.add(title, BorderLayout.NORTH);
        
        theGarden = new DrawingPanel(this);
        // Don't need to set the panels' preferred size: 
        // In BorderLayout the component is stretched to fit!
        theGarden.setBackground(new Color(200,255,200));
        window.add(theGarden, BorderLayout.CENTER);
        
        // Register to be notified of mouse clicks on the drawing panel,
        // and nowhere else in the window. Coordinates will be relative to the panel.
        theGarden.addMouseListener(this);
        
        selectShape.addActionListener(this);
        
    }
    
    
    /**
     * Create the main menu.
     */
    
    public void createMenu() {
    	JMenuBar menuBar = new JMenuBar();
    	
    	JMenu fileMenu = new JMenu("File");
    	fileMenu.setMnemonic(KeyEvent.VK_F);
    	
    	JMenuItem menuItemNew = new JMenuItem("New", UIManager.getIcon("FileView.fileIcon"));
    	menuItemNew.setMnemonic(KeyEvent.VK_N);
    	menuItemNew.addActionListener((ActionEvent event) -> {
    		this.newGarden();
    	});
    	
    	JMenuItem menuItemOpen = new JMenuItem("Open", UIManager.getIcon("FileView.directoryIcon"));
    	menuItemOpen.setMnemonic(KeyEvent.VK_O);
    	menuItemOpen.addActionListener((ActionEvent event) -> {
    		this.openGarden();
    	});
    	
    	//UIManager.getIcon("FileView.floppyDriveIcon")
    	
    	JMenuItem menuItemSave = new JMenuItem("Save", UIManager.getIcon("FileView.floppyDriveIcon"));
    	menuItemSave.setMnemonic(KeyEvent.VK_V);
    	menuItemSave.addActionListener((ActionEvent event) -> {
    		this.saveGarden();
    	});
    	
    	JMenuItem menuItemExit = new JMenuItem("Exit");
    	menuItemExit.setMnemonic(KeyEvent.VK_X);
    	menuItemExit.addActionListener((ActionEvent event) -> {
    		System.exit(0);
    	});
    	
    	fileMenu.add(menuItemNew);
    	fileMenu.add(menuItemOpen);
    	fileMenu.add(menuItemSave);
    	fileMenu.add(menuItemExit);
    	menuBar.add(fileMenu);
    	
    	setJMenuBar(menuBar);
    	
    }
    
    
    /**
     * Create the toolbar.
     * @return Instance of JPanel.
     * @see  javax.swing.JPanel
     */
    
    private JPanel createToolBar() {
    	JPanel toolBar = new JPanel();
    	
    	JLabel labelShape = new JLabel("Shape");
    	this.selectShape = new JComboBox<String>(new String[] {"Tree", "Pond"});
    	toolBar.add(labelShape);
    	toolBar.add(selectShape);
    	
    	this.labelType = new JLabel("Type:");
    	this.selectType = new JComboBox<String>( Tree.getTypes() );
    	toolBar.add(labelType);
    	toolBar.add(selectType);
    	
    	this.labelSize = new JLabel("Size:");
    	this.selectSize = new JComboBox<String>( Pond.getSizes() );
    	toolBar.add(labelSize);
    	toolBar.add(selectSize);
    	
    	setComboBoxVisibility();
    	
    	return toolBar;
    	
    }
    
    
    /**
     * Handle visibility of toolbar dropdowns.
     */
    
    private void setComboBoxVisibility() {
    	if((String) selectShape.getSelectedItem()=="Tree") {
        	this.selectType.setVisible(true);
        	this.labelType.setVisible(true);
        	this.selectSize.setVisible(false);
        	this.labelSize.setVisible(false);
        	
        }
        else {
        	this.selectType.setVisible(false);
        	this.labelType.setVisible(false);
        	this.selectSize.setVisible(true);
        	this.labelSize.setVisible(true);
        }
           
    }
    
    
    /**
     * Redraw the entire garden.
     * @param Graphics g
     */
    
    public void paintGarden(Graphics g) {
        
    	for(Pond pond:ponds) {
        	pond.draw(g);
        }
    	
        for(Tree tree:trees) {
        	tree.draw(g);
        }
    }
    
    
    /**
     * Clear the current design.
     */
    
    public void newGarden() {
    	this.trees = new ArrayList<Tree>();
    	this.ponds = new ArrayList<Pond>();
    	repaint();
    }
    
    
    /**
     * Save the current garden.
     */
    
    public void saveGarden() {
    	
    	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
    		File file = fileChooser.getSelectedFile();
    			
    		try {
    			FileOutputStream f;
    			if(file.getName().endsWith(".dat"))
    				f = new FileOutputStream(file);
    			else
    				f = new FileOutputStream(file + ".dat");
    			
    			ObjectOutputStream o = new ObjectOutputStream(f);
    	    	
    			o.writeObject(this.trees);
    			o.writeObject(this.ponds);
    			
    			o.close();
    			f.close();
    			
        	} catch (FileNotFoundException e) {
    			System.out.println("File not found");
    		} catch (IOException e) {
    			System.out.println("Error initializing stream");
    		}
    	}
    	
    }
    
    
    /**
     * Open a previously saved garden.
     */
    
    public void openGarden() {
    	
    	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		File file = fileChooser.getSelectedFile();
    		
    		try {
    			FileInputStream fi = new FileInputStream(file);
    			ObjectInputStream oi = new ObjectInputStream(fi);
    			
    			this.trees = (ArrayList<Tree>) oi.readObject();
    			this.ponds = (ArrayList<Pond>) oi.readObject();
    			
    			oi.close();
    			fi.close();
    			
        	} catch (FileNotFoundException e) {
    			System.out.println("File not found");
    		} catch (IOException e) {
    			System.out.println("Error initializing stream");
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}

        	repaint();
    	}
    }
    
    
    /**
     * Handle new shape being added.
     */
    
    public void mousePressed(MouseEvent e) {
    	// Moved this from mouseClicked because it 
    	// failed if the mouse moved while clicking.
    	
        int x = e.getX();
        int y = e.getY();
        
        if((String) selectShape.getSelectedItem()=="Tree") {
        	String selectedTreeType = (String) selectType.getSelectedItem();
            
            trees.add(new Tree(x, y, selectedTreeType));
        }
        else {
        	String selectedPondSize = (String) selectSize.getSelectedItem();
            ponds.add(new Pond(x, y, selectedPondSize));
        }
        
        repaint();
        
    }
    
    
    /**
     * Handle selected shape change
     */
    
    @Override
	public void actionPerformed(ActionEvent e) {
		
		setComboBoxVisibility();
	}
    
    public void mouseClicked(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

	
   
}

