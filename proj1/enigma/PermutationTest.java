package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Jeremy Lazo
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
        assertFalse((msg("identity", "wrong derangement")), perm.derangement());

        perm = new Permutation(NAVALA.get("I"),
                new Alphabet(NAVALA_MAP.get("I")));
        checkPerm("I rotor", UPPER_STRING, NAVALA_MAP.get("I"));
        assertFalse((msg("I rotot", "wrong derangement")), perm.derangement());

        perm = new Permutation(NAVALA.get("V"),
                new Alphabet(NAVALA_MAP.get("V")));
        checkPerm("V rotor", UPPER_STRING, NAVALA_MAP.get("V"));
        assertTrue((msg("V rotot", "wrong derangement")), perm.derangement());

        perm = new Permutation("(AGB) (FH) (S)", UPPER);
        checkPerm("duplicate cycles", UPPER_STRING,
                "GACDEHBFIJKLMNOPQRSTUVWXYZ");
        assertFalse((msg("duplicate cycles",
                "wrong derangement")), perm.derangement());

    }
    @Test
    public void checkTest1() {
        perm = new Permutation(" "
                + "    (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)\n", UPPER);
    }

}
