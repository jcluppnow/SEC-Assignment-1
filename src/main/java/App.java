import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App 
{
    public static void main(String[] args) 
    {
		GameMap gameMap = new GameMap(4,5);
        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            JFrame window = new JFrame("Example App (Swing)");
            SwingArena arena = new SwingArena(gameMap);
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
