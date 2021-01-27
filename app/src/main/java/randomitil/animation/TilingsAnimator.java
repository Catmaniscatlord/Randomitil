// Package Declaration
package randomitil.animation;

// Imports
import randomitil.tilings.TilingIteration;

// Class Declaration
public interface TilingsAnimator {
    /// Setup Tiling ///
    public void setupTiling();

    /// Iterate On Tiling ///
    public void iterateTiling();

    /// Automatic Iteration ///
    public void autoIterateTiling();

    // Set Final Size
    public void setFinalSize(int finalSize);

    /// Set Auto Animate ///
    public void setAutoAnimate(boolean autoAnimate);

    /// Get Final Size ///
    public int getFinalSize();

    // Is Auto Animate? //
    public boolean isAutoAnimate();

    // Get Iterator
    public TilingIteration getIterator();

}
