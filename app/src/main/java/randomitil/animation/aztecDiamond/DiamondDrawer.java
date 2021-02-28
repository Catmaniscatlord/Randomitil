// Package Declaration
package randomitil.animation.aztecDiamond;

// Imports
import java.awt.*;
import randomitil.animation.*;
import randomitil.tilings.aztecDiamond.*;

// Class Declaration
public class DiamondDrawer extends TilingsDrawer {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // Variables
    private DiamondTilings mainTiles = null;
    private DiamondTilings prevTiles;
    private DiamondTilings newTiles;
    private DiamondTilings removedTiles;

    /// Constructor ///
    public DiamondDrawer() {
        // Super constructor
        super(4);

        // Set Color Coding
        setDirColor(new Color(0, 0, 255), 0);    // UP Color
        setDirColor(new Color(255, 0, 0), 1);    // RIGHT Color
        setDirColor(new Color(255, 255, 0), 2);  // DOWN Color
        setDirColor(new Color(0, 255, 0), 3);    // LEFT Color
    }

    /// Update Diamond Tiling ///
    public void updateTiling(DiamondIteration diamondIterator) {
        // Update diamond
        this.prevTiles = this.mainTiles;
        this.mainTiles = (DiamondTilings) diamondIterator.getTiling();
        this.newTiles = (DiamondTilings) diamondIterator.getGeneratedTiles();
        this.removedTiles = (DiamondTilings) diamondIterator.getRemovedTiles();

        // Update Size
        setBoardSize(diamondIterator.getTiling().getSize());

        // Update ExpansionRate
        this.expansionRate = diamondIterator.getExpansionRate();
    }

    /// Change paint offsets ///
    @Override
    protected void changePaintOffsets() {
        paintXOffset = (int) Math.round(cellSize / 2.0 - cellSize / getScale(getAnimPhase()));
    }

    /// Finding Coordinates ///
    private int[] getCoords(int i, int j, int cellStep, DominoTile dom, boolean moving) {
        // Create new array
        int[] newCoords = new int[2];
        int[] nextCoords = new int[2];

        // Set array
        newCoords[0] = i * cellStep + j * cellStep;
        newCoords[1] = i * cellStep - j * cellStep;

        nextCoords[0] = newCoords[0];
        nextCoords[1] = newCoords[1];

        // Predict Movement
        if (moving) {
            switch((DominoDirection) dom.getDirection()) {
                case UP: 
                    nextCoords[0] = (i + expansionRate - 1) * cellStep + (j + expansionRate + 1) * cellStep;
                    nextCoords[1] = (i + expansionRate - 1) * cellStep - (j + expansionRate + 1) * cellStep;
                    break;

                case RIGHT: 
                    nextCoords[0] = (i + expansionRate + 1) * cellStep + (j + expansionRate + 1) * cellStep;
                    nextCoords[1] = (i + expansionRate + 1) * cellStep - (j + expansionRate + 1) * cellStep;
                    break;

                case DOWN:
                    nextCoords[0] = (i + expansionRate + 1) * cellStep + (j + expansionRate - 1) * cellStep;
                    nextCoords[1] = (i + expansionRate + 1) * cellStep - (j + expansionRate - 1) * cellStep;
                    break;

                case LEFT: 
                    nextCoords[0] = (i + expansionRate - 1) * cellStep + (j + expansionRate - 1) * cellStep;
                    nextCoords[1] = (i + expansionRate - 1) * cellStep - (j + expansionRate - 1) * cellStep;
                    break;
            }
        
        // Tween Movement
        newCoords[0] += Math.round((nextCoords[0] - newCoords[0]) * getTweenFactor());
        newCoords[1] += Math.round((nextCoords[1] - newCoords[1]) * getTweenFactor());
        }

        // Offset Coords
        newCoords[0] += paintXOffset;
        newCoords[1] += paintYOffset;

        // Return coords
        return newCoords;
    }

    /// Domino Drawing Method ///
    private void drawDomino(Graphics2D g2D, int index, int x, int y, double scale, int alpha) {
        // Fill
        int newAlpha = (int) Math.round(useColors[index].getAlpha() * (alpha / 255.0));
        g2D.setColor(new Color(useColors[index].getRed(), useColors[index].getGreen(), useColors[index].getBlue(), newAlpha));

        if (index % 2 == 0) {
            g2D.fillRect(x - cellSize, y - cellSize / 2, cellSize * 2, cellSize);
        } else {
            g2D.fillRect(x - cellSize / 2, y - cellSize, cellSize, cellSize * 2);
        }
        
        // Outline
        if (scale >= 0.025) {
            newAlpha = (int) Math.round(outlineColor.getAlpha() * (alpha / 255.0));
            g2D.setColor(new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), newAlpha));

            if (index % 2 == 0) {
                g2D.drawRect(x - cellSize, y - cellSize / 2, cellSize * 2, cellSize);
            } else {
                g2D.drawRect(x - cellSize / 2, y - cellSize, cellSize, cellSize * 2);
            }
        }
    }

    /// Draw Domino Based on Direction ///
    private void drawDominoDirection(Graphics2D g2D, DominoTile dom, int x, int y, double scale, int alpha) {
        switch((DominoDirection) dom.getDirection()) {
            case UP: 
                drawDomino(g2D, 0, x, y, scale, alpha);
                break;

            case RIGHT: 
                drawDomino(g2D, 1, x, y, scale, alpha);
                break;

            case DOWN: 
                drawDomino(g2D, 2, x, y, scale, alpha);
                break;

            case LEFT: 
                drawDomino(g2D, 3, x, y, scale, alpha);
                break;
        }
    }

    /// Main Diamond Drawing Method ///
    @Override
    protected void drawTiling(Graphics2D g2D, double scale) {
        // Setup Variables
        int cellStep = (int) Math.round(cellSize / 2.0);

        DominoTile dom = null;

        DiamondTilings drawTiles = prevTiles;
        int size = prevSize;
        int[] coords;
        
        // Size and tile Adjustment
        if (!isAnimate() || prevSize == 0 || getAnimPhase() == 3) {
            drawTiles = mainTiles;
            size = boardSize;
        }

        // Phase Colors
        useColors[0] = getPhaseColor(0);
        useColors[1] = getPhaseColor(1);
        useColors[2] = getPhaseColor(2);
        useColors[3] = getPhaseColor(3);

        // Iterate through Diamond Matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Retrive Domino
                dom = (DominoTile) drawTiles.getTile(i, j);
                
                // Draw Placeable Dominoes
                if (dom != null && dom.isPlaceable()) {
                    // Calculate coordinates
                    coords = getCoords(i, j, cellStep, dom, getPhaseMove());

                    // Change drawing based on direction
                    drawDominoDirection(g2D, dom, coords[0], coords[1], scale, getPhaseAlpha(getAnimPhase(), i, j));

                    // Skip over Null Domino
                    j++;
                }
            }
        }
    }

    /// Control phase scaling ///
    @Override
    public double getScale(int phase) {
        // Scaling Decision
        if (!isAnimate() || (isAnimate() && phase == 3)) {
            // Return New Scale for phase 3 && inanimate state
            return getNewScale();
        } else {
            if (phase == 2) {
                // Return Moving Scale for Phase 2
                return getAdaptedScale();
            } else {
                // Return Old Scale for phase 1
                return getOldScale();
            }
        }
    }

    /// Control phase Alpha ///
    private int getPhaseAlpha(int phase, int i, int j) {
        // Changing Alpha
        if (isAnimate()) {
            // Fading out
            if (phase == 1 && getNumAnimPhases() >= 2 && removedTiles != null  && removedTiles.getTile(i, j) != null) {
                return (int) Math.round(255 - 255 * getTweenFactor());
            }

            // Fading in
            if (phase == 3 && newTiles != null && newTiles.getTile(i, j) != null) {
                return (int) Math.round(255 * getTweenFactor());
            }
        }

        // default alpha
        return 255;
    }

    /// Control phase Movement ///
    private boolean getPhaseMove() {
        return (isAnimate() && (getAnimPhase() == 2 || getNumAnimPhases() == 1));
    }

    /// Reset Previous Tiles ///
    public void resetPrevTiles() {
        prevTiles = null;
        prevSize = 0;
    }
}