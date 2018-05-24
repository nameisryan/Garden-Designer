import java.awt.*;
import javax.swing.*;

public class DrawingPanel extends JPanel {

    // We need to hold on to a reference to the main application,
    // so we can call its paintGarden method
    private GardenDesign mainProgram;

    // Constructor for objects of class DrawingPanel
    // The parameter main is a reference to the main program
    public DrawingPanel(GardenDesign main) {
        mainProgram = main;
    }
    
    // paintComponent is called automatically when a screen refresh is needed
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        // Paint the panel's background
        mainProgram.paintGarden(g);     // Then the main painting
    }

}
