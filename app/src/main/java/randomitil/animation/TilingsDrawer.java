// Package Declaration
package randomitil.animation;

// Imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// Class Declaration
public class TilingsDrawer extends JPanel implements Runnable {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // GUI Variables
    boolean running = true;
    boolean animate = true;
    boolean colorChange = false;
    int animSpeed = 1;
    int frameNum = 0;
    
    // Animation Variables
    Thread animThread;
    final int FPS = 60;
    final int DELAY = (int) Math.round(1000.0 / FPS);

    int displaySize = 500;
    int paintSize = 400;
    int paintXOffset = 0;
    int paintYOffset = 0;
    int borderOffset = 50;
    int cellSize = (int) Math.round(paintSize / 2.0);

    double colorPhase = 0;
    int colorStep = -1;
    int colorIndex = 0;
    int numColors;
    Color[] dirColors;
    Color[] useColors;
    Color outlineColor = Color.BLACK;
    Color backColor = new Color(240, 240, 240);

    int boardWidth = 2;
    int boardHeight = 2;
    int boardSize = -1;
    int finalSize = 4;

    /// Constructor ///
    public TilingsDrawer(int numColors) {
        // Setup Background
        setBackground(backColor);
        setPreferredSize(new Dimension(400, 600));

        // Event Listener
        setupListeners();

        // Setup empty Color arrays
        this.numColors = numColors;
        this.dirColors = new Color[this.numColors];
        this.useColors = new Color[this.numColors];
    }

    /// Setup Event Listener ///
    public void setupListeners() {
        // Setup Resize Event Calculations
        class DrawingListener implements ComponentListener {
                // Variables
                TilingsDrawer panel;
                
                /// Constructor ///
                public DrawingListener(TilingsDrawer panel) {
                    this.panel = panel;
                }
                
                /// Resize Event ///
                public void componentResized(ComponentEvent e) {
                    // Recalculate Display Size
                    panel.setDisplaySize(e.getComponent().getWidth(), e.getComponent().getHeight());
                }
            
                /// Shown Event ///
                public void componentMoved(ComponentEvent e) {
                    //
                }

                /// Shown Event ///
                public void componentShown(ComponentEvent e) {
                    //
                }

                /// Shown Event ///
                public void componentHidden(ComponentEvent e) {
                    //
                }
        }

        this.addComponentListener(new DrawingListener(this));
    }

    /// Run Method ///
    public void run() {
        // Setup Variables
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        // Running Loop
        while(running) {
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
    @Override
    public void addNotify() {
        // Super Method
        super.addNotify();

        // Setup Canvas Sizing
        setDisplaySize(getWidth(), getHeight());
        
        // Start Thread
        animThread = new Thread(this);
        animThread.start();
    }

    /// Paint Method ///
    @Override
    public void paintComponent(Graphics g) {
        // Call Super Method
        super.paintComponent(g);
        
        // Setup Drawing
        Graphics2D g2 = (Graphics2D) g;
        double scale = ((double) paintSize / boardSize) / cellSize;

        // Draw Bounding Box
        g.setColor(outlineColor);
        g.drawRect(borderOffset / 2 + paintXOffset, borderOffset / 2 + paintYOffset, paintSize + borderOffset, paintSize + borderOffset);

        // Transform
        g2.translate((int) Math.round((cellSize * scale) / 2) + borderOffset + paintXOffset, (int) Math.round(paintSize / 2.0) + borderOffset + paintYOffset);
        g2.scale(scale, scale);

        // Drawing Dominoes
        drawTiling(g2, scale);

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
                colorIndex += animSpeed * colorStep;
                // Wrapping
                colorIndex = wrapVal(colorIndex, 0, 3);
            }
        }
    }

    /// Tilings Drawing Method ///
    public void drawTiling(Graphics2D g2D, double scale) {
        // Filled by subclass drawer
    }

    /// Wrap values Method ///
    public int wrapVal(int val, int min, int max) {
        if (val > max) {
            val = min + (Math.abs(val - max) - 1) % (Math.abs(max - min));
        }
        
        if (val < min) {
            val = max - (Math.abs(min - val) - 1) % (Math.abs(max - min));
        }

        return val;
    }

    /// Color Changing Method ///
    public Color getPhaseColor(int index) {
        // adjust phase & index
        double phase = colorPhase % FPS;
        index = wrapVal(colorIndex + index, 0, 3);
        
        // Vars
        int newR = dirColors[index].getRed();
        int newG = dirColors[index].getGreen();
        int newB = dirColors[index].getBlue();
        int index2 = wrapVal(index + colorStep, 0, 3);

        // Tween towards next RGB values
        newR += (int) Math.round((dirColors[index2].getRed() - newR) * (phase / FPS));
        newG += (int) Math.round((dirColors[index2].getGreen() - newG) * (phase / FPS));
        newB += (int) Math.round((dirColors[index2].getBlue() - newB) * (phase / FPS));

        // return new Color
        return new Color(newR, newG, newB);
    }

    /// Stop Color Changing ///
    public void stopColorChange() {
        // set Color Change
        this.setColorChange(false);

        // Reset Color Variables to default
        if (!colorChange) {
            colorPhase = 0;
            colorIndex = 0;
        }
    }

    /// Setter Methods ///---------------------------------------------------------

    /// Display Size ///
    public void setDisplaySize(int width, int height) {
        if (height > width) {
            displaySize = width;
        } else {
            displaySize = height;
        }
        paintSize = (int) Math.round(displaySize * 0.8);
        borderOffset = (int) Math.round(paintSize / 8.0);

        paintXOffset = Math.max(0, (width - displaySize) / 2);
        paintYOffset = Math.max(0, (height - displaySize) / 2);

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

    /// Color Step Direction ///
    public void setColorStep(int colorStep) {
        this.colorStep = colorStep;
    }

    /// Direction Colors ///
    public void setDirColor(Color newColor, int index) {
        this.dirColors[index] = newColor;
    }

    /// Outline Color ///
    public void setOutlineColor(Color newColor) {
        this.outlineColor = newColor;
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

    /// Display Size ///
    public int getDisplaySize() {
        return displaySize;
    }

    /// Animate? ///
    public boolean getAnimate() {
        return this.animate;
    }

    /// Color Change? ///
    public boolean getColorChange() {
        return this.colorChange;
    }

    /// Color Step Direction ///
    public int getColorStep() {
        return this.colorStep;
    }

    /// Direction Colors ///
    public Color getDirColor(int index) {
        return this.dirColors[index];
    }

    /// Outline Color ///
    public Color getOutlineColor() {
        return this.outlineColor;
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