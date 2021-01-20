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
    public void updateTiling(DiamondTilings mainTiles, int newSize) {
        // Update diamond
        this.prevTiles = this.mainTiles;
        this.mainTiles = mainTiles;

        // Update Size
        setBoardSize(newSize);
    }

    /// Transform Method ///
    @Override
    protected void setBoardTranslate(Graphics2D g2D, double scale) {
        // Translate origin to Middle-Left point
        g2D.translate((int) Math.round((cellSize * scale) / 2), (int) Math.round(paintSize / 2.0));

        // Adjust for animation
        if (animate) {
            //g2D.translate((int) Math.round(cellSize * scale * getTweenFactor()), 0);
        }
    }

    /// Finding Coordinates ///
    private int[] getCoords(int i, int j, int cellStep, DominoTile dom) {
        // Create new array
        int[] newCoords = new int[2];

        // Set array
        newCoords[0] = i * cellStep + j * cellStep;
        newCoords[1] = i * cellStep - j * cellStep;

        // Tween Movement
        if (this.isAnimate()) {
            int tweenStep = (int) Math.floor(cellSize * getTweenFactor());

            switch((DominoDirection) dom.getDirection()) {
                case UP: 
                    newCoords[1] -= tweenStep;
                    break;

                case RIGHT: 
                    newCoords[0] += tweenStep;
                    break;

                case DOWN: 
                    newCoords[1] += tweenStep;
                    break;

                case LEFT: 
                    newCoords[0] -= tweenStep;
                    break;
            }
        }

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