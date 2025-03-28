
package enigma;

import java.util.HashMap;

import static enigma.EnigmaException.*;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Jeremy Lazo
 */
class FixedRotor extends Rotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is given by PERM. */
    FixedRotor(String name, Permutation perm) {
        super(name, perm);
        _permutation = super.permutation();
        _setting = super.setting();
    }

    /** Overrides set methods of Rotor,
     *  removing the ability for rotor to move. */
    @Override
    void set(int posn) {
        HashMap sets = mySets();
        _permutation.updateCycles((String) sets.get(posn));
        _setting = posn;
    }

    @Override
    void set(char cposn) {
        int posn = _permutation.alphabet().toInt(cposn);
        set(posn);
    }
    /** returns this.setting, if not overridden,
     * settin() would return super.setting. */
    @Override
    int setting() {
        return _setting;
    }
    /** Instance variable for permutation. */
    private Permutation _permutation;
    /** Instance variable for _setting. */
    private int _setting;
}

