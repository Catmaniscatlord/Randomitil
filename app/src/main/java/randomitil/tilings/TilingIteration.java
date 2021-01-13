package randomitil.tilings;

public abstract class TilingIteration{
    
    protected Tilings tiling;

    protected int iteration;
    protected boolean[][] blankSpaces;

    protected double tilingBias;

    public boolean animationMode;
    protected Tilings generatedTiles;

    public TilingIteration(Tilings tiling) {
        this.tiling = tiling;
    }

    abstract public Tilings expandedTiling();
    
    abstract public void findBlankSpaces();
    
    abstract public void fillBlankSpaces();
   
    abstract public void fillBlankSpace(int y, int x);
   
    abstract public void moveTiles();
   
    abstract public void iterateTiles();

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

    public void setTiling(Tilings tiling) {
        this.tiling = tiling;
    }
   
    public Tilings getTiling() {
        return tiling;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
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

    public boolean isAnimationMode() {
        return animationMode;
    }

    public void setAnimationMode(boolean animationMode) {
        this.animationMode = animationMode;
    }

    public Tilings getGeneratedTiles() {
        return generatedTiles;
    }
}
