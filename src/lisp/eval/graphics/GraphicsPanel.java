package lisp.eval.graphics;

import java.io.IOException;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import java.util.ArrayList;

/**
 *
 * JPanel
 *
 */
public class GraphicsPanel extends JPanel {
    // ペンの色
    Color penColor = Color.BLACK;

    // 線の描画
    ArrayList<Line2D.Double> lines = new ArrayList<>();
    ArrayList<Color> lineColors = new ArrayList<>();

    // 亀の画像、位置、角度
    BufferedImage turtleImg;
    Point2D.Double turtlePoint = null;
    double theta;

    public GraphicsPanel(int width, int height) {
        setSize(width, height);

        try{
            this.turtleImg = ImageIO.read(new File("src/images/turtle50_50.png"));
        }catch(IOException e){
            System.out.println("Load error");
            e.printStackTrace();
        }
    }

    public void setPenColor(int r, int g, int b) {
        this.penColor = new Color(r, g, b);
        repaint();
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        this.lineColors.add(penColor);
        this.lines.add(new Line2D.Double(x1, y1, x2, y2));
        repaint();
    }

    public void drawTurtle(double d, double x, double y) {
        this.theta = d;
        this.turtlePoint = new Point2D.Double(x, y);
        repaint();
    }

    public void drawClear() {
        this.penColor = Color.BLACK;
        this.lines = new ArrayList<>();
        this.turtlePoint = null;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (int i=0; i < this.lines.size(); i++) {
            g2.setPaint(this.lineColors.get(i));
            g2.draw(this.lines.get(i));
        }

        if (turtlePoint != null) {
            int imgW = this.turtleImg.getWidth();
            int imgH = this.turtleImg.getHeight();
            g2.rotate(this.theta, this.turtlePoint.x, this.turtlePoint.y);
            g2.drawImage(this.turtleImg, null, -imgW / 2 + (int)this.turtlePoint.x, -imgH / 2 + (int)this.turtlePoint.y);
        }

    }
}
