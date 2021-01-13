package randomitil.tilings;

public abstract class Tilings {
     
    protected Tile[][] tiles;

    protected int size;

    abstract public void setUpTiles();

    abstract public Tilings checkTiles();
    
    abstract public int checkTile(int y, int x);

    abstract public Tile[][] getTileNeighbors(int y, int x);

    abstract public boolean tileHasNeighbors(int y, int x);

    abstract public Orientation getOrientation(int y, int x);

    public boolean isEmpty() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != null && tiles[i][j].isPlaceable())
                    return false; 
            }
        }
        return true;
    }

    public void setTile(int y, int x, Tile tile) {
        this.tiles[y][x] = tile;
    }

    public Tile getTile(int y, int x) {
        return tiles[y][x];
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}