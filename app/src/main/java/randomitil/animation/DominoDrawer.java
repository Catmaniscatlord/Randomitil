// Package Declaration
package randomitil.animation;

// Imports
import java.awt.*;
import javax.swing.*;
import randomitil.*;
import java.lang.Runnable;
import java.lang.Math;

// Class Declaration
public class DominoDrawer extends JPanel implements Runnable {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // Global Variables
    Thread animator;
    final int FPS = 30;
    final int DELAY = (int)(Math.round(1000 / 30));
    int frame_num = 0;

    int tileSize = -1;
    AztecDiamond mainTiles;
    AztecDiamond nextTiles;

    /// Constructor ///
    public DominoDrawer() {
        // Setup Background
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 500));

    }

    /// Update Diamond ///
    public void updateDiamond(AztecDiamond oldTiles, AztecDiamond nextTiles, int newSize) {
        // Update diamond and variables
        this.mainTiles = oldTiles;
        this.nextTiles = nextTiles;
        this.tileSize = newSize;
    }

    /// Run Method ///
    public void run() {
        // Setup Variables
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        // Running Loop
        while(true) {
            // Advance to next frame
            nextFrame();

            // Repaint
            repaint();

            // Calculate System time
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            // Thread Sleep
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println(e);
                return;
            }

            // Refind system time
             beforeTime = System.currentTimeMillis();
        }
    }

    /// Initialization Work ///
    public void addNotify() {
        // Super Method
        super.addNotify();
        
        // Start Thread
        animator = new Thread(this);
        animator.start();
    }

    /// Paint Method ///
    public void paintComponent(Graphics g) {
        // Call Super Method
        super.paintComponent(g);
        
        // Draw grid
        draw_grid(g, 50, 200);

        // Test Rectangle
        g.setColor(Color.RED);
        g.fillRect(30 + frame_num * 5, 60, 50, 50);
        g.setColor(Color.BLACK);

        // Resync drawing
        Toolkit.getDefaultToolkit().sync();
    }

    /// Update Method ///
    public void nextFrame() {
        // Change Frame
        if (frame_num < FPS) {
            frame_num++;
        } else {
            frame_num = 0;
        }
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
}