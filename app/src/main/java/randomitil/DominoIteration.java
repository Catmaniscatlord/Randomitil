package randomitil;

import java.util.Arrays;
import java.util.Random;

public class DominoIteration {

    private AztecDiamond aztecDiamond;
    private AztecDiamond removedTiles;
    private AztecDiamond generatedTiles;

    private boolean[][] emptySquares;
    private boolean animationMode = false;

    double tilingBias;

    private Random rand = new Random();

    public DominoIteration(AztecDiamond aztecDiamond) {
        this.aztecDiamond = aztecDiamond;
        this.tilingBias = 0.5;
    }


    /*
     * This function checks the tiles around the given cooridnates int the folloing
     * fasion 
     * [_] [_] [_] 
     * [_] [X] [*] 
     * [_] [*] [*]
     * 
     * and sets to false if any have neighbors
     */

    public void findEmptySquares() {
        /* we subtract one because empty squares needs a space of 2 tiles
         * the size of a tile at the end of the array is 1, so we subtract
         *
         * note: the indice of the square and tiles in the aztec diamond corralate in a somewhat 1-1 manner
         */
        boolean isEmpty;
        this.emptySquares = new boolean[this.aztecDiamond.getSize() - 1][this.aztecDiamond.getSize() - 1];
        for (int i = 0; i < this.emptySquares.length; i++) {
            for (int j = 0; j < this.emptySquares[i].length; j++) {
                // only when the tile isnt null may the tile be empty and  
                // if the orientation of the tile is vertical, then the square my be valid
                if(this.aztecDiamond.getTile(i, j) == null && aztecDiamond.getOrientation(i, j) == Orientation.VERTICAL) {
                    isEmpty = true;
                    if(!this.aztecDiamond.tileHasNeighbors(i, j)) {
                        for (int k = 0; k < 2; k++) {
                            for (int l = 0; l < 2; l++) {
                                if (this.aztecDiamond.tileHasNeighbors(k + i, l + j) || 
                                    this.aztecDiamond.getTile(k + i, l + j) != null) {
                                    isEmpty = false;
                                }
                            }
                        }
                        this.emptySquares[i][j] = isEmpty;
                    }
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

        int[] neighborX = {2,1,0,-1,0};
        int[] neighborY = {0,1,2,1,0};
        /* these arrays will access the neighbors in the following order
         * [*,*,*,*,*]
         * [*,*,*,*,*]
         * [*,*,5,*,1]
         * [*,4,*,2,*]
         * [*,*,3,*,*]
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
                    // since the above algorithm sets the next tile 
                    //in the array to false we can skip over it
                    j++;
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
        this.aztecDiamond.setTile(y, x, new Domino(direction, true));
        if(animationMode) {
            this.generatedTiles.setTile(y, x,  new Domino(direction, true));
        }
    }

    public void moveDominos() {
        removeOpposingTiles();
        AztecDiamond newDiamond = new AztecDiamond(aztecDiamond.getWidth(),aztecDiamond.getHeight(),
                                                   aztecDiamond.getWidthRate(),aztecDiamond.getHeightRate(),
                                                   aztecDiamond.getIteration());
        newDiamond.setEmpty(true);
        newDiamond.expand();
        int expansionRate =  2 * newDiamond.getExpansionRate() - 1;
        Domino tile;
        for (int i = 0; i < aztecDiamond.getSize(); i++) {
            for (int j = 0; j < aztecDiamond.getSize(); j++) {
                tile = aztecDiamond.getTile(i, j);
                if(tile != null && tile.isPlaceable()){
                    switch (tile.getDirection()) {
                    case UP:
                        newDiamond.setTile(i + expansionRate - 1, j + expansionRate + 1, tile);
                        break;
                    case DOWN:
                        newDiamond.setTile(i + expansionRate + 1, j + expansionRate - 1, tile);
                        break;
                    case LEFT:
                        newDiamond.setTile(i + expansionRate - 1, j + expansionRate - 1, tile);
                        break;
                    case RIGHT:
                        newDiamond.setTile(i + expansionRate + 1, j + expansionRate + 1, tile);
                        break;
                    }
                    // in all valid tilings the next item in the array must be null
                    // this loops over the next tile
                    j++;
                }
            }
        }
        newDiamond.setEmpty(false);
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
                    // in all valid tilings the next item in the array must be null
                    // this loops over the next tile
                    j++;
                }
            }
        }
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
