import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


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
    
    private JComboBox<String> selectType;
    
    private DrawingPanel theGarden;
    
    JFileChooser fileChooser;
    
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
    
    public GardenDesign() {
    	
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
    	
    	this.fileChooser = new JFileChooser();
    	fileChooser.setAcceptAllFileFilterUsed(false);
    	fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Garden Design Files", "dat"));
    	
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
     * Used to create the toolbar.
     * @return Instance of JPanel.
     * @see  javax.swing.JPanel
     */
    
    private JPanel createToolBar() {
    	JPanel toolBar = new JPanel();
    	
    	JLabel labelType = new JLabel("Type:");
    	this.selectType = new JComboBox<String>( Tree.getTypes() );
    	toolBar.add(labelType);
    	toolBar.add(selectType);
    	
    	
    	return toolBar;
    	
    }
    
    public void paintGarden(Graphics g) {
        
        for(Tree tree:trees) {
        	tree.draw(g);
        }
      
    }
    
    /**
     * Clear the current design.
     */
    
    public void newGarden() {
    	this.trees = new ArrayList<Tree>();
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
    
    public void mousePressed(MouseEvent e) {
    	// Moved this from mouseClicked because it 
    	// failed if the mouse moved while clicking.
    	
        int x = e.getX();
        int y = e.getY();
        
        String selectedTreeType = (String) selectType.getSelectedItem();
        
        trees.add(new Tree(x, y, selectedTreeType));
        repaint();
        
    }
    
    public void mouseClicked(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }
   
}

