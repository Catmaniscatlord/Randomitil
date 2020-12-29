package randomitil;

public class DominoIteration {

    private boolean[][] emptyTiles;
    private boolean[][] emptySquares;
    double tilingBias;

    private AztecDiamond aztecDiamond;

    public DominoIteration(AztecDiamond aztecDiamond) {
        this.aztecDiamond = aztecDiamond;
        findEmptyTiles();
    }

    private void findEmptyTiles() {
        emptyTiles = new boolean[this.aztecDiamond.getSize()][this.aztecDiamond.getSize()];
        for (int i = 0; i < this.emptyTiles.length; i++) {
            for (int j = 0; j < this.emptyTiles[i].length; j++) {
                if (this.aztecDiamond.getTile(i, j) == null) {
                    if (this.aztecDiamond.hasNoNeighbors(i, j)) {
                        emptyTiles[i][j] = true;
                    }
                }
            }
        }
    }

    public void moveDominos(int expansionSize) {

    }

    public AztecDiamond getAztecDiamond() {
        return aztecDiamond;
    }
}
