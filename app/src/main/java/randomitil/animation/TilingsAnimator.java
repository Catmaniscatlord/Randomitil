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
    public void setNumIterate(int numIterate);

    /// Set Auto Animate ///
    public void setAutoAnimate(boolean autoAnimate);

    /// Get Final Size ///
    public int getNumIterate();

    // Is Auto Animate? //
    public boolean isAutoAnimate();

    // Get Iterator
    public TilingIteration getIterator();

}
