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
        
        // Draw grid
        draw_grid(g, 50, 200);

        // Test Rectangle
        draw_dominoes(g, 50, 200);

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
    public void draw_dominoes(Graphics g, int paintOffset, int paintSize) {
        // Setup Variables
        Graphics2D g2 = (Graphics2D) g;
        Domino dom;

        double ang_offset = 0.0;
        double[] coords = new double[2];
        int[] x_coords = new int[4];
        int[] y_coords = new int[4];

        double cellSize = (double) paintSize / boardSize;
        double cellDiagonal = Math.sqrt(Math.pow(cellSize, 2) + Math.pow(cellSize, 2)) / 2;
        double dominoDiagonal =  Math.sqrt(Math.pow(cellSize, 2) + Math.pow(cellSize * 2, 2)) / 2;

        // Transform
        g2.rotate((Math.PI * 7) / 4);
        g2.translate(paintOffset + 50 + (int) Math.round(cellSize / 2), paintOffset + 50 + (int) Math.round(cellSize * 3));

        // Iterate through Diamond Matrix
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Retrive Domino
                dom = mainTiles.getTile(i, j);
                coords[0] = i * cellDiagonal;
                coords[1] = j * cellDiagonal;

                if (dom != null && dom.isPlaceable() == true) {
                    // change angle based on direction
                    switch(dom.getDirection()) {
                        case UP: 
                            ang_offset = Math.PI * (5.65 / 9);
                            break;

                        case DOWN: 
                            ang_offset = Math.PI * (14.65 / 9);
                            break;

                        case RIGHT: 
                            ang_offset = Math.PI / 8;
                            break;

                        case LEFT: 
                            ang_offset = (Math.PI * 5) / 8;
                            break;
                    }

                    // Set Domino Coordinates
                    x_coords[0] = (int) Math.round(coords[0] + Math.cos(ang_offset) * dominoDiagonal);
                    y_coords[0] = (int) Math.round(coords[1] + Math.sin(ang_offset) * dominoDiagonal);

                    x_coords[1] = (int) Math.round(coords[0] + Math.cos(ang_offset + Math.PI  / 4) * dominoDiagonal);
                    y_coords[1] = (int) Math.round(coords[1] + Math.sin(ang_offset + Math.PI / 4) * dominoDiagonal);

                    x_coords[2] = (int) Math.round(coords[0] + Math.cos(ang_offset + Math.PI) * dominoDiagonal);
                    y_coords[2] = (int) Math.round(coords[1] + Math.sin(ang_offset + Math.PI) * dominoDiagonal);

                    x_coords[3] = (int) Math.round(coords[0] + Math.cos(ang_offset + (Math.PI * 5) / 4) * dominoDiagonal);
                    y_coords[3] = (int) Math.round(coords[1] + Math.sin(ang_offset + (Math.PI * 5) / 4) * dominoDiagonal);

                    // Draw domino
                    g2.setColor(new Color(100, 255, 0));
                    g2.fillPolygon(x_coords, y_coords, 4);

                    g2.setColor(Color.BLACK);
                    g2.drawPolygon(x_coords, y_coords, 4);
                }

            }
        }
    }

    /// Diamond Grid Method ///
    public void draw_grid(Graphics g, int paintOffset, int paintSize) {
        // Calculate
        int paintCenter = (int) (Math.round(paintSize / 2)) + paintOffset;
        int cellSize = (int) (Math.round(paintSize / boardSize));

        // Draw Bounding Box
        g.drawRect(paintOffset - 25, paintOffset - 25, paintSize + 50, paintSize + 50);

        // Draw Diamond
        if (boardSize > 0) {
            for (int i = 0; i < boardSize / 2; i++) {
                for (int k = 0; k < boardSize / 2 - i; k++) {
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