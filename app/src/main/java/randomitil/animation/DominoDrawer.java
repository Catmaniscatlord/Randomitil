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
    final int paintOffset = 50;
    final int paintSize = 200;
    final int cellSize = 100;
    int frame_num = 0;

    int boardSize = -1;
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
        this.boardSize = newSize;
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
        
        // Setup Drawing
        Graphics2D g2 = (Graphics2D) g;
        double scale = ((double) paintSize / boardSize) / (double) cellSize;

        // Draw Bounding Box
        g.drawRect(paintOffset / 2, paintOffset / 2, paintSize + paintOffset, paintSize + paintOffset);

        // Transform
        g2.translate((int) Math.round(paintSize / 2) + paintOffset, (int) Math.round(paintSize / 2) + paintOffset);
        g2.scale(scale, scale);

        // Draw grid
        draw_grid(g2);

        // Drawing Dominoes
        g2.translate(0, - (int) Math.round(paintSize * 3 / 4));
        draw_dominoes(g2);

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

    /// Domino Drawing Method ///
    public void draw_dominoes(Graphics2D g2D) {
        // Setup Variables
        Domino dom;
        int[] coords = new int[2];

        // Iterate through Diamond Matrix
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Retrive Domino
                dom = mainTiles.getTile(i, j);

                // Calculate coordinates
                coords[0] = j * cellSize - i * cellSize;
                coords[1] = j * cellSize + i * cellSize;

                // Draw Placeable Dominoes
                if (dom != null && dom.isPlaceable() == true) {
                    // Change drawing based on direction
                    switch(dom.getDirection()) {
                        case UP: 
                            // Outline
                            g2D.setColor(Color.BLACK);
                            g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            
                            // Fill
                            g2D.setColor(Color.BLUE);
                            g2D.fillRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            break;

                        case DOWN: 
                            // Outline
                            g2D.setColor(Color.BLACK);
                            g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            
                            // Fill
                            g2D.setColor(Color.GREEN);
                            g2D.fillRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            break;

                        case RIGHT: 
                            // Outline
                            g2D.setColor(Color.BLACK);
                            g2D.drawRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            
                            // Fill
                            g2D.setColor(Color.RED);
                            g2D.fillRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            break;

                        case LEFT: 
                            // Outline
                            g2D.setColor(Color.BLACK);
                            g2D.drawRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            
                            // Fill
                            g2D.setColor(Color.YELLOW);
                            g2D.fillRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            break;
                    }

                }
            }
        }
    }

    /// Diamond Grid Method ///
    public void draw_grid(Graphics2D g2D) {
        // Draw Diamond
        if (boardSize > 0) {
            for (int i = 0; i < boardSize / 2; i++) {
                for (int k = 0; k < boardSize / 2 - i; k++) {
                    // Diamond Top
                    g2D.drawRect(k * cellSize, -(i + 1) * cellSize, cellSize, cellSize);
                    g2D.drawRect(-(k + 1) * cellSize, -(i + 1) * cellSize, cellSize, cellSize);

                    // Diamond bottom
                    g2D.drawRect(k * cellSize, i * cellSize, cellSize,cellSize);
                    g2D.drawRect(-(k + 1) * cellSize, i * cellSize,cellSize, cellSize);
                }
            }
        }
    }
}