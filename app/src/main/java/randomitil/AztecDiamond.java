package randomitil;

public class AztecDiamond {
    Domino[][] tiles;
    int width;
    int height;

    public AztecDiamond() {
        this(2, 2);
    }

    public AztecDiamond(int height, int width) {
        tiles = new Domino[height][width];
        this.width = width;
        this.height = height;
    }

    public void expand(int size) {
        expandWidth(size);
        expandHeight(size);
    }

    private void expandWidth(int width) {
        int originalWidth = this.width;
        this.width += 2 * width;

        Domino[][] tempTiles = this.tiles.clone();
        tiles = new Domino[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            System.arraycopy(tempTiles[i], 0, tiles[i], width, originalWidth);
        }
    }

    private void expandHeight(int height) {
        int originalHeight = this.height;
        this.height += 2 * height;

        Domino[][] tempTiles = this.tiles.clone();
        tiles = new Domino[this.height][this.width];

        System.arraycopy(tempTiles, 0, tiles, height, originalHeight);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[\n");
        for (int i = 0; i < this.width; i++) {
            buf.append('[');
            for (int j = 0; j < this.height; j++) {
                Domino tile = getTile(i, j);
                if (tile != null) {
                    if (tile.direction == Direction.LEFT)
                        buf.append(" <- "); // →
                    else if (tile.direction == Direction.RIGHT)
                        buf.append(" -> ");
                    else if (tile.direction == Direction.UP)
                        buf.append("  ^ ");
                    else if (tile.direction == Direction.DOWN)
                        buf.append(" \\/ ");
                    else
                        buf.append("oops");
                } else {
                    buf.append("null");
                }
                if (j != this.height - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        buf.append(']');
        return buf.toString();
    }

    public void setTile(int x, int y, Domino domino) {
        tiles[x][y] = domino;
    }

    public Domino getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTiles(Domino[][] tiles) {
        this.tiles = tiles;
    }

    public Domino[][] getTiles() {
        return tiles;
    }
}
