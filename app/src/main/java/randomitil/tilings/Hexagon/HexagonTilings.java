package randomitil.tilings.Hexagon;

import java.util.Arrays;

import randomitil.tilings.*;

public class HexagonTilings extends Tilings {
    
    public HexagonTilings() {
        this(1);
    }

    public HexagonTilings(int size) {
        createTiles(size);
    }

    public void createTiles(int size) {
        this.tiles = new Tile[4*size - 1][];
        this.size = size;
        
        int x;
        for(int i = 0; i < (this.tiles.length - 1)/2; i++){
            x = this.size + ((i + 1)/2);
            if(i % 2 == 0) {
                this.tiles[i] = new Tile[2 * x];
                this.tiles[this.tiles.length - i - 1] = new Tile[2 * x];

            }
            else {
                this.tiles[i] = new Tile[x];
                this.tiles[this.tiles.length - i - 1] = new Tile[x];
            }
        }
        this.tiles[this.size*2 - 1] = new Tile[this.size*2];
    }

    @Override
    public void setUpTiles() {
        //
    }
    
    @Override
    public RhombusTile[][] getTileNeighbors(int y, int x) {
        RhombusTile[][] neighbors = new RhombusTile[2][2];
        if(getOrientation(y, x) == RhombusOrientation.VERTICAL) {
            if(y > this.size * 2) {
                neighbors[0][0] = getTile(y - 1, (x * 2));
                neighbors[0][1] = getTile(y - 1, (x * 2) + 1);
                if(x != 0)
                    neighbors[1][0] = getTile(y + 1, (x * 2) - 1);
                if(x != this.tiles[y].length - 1)
                    neighbors[1][1] = getTile(y + 1, (x * 2));
            }
            if(y < this.size * 2) {
                if(x != 0)
                    neighbors[0][0] = getTile(y - 1, (x * 2) - 1);
                if(x != this.tiles[y].length - 1)
                    neighbors[0][1] = getTile(y - 1, (x * 2));
                neighbors[1][0] = getTile(y + 1, (x * 2));
                neighbors[1][1] = getTile(y + 1, (x * 2) + 1);
            }
        }
        else if(getOrientation(y, x) == RhombusOrientation.RIGHTDIAGONAL){
            if(x != 0)
                neighbors[1][0] = getTile(y, x - 1);
            if(y != this.tiles.length - 1)
                neighbors[1][1] = getTile(y + 1, (x / 2));
            if(y != 0)
                neighbors[0][0] = getTile(y - 1, (x + 1) / 2);
            if(x != this.tiles[y].length - 1)
                neighbors[0][1] = getTile(y, x + 1);  
        }
        else if(getOrientation(y, x) == RhombusOrientation.LEFTDIAGONAL){
            if(x != this.tiles[y].length - 1)
                neighbors[0][0] = getTile(y, x + 1);
            if(y != this.tiles.length - 1)
                neighbors[0][1] = getTile(y + 1, (x + 1) / 2);
            if(x != 0)
                neighbors[1][1] = getTile(y, x - 1);
            if(y != 0)
                neighbors[1][0] = getTile(y - 1, (x / 2));
        }
        return neighbors;
    }

    public RhombusTile[][] getTileOpposingNeighbors(int y, int x) {

        return null;
    }

    @Override
    public boolean tileHasNeighbors(int y, int x) {
        RhombusTile[][] tileNeighbors = getTileNeighbors(y, x);
        if(getTile(y, x) != null)
            System.out.println(Arrays.deepToString(tileNeighbors));
        for (RhombusTile[] i : tileNeighbors) {
            for (RhombusTile j : i) {
                if(j != null && j.isPlaceable())
                    return true;
            }
        }

        return false;
    }

    @Override
    public Orientation getOrientation(int y, int x) {
        if(y % 2 == 1)
            return RhombusOrientation.VERTICAL;
        if((x % 2 == 0 && y < this.size * 2) || (x % 2 == 1 && y > this.size * 2))
            return RhombusOrientation.RIGHTDIAGONAL;
        return RhombusOrientation.LEFTDIAGONAL;
    }

    @Override
    public Tilings checkTiles() {
        Tilings checkArray = new HexagonTilings(this.size);

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (checkTile(i, j) != 0) {
                    checkArray.setTile(i, j, getTile(i, j));
                }
            }
        }
        return checkArray;
    }

    @Override
    public int checkTile(int y, int x) {
        RhombusTile tile = (RhombusTile) getTile(y,x);
        
        if(tile == null || !tile.isPlaceable())
            return 0;
        
        Direction tileDirection = tile.getDirection();

        if(tileHasNeighbors(y, x))
            return 1 ;

        if(getOrientation(y, x) == RhombusOrientation.VERTICAL) {
            if(!(tileDirection != RhombusDirection.UP || tileDirection !=  RhombusDirection.DOWN))
                return 1;
        }
        if(getOrientation(y, x) == RhombusOrientation.LEFTDIAGONAL) {
            if(!(tileDirection != RhombusDirection.UPLEFTDIAGONAL || tileDirection != RhombusDirection.DOWNLEFTDIAGONAL))
                return 1;
        }
        if(getOrientation(y, x) == RhombusOrientation.RIGHTDIAGONAL) {
            if(!(tileDirection != RhombusDirection.UPRIGHTDIAGONAL || tileDirection !=  RhombusDirection.DOWNRIGHTDIAGONAL))
                return 1;
        }

        return 0;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        int blankSpace;
        for (int i = 0; i < this.tiles.length; i++) {
            blankSpace = Math.abs((this.size * 2 - 1) - i); 
            for (int j = 0; j < blankSpace; j++) {
                buf.append("  ");
            }
            buf.append('[');
            for (int j = 0; j < this.tiles[i].length; j++) {
                RhombusTile tile = (RhombusTile) getTile(i, j);
                if (tile != null) {
                    buf.append(tile.toString());
                } else {
                    buf.append("   ");
                }
                if (j != this.tiles[i].length - 1) {
                    buf.append(",");
                    if(i % 2 == 1) {
                        buf.append("___");
                        buf.append(",");
                    }
                }
            }
            buf.append("]\n");
        }
        return buf.toString();
    }

    @Override
    public RhombusTile getTile(int y, int x) {
        return (RhombusTile) super.getTile(y, x);
    }
}
