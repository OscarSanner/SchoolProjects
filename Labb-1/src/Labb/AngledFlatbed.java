package Labb;

/**
 * Class for an Angled Flatbed.
 */
public class AngledFlatbed extends Flatbed {


    /**
     *  Constructor for AngledFlatbed.
     *  Initiates constructor in superclass(Flatbed) with specific values for an AngledFlatbed.
     *  minAngle = 0; number represents grades, 0 means the flatbed is flat.
     *  maxAngle = 70; number represents grades, the maximum angle the flatbed can be tipped.
     *  currentAngle = 0; the current angle of the flatbed, when created set to 0 (flat)
     */
    public AngledFlatbed() {
        super(0, 70, 0);
    }
}
