package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Machine class.
 *  @author Jeremy Lazo
 */
public class MachineTest {
    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */
    private final String alpha = UPPER_STRING;
    private final Alphabet _alph = new Alphabet(UPPER_STRING);
    private Rotor I = new MovingRotor("I",
            new Permutation(NAVALA.get("I"), _alph), "I");
    private Rotor II = new MovingRotor("II",
            new Permutation(NAVALA.get("II"), _alph), "NE");
    private Rotor III = new MovingRotor("III",
            new Permutation(NAVALA.get("III"), _alph), "ED");
    private Rotor IV = new MovingRotor("IV",
            new Permutation(NAVALA.get("IV"), _alph), "HELP");
    private Rotor V = new MovingRotor("V",
            new Permutation(NAVALA.get("V"), _alph), "IM");
    private Rotor VI = new MovingRotor("VI",
            new Permutation(NAVALA.get("VI"), _alph), "MEDIAT");
    private Rotor VII = new MovingRotor("VII",
            new Permutation(NAVALA.get("VII"), _alph), "ELY");
    private Rotor VIII = new MovingRotor("VIII",
            new Permutation(NAVALA.get("VIII"), _alph), "");
    private Rotor Beta = new FixedRotor("Beta",
            new Permutation(NAVALA.get("Beta"), _alph));
    private Rotor Gamma = new FixedRotor("Gamma",
            new Permutation(NAVALA.get("Gamma"), _alph));
    private Rotor B = new Reflector("B",
            new Permutation(NAVALA.get("B"), _alph));
    private Rotor C = new Reflector("C",
            new Permutation(NAVALA.get("C"), _alph));

    private ArrayList<Rotor> allRotors = new ArrayList<Rotor>(12);

    private void constructAllRotors() {
        allRotors.add(I);
        allRotors.add(II);
        allRotors.add(III);
        allRotors.add(IV);
        allRotors.add(V);
        allRotors.add(VI);
        allRotors.add(VII);
        allRotors.add(VIII);
        allRotors.add(Beta);
        allRotors.add(Gamma);
        allRotors.add(B);
        allRotors.add(C);

    }

    @Test
    public void check5Machine() {
        constructAllRotors();
        Machine funfMaschine = new Machine(_alph, 5, 4, allRotors);
    }





}
