package lisp.eval.graphics;

import javax.swing.*;

/**
 *
 * JFrame
 *
 */
public class GraphicsFrame extends JFrame {

    // JPanel
    private GraphicsPanel panel;

    public GraphicsFrame(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setResizable(false);

        this.panel = new GraphicsPanel(width, height);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GraphicsPanel getPanel() {
        return this.panel;
    }
}
