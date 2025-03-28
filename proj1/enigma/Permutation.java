package enigma;

import java.util.HashMap;
import java.util.Scanner;

import static enigma.EnigmaException.*;

import static enigma.TestUtils.msg;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Jeremy Lazo
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _cycles = cycles;
        _alphabet = alphabet;
        updateCycles(cycles);

    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        for (char a: cycle.toCharArray()) {
            if (_charMap.containsKey(a)) {
                throw error(msg("addCycles",
                        "character %c already in a cycle", cycle.charAt(0)));
            }

        }
        if (!_alphabet.contains(cycle.charAt(0))) {
            throw error(msg("addCycles",
                    "character %c not found in alphabet", cycle.charAt(0)));
        }

        for (int i = 0; i < cycle.length() - 1; i++) {
            char from = cycle.charAt(i);
            char to = cycle.charAt(i + 1);
            if (!_alphabet.contains(to)) {
                throw error(msg("addCycles",
                        "character %c not found in alphabet", to));
            }
            _charMap.put(from, to);
            _invMap.put(to, from);
        }
        _charMap.put(cycle.charAt(cycle.length() - 1), cycle.charAt(0));
        _invMap.put(cycle.charAt(0), cycle.charAt(cycle.length() - 1));
    }
    /** Updates HashMaps of Permutation with cycles "(ABC) (DEF)".
     * @param cycles is cycles to update Permutation by */
    protected void updateCycles(String cycles) {
        _charMap = new HashMap<>();
        _invMap = new HashMap<>();
        _cycles = cycles.replaceAll("\n", "");
        _cycles = _cycles.replaceAll(" ", "");

        if (!_cycles.equals("")) {
            Scanner sc = new Scanner(_cycles).useDelimiter("\\(");
            while (sc.hasNext()) {
                String cycle = sc.next();
                cycle = cycle.replaceAll(" ", "");
                String ret = "";
                while (cycle.length() != 0) {
                    if (cycle.substring(0, 1).equals(")")
                            && cycle.length() != 1) {
                        throw error("Cycle format wrong");
                    }
                    ret += cycle.substring(0, 1);
                    cycle = cycle.substring(1);
                }
                if (!ret.contains(")")) {
                    throw error("Cycle not closed");
                }
                addCycle(ret.substring(0, ret.length() - 1));

            }

        }

        for (Object C : _alphabet.chars().values()) {
            char c = (char) C;
            if (!_charMap.containsValue(c)) {
                _charMap.put(c, c);
                _invMap.put(c, c);
            }
        }

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
        int pi = wrap(p);
        char c = _alphabet.toChar(pi);
        int i = _alphabet.toInt(permute(c));
        return i;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        int ci = wrap(c);
        char p = _alphabet.toChar(ci);
        int i = _alphabet.toInt(invert(p));
        return i;
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
        for (Object C : _alphabet.chars().values()) {
            char c = (char) C;
            if (permute(c) == c) {
                return false;
            }
        }
        return true;
    }
    /** Returns _cycles. */
    public String getCycles() {
        return _cycles;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Initializes a copy of original cycle. */
    private String _cycles;

    /** HashMap that permutes chars. */
    private HashMap<Character, Character> _charMap = new HashMap<>();
    /** HashMap that inverses chars. */
    private HashMap<Character, Character> _invMap = new HashMap<>();
}
