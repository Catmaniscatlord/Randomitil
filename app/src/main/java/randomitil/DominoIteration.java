package randomitil;

import java.util.Arrays;
import java.util.Random;

public class DominoIteration {

    private AztecDiamond aztecDiamond;
    private AztecDiamond removedTiles;
    private AztecDiamond generatedTiles;
    private boolean[][] emptyTiles;
    private boolean[][] emptySquares;
    private boolean animationMode = false;

    double tilingBias;

    private Random rand = new Random();

    public DominoIteration(AztecDiamond aztecDiamond) {
        this.aztecDiamond = aztecDiamond;
        this.tilingBias = 0.5;
    }

    private void findEmptyTiles() {
        this.emptyTiles = new boolean[this.aztecDiamond.getSize()][this.aztecDiamond.getSize()];
        for (int i = 0; i < this.emptyTiles.length; i++) {
            for (int j = 0; j < this.emptyTiles[i].length; j++) {
                if(aztecDiamond.getTile(i, j) == null) {    
                    this.emptyTiles[i][j] = !this.aztecDiamond.tileHasNeighbors(i, j);
                }
                else
                    this.emptyTiles[i][j] = false;
            }
        }
    }
    
    public void fillEmptyTiles() {
        findEmptyTiles();
        if(this.animationMode) {
            if(this.generatedTiles == null) {
                this.generatedTiles = new AztecDiamond(this.aztecDiamond.getWidth(),this.aztecDiamond.getHeight());
            }
            if(this.generatedTiles.getSize() != this.aztecDiamond.getSize()) {
                this.generatedTiles = new AztecDiamond(this.aztecDiamond.getWidth(),this.aztecDiamond.getHeight());
            }
        }   
        for (int i = 0; i < this.emptyTiles.length; i++) {
            for (int j = 0; j < this.emptyTiles[i].length; j++) {
                if(this.emptyTiles[i][j]) {
                    generateTileRand(i, j, this.aztecDiamond.getOrientation(i, j));
                     
                    if(this.animationMode) {
                        this.generatedTiles.setTile(i, j,  this.aztecDiamond.getTile(i, j));
                    }
                }
            }
        }
    }

    /*
     * This function checks the tiles around the given cooridnates int he folloing
     * fasion 
     * [_] [_] [_] 
     * [_] [X] [*] 
     * [_] [*] [*]
     * 
     * and returns false if any are true
     * 
     * Do not check for tiles at the end of a coloumn or row
     */

    private boolean isEmptySquare(int y, int x) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (!this.emptyTiles[i + y][j + x]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void findEmptySquares() {
        findEmptyTiles();
        this.emptySquares = new boolean[this.aztecDiamond.getSize() - 1][this.aztecDiamond.getSize() - 1];
        // we subtract one so that we don't check the final row and column
        // as they aren't important
        for (int i = 0; i < this.emptyTiles.length - 1; i++) {
            for (int j = 0; j < this.emptyTiles[i].length - 1; j++) {
                // if the orientation of the tile is vertical, the the square my be valid
                if (aztecDiamond.getOrientation(i, j) == Orientation.VERTICAL) {
                    this.emptySquares[i][j] = isEmptySquare(i, j);
                } else {
                    this.emptySquares[i][j] = false;
                }
            }
        }
    }

    
    // might change this algorithm later
    public void fillEmptySquares() {
        findEmptySquares();
        if(this.animationMode) {
            if(this.generatedTiles == null) {
                this.generatedTiles = new AztecDiamond(this.aztecDiamond.getWidth(),this.aztecDiamond.getHeight());
            }
            if(this.generatedTiles.getSize() != this.aztecDiamond.getSize()) {
                this.generatedTiles = new AztecDiamond(this.aztecDiamond.getWidth(),this.aztecDiamond.getHeight());
            }
        }

        int[] neighborX = {0,1,2,1,0,-1,-2,-1,0};
        int[] neighborY = {-2,-1,0,1,2,1,0,-1,0};
        /* these arrays will access the neighbors in the following order
         * [*,*,1,*,*]
         * [*,8,*,2,*]
         * [7,*,9,*,3]
         * [*,6,*,4,*]
         * [*,*,5,*,*]
        */

        for (int i = 0; i < this.emptySquares.length; i++) {
            for (int j = 0; j < this.emptySquares[i].length; j++) {
                if(this.emptySquares[i][j]) {
                    generateSquare(i, j);
                    for (int k = 0; k < neighborX.length; k++) {
                        if (neighborX[k] + j >= 0 && neighborY[k] + i >= 0 && 
                            neighborX[k] + j < this.emptySquares.length && neighborY[k] + i < this.emptySquares.length) {
                            this.emptySquares[neighborY[k] + i][neighborX[k] + j] = false;
                        }  
                    }
                }
            }
        }
    }

    private void generateSquare(int y, int x) {
        double orientation = rand.nextDouble();
        if (orientation <= tilingBias) {
            generateTile(y + 1, x, Direction.DOWN);
            generateTile(y , x + 1, Direction.UP);          
        } else {
            generateTile(y, x, Direction.LEFT);
            generateTile(y + 1, x + 1, Direction.RIGHT);
        }
    }

    private void generateTile(int y, int x, Direction direction) {
        this.aztecDiamond.setTile(y, x, new Domino(direction, false, true));
        if(animationMode) {
            this.generatedTiles.setTile(y, x,  new Domino(direction, false, true));
        }
    }

    private void generateTileRand(int y, int x, Orientation orientation) {
        int direction = rand.nextInt(2); // generates either 1 or 0
        if (orientation == Orientation.HORIZONTAL) {
            if (direction == 0)
                this.aztecDiamond.setTile(y, x, new Domino(Direction.UP, false, true));
            else
                this.aztecDiamond.setTile(y, x, new Domino(Direction.DOWN, false, true));
        }
        else if (orientation == Orientation.VERTICAL) {
            if (direction == 0)
                this.aztecDiamond.setTile(y, x, new Domino(Direction.LEFT, false, true));
            else
                this.aztecDiamond.setTile(y, x, new Domino(Direction.RIGHT, false, true));
        }
    }

    public void moveDominos(int expansionSize) {
        removeOpposingTiles();
        aztecDiamond.expand(expansionSize);
        Domino tile;
        AztecDiamond newDiamond = new AztecDiamond(aztecDiamond.getWidth(),aztecDiamond.getHeight());
        for (int i = 0; i < aztecDiamond.getSize(); i++) {
            for (int j = 0; j < aztecDiamond.getSize(); j++) {
                tile = aztecDiamond.getTile(i, j);
                if(tile != null && tile.isPlaceable()){
                    switch (tile.getDirection()) {
                    case UP:
                        newDiamond.setTile(i - 1, j + 1, tile);
                        break;
                    case DOWN:
                        newDiamond.setTile(i + 1, j - 1, tile);
                        break;
                    case LEFT:
                        newDiamond.setTile(i - 1, j - 1, tile);
                        break;
                    case RIGHT:
                        newDiamond.setTile(i + 1, j + 1, tile);
                        break;
                    }
                }
            }
        }
        this.aztecDiamond = newDiamond;
    }

    private void removeOpposingTiles () {
        Domino tile;
        Domino[][] neighbors;
         
        if (this.animationMode) {
            removedTiles = new AztecDiamond(this.aztecDiamond.getWidth(),this.aztecDiamond.getHeight());
        }

        for (int i = 0; i < this.aztecDiamond.getSize(); i++) {
            for (int j = 0; j < this.aztecDiamond.getSize(); j++) {
                tile = this.aztecDiamond.getTile(i,j);
                if(tile != null && tile.isPlaceable()) {
                    neighbors = this.aztecDiamond.getTileNeighbors(i, j);
                    if(this.aztecDiamond.getOrientation(i, j) == Orientation.VERTICAL) {
                        if(tile.direction == Direction.LEFT && neighbors[0][0] != null && neighbors[0][0].direction == Direction.RIGHT) {
                            this.aztecDiamond.setTile(i, j, null);
                            this.aztecDiamond.setTile(i - 1, j - 1, null);
                             
                            if (this.animationMode) {
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i - 1, j - 1, neighbors[0][0]);
                            }
                        } 
                        else if (tile.direction == Direction.RIGHT && neighbors[2][2] != null && neighbors[2][2].direction == Direction.LEFT) {
                            this.aztecDiamond.setTile(i, j, null);
                            this.aztecDiamond.setTile(i + 1, j + 1, null);
                             
                            if (this.animationMode) {        
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i + 1, j + 1, neighbors[2][2]);
                            }
                        }
                    }
                    else {
                        if(tile.direction == Direction.UP && neighbors[0][2] != null && neighbors[0][2].direction == Direction.DOWN) {
                            this.aztecDiamond.setTile(i, j, null);
                            this.aztecDiamond.setTile(i - 1, j + 1, null);
                             
                            if (this.animationMode) {
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i - 1, j + 1, neighbors[0][2]);
                            }
                        } 
                        else if (tile.direction == Direction.DOWN && neighbors[2][0] != null && neighbors[2][0].direction == Direction.UP) {
                            this.aztecDiamond.setTile(i, j, null);
                            this.aztecDiamond.setTile(i + 1, j - 1, null);
                             
                            if (this.animationMode) {
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i + 1, j - 1, neighbors[2][0]);
                            }
                        } 
                    }
                }
            }
        }
    }

    public String emptyTilesString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.emptyTiles.length; i++) {
            buf.append('[');
            for (int j = 0; j < this.emptyTiles[i].length; j++) {
                buf.append(this.emptyTiles[i][j]);
                if (j != this.emptyTiles[i].length - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        return buf.toString();
    }

    
    public String emptySquaresString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.emptySquares.length; i++) {
            buf.append('[');
            for (int j = 0; j < this.emptySquares[i].length; j++) {
                if(this.emptySquares[i][j])
                    buf.append("*");
                else
                    buf.append("_");
                if (j != this.emptySquares[i].length - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        return buf.toString();
    }

    public void setTilingBias(double tilingBias) {
        if (tilingBias > 1)
            this.tilingBias = 1;
        else if (tilingBias < 0)
            this.tilingBias = 0;
        else
            this.tilingBias = tilingBias;
    }

    public double getTilingBias() {
        return tilingBias;
    }

    public boolean[][] getEmptySquares() {
        return emptySquares;
    }

    public boolean[][] getEmptyTiles() {
        return emptyTiles;
    }
    
    public void setanimationMode(boolean animationMode) {
        this.animationMode = animationMode;
    }
    
    public boolean isanimationMode() {
        return animationMode;
    }

    public AztecDiamond getAztecDiamond() {
        return aztecDiamond;
    }

    public AztecDiamond getRemovedTiles() {
        return removedTiles;
    }

    public AztecDiamond getGeneratedTiles() {
        return generatedTiles;
    }
}
