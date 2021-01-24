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
        this.iteration ++;
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveTiles() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeOpposingTiles() {
        RhombusTile tile;
        RhombusTile[][] neighbors;
        
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
