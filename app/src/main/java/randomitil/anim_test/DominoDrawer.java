// Package Declaration
package randomitil.anim_test;

// Imports
import java.awt.*;
import javax.swing.*;
import randomitil.*;
import java.lang.Math;

// Class Declaration
public class DominoDrawer extends Canvas {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // Global Variables
    final int fps = 30;
    int frame_num = 0;

    int tileSize = -1;
    AztecDiamond mainTiles;
    AztecDiamond nextTiles;

    /// Setup Method ///
    public static void setup(String window_name) {
        // Setup GUI objects
        JFrame frame = new JFrame(window_name);
        Canvas canvas = new DominoDrawer();

        // Setup Canvas
        canvas.setSize(600, 600);
        canvas.setBackground(new Color(200, 200, 200));

        // Setup frame
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    /// Update Diamond ///
    public void updateDiamond(AztecDiamond oldTiles, AztecDiamond nextTiles, int newSize) {
        // Update diamond and variables
        this.mainTiles = oldTiles;
        this.nextTiles = nextTiles;
        this.tileSize = newSize;
    }

    /// Diamond Grid Method ///
    public void draw_grid(Graphics g, int paintOffset, int paintSize) {
        // Calculate
        int paintCenter = (int) (Math.round(paintSize / 2)) + paintOffset;
        int cellSize = (int) (Math.round(paintSize / tileSize));

        // Draw Bounding Box
        g.drawRect(paintOffset - 25, paintOffset - 25, paintSize + 50, paintSize + 50);

        // Draw Diamond
        if (tileSize > 0) {
            for (int i = 0; i < tileSize / 2; i++) {
                for (int k = 0; k < tileSize / 2 - i; k++) {
                    // Diamond Top
                    g.drawRect(paintCenter + k * cellSize, paintCenter - (i + 1) * cellSize, cellSize, cellSize);
                    g.drawRect(paintCenter - (k + 1) * cellSize, paintCenter - (i + 1) * cellSize, cellSize, cellSize);

                    // Diamond bottom
                    g.drawRect(paintCenter + k * cellSize, paintCenter + i * cellSize, cellSize,cellSize);
                    g.drawRect(paintCenter - (k + 1) * cellSize, paintCenter + i * cellSize,cellSize, cellSize);
                }
            }
        }
    }

    /// Paint Method ///
    public void paint(Graphics g) {
        // Draw grid
        draw_grid(g, 50, 500);

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