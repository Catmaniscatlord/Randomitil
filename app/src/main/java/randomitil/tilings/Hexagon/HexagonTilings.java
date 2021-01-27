// THE AMOUNT OF IF STATEMENTS IN THEIS FILE IS TO DAMN HIGH
// https://memecreator.org/meme/the-amount-of-if-statements-in-this-file-is-too-damn-high


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
            x = this.size + (i + 1) / 2;
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
        switch (getOrientation(y, x)) {
            case HORIZONTAL:
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
                break;
            case RIGHTDIAGONAL:
                if(x != this.tiles[y].length - 1)
                    neighbors[0][0] = getTile(y, x + 1);
                if(y != this.tiles.length - 1)
                    neighbors[0][1] = getTile(y + 1, (x + 1) / 2);
                if(x != 0)
                    neighbors[1][1] = getTile(y, x - 1);
                if(y != 0)
                    neighbors[1][0] = getTile(y - 1, (x / 2));
                break;    
            case LEFTDIAGONAL:
                if(x != 0)
                    neighbors[1][0] = getTile(y, x - 1);
                if(y != this.tiles.length - 1)
                    neighbors[1][1] = getTile(y + 1, (x / 2));
                if(y != 0)
                    neighbors[0][0] = getTile(y - 1, (x + 1) / 2);
                if(x != this.tiles[y].length - 1)
                    neighbors[0][1] = getTile(y, x + 1);
                break;
        }
        return neighbors;
    }


    /*  We return an int[][x,y] that way wer can retrieve the coordinates
     * of each opposing tile to remove it, I would do it in a RhombusTile[][]
     * so I could use relative coors, but there is 2 different situations 
     * of conflicting tiles, so its easier to use absolute positions instead of relative
    */
    public int[][] getTileOpposingNeighbors(int y, int x) {
        int[][] neighbors = new int[7][];
        int[][] hexagons = new int[3][];
        int[][] hexagonTiles = new int[6][];
        int[] hex;
        RhombusTile tile;

        // I never want to touch an array ever again.

        switch ((RhombusDirection) getTile(y, x).getDirection()) {
            case LEFT:
                hex = getTileHexagon(y, x, 0);
                hexagons[0] = new int[]{hex[0] - 1,hex[1] - 1};
                hexagons[1] = new int[]{hex[0] - 1,hex[1]};
                hexagons[2] = new int[]{hex[0] - 1,hex[1] + 1};
                
                // top hexagon 1,4
                hexagonTiles = getHexagonTiles(hexagons[0][1], hexagons[0][0]);

                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL) 
                    neighbors[0] = new int[]{hexagonTiles[1][0],hexagonTiles[1][1]};
            
                    tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL) 
                    neighbors[1] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                    
                // middle hexagon 0,2,4
                hexagonTiles = getHexagonTiles(hexagons[1][1], hexagons[1][0]);

                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT)
                    neighbors[2] = new int[]{hexagonTiles[0][1],hexagonTiles[0][0]};
                
                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[3] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
                
                tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL)
                    neighbors[4] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                
                // bottom hexagon 2,5
                hexagonTiles = getHexagonTiles(hexagons[2][1], hexagons[2][0]);

                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[5] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
            
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[6] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};
                
                    break;
            case RIGHT:   
                hex = getTileHexagon(y, x, 1);
                hexagons[0] = new int[]{hex[0],hex[1] - 1};
                hexagons[1] = new int[]{hex[0] + 1,hex[1]};
                hexagons[2] = new int[]{hex[0],hex[1] + 1};
                
                // top hexagon 2,5
                hexagonTiles = getHexagonTiles(hexagons[0][1], hexagons[0][0]);
                
                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL) 
                    neighbors[0] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
            
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL) 
                    neighbors[1] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};
                    
                // middle hexagon 1,3,5
                hexagonTiles = getHexagonTiles(hexagons[1][1], hexagons[1][0]);

                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                    neighbors[2] = new int[]{hexagonTiles[1][1],hexagonTiles[1][0]};
                
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT)
                    neighbors[3] = new int[]{hexagonTiles[3][0],hexagonTiles[3][1]};
                
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL)
                    neighbors[4] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};
                
                // bottom hexagon 1,4
                hexagonTiles = getHexagonTiles(hexagons[2][1], hexagons[2][0]);

                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                    neighbors[5] = new int[]{hexagonTiles[1][0],hexagonTiles[1][1]};
            
                tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                    neighbors[6] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                
                break;
            case UPLEFTDIAGONAL:
                hex = getTileHexagon(y, x, 0);
                hexagons[0] = new int[]{hex[0],hex[1] - 1};
                hexagons[1] = new int[]{hex[0] - 1,hex[1] - 1};
                hexagons[2] = new int[]{hex[0] - 1,hex[1]};
                
                // top hexagon 2,5
                hexagonTiles = getHexagonTiles(hexagons[0][1], hexagons[0][0]);
                
                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL) 
                    neighbors[0] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
            
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL) 
                    neighbors[1] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};
                    
                // middle hexagon 1,3,5
                hexagonTiles = getHexagonTiles(hexagons[1][1], hexagons[1][0]);

                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL)
                    neighbors[2] = new int[]{hexagonTiles[1][1],hexagonTiles[1][0]};
                
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT)
                    neighbors[3] = new int[]{hexagonTiles[3][0],hexagonTiles[3][1]};
                
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL)
                    neighbors[4] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};
                
                // bottom hexagon 0, 3
                hexagonTiles = getHexagonTiles(hexagons[2][1], hexagons[2][0]);

                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT)
                    neighbors[5] = new int[]{hexagonTiles[0][0],hexagonTiles[0][1]};
            
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT)
                    neighbors[6] = new int[]{hexagonTiles[3][0],hexagonTiles[3][1]};

                break;
            case DOWNLEFTDIAGONAL:
                hex = getTileHexagon(y, x, 1);
                hexagons[0] = new int[]{hex[0] + 1,hex[1]};
                hexagons[1] = new int[]{hex[0],hex[1] + 1};
                hexagons[2] = new int[]{hex[0] - 1,hex[1] + 1};
                
                // top hexagon 0 , 3
                hexagonTiles = getHexagonTiles(hexagons[0][1], hexagons[0][0]);
                
                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT) 
                    neighbors[0] = new int[]{hexagonTiles[0][0],hexagonTiles[0][1]};
            
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT) 
                    neighbors[1] = new int[]{hexagonTiles[3][0],hexagonTiles[3][1]};
                    
                // middle hexagon 0, 2, 4
                hexagonTiles = getHexagonTiles(hexagons[1][1], hexagons[1][0]);

                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT)
                    neighbors[2] = new int[]{hexagonTiles[0][1],hexagonTiles[0][0]};
                
                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[3] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
                
                tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                    neighbors[4] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                
                // bottom hexagon 2,5
                hexagonTiles = getHexagonTiles(hexagons[2][1], hexagons[2][0]);

                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[5] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
            
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[6] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};

                break;
            case UPRIGHTDIAGONAL:
                hex = getTileHexagon(y, x, 0);
                hexagons[0] = new int[]{hex[0] - 1,hex[1] - 1};
                hexagons[1] = new int[]{hex[0],hex[1] - 1};
                hexagons[2] = new int[]{hex[0] + 1,hex[1]};
                
                // top hexagon 1,4
                hexagonTiles = getHexagonTiles(hexagons[0][1], hexagons[0][0]);
                
                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL) 
                    neighbors[0] = new int[]{hexagonTiles[1][0],hexagonTiles[1][1]};
            
                tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL) 
                    neighbors[1] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                    
                // middle hexagon 0,2,4
                hexagonTiles = getHexagonTiles(hexagons[1][1], hexagons[1][0]);

                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT)
                    neighbors[2] = new int[]{hexagonTiles[0][1],hexagonTiles[0][0]};
                
                tile = getTile(hexagonTiles[2][1], hexagonTiles[2][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNRIGHTDIAGONAL)
                    neighbors[3] = new int[]{hexagonTiles[2][0],hexagonTiles[2][1]};
                
                tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.DOWNLEFTDIAGONAL)
                    neighbors[4] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                
                // bottom hexagon 0, 3
                hexagonTiles = getHexagonTiles(hexagons[2][1], hexagons[2][0]);

                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT)
                    neighbors[5] = new int[]{hexagonTiles[0][0],hexagonTiles[0][1]};
            
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.LEFT)
                neighbors[6] = new int[]{hexagonTiles[3][0],hexagonTiles[3][1]};
                
                break;
            case DOWNRIGHTDIAGONAL:    
                hex = getTileHexagon(y, x, 1);
                hexagons[0] = new int[]{hex[0] - 1,hex[1]};
                hexagons[1] = new int[]{hex[0] - 1,hex[1] + 1};
                hexagons[2] = new int[]{hex[0],hex[1] + 1};
                
                // top hexagon 0,3
                hexagonTiles = getHexagonTiles(hexagons[0][1], hexagons[0][0]);
                
                tile = getTile(hexagonTiles[0][1], hexagonTiles[0][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT) 
                    neighbors[0] = new int[]{hexagonTiles[0][0],hexagonTiles[0][1]};
            
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT) 
                    neighbors[1] = new int[]{hexagonTiles[3][0],hexagonTiles[5][1]};
                    
                // middle hexagon 1,3,5
                hexagonTiles = getHexagonTiles(hexagons[1][1], hexagons[1][0]);

                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                    neighbors[2] = new int[]{hexagonTiles[1][1],hexagonTiles[1][0]};
                
                tile = getTile(hexagonTiles[3][1], hexagonTiles[3][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.RIGHT)
                    neighbors[3] = new int[]{hexagonTiles[3][0],hexagonTiles[3][1]};
                
                tile = getTile(hexagonTiles[5][1], hexagonTiles[5][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPRIGHTDIAGONAL)
                    neighbors[4] = new int[]{hexagonTiles[5][0],hexagonTiles[5][1]};
                
                // bottom hexagon 1,4
                hexagonTiles = getHexagonTiles(hexagons[2][1], hexagons[2][0]);

                tile = getTile(hexagonTiles[1][1], hexagonTiles[1][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                    neighbors[5] = new int[]{hexagonTiles[1][0],hexagonTiles[1][1]};
            
                tile = getTile(hexagonTiles[4][1], hexagonTiles[4][0]);
                if(tile != null && tile.getDirection() == RhombusDirection.UPLEFTDIAGONAL)
                neighbors[6] = new int[]{hexagonTiles[4][0],hexagonTiles[4][1]};
                
                break;
        }
        return null;
    }

    @Override
    public boolean tileHasNeighbors(int y, int x) {
        RhombusTile[][] tileNeighbors = getTileNeighbors(y, x);
        if(getTile(y, x) != null)
        for (RhombusTile[] i : tileNeighbors) {
            for (RhombusTile j : i) {
                if(j != null && j.isPlaceable())
                    return true;
            }
        }

        return false;
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

        if(getOrientation(y, x) == RhombusOrientation.HORIZONTAL) {
            if(!(tileDirection != RhombusDirection.RIGHT || tileDirection !=  RhombusDirection.LEFT))
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
    
    @Override
    public RhombusOrientation getOrientation(int y, int x) {
        if(y % 2 == 1)
            return RhombusOrientation.HORIZONTAL;
        if((x % 2 == 0 && y < this.size * 2) || (x % 2 == 1 && y > this.size * 2))
            return RhombusOrientation.LEFTDIAGONAL;
        return RhombusOrientation.RIGHTDIAGONAL;
    }

    /* we return the locations of the tiles so we may alter the tiles locations later
     * if there is no tile at a given indes, its tilenumber in the returned array is null
     * 
     *    [5,6]
     *  [4,__,1]
     *   [2,3]
    */
    private int[][] getHexagonTiles(int y, int x)  {
        // [tile number][x,y]
        int[][] hexagonTiles = new int[6][2];
        int tX;
        int tY;

        // horizontal-view/vertical-view

        /*
         *  HORIZONTAL TILES
        **/
        
        // right/top tile
        tX = x + 1;
        tY = (y * 2) + 1;
        try {
            getTile(tY, tX);
            hexagonTiles[0][0] = tX;
            hexagonTiles[0][1] = tY; 
        } catch(IndexOutOfBoundsException e) {
            hexagonTiles[0] = null;
        } 
        
        // left/bottom tile
        tX = x;
        tY = (y * 2) + 1;
        try {
            getTile(tY, tX);
            hexagonTiles[3][0] = tX;
            hexagonTiles[3][1] = tY; 
        } catch(IndexOutOfBoundsException e) {
            hexagonTiles[3] = null;
        } 

        /*
        *  RIGHTDIAGONAL TILES
       **/

        // bottomRight/topRight tile
        tX = x * 2 + (y < this.tiles.length ? 2 : 1);
        tY = (y * 2) + 2 ;
        try {
            getTile(tY, tX);
            hexagonTiles[1][0] = tX;
            hexagonTiles[1][1] = tY; 
        }catch(IndexOutOfBoundsException e) {
            hexagonTiles[1] = null;
        } 

        // topleft/bottomleft tile
        tX = x * 2 + (y < this.tiles.length ? 0 : 1);
        tY = (2 * y);
        try {
            getTile(tY, tX);
            hexagonTiles[4][0] = tX;
            hexagonTiles[4][1] = tY; 
        } catch(IndexOutOfBoundsException e) {
            hexagonTiles[4] = null;
        } 

        /*
         *  LEFTDIAGONAL TILES
        **/

        // bottomleft/bottomright tile
        tX = x * 2 + (y < this.tiles.length ? 1 : 0);
        tY = (y * 2) + 2;
        try {
            getTile(tY, tX);
            hexagonTiles[2][0] = tX;
            hexagonTiles[2][1] = tY; 
        } catch(IndexOutOfBoundsException e) {
            hexagonTiles[2] = null;
        } 

        // topright/topleft tile
        tX = x * 2 + (y < this.tiles.length ? 1 : 2);
        tY = (y * 2);
        try {
            getTile(tY, tX);
            hexagonTiles[5][0] = tX;
            hexagonTiles[5][1] = tY; 
        } catch(IndexOutOfBoundsException e) {
            hexagonTiles[5] = null;
        } 

        return hexagonTiles;
    }

    // first entry its the hexagon farther down, or to the left
    private int[] getTileHexagon(int y, int x, int selection) {
        int[] hexCoords = new int[2];
        if(selection != 0 && selection != 1) {
            throw new IndexOutOfBoundsException(selection + " : is not a valid hexagon");
        }
        switch (getOrientation(y, x)) {
            case LEFTDIAGONAL:
                if(selection == 0){
                    hexCoords[0] = ((x + 1) / 2) - 1;
                    hexCoords[1] = (y / 2) - 1;
                } else {
                    hexCoords[0] = x / 2;
                    hexCoords[1] = y / 2;
                } 
                break;
            case RIGHTDIAGONAL:
                if(selection == 0) {
                    hexCoords[0] = x / 2;
                    hexCoords[1] = y / 2;
                } else {
                    hexCoords[0] = ((x + 1) / 2) - 1;
                    hexCoords[1] = (y / 2) - 1;
                }
                break;
            case HORIZONTAL:
                if (selection == 0) {
                    hexCoords[0] = x - 1;
                    hexCoords[1] = y / 2;
                } else {
                    hexCoords[0] = x + 1;
                    hexCoords[1] = y / 2;
                }
                break;
        }
        return hexCoords;
    }
}
