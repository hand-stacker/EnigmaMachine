package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Jeremy Lazo
 */
public class MovingRotorTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Rotor rotor;
    private String alpha = UPPER_STRING;

    /** Check that rotor has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkRotor(String testId,
                            String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, rotor.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d (%c)", ci, c),
                         ei, rotor.convertForward(ci));
            assertEquals(msg(testId, "wrong inverse of %d (%c)", ei, e),
                         ci, rotor.convertBackward(ei));
        }
    }

    private void checkRotor(Rotor rot, String testId,
                            String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, rot.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            int ci = rot.alphabet().str.indexOf(c),
                    ei = rot.alphabet().str.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d (%c)", ci, c),
                    ei, rot.convertForward(ci));
            assertEquals(msg(testId, "wrong inverse of %d (%c)", ei, e),
                    ci, rot.convertBackward(ei));
        }
    }

    /** Set the rotor to the one with given NAME and permutation as
     *  specified by the NAME entry in ROTORS, with given NOTCHES. */
    private void setRotor(String name, HashMap<String, String> rotors,
                          String notches) {
        rotor = new MovingRotor(name, new Permutation(rotors.get(name), UPPER),
                                notches);
    }

    /* ***** TESTS ***** */

    @Test
    public void checkRotorAtA() {
        setRotor("I", NAVALA, "");
        checkRotor("Rotor I (A)", UPPER_STRING, NAVALA_MAP.get("I"));
    }

    @Test
    public void checkrotoratB() {
        setRotor("I", NAVALA, "");
        rotor.set(1);
        checkRotor("Rotor I set 1", UPPER_STRING, NAVALB_MAP.get("I"));
        rotor.set('Z');
        checkRotor("Rotor I set 25", UPPER_STRING, NAVALZ_MAP.get("I"));
        assertTrue(msg("checkrotoratB", "Setting is wrong %d",
                rotor.setting()), rotor.setting() == 25);

    }
    @Test
    public void checkRotorAdvance() {
        setRotor("I", NAVALA, "");
        rotor.advance();
        checkRotor("Rotor I advanced", UPPER_STRING, NAVALB_MAP.get("I"));
        assertTrue("rotate() method wrong", rotor.rotates());
        assertTrue(msg("checkrotorAdvance", "Setting is wrong %d",
                rotor.setting()), rotor.setting() == 1);
        assertFalse(rotor.atNotch());
        rotor.advance();
        assertTrue(msg("checkrotorAdvance", "Setting is wrong %d",
                rotor.setting()), rotor.setting() == 2);
        assertFalse(rotor.atNotch());
        rotor.advance();
        assertTrue(msg("checkrotorAdvance", "Setting is wrong %d",
                rotor.setting()), rotor.setting() == 3);
        assertFalse(rotor.atNotch());


    }

    @Test
    public void checkRotorSetZ() {
        setRotor("I", NAVALA, "");
        rotor.set(25);
        checkRotor("Rotor I set 25", UPPER_STRING, NAVALZ_MAP.get("I"));
    }
    @Test
    public void checkWeirdAlphabet() {
        Rotor vowels = new Rotor("Vowels",
                new Permutation("(AIU) (EO)", new Alphabet("AEIOU")));
        checkRotor(vowels, "Rotor vowels", "AEIOU", "IOUEA");
        vowels.set(1);
        checkRotor(vowels, "Rotor vowels", "AEIOU", "IOAUE");
        vowels.set('I');
        checkRotor(vowels, "Rotor vowels", "AEIOU", "IUOAE");

    }

    @Test
    public void checkFixedRotor() {
        FixedRotor fixed = new FixedRotor("fixed",
                new Permutation(NAVALA.get("Beta"), UPPER));
        checkRotor(fixed, "Fixed Rotor", UPPER_STRING, NAVALA_MAP.get("Beta"));
        fixed.set(1);
        checkRotor(fixed, "Fixed Rotor", UPPER_STRING, NAVALA_MAP.get("Beta"));
        assertTrue("Setting is not correct", fixed.setting() == 1);
        fixed.set('C');
        checkRotor(fixed, "Fixed Rotor", UPPER_STRING, NAVALA_MAP.get("Beta"));
        assertTrue("Setting is not correct", fixed.setting() == 2);

    }

    @Test
    public void checkReflector() {
        Reflector reflector = new Reflector("reflector",
                new Permutation(NAVALA.get("B"), UPPER));
        checkRotor(reflector, "Reflector B", UPPER_STRING,
                "ENKQAUYWJICOPBLMDXZVFTHRGS");

    }

}
