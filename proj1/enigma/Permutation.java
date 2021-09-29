package enigma;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static enigma.EnigmaException.*;

import static enigma.EnigmaException.*;
import static enigma.TestUtils.msg;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        if (!cycles.equals("")){
            String[] _s = cycles.split(" ");
            for (String str : _s) { /* adds all switches in cycles */
                str = str.substring(1,str.length()-1);
                addCycle(str);
            }
        }

        for (char c : _alphabet._chars.values()) { /* adds all chars not in cycle but in alphabet */
            if (!_charMap.containsValue(c)) {
                _charMap.put(c,c);
                _invMap.put(c,c);
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        if (!_alphabet.contains(cycle.charAt(0))) {throw error(msg("addCycles",
                "character %c not found in alphabet", cycle.charAt(0)));}

        for (int i = 0; i < cycle.length() - 1; i ++) {
            char _from = cycle.charAt(i);
            char _to = cycle.charAt(i+1);
            if (!_alphabet.contains(_to)) {throw error(msg("addCycles",
                    "character %c not found in alphabet", _to));}
            _charMap.put(_from, _to);
            _invMap.put(_to, _from);
        }
        _charMap.put(cycle.charAt(cycle.length()-1), cycle.charAt(0));
        _invMap.put(cycle.charAt(0), cycle.charAt(cycle.length()-1));
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        p = wrap(p);
        char _c = _alphabet.toChar(p);
        int _i = _alphabet.toInt(permute(_c));
        return _i;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        c = wrap(c);
        char _p = _alphabet.toChar(c);
        int _i = _alphabet.toInt(invert(_p));
        return _i;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return _charMap.get(p);
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return _invMap.get(c);
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (char c : _alphabet._chars.values()) {
            if (permute(c) == c) {return false;}
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    private HashMap<Character, Character> _charMap = new HashMap<>();
    private HashMap<Character, Character> _invMap = new HashMap<>();
}
