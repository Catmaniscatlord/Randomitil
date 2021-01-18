// Package Declaration
package randomitil.animation;

// Imports
import java.awt.*;
import randomitil.tilings.aztecDiamond.*;

// Class Declaration
public class DiamondDrawer extends TilingsDrawer {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // Variables
    DiamondTilings mainTiles;
    DiamondIteration calc;

    /// Constructor ///
    public DiamondDrawer() {
        // Super constructor
        super(4);

        // Set Color Coding
        dirColors[0] = new Color(0, 0, 255);    // UP Color
        dirColors[1] = new Color(255, 0, 0);    // RIGHT Color
        dirColors[2] = new Color(255, 255, 0);  // DOWN Color
        dirColors[3] = new Color(0, 255, 0);    // LEFT Color
    }

    /// Update Diamond Tiling ///
    public void updateTiling(DiamondTilings mainTiles, DiamondIteration calc, int newSize) {
        // Update diamond
        this.mainTiles = mainTiles;

        // Update iteration calculater
        this.calc = calc;

        // Update Size
        this.boardSize = newSize;
    }

    /// Domino Drawing Method ///
    @Override
    public void drawTiling(Graphics2D g2D, double scale) {
        // Setup Variables
        DominoTile dom = null;
        int[] coords = new int[2];
        int cellStep = (int) Math.round(cellSize / 2.0);

        // Phase Colors
        useColors[0] = getPhaseColor(0);
        useColors[1] = getPhaseColor(1);
        useColors[2] = getPhaseColor(2);
        useColors[3] = getPhaseColor(3);

        // Iterate through Diamond Matrix
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Retrive Domino
                dom = (DominoTile) mainTiles.getTile(i, j);

                // Draw Placeable Dominoes
                if (dom != null && dom.isPlaceable()) {
                    // Calculate coordinates
                    coords[0] = i * cellStep + j * cellStep;
                    coords[1] = i * cellStep - j * cellStep;

                    // Change drawing based on direction
                    switch((DominoDirection) dom.getDirection()) {
                        case UP: 
                            // Fill
                            g2D.setColor(useColors[0]);
                            g2D.fillRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            
                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(outlineColor);
                                g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            }
                            break;

                        case RIGHT: 
                            // Fill
                            g2D.setColor(useColors[1]);
                            g2D.fillRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);

                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(outlineColor);
                                g2D.drawRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);
                            }
                            break;

                        case DOWN: 
                            // Fill
                            g2D.setColor(useColors[2]);
                            g2D.fillRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);

                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(outlineColor);
                                g2D.drawRect(coords[0] - cellSize, coords[1] - cellSize / 2, cellSize * 2, cellSize);
                            }
                            break;

                        case LEFT: 
                            // Fill
                            g2D.setColor(useColors[3]);
                            g2D.fillRect(coords[0] - cellSize / 2, coords[1] - cellSize, cellSize, cellSize * 2);

                            // Outline
                            if (scale >= 0.025) {
                                g2D.setColor(outlineColor);
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
}