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
    private boolean spin = false;
    private boolean animate = false;
    private boolean colorChange = false;
    private double colorSpeed = 0.5;
    private double spinSpeed = -1;
    private double animSpeed = 0.5;
    private double frameNum = 0;
    
    // Animation Variables
    private Thread animThread;
    private int animPhase = 1;
    private int numAnimPhases = 3;
    private final int FPS = 60;
    private final int DELAY = (int) Math.round(1000.0 / FPS);
    private int numIterations = 0;

    // Drawing Variables
    private int displaySize = 500;
    protected int paintSize = 400;

    private int displayXOffset = 0;
    private int displayYOffset = 0;

    protected int paintXOffset = 0;
    protected int paintYOffset = 0;

    private int borderOffset = 50;

    protected int cellSize = (int) Math.round(paintSize / 2.0);

    private double paintDir = 0;

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
        double scale = getScale(getAnimPhase());

        // Draw Bounding Box
        g.setColor(outlineColor);
        g.drawRect(borderOffset / 2 + displayXOffset, borderOffset / 2 + displayYOffset, paintSize + borderOffset, paintSize + borderOffset);

        // Transform
        g2.translate(borderOffset + displayXOffset + paintSize / 2, borderOffset + displayYOffset + paintSize / 2);
        changePaintOffsets();

        // Scale Graphics Object
        g2.scale(scale, scale);

        // Rotate Graphics Object
        g2.rotate((paintDir * Math.PI) / 180.0);

        // Drawing Dominoes
        drawTiling(g2, scale);

        // Resync drawing
        Toolkit.getDefaultToolkit().sync();
    }

    /// Change Paint Offsets ///
    protected void changePaintOffsets() {
        // Empty for Override
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
            resetAnimate();
            setAnimate(false);

            // Call for next expansion
            if (animator.isAutoAnimate()) {
                animator.autoIterateTiling();
            }
        }

        // Change Paint Direction
        if (spin) {
            paintDir += spinSpeed;
            paintDir = wrapVal(paintDir, 0.0, 359.0);
        } else {
            paintDir = 0;
        }

        // Change Color Phase
        if (colorChange) {
            if (colorPhase + colorSpeed < FPS) {
                colorPhase += colorSpeed;
            } else {
                colorPhase = 0;
                colorIndex += colorSpeed * colorStep;
                // Wrapping
                colorIndex = wrapVal((int) Math.floor(colorIndex), 0, numColors - 1);
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

    /// Wrap double values Method ///
    public double wrapVal(double val, double min, double max) {
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

        displayXOffset = Math.max(0, (width - displaySize) / 2);
        displayYOffset = Math.max(0, (height - displaySize) / 2);

        cellSize = (int) Math.round(paintSize / 2.0);
    }

    /// Color Change? ///
    public void setColorChange(boolean colorChange) {
        this.colorChange = colorChange;
    }

    /// Spinning? ///
    public void setSpin(boolean spin) {
        this.spin = spin;
    }

    /// Animate? ///
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    /// Frame Number ///
    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }

    /// Animation reset ///
    public void resetAnimate() {
        frameNum = 0;
        animPhase = 1;
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

    /// Color Speed ///
    public void setColorSpeed(double colorSpeed) {
        this.colorSpeed = colorSpeed;
    }

    /// Spin Speed ///
    public void setSpinSpeed(double spinSpeed) {
        this.spinSpeed = spinSpeed;
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

    /// Num of Iterations Done ///
    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
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

    /// Color Change? ///
    public boolean isColorChange() {
        return this.colorChange;
    }

    /// Spinning? ///
    public boolean isSpin() {
        return this.spin;
    }

    /// Animate? ///
    public boolean isAnimate() {
        return this.animate;
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

    /// Color Speed ///
    public double getColorSpeed() {
        return this.colorSpeed;
    }

    /// Anim Speed ///
    public double getSpinSpeed() {
        return this.spinSpeed;
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

    /// Num of Iterations Done ///
    public int getNumIterations() {
        return this.numIterations;
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

    public double getScale(int phase) {
        // Override
        return (double) phase;
    }

    /// Tweening Factor ///
    public double getTweenFactor() {
        // Variables
        int phaseLength = (int) Math.round(FPS / (double) numAnimPhases);

        if (!animate) {
            phaseLength = 1;
        }
        
        // Return Tween Factor      
        return (frameNum % phaseLength) / (double) phaseLength;
    }
}