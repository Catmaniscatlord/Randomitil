package randomitil.tilings.aztecDiamond;

import java.util.Random;

import randomitil.tilings.*;

public class DiamondIteration extends TilingIteration {

    private int expansionRate;

    private Random rand = new Random();

    private DiamondTilings removedTiles;

    public DiamondIteration(DiamondTilings tiling) {
        super(tiling);
    }

    /* Override this function on creation
     * if you want custom expansion
     */
    @Override
    public Tilings expandedTiling() {
        int newSize = this.tiling.getSize() + 2 * (2 * this.expansionRate - 1);
        this.iteration++;
        DiamondTilings newDiamond =  new DiamondTilings(newSize);
        newDiamond.setUpTiles();
        return newDiamond;
    }

    @Override
    public void findBlankSpaces() {
         /* we subtract one because empty squares needs a space of 2 tiles
         * the size of a tile at the end of the array is 1, so we subtract
         *
         * note: the indice of the square and tiles in the aztec diamond corralate in a somewhat 1-1 manner
         */
        boolean isEmpty;
        this.blankSpaces = new boolean[this.tiling.getSize() - 1][this.tiling.getSize() - 1];
        for (int i = 0; i < this.blankSpaces.length; i++) {
            for (int j = 0; j < this.blankSpaces[i].length; j++) {
                // only when the tile isnt null may the tile be empty and  
                // if the orientation of the tile is vertical, then the square my be valid
                if(this.tiling.getTile(i, j) == null && tiling.getOrientation(i, j) == DominoOrientation.VERTICAL) {
                    isEmpty = true;
                    if(!this.tiling.tileHasNeighbors(i, j)) {
                        for (int k = 0; k < 2; k++) {
                            for (int l = 0; l < 2; l++) {
                                if (this.tiling.tileHasNeighbors(k + i, l + j) || 
                                    this.tiling.getTile(k + i, l + j) != null) {
                                    isEmpty = false;
                                }
                            }
                        }
                        this.blankSpaces[i][j] = isEmpty;
                    }
                }
            }
        }
    }

    @Override
    public void fillBlankSpaces() {
        findBlankSpaces();
        if(this.animationMode) {
            if(this.generatedTiles == null || (this.generatedTiles.getSize() != this.generatedTiles.getSize())) {
                this.generatedTiles = new DiamondTilings(this.tiling.getSize());
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

        for (int i = 0; i < this.blankSpaces.length; i++) {
            for (int j = 0; j < this.blankSpaces[i].length; j++) {
                if(this.blankSpaces[i][j]) {
                    fillBlankSpace(i, j);
                    for (int k = 0; k < neighborX.length; k++) {
                        if (neighborX[k] + j >= 0 && neighborY[k] + i >= 0 && 
                            neighborX[k] + j < this.blankSpaces.length && neighborY[k] + i < this.blankSpaces.length) {
                            this.blankSpaces[neighborY[k] + i][neighborX[k] + j] = false;
                        }  
                    }
                    // since the above algorithm sets the next tile 
                    // in the array to false we can skip over it
                    j++;
                }
            }
        }
    }

    @Override
    public void fillBlankSpace(int y, int x) {
        double orientation = rand.nextDouble();
        if (orientation <= tilingBias) {
            generateTile(y + 1, x, DominoDirection.DOWN);
            generateTile(y , x + 1, DominoDirection.UP);          
        } else {
            generateTile(y, x, DominoDirection.LEFT);
            generateTile(y + 1, x + 1, DominoDirection.RIGHT);
        }
    }

    private void generateTile(int y, int x, DominoDirection direction) {
        this.tiling.setTile(y, x, new DominoTile(direction));
        if(animationMode) {
            this.generatedTiles.setTile(y, x,  new DominoTile(direction));
        }
    }

    @Override
    public void moveTiles() {
        removeOpposingTiles();
        DiamondTilings newDiamond = (DiamondTilings) expandedTiling();
        int expansionRate =  2 * this.expansionRate - 1;
        DominoTile tile;
        for (int i = 0; i < this.tiling.getSize(); i++) {
            for (int j = 0; j < this.tiling.getSize(); j++) {
                tile = (DominoTile) this.tiling.getTile(i, j);
                if(tile != null && tile.isPlaceable()){
                    switch ((DominoDirection) tile.getDirection()) {
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
        this.tiling = newDiamond;
    }

    private void removeOpposingTiles () {
        DominoTile tile;
        DominoTile[][] neighbors;
         
        if (this.animationMode) {
            removedTiles = new DiamondTilings(this.tiling.getSize());
        }

        for (int i = 0; i < this.tiling.getSize(); i++) {
            for (int j = 0; j < this.tiling.getSize(); j++) {
                tile = (DominoTile) this.tiling.getTile(i,j);
                if(tile != null && tile.isPlaceable()) {
                    neighbors = (DominoTile[][]) this.tiling.getTileNeighbors(i, j);
                    if(this.tiling.getOrientation(i, j) == DominoOrientation.VERTICAL) {
                        if(tile.direction == DominoDirection.LEFT && neighbors[0][0] != null && neighbors[0][0].direction == DominoDirection.RIGHT) {
                            this.tiling.setTile(i, j, null);
                            this.tiling.setTile(i - 1, j - 1, null);
                             
                            if (this.animationMode) {
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i - 1, j - 1, neighbors[0][0]);
                            }
                        } 
                        else if (tile.direction == DominoDirection.RIGHT && neighbors[2][2] != null && neighbors[2][2].direction == DominoDirection.LEFT) {
                            this.tiling.setTile(i, j, null);
                            this.tiling.setTile(i + 1, j + 1, null);
                             
                            if (this.animationMode) {        
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i + 1, j + 1, neighbors[2][2]);
                            }
                        }
                    }
                    else {
                        if(tile.direction == DominoDirection.UP && neighbors[0][2] != null && neighbors[0][2].direction == DominoDirection.DOWN) {
                            this.tiling.setTile(i, j, null);
                            this.tiling.setTile(i - 1, j + 1, null);
                             
                            if (this.animationMode) {
                                this.removedTiles.setTile(i, j, tile);
                                this.removedTiles.setTile(i - 1, j + 1, neighbors[0][2]);
                            }
                        } 
                        else if (tile.direction == DominoDirection.DOWN && neighbors[2][0] != null && neighbors[2][0].direction == DominoDirection.UP) {
                            this.tiling.setTile(i, j, null);
                            this.tiling.setTile(i + 1, j - 1, null);
                             
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

    @Override
    public void iterateTiles() {
        moveTiles();
        fillBlankSpaces();
    }

    @Override
    public String blankSpacesString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.blankSpaces.length; i++) {
            buf.append('[');
            for (int j = 0; j < this.blankSpaces[i].length; j++) {
                if(this.blankSpaces[i][j])
                    buf.append("*");
                else
                    buf.append("_");
                if (j != this.blankSpaces[i].length - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        return buf.toString();
    }

    public DiamondTilings getRemovedTiles() {
        return removedTiles;
    }
}