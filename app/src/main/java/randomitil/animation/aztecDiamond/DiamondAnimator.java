// Package Declaration
package randomitil.animation.aztecDiamond;

// Imports
import randomitil.animation.*;
import randomitil.tilings.aztecDiamond.*;

// Class Declaration
public class DiamondAnimator implements TilingsAnimator {

    // Class Variables
    private DiamondDrawer drawer;
    private DiamondIteration diamondIterator;

    /// Constructor ///
    public DiamondAnimator(DiamondDrawer drawer) {
        this.drawer = drawer;
        this.setupTiling();
    }

    /// Setup 2 by 2 Diamond Tiling ///
    public void setupTiling() {
        // Create Instances
        DiamondTilings diamondTiling = new DiamondTilings();
        diamondIterator = new DiamondIteration(diamondTiling);
        
        // Setup Iterator
        diamondIterator.setAnimationMode(false);
        diamondIterator.setTilingBias(0.5);

        // Setup Tiling
        diamondIterator.fillBlankSpaces();

        // Setup Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());
    }

    /// Iterate Tiling Once ///
    public void iterateTiling() {
        // Iterate
        diamondIterator.iterateTiles();

        // Update Drawer
        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator.getTiling().getSize());
        drawer.setAnimate(true);
        drawer.setFrameNum(0);
    }
}
