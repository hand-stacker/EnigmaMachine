package enigma;


import java.util.HashMap;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Jeremy Lazo
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _permutation = super.permutation();
        _setting = super.setting();
        _notches = notches.toCharArray();
    }

    @Override
    void advance() {
        _setting += 1;
        _setting = _permutation.wrap(_setting);
        set(_setting);
    }

    @Override
    /** Set setting() to POSN.  */
    void set(int posn) {
        HashMap sets = mySets();
        _permutation.updateCycles((String) sets.get(posn));
        _setting = posn;
    }

    @Override
    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        int posn = _permutation.alphabet().toInt(cposn);
        set(posn);

    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        for (char c: _notches) {
            Character ch = (Character) alphabet().toChar(_setting);
            if (ch.equals((Character) c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    /** Return my current setting. */
    int setting() {
        return _setting;
    }

    /** New Instance variable for all notches. */
    private char[] _notches;
    /** Instance variable of _settings. */
    private int _setting;
    /** new permutation variable. */
    private Permutation _permutation;

}
