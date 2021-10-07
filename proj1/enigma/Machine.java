package enigma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Jeremy Lazo
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _numPawls = pawls;
        _allRotors = (ArrayList) allRotors;
        _myRotors = new HashMap<String, Rotor>(numRotors);
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        if (rotors.length != _numRotors) {
            throw error("string[] not the same as required num" +
                    "of rotors");
        }
        for (Object o : _allRotors) {
            Rotor r = (Rotor) o;
            if (rotors[0].equals(r.name())) {
                if (!r.reflecting()) {
                    throw error("rotor[0] not a reflector");
                } else {
                    _myRotors.put("Rotor0", r);
                }
            }
            for (int i = 1; i < rotors.length; i++) {
                if (rotors[i].equals(r.name())) {
                    if (!_myRotors.isEmpty() && _myRotors.containsValue(r)) {
                        throw error("Duplicate Rotors");
                    }
                    if (r.reflecting()) {
                        throw error("Rotor is a reflector");
                    }
                    _myRotors.put("Rotor" + i, r);
                }
            }

        }
        checkOrder(_myRotors);
    }

    /** Checks if _myRotors is propery ordered
     * (no rotatable rotors before fixed rotors). */
    void checkOrder(HashMap map){
        boolean prevCanRotate = true;
        for (int i = numRotors()-1; i != 0; i--) {
            Rotor current = (Rotor) map.get("Rotor" + i);
            if (!prevCanRotate && current.rotates()) {
                throw error("Rotors not ordered correctly");

            }            if (!current.rotates()) {
                prevCanRotate = false;

            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        char[] sets = setting.toCharArray();
        if (sets.length != numRotors() - 1) {
            throw error("String setting not correct size");
        }
        for (int i = 1; i < numRotors(); i++) {
            Rotor r = _myRotors.get("Rotor" + i);
            r.set(sets[i-1]);
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        if (!plugboard.alphabet().equals(_alphabet)){
            throw error("inconsistent alphabet btwn plugboard and machine");
        }
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        advancer(_numRotors - 1, true, _numPawls);
        c =_plugboard.permute(c);
        for (int i = _numRotors - 1; i != 0; i--) {
            c = _myRotors.get("Rotor" + i).convertForward(c);
        }
        c = _myRotors.get("Rotor0").convertForward(c);
        for (int j = 1; j < _numRotors; j++) {
            c = _myRotors.get("Rotor" + j).convertBackward(c);
        }
        c=_plugboard.invert(c);
        return c;
    }
    /** advances rotors based on rotor num (decreasing) and if pawl is touching rotor*/
    void advancer(int i, boolean pawlTouchMe, int pawlLeft){
        Rotor me = _myRotors.get("Rotor" + i);
        if (i == 0 | pawlLeft == 0) {
            return;
        }
        if (me.atNotch()) {
            advancer(i - 1, true, pawlLeft - 1);
            me.advance();
        } else if (pawlTouchMe) {
            advancer(i - 1, false, pawlLeft - 1);
            me.advance();
        } else {
            advancer(i - 1, false, pawlLeft - 1);
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        msg = msg.replaceAll("\\s", "");
        char[] tmsg = msg.toCharArray();
        for (int i = 0; i < tmsg.length; i++) {
            if (_alphabet.contains(tmsg[i])) {
                tmsg[i] = _alphabet.toChar(
                        convert(_alphabet.toInt(tmsg[i])));
            }

        }
        msg = String.valueOf(tmsg);
        msg = wordSize(msg);
        return msg;
    }

    /** returns a modified string that has words of size 5. */
    String wordSize(String str) {
        if (str.length() < 5) {
            return str;
        } else {
            return str.substring(0, 5) + " " + wordSize(str.substring(5));
        }

    }

    /** returns _myRotors. */
    HashMap myRotors(){
        return _myRotors;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** variable for number of rotors. */
    private final int _numRotors;

    /** variables for number of pawls. */
    private final int _numPawls;

    /** general list containing all rotors. */
    private final ArrayList _allRotors;

    /** HashMap of all of Machine's rotors. */
    private HashMap<String, Rotor> _myRotors;

    /** permutation representing the plugboard. */
    private Permutation _plugboard;
}
