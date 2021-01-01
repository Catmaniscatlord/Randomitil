package randomitil;

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
    
    public void fillEmptyTiles() {
        findEmptyTiles();
        System.out.println(Arrays.deepToString(emptyTiles));
        for (int i = 0; i < this.emptyTiles.length; i++) {
            for (int j = 0; j < this.emptyTiles[i].length; j++) {
                if(this.emptyTiles[i][j]) {
                    generateTileRand(i, j, this.aztecDiamond.getOrientation(i, j));
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
                // if the orientation of the tile is horizontal, the the square my be valid
                if (aztecDiamond.getOrientation(i, j) == Orientation.HORIZONTAL) {
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
        int neighborX;
        int neighborY;
        //I am well aware this is illegal, but I have no ther choice (4 nested 4 loops)
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
            generateTileRand(y + 1, x, Orientation.VERTICAL);
            generateTileRand(y , x + 1, Orientation.VERTICAL);
        } else {
            generateTileRand(y, x, Orientation.HORIZONTAL);
            generateTileRand(y + 1, x + 1, Orientation.HORIZONTAL);
        }
    }

    private void generateTile(int y, int x, Direction direction) {
        this.aztecDiamond.setTile(y, x, new Domino(direction, false, true));
    }

    private void generateTileRand(int y, int x, Orientation orientation) {
        int direction = rand.nextInt(2); //generats either 1 or 0
        if (orientation == Orientation.VERTICAL) {
            if (direction == 0)
                this.aztecDiamond.setTile(y, x, new Domino(Direction.UP, false, true));
            else
                this.aztecDiamond.setTile(y, x, new Domino(Direction.DOWN, false, true));
        }
        else if (orientation == Orientation.HORIZONTAL) {
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
        for (int i = 0; i < this.aztecDiamond.getSize(); i++) {
            for (int j = 0; j < this.aztecDiamond.getSize(); j++) {
                tile = aztecDiamond.getTile(i,j);
                if(tile != null && tile.isPlaceable()) {
                    neighbors = aztecDiamond.getTileNeighbors(i, j);
                    if(aztecDiamond.getOrientation(i, j) == Orientation.HORIZONTAL) {
                        if(tile.direction == Direction.LEFT && neighbors[0][0].direction == Direction.RIGHT) {
                            aztecDiamond.setTile(i, j, null);
                            aztecDiamond.setTile(i - 1, j - 1, null);
                        } 
                        else if (tile.direction == Direction.RIGHT && neighbors[2][2].direction == Direction.LEFT) {
                            aztecDiamond.setTile(i, j, null);
                            aztecDiamond.setTile(i + 1, j + 1, null);
                        }
                    } 
                    else {
                        if(tile.direction == Direction.UP && neighbors[0][2].direction == Direction.DOWN) {
                            aztecDiamond.setTile(i, j, null);
                            aztecDiamond.setTile(i - 1, j + 1, null);
                        } 
                        else if (tile.direction == Direction.DOWN && neighbors[2][0].direction == Direction.UP) {
                            aztecDiamond.setTile(i, j, null);
                            aztecDiamond.setTile(i + 1, j - 1, null);
                        } 
                    }
                }
            }
        }
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
