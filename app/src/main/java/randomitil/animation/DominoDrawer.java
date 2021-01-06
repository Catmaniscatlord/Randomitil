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

    // GUI Variables
    boolean animate = true;
    boolean colorChange = false;
    int animSpeed = 1;
    int frameNum = 0;
    
    // Animation Variables
    Thread animator;
    final int FPS = 60;
    final int DELAY = (int) Math.round(1000.0 / FPS);
    int paintSize = 400;
    int paintOffset = (int) Math.round(paintSize / 8.0);
    int cellSize = (int) Math.round(paintSize / 2.0);

    double colorPhase = 0;
    int colorIndex = 0;
    Color[] dirColors = new Color[4];

    int boardWidth = 2;
    int boardHeight = 2;
    int boardSize = -1;
    int finalSize = 4;
    AztecDiamond mainTiles;
    DominoIteration calc;

    /// Constructor ///
    public DominoDrawer() {
        // Setup Background
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 500));

        // Set Color Coding
        dirColors[0] = new Color(0, 0, 255);    // UP Color
        dirColors[1] = new Color(255, 0, 0);    // RIGHT Color
        dirColors[2] = new Color(255, 255, 0);  // DOWN Color
        dirColors[3] = new Color(0, 255, 0);    // LEFT Color
    }

    /// Update Diamond ///
    public void updateDiamond(AztecDiamond mainTiles, DominoIteration calc, int newSize) {
        // Update diamond
        this.mainTiles = mainTiles;

        // Update domino iteration calculater
        this.calc = calc;

        // Update Size
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
            if (boardSize != -1) {
                repaint();
            }

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
        double scale = ((double) paintSize / boardSize) / cellSize;

        // Draw Bounding Box
        g.drawRect(paintOffset / 2, paintOffset / 2, paintSize + paintOffset, paintSize + paintOffset);

        // Transform
        g2.translate((int) Math.round((cellSize * scale) / 2) + paintOffset, (int) Math.round(paintSize / 2) + paintOffset);
        g2.scale(scale, scale);

        if (scale > 0.01) {
            // Draw grid
            drawGrid(g2);
        }

        // Drawing Dominoes
        drawDominoes(g2, scale);

        // Resync drawing
        Toolkit.getDefaultToolkit().sync();
    }

    /// Update Method ///
    public void nextFrame() {
        // Change Frame
        if (frameNum < FPS) {
            frameNum += animSpeed;
        } else {
            frameNum = 0;
        }

        // Change Color Phase
        if (colorChange) {
            if (colorPhase + 1 < FPS) {
                colorPhase += animSpeed;
            } else {
                colorPhase = 0;
                colorIndex++;
                if (colorIndex >= 4) {
                    colorIndex = 0;
                }
            }
        }
    }

    /// Domino Drawing Method ///
    public void drawDominoes(Graphics2D g2D, double scale) {
        // Setup Variables
        Domino dom = null;
        int[] coords = new int[2];
        int cellStep = (int) Math.round(cellSize / 2);

        // Iterate through Diamond Matrix
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Retrive Domino
                dom = mainTiles.getTile(i, j);

                // Draw Placeable Dominoes
                if (dom != null && dom.isPlaceable()) {
                    // Calculate coordinates
                    coords[0] = i * cellStep + j * cellStep;
                    coords[1] =  i * cellStep - j * cellStep;

                    // Change drawing based on direction
                    switch(dom.getDirection()) {
                        case UP: 
                            // Fill
                            g2D.setColor(getPhaseColor(0));
                            g2D.fillRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            
                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(Color.BLACK);
                                g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            }
                            break;

                        case RIGHT: 
                            // Fill
                            g2D.setColor(getPhaseColor(1));
                            g2D.fillRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);

                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(Color.BLACK);
                                g2D.drawRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            }
                            break;

                        case DOWN: 
                            // Fill
                            g2D.setColor(getPhaseColor(2));
                            g2D.fillRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);

                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(Color.BLACK);
                                g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            }
                            break;

                        case LEFT: 
                            // Fill
                            g2D.setColor(getPhaseColor(3));
                            g2D.fillRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);

                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(Color.BLACK);
                                g2D.drawRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            }
                            break;
                    }
                
                    // Skip over Null Domino
                    j++;
                }
            }
        }
    }

    /// Grid Drawing Method ///
    public void drawGrid(Graphics2D g2D) {
        // Setup Variables
        Domino dom = null;
        int[] coords = new int[2];
        int cellStep = (int) Math.round(cellSize / 2);
        g2D.setColor(Color.BLACK);

        // Draw Squares
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Retrive Domino
                dom = mainTiles.getTile(i, j);

                if (dom == null || (dom != null && dom.isPlaceable())) {
                    // Calculate coordinates
                    coords[0] = i * cellStep + j * cellStep;
                    coords[1] =  i * cellStep - j * cellStep;

                    // Draw Square Outlines
                    if (mainTiles.getOrientation(i, j) == Orientation.HORIZONTAL) {
                        g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                    } else {
                        g2D.drawRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                    }
                }
            }
            
            // Skip some rows
            if (i % 2 == 0 && i < boardSize - 2) {
                i++;
            }
        }
    }

    /// Color Changing Method ///
    public Color getPhaseColor(int index) {
        // adjust phase & index
        double phase = colorPhase % FPS;
        index = (colorIndex + index) % 4;
        
        // Vars
        int newR = dirColors[index].getRed();
        int newG = dirColors[index].getGreen();
        int newB = dirColors[index].getBlue();
        int index2 = (index + 1) % 4;

        // Tween towards next RGB values
        newR += (int) Math.round((dirColors[index2].getRed() - newR) * (phase / FPS));
        newG += (int) Math.round((dirColors[index2].getGreen() - newG) * (phase / FPS));
        newB += (int) Math.round((dirColors[index2].getBlue() - newB) * (phase / FPS));

        // return new Color
        return new Color(newR, newG, newB);
    }

    /// Setter Methods ///---------------------------------------------------------

    /// UI Scale ///
    public void setUiScale(double uiScale) {
        paintSize = (int) Math.round(400 * uiScale);
        paintOffset = (int) Math.round(paintSize / 8.0);
        cellSize = (int) Math.round(paintSize / 2.0);
    }

    /// Animate? ///
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    /// Color Change? ///
    public void setColorChange(boolean colorChange) {
        this.colorChange = colorChange;
    }

    /// Direction Colors ///
    public void setDirColors(Color uColor, Color rColor, Color dColor, Color lColor) {
        this.dirColors[0] = uColor;
        this.dirColors[1] = rColor;
        this.dirColors[2] = dColor;
        this.dirColors[3] = lColor;
    }

    /// Anim Speed ///
    public void setAnimSpeed(int animSpeed) {
        this.animSpeed = animSpeed;
    }

    /// Board Size ///
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    /// Board Width ///
    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    /// Board Height ///
    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    /// final Size ///
    public void setFinalSize(int finalSize) {
        this.finalSize = finalSize;
    }

    /// Getter Methods ///---------------------------------------------------------

    /// UI Scale ///
    public double getUiScale() {
        return (double) paintSize / 400;
    }

    /// Animate? ///
    public boolean getAnimate() {
        return this.animate;
    }

    /// Color Change? ///
    public boolean getColorChange() {
        return this.colorChange;
    }

    /// Direction Colors ///
    public Color[] getDirColors() {
        return this.dirColors;
    }

    /// Anim Speed ///
    public int getAnimSpeed() {
        return this.animSpeed;
    }

    /// Board Size ///
    public int getBoardSize() {
        return this.boardSize;
    }

    /// Board Width ///
    public int getBoardWidth() {
        return this.boardWidth;
    }

    /// Board Height ///
    public int getBoardHeight() {
        return this.boardHeight;
    }

    /// final Size ///
    public int getFinalSize() {
        return this.finalSize;
    }
}