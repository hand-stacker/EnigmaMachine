
package enigma;

import java.util.HashMap;
import java.util.Map;
import static enigma.EnigmaException.*;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Jeremy Lazo
 */
class Alphabet {

    /** A new alphabet containing CHARS. The K-th character has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        str = chars;
        for (int i = 0; i < chars.length(); i++) {
            if (_chars.containsValue(chars.charAt(i))) {
                throw error("Error when"
                        + " making alphabet, two identical characters were"
                        + " passed in constructor");
            }
            _chars.put(i, chars.charAt(i));
        }
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _chars.size();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        return _chars.containsValue(ch);
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return _chars.get(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    Integer toInt(char ch) {
        for (Map.Entry<Integer, Character> entry : _chars.entrySet()) {
            if (entry.getValue().equals(ch)) {
                return entry.getKey();
            }
        }
        return null;
    }
    /** Returns HashMap _char. */
    public HashMap chars() {
        return _chars;
    }
    /** A string version of characters in Alphabet. */
    protected final String str;
    /** A HashMap containing all characters of Alphabet. */
    private HashMap<Integer, Character> _chars =
            new HashMap<Integer, Character>();
}
