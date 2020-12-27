// Package Declaration
package randomitil.anim_test;

// Imports
import java.awt.*;
import javax.swing.*;

// Class Declaration
public class DominoDrawer extends Canvas {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // Global Variable
    final int fps = 30;
    int frame_num = 0;

    /// Setup Method ///
    public static void setup(String window_name) {
        // Setup GUI objects
        JFrame frame = new JFrame(window_name);
        Canvas canvas = new DominoDrawer();

        // Setup Canvas
        canvas.setSize(400, 400);
        canvas.setBackground(new Color(200, 200, 200));

        // Setup frame
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    /// Paint Method ///
    public void paint(Graphics g) {
        // Draw
        g.setColor(Color.RED);
        g.fillOval(100 + frame_num * 5, 100, 200, 200);

        // Advance to next frame
        nextFrame();
    }

    /// Frame Method ///
    public void nextFrame() {
        // Change Frame
        if (frame_num < fps) {
            frame_num++;
        } else {
            frame_num = 0;
        }

        // sleep Animation
        try {
            Thread.sleep((long) (1000 / fps));
        } catch (InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
            return;
        }

        // Setup repaint
        repaint();
    }
}