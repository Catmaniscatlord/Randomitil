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
    public void updateTiling(DiamondTilings mainTiles, int newSize, int expansionRate) {
        // Update diamond
        this.prevTiles = this.mainTiles;
        this.mainTiles = mainTiles;

        // Update Size
        setBoardSize(newSize);

        // Update ExpansionRate
        this.expansionRate = expansionRate;
    }

    /// Transform Method ///
    @Override
    protected void setBoardTranslate(Graphics2D g2D) {
        // Variables
        int drawOffset = (int) Math.round((cellSize / 2.0) * getScale());
        
        // Translate origin down to middle
        g2D.translate(drawOffset, (int) Math.round(paintSize / 2.0));
    }

    /// Finding Coordinates ///
    private int[] getCoords(int i, int j, int cellStep, DominoTile dom) {
        // Create new array
        int[] newCoords = new int[2];
        int[] nextCoords = new int[2];

        // Set array
        newCoords[0] = i * cellStep + j * cellStep;
        newCoords[1] = i * cellStep - j * cellStep;

        nextCoords[0] = newCoords[0];
        nextCoords[1] = newCoords[1];

        // Predict Movement
        if (this.isAnimate()) {
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
        }

        // Tween Movement
        newCoords[0] += Math.round((nextCoords[0] - newCoords[0]) * getTweenFactor());
        newCoords[1] += Math.round((nextCoords[1] - newCoords[1]) * getTweenFactor());

        // Return coords
        return newCoords;
    }

    /// Domino Drawing Method ///
    private void drawDomino(Graphics2D g2D, int index, int x, int y, double scale) {
        // Fill
        g2D.setColor(useColors[index]);

        if (index % 2 == 0) {
            g2D.fillRect(x - cellSize, y - cellSize / 2, cellSize * 2, cellSize);
        } else {
            g2D.fillRect(x - cellSize / 2, y - cellSize, cellSize, cellSize * 2);
        }
        
        // Outline
        if (scale >= 0.025) {
            g2D.setColor(outlineColor);

            if (index % 2 == 0) {
                g2D.drawRect(x - cellSize, y - cellSize / 2, cellSize * 2, cellSize);
            } else {
                g2D.drawRect(x - cellSize / 2, y - cellSize, cellSize, cellSize * 2);
            }
        }
    }

    /// Domino Drawing Method ///
    @Override
    protected void drawTiling(Graphics2D g2D, double scale) {
        // Setup Variables
        DominoTile dom = null;
        int[] coords;
        int size = boardSize;
        DiamondTilings drawTiles = mainTiles;
        int cellStep = (int) Math.round(cellSize / 2.0);


        // Adjust for animation
        if (animate && prevSize != -1) {
            size = prevSize;
            drawTiles = prevTiles;
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
                    coords = getCoords(i, j, cellStep, dom);

                    // Change drawing based on direction
                    switch((DominoDirection) dom.getDirection()) {
                        case UP: 
                            drawDomino(g2D, 0, coords[0], coords[1], scale);
                            break;

                        case RIGHT: 
                            drawDomino(g2D, 1, coords[0], coords[1], scale);
                            break;

                        case DOWN: 
                            drawDomino(g2D, 2, coords[0], coords[1], scale);
                            break;

                        case LEFT: 
                            drawDomino(g2D, 3, coords[0], coords[1], scale);
                            break;
                    }
                    // Skip over Null Domino
                    j++;
                }
            }
        }
    }
}