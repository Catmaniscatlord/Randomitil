package randomitil.tilings.Hexagon;

import java.util.Random;

import randomitil.tilings.*;

public class HexagonIteration extends TilingIteration {
    
    private Random rand = new Random();

    public HexagonIteration(HexagonTilings tiling) {
        super(tiling);
    }

    @Override
    protected Tilings expandedTiling() {
        int newSize = this.tiling.getSize() + 1;
        this.iteration++;
        HexagonTilings newHexagon = new HexagonTilings(newSize);
        newHexagon.setUpTiles();
        return newHexagon;
    }

    @Override
    protected void findBlankSpaces() {
        // TODO Auto-generated method stub
    
    }
    
    @Override
    public void fillBlankSpaces() {
        // TODO Auto-generated method stub
        
    }    

    @Override
    protected void fillBlankSpace(int y, int x) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void generateTile(int y, int x, Direction direction) {
        
    }

    @Override
    public void moveTiles() {
        HexagonTilings newHexagon = (HexagonTilings) expandedTiling();
        RhombusTile tile;
        for (int i = 0; i < this.tiling.getTiles().length; i++) {
            for (int j = 0; j < this.tiling.getTiles()[i].length; j++) {
                tile = (RhombusTile) this.tiling.getTile(i,j);
                if(tile != null && tile.isPlaceable()) {
                    /* Left and right are nice and tight
                     * but all the rest leave me in distress
                    **/
                    switch((RhombusDirection) tile.getDirection()) {
                        case LEFT:
                            newHexagon.setTile(i + 2, j, tile);
                            break;
                        case RIGHT:
                            newHexagon.setTile(i + 2, j + 2, tile);
                            break;
                        case DOWNLEFTDIAGONAL:
                            if(i == (this.tiling.getTiles().length / 2) - 1)
                                newHexagon.setTile(i + 4, j + 3, tile);
                            newHexagon.setTile(i + 4, j + 4, tile);
                            break;
                        case UPLEFTDIAGONAL:
                            if(i == (this.tiling.getTiles().length / 2) + 1)
                                newHexagon.setTile(i + 0, j + 1, tile);
                            newHexagon.setTile(i + 0, j + 0, tile);
                            break;
                        case DOWNRIGHTDIAGONAL:
                            if(i == (this.tiling.getTiles().length / 2) - 1)
                                newHexagon.setTile(i + 4, j + 1, tile);
                            newHexagon.setTile(i + 4, j + 0, tile);
                            break;
                        case UPRIGHTDIAGONAL:
                            if(i == (this.tiling.getTiles().length / 2) + 1)
                                newHexagon.setTile(i + 0, j + 3, tile);
                            newHexagon.setTile(i + 0, j + 4, tile);
                            break;
                    }
                }
            }
        }
    }

    // The most expesnive step in iteration
    @Override
    public void removeOpposingTiles() {
        RhombusTile tile;
        int[][] neighbors;

        if(this.animationMode) {
            this.removedTiles = new HexagonTilings(this.tiling.getSize());
        }
        
        for (int i = 0; i < this.tiling.getTiles().length; i++) {
            for (int j = 0; j < this.tiling.getTiles()[i].length; j++) {
                tile = (RhombusTile) this.tiling.getTile(i, j);
                if(tile != null &&  tile.isPlaceable()) {
                    // this is the strangest way I've ever used casting in java
                    neighbors = ((HexagonTilings) this.tiling).getTileOpposingNeighbors(i, j);
                    for (int[] k : neighbors) {
                        if(k != null) {
                            this.tiling.setTile(k[1], k[0], null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void iterateTiles() {
        removeOpposingTiles();
        moveTiles();
        fillBlankSpaces();
    }

    @Override
    public String blankSpacesString() {
        // TODO Auto-generated method stub
        return null;
    }

}
