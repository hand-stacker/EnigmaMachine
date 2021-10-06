package enigma;

import java.util.Arrays;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _setting = 0;
        // FIXME
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _setting; // FIXME
    }

    /** Set setting() to POSN.  */
    void set(int posn) {

        String cycles = _permutation._cycles;
        /** update cycles to shift by posn */
        Permutation newPerm = new Permutation("("+ alphabet().str + ")", alphabet());
        char[] charArr = cycles.toCharArray();
        char[] newcycles = setCycles(newPerm, charArr, posn);
        _permutation.updateCycles(String.valueOf(newcycles));
        // FIXME
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        int posn = _permutation.alphabet().toInt(cposn);
        set(posn);
        // FIXME
    }

    private char[] setCycles(Permutation perm, char[] chars, int posn) {
        Character space = ' ';
        Character openP = '(';
        Character closP = ')';
        char[] retChars = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            Character c = chars[i];
            /** Messy code but at least it wont uneccesarily
             *  run permutes for characters that will not change*/
            if (!c.equals(space)&&!c.equals(openP)&&!c.equals(closP)) {
                for (int j = 0; j < posn; j++){
                    c = perm.invert(c);
                }
            }
            retChars[i] = c;
        }
        return retChars;
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        return _permutation.permute(p);
        // FIXME
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        return _permutation.invert(e);
        // FIXME
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** current setting */
    private int _setting;
    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED

}
