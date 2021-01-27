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
    private boolean running = true;
    private boolean animate = false;
    private boolean colorChange = false;
    private double animSpeed = 0.5;
    private double frameNum = 0;
    
    // Animation Variables
    private Thread animThread;
    private int animPhase = 1;
    private int numAnimPhases = 3;
    private final int FPS = 60;
    private final int DELAY = (int) Math.round(1000.0 / FPS);

    // Drawing Variables
    private int displaySize = 500;
    protected int paintSize = 400;
    private int paintXOffset = 0;
    private int paintYOffset = 0;
    private int borderOffset = 50;
    protected int cellSize = (int) Math.round(paintSize / 2.0);

    // Color Changing Variables
    private double colorPhase = 0;
    private int colorStep = -1;
    private double colorIndex = 0;
    private int numColors;
    private Color[] dirColors;
    protected Color[] useColors;
    protected Color outlineColor = Color.BLACK;
    private Color backColor = new Color(240, 240, 240);

    // Size variables
    protected int prevSize = 0;
    protected int boardSize = -1;
    protected int expansionRate = 1;

    // Animator Instance
    private TilingsAnimator animator;

    /// Constructor ///
    protected TilingsDrawer(int numColors) {
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
    private void setupListeners() {
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
        double scale = getScale();

        // Draw Bounding Box
        g.setColor(outlineColor);
        g.drawRect(borderOffset / 2 + paintXOffset, borderOffset / 2 + paintYOffset, paintSize + borderOffset, paintSize + borderOffset);
        g.drawRect(borderOffset + paintXOffset, borderOffset + paintYOffset, paintSize, paintSize);

        // Transform
        g2.translate(borderOffset + paintXOffset, borderOffset + paintYOffset);
        this.setBoardTranslate(g2);

        // Scale Graphics Object
        g2.scale(scale, scale);

        // Drawing Dominoes
        drawTiling(g2, scale);

        // Resync drawing
        Toolkit.getDefaultToolkit().sync();
    }

    /// Transform Method ///
    protected void setBoardTranslate(Graphics2D g2D) {
        // Override
    }

    /// Update Method ///
    private void nextFrame() {
        // Change Frame
        if (frameNum < FPS) {
            // Change Frame Number
            frameNum += animSpeed;

            // Change Animation Phase
            if (frameNum % (int) Math.round(FPS / (double) numAnimPhases) == 0) {
                animPhase++;
            }
        } else {
            // Reset Animation
            frameNum = 0;
            animate = false;
            animPhase = 1;

            // Call for next expansion
            if (animator.isAutoAnimate()) {
                animator.autoIterateTiling();
            }
        }

        // Change Color Phase
        if (colorChange) {
            if (colorPhase + animSpeed < FPS) {
                colorPhase += animSpeed;
            } else {
                colorPhase = 0;
                colorIndex += animSpeed * colorStep;
                // Wrapping
                colorIndex = wrapVal((int) Math.floor(colorIndex), 0, 3);
            }
        }
    }

    /// Tilings Drawing Method ///
    protected void drawTiling(Graphics2D g2D, double scale) {
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
    protected Color getPhaseColor(int index) {
        // adjust phase & index
        double phase = colorPhase % FPS;
        index = wrapVal((int) Math.floor(colorIndex) + index, 0, 3);
        
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

    /// Animate? ///
    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
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
    public void setAnimSpeed(double animSpeed) {
        this.animSpeed = animSpeed;
    }

    /// Board Size ///
    public void setBoardSize(int newSize) {
        this.prevSize = this.boardSize;
        this.boardSize = newSize;
    }

    /// Animation Phase ///
    public void setAnimPhase(int animPhase) {
        this.animPhase = animPhase;
    }

    /// Num of Animation Phases ///
    public void setNumAnimPhases(int numAnimPhases) {
        this.numAnimPhases = numAnimPhases;
    }

    /// Animator Object ///
    public void setAnimator(TilingsAnimator animator) {
        this.animator = animator;
    }

    /// Getter Methods ///---------------------------------------------------------

    /// Display Size ///
    public int getDisplaySize() {
        return displaySize;
    }

    /// Animate? ///
    public boolean isAnimate() {
        return this.animate;
    }

    /// Color Change? ///
    public boolean isColorChange() {
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
    public double getAnimSpeed() {
        return this.animSpeed;
    }

    /// Board Size ///
    public int getBoardSize() {
        return this.boardSize;
    }

    /// Animator Object ///
    public TilingsAnimator getAnimator() {
        return this.animator;
    }

    /// Animation Phase ///
    public int getAnimPhase() {
        return this.animPhase;
    }

    /// Num of Animation Phases ///
    public int getNumAnimPhases() {
        return this.numAnimPhases;
    }

    /// Scaling Factor ///
    public double getOldScale() {
        return ((double) paintSize / prevSize) / cellSize;
    }

    public double getNewScale() {
        return ((double) paintSize / boardSize) / cellSize;
    }

    public double getAdaptedScale() {
        double tempSize = prevSize + (expansionRate + 1) * getTweenFactor();
        return ((double) paintSize / tempSize) / cellSize;
    }

    public double getScale() {
        // Override
        return 0.0;
    }

    /// Tweening Factor ///
    public double getTweenFactor() {
        // Variables
        int phaseLength = (int) Math.round(FPS / (double) numAnimPhases);
        
        // Return Tween Factor      
        return (frameNum % phaseLength) / (double) phaseLength;
    }
}