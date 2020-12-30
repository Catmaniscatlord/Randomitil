package randomitil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class DominoIteration {

    private AztecDiamond aztecDiamond;
    private boolean[][] emptyTiles;
    private boolean[][] emptySquares;

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
                if (this.aztecDiamond.getTile(i, j) == null) {
                    this.emptyTiles[i][j] = !this.aztecDiamond.tileHasNeighbors(i, j); 
                } else {
                    this.emptyTiles[i][j] = false;
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
        // we subtract one se we don't check the final row and column
        // as they aren't important
        for (int i = 0; i < this.emptyTiles.length - 1; i++) {
            for (int j = 0; j < this.emptyTiles[i].length - 1; j++) {
                // centers of square are only there when the sum of the tiles
                // x and y are even
                if ((i + j) % 2 == 0) {
                    this.emptySquares[i][j] = isEmptySquare(i, j);
                } else {
                    this.emptySquares[i][j] = false;
                }
            }
        }
    }


    // might change this algorithm later
    public void generateSquares() {
        findEmptySquares();
        int neighborX;
        int neighborY;
        //I am well aware this is illegal, but I have no ther choice
        for (int i = 0; i < this.emptySquares.length; i++) {
            for (int j = 0; j < this.emptySquares[i].length; j++) {
                if(this.emptySquares[i][j]) {
                    generateSquare(i, j);
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            neighborY = k + i - 1;
                            neighborX = l + j - 1;
                            if (neighborX >= 0 && neighborY >= 0 && neighborX < this.emptySquares.length && neighborY < this.emptySquares.length) {
                                this.emptySquares[neighborY][neighborX] = false;
                            }  
                        }
                    }
                }
            }
        }
    }

    private void generateSquare(int y, int x) {
        double direction = rand.nextDouble();
        if (direction <= tilingBias) {
            generateTileRand(y + 1, x, Orinetation.VERTICAL);
            generateTileRand(y , x + 1, Orinetation.VERTICAL);
        } else {
            generateTileRand(y, x, Orinetation.HORIZONTAL);
            generateTileRand(y + 1, x + 1, Orinetation.HORIZONTAL);
        }
    }

    private void generateTile(int y, int x, Direction direction) {
        this.aztecDiamond.setTile(y, x, new Domino(direction, false, true));
    }

    private void generateTileRand(int y, int x, Orinetation orinetation) {
        int direction = rand.nextInt(2); //generats either 1 or 0
        if (orinetation == Orinetation.VERTICAL) {
            if (direction == 0)
                this.aztecDiamond.setTile(y, x, new Domino(Direction.UP, false, true));
            else
                this.aztecDiamond.setTile(y, x, new Domino(Direction.DOWN, false, true));
        }
        else if (orinetation == Orinetation.HORIZONTAL) {
            if (direction == 0)
                this.aztecDiamond.setTile(y, x, new Domino(Direction.LEFT, false, true));
            else
                this.aztecDiamond.setTile(y, x, new Domino(Direction.RIGHT, false, true));
        }
    }

    public void moveDominos(int expansionSize) {

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

    public AztecDiamond getAztecDiamond() {
        return aztecDiamond;
    }
}
