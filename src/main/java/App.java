import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App 
{
    public static void main(String[] args) 
    {
		int width = 9;
		int height = 9;
		int creationDelay = 5000;
		
		MapData mapData = new MapData(width, height);
		MainController mainController = new MainController(mapData);
		RobotFactory robotFactory = new RobotFactory();
	
		if (mainController == null)
		{
			System.out.println("Main Controller is null inside App.");
		}
		
		if (robotFactory == null)
		{
			System.out.println("Robot Factory is null inside App.");
		}
			
		FactoryController factoryController = new FactoryController(mainController, robotFactory, creationDelay);
		
		if (factoryController == null)
		{
			System.out.println("Factory Controller is null inside App.");
		}
		
		//factoryController.start();
		
        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            JFrame window = new JFrame("Example App (Swing)");
			
			//Create Starting Objects
            SwingArena arena = new SwingArena(width, height);
			
			mainController.setArena(arena);
			
			factoryController.start();
			
            arena.addListener((x, y) ->
            {
                System.out.println("Arena click at (" + x + "," + y + ")");
            });
            
            JToolBar toolbar = new JToolBar();
//             JButton btn1 = new JButton("My Button 1");
//             JButton btn2 = new JButton("My Button 2");
            JLabel label = new JLabel("Score: 999");
//             toolbar.add(btn1);
//             toolbar.add(btn2);
            toolbar.add(label);
            
//             btn1.addActionListener((event) ->
//             {
//                 System.out.println("Button 1 pressed");
//             });
            
            JTextArea logger = new JTextArea();
            JScrollPane loggerArea = new JScrollPane(logger);
            loggerArea.setBorder(BorderFactory.createEtchedBorder());
            logger.append("Hello\n");
            logger.append("World\n");
            
            JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, arena, logger);

            Container contentPane = window.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(toolbar, BorderLayout.NORTH);
            contentPane.add(splitPane, BorderLayout.CENTER);
            
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(800, 800));
            window.pack();
            window.setVisible(true);
            
            splitPane.setDividerLocation(0.75);
        });
    }    
}
