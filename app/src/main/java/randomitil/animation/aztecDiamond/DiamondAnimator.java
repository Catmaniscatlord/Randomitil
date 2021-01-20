// Package Declaration
package randomitil.animation.aztecDiamond;

// Imports
import randomitil.animation.*;
import randomitil.tilings.Tilings;
import randomitil.tilings.aztecDiamond.*;

// Class Declaration
public class DiamondAnimator implements TilingsAnimator {

    // Class Variables
    private DiamondDrawer drawer;
    private DiamondIteration diamondIterator;

    /// Constructor ///
    public DiamondAnimator(DiamondDrawer drawer) {
        this.drawer = drawer;
        this.setupTiling();
    }

    /// Setup 2 by 2 Diamond Tiling ///
    public void setupTiling() {
        // Create Instances
        this.diamondIterator = new DiamondIteration(new DiamondTilings());
        
        // Setup Iterator
        diamondIterator.setAnimationMode(false);
        diamondIterator.setTilingBias(0.5);

        // Setup Tiling
        diamondIterator.fillBlankSpaces();

        // Setup Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());
    }

    /// Iterate Tiling Once ///
    public void iterateTiling() {
        // Iterate
        diamondIterator.iterateTiles();

        // Update Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());
        drawer.setAnimate(true);
        drawer.setFrameNum(0);
    }

    //sets up the aztec diamond to perform same rate expansions
    public void sameRateSetup(int expansionRate) {

        class BaseTiling extends DiamondTilings {

            public BaseTiling(){
                super();
            }

            public BaseTiling(int size){
                super(size);
            }

            @Override
            public void setUpTiles() {
                boolean[][] chamfer = new boolean[2 * (expansionRate - 1)][4 * (expansionRate - 1)];
            
                for (int i = 0; i < chamfer.length ; i++) {
                    for (int j = 0; j < chamfer[i].length/2; j++) {
                        if(j - i >= 0) {
                            chamfer[i][j] = true;
                            chamfer[i][chamfer[i].length - j - 1] = true;
                        }
                        else { 
                            chamfer[i][j] = false;
                            chamfer[i][chamfer[i].length - j - 1] = false;
                        }
                    }
                }
                
                int offset;
                DominoTile dom = new DominoTile(false);
                for (int k = 0; k < diamondIterator.getIteration(); k++) {
                    offset = 2 + k * (2 + chamfer[1].length);
                    for (int i = 0; i < chamfer.length; i++) {
                        for (int j = 0; j < chamfer[i].length; j++) {
                            if(chamfer[i][j]){
                                this.tiles[i][j + offset]                 = dom; // top
                                this.tiles[this.size - i - 1][j + offset] = dom; // bottom
                                this.tiles[j + offset][i]                 = dom; // left
                                this.tiles[j + offset][this.size - i - 1] = dom; // right
                            }
                        }
                    }
                }
            }
        }
        
        this.diamondIterator = new DiamondIteration(new BaseTiling()) {
            @Override
            public Tilings expandedTiling() {
                int newSize = this.tiling.getSize() + 2 * (2 * getExpansionRate() - 1);
                this.iteration++;
                DiamondTilings newDiamond = new BaseTiling(newSize);
                newDiamond.setUpTiles();
                return newDiamond;
            }
        };

        diamondIterator.setExpansionRate(expansionRate);
        diamondIterator.setTilingBias(0.5);

        // Setup Tiling
        diamondIterator.fillBlankSpaces();

        // Setup Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());
    }

   public void diffRateSetup( int widthRate, int heightRate) {
        class BaseTiling extends DiamondTilings {

            public BaseTiling(){
                super();
            }

            public BaseTiling(int size){
                super(size);
            }

            @Override
            public void setUpTiles() {
                int[] chamfer;
                int iteration = diamondIterator.getIteration();
                int growthDifference = Math.abs(widthRate - heightRate);
                int squareSize = 2 * growthDifference * iteration;
                int blockHeight = 2 * (growthDifference + 1);
                int blockWidth = 2 * growthDifference;
                int blockOffset;

                chamfer = new int[blockWidth];

                for (int i = 0; i < chamfer.length; i++) {
                    chamfer[i] = 3 + (chamfer.length - i - 1);
                }

                DominoTile dom = new DominoTile(false);
                if(heightRate > widthRate) { 
                    for (int k = 0; k < iteration; k++) {
                        blockOffset = blockWidth * (iteration - k - 1);
                        for (int i = 0; i < chamfer.length; i++) {
                            for (int j = 0; j < chamfer[i] + (k * blockHeight); j++) {
                                this.tiles[i + blockOffset][squareSize + j] = dom;  // left top
                                this.tiles[squareSize + j][i + blockOffset] = dom;  // left bottom
                                this.tiles[this.size - (i + blockOffset) - 1][this.size - (squareSize + j) - 1] = dom; // right top
                                this.tiles[this.size - (j + squareSize) - 1][this.size - (i + blockOffset) - 1] = dom; // right bottom
                            }
                        }
                    }
                    for (int i = 0; i < squareSize; i++) {
                        for (int j = 0; j < squareSize; j++) {
                            this.tiles[i][j] = dom;
                            this.tiles[this.size - i - 1][this.size - j - 1] = dom;
                        }
                    }
                }
                else {
                    for (int k = 0; k < iteration; k++) {
                        blockOffset = blockWidth * (iteration - k - 1);
                        for (int i = 0; i < chamfer.length; i++) {
                            for (int j = 0; j < chamfer[i] + (k * blockHeight); j++) {
                                this.tiles[this.size - (i + blockOffset) - 1][squareSize + j] = dom; // top left
                                this.tiles[squareSize + j][this.size - (i + blockOffset) - 1] = dom; // top right
                                this.tiles[i + blockOffset][this.size - (squareSize + j) - 1] = dom; // bottom left
                                this.tiles[this.size - (j + squareSize) - 1][i + blockOffset] = dom; // bottom right
                            }
                        }
                    }
                    for (int i = 0; i < squareSize; i++) {
                        for (int j = 0; j < squareSize; j++) {
                            this.tiles[this.size - i - 1][j] = dom;
                            this.tiles[i][this.size - j - 1] = dom;
                        }
                    }
                }
            }
        }
        this.diamondIterator = new DiamondIteration(new BaseTiling()) {
            @Override
            public Tilings expandedTiling() {
                int newSize = this.tiling.getSize() + 2 * (2 * getExpansionRate() - 1);
                this.iteration++;
                DiamondTilings newDiamond = new BaseTiling(newSize);
                newDiamond.setUpTiles();
                return newDiamond;
            }
        };

        diamondIterator.setExpansionRate(Math.max(widthRate, heightRate));
        diamondIterator.setTilingBias(0.5);

        // Setup Tiling
        diamondIterator.fillBlankSpaces();

        // Setup Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());

   }

    public void variableTilingBias() {
        diamondIterator.setTilingBias((diamondIterator.getIteration() % 4)/3.0);
        diamondIterator.iterateTiles();
        // Update Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());
        drawer.setAnimate(false);
        drawer.setFrameNum(0);
    }
}
