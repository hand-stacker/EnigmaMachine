package enigma;

import java.util.HashMap;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Jeremy Lazo
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _setting = 0;
        _Sets = new HashMap<>(_permutation.size());
        setSets();

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
        return _setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _permutation.updateCycles(_Sets.get(posn));
        _setting = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        int posn = _permutation.alphabet().toInt(cposn);
        set(posn);

    }
    /** Helper function that updates a cycles in char[] form to a
     * new char[] where characters other than space or parenthesis
     * will be inverse by a set permutation 'count' number of times.
     * @param count is the number of permutations that have to be made
     * @param perm is helper Permutation that shifts characters of
     * specific alphabet
     * @param chars is old Permutation of Rotor in char[] form
     * @return is the new Permutation Rotor gets*/
    private char[] setCycles(Permutation perm, char[] chars, int count) {
        Character space = ' ';
        Character openP = '(';
        Character closP = ')';
        char[] retChars = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            Character c = chars[i];
            if (!c.equals(space) && !c.equals(openP) && !c.equals(closP)) {
                for (int j = 0; j < count; j++) {
                    c = perm.invert(c);
                }
            }
            retChars[i] = c;
        }
        return retChars;
    }


    /** makes a set for all possible settings. */
    private void setSets() {
        _Sets.put(0, _permutation.getCycles());
        Permutation newPerm =
                new Permutation("(" + alphabet().str + ")", alphabet());
        Character space = ' ';
        Character openP = '(';
        Character closP = ')';
        char[] chars = _permutation.getCycles().toCharArray();
        char[] retChars = new char[chars.length];
        for (int k = 0; k < _permutation.alphabet().size(); k++) {
            for (int i = 0; i < chars.length; i++) {
                Character c = chars[i];
                if (!c.equals(space) && !c.equals(openP) && !c.equals(closP)) {
                    for (int j = 0; j < k; j++) {
                        c = newPerm.invert(c);
                    }
                }
                retChars[i] = c;
            }
            _Sets.put(k, String.valueOf(retChars));
        }

    }
    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        return _permutation.permute(p);
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        return _permutation.invert(e);
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    /** returns _sets. */
    HashMap mySets() {
        return  _Sets;
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** The current setting of Rotor. */
    private int _setting;

    /** A HashMap of all possible settings. */
    private HashMap<Integer, String> _Sets;

}
