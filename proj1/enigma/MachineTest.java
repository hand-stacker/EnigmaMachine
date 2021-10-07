package enigma;

import org.checkerframework.checker.units.qual.A;
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
    private final Alphabet _vowels = new Alphabet("AEIOUY");
    private Rotor I = new MovingRotor("I",
            new Permutation(NAVALA.get("I"), _alph), "Q");
    private Rotor II = new MovingRotor("II",
            new Permutation(NAVALA.get("II"), _alph), "E");
    private Rotor III = new MovingRotor("III",
            new Permutation(NAVALA.get("III"), _alph), "V");
    private Rotor IV = new MovingRotor("IV",
            new Permutation(NAVALA.get("IV"), _alph), "J");
    private Rotor V = new MovingRotor("V",
            new Permutation(NAVALA.get("V"), _alph), "Z");
    private Rotor VI = new MovingRotor("VI",
            new Permutation(NAVALA.get("VI"), _alph), "ZM");
    private Rotor VII = new MovingRotor("VII",
            new Permutation(NAVALA.get("VII"), _alph), "ZM");
    private Rotor VIII = new MovingRotor("VIII",
            new Permutation(NAVALA.get("VIII"), _alph), "ZM");
    private Rotor Beta = new FixedRotor("Beta",
            new Permutation(NAVALA.get("Beta"), _alph));
    private Rotor Gamma = new FixedRotor("Gamma",
            new Permutation(NAVALA.get("Gamma"), _alph));
    private Rotor B = new Reflector("B",
            new Permutation(NAVALA.get("B"), _alph));
    private Rotor C = new Reflector("C",
            new Permutation(NAVALA.get("C"), _alph));

    private Rotor First = new Reflector("First",
            new Permutation("(AI) (EO) (UY)", _vowels));

    private Rotor Second = new FixedRotor("Second",
            new Permutation("(AIU) (EO)", _vowels));

    private Rotor Third = new MovingRotor("Third",
            new Permutation("(AO) (EY)", _vowels), "");

    private Rotor Fourth = new MovingRotor("Fourth",
            new Permutation("(UY) (IA)", _vowels), "");

    private ArrayList<Rotor> allRotors = new ArrayList<Rotor>(16);

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
        allRotors.add(First);
        allRotors.add(Second);
        allRotors.add(Third);
        allRotors.add(Fourth);


    }

    public void advanc(Machine mach, HashMap hash, int[] arr) {
        for (int i = 0; i< arr.length; i++) {
            Rotor r = (Rotor) mach.myRotors().get("Rotor" + i);
            assertEquals("advance method is wrong", arr[i], r.setting());
        }
    }

    @Test
    public void check5Machine() {
        constructAllRotors();
        Machine funfMaschine = new Machine(_alph, 5, 3, allRotors);
        String[] str = {"B", "Beta", "I", "II", "III"};
        funfMaschine.insertRotors(str);
        funfMaschine.setRotors("AAAA");
        Permutation perm = new Permutation("(CK) (MX) (LV)", _alph);
        funfMaschine.setPlugboard(perm);
        assertEquals("Wrong...", "CPKUR MPUXR HEGBW CUQTJ", funfMaschine.convert("WHERE DID ALL THE FISH GO"));
    }

    @Test
    public void checktrivMachine() {
        constructAllRotors();
        Machine funfMaschine = new Machine(_alph, 5, 3, allRotors);
        String[] str = {"B", "Beta", "I", "II", "III"};
        funfMaschine.insertRotors(str);
        funfMaschine.setRotors("AAAA");
        Permutation perm = new Permutation("", _alph);
        funfMaschine.setPlugboard(perm);
        assertEquals("Wrong...", "ILBDA AMTAZ", funfMaschine.convert("HELLO WORLD"));
    }

    @Test
    public void checkVowel() {
        constructAllRotors();
        Machine vow = new Machine(_vowels, 3, 1, allRotors);
        String[] str = {"First", "Second", "Third"};
        vow.insertRotors(str);
        vow.setRotors("AA");
        Permutation perm = new Permutation("", _vowels);
        vow.setPlugboard(perm);
        int[] arr = {0, 0, 0};
        advanc(vow, vow.myRotors(), arr);
        assertEquals("Wrong...", "UEEU", vow.convert("AAAA"));
        arr = new int[] {0, 0, 4};
        advanc(vow, vow.myRotors(), arr);
    }

    @Test
    public void checkAdvancer() {
        constructAllRotors();
        Machine funfMaschine = new Machine(_alph, 5, 3, allRotors);
        String[] str = {"B", "Beta", "I", "II", "III"};
        funfMaschine.insertRotors(str);
        funfMaschine.setRotors("AAAA");
        Permutation perm = new Permutation("(CK) (MX) (LV)", _alph);
        funfMaschine.setPlugboard(perm);
        funfMaschine.convert('C');
        Rotor row = (Rotor) funfMaschine.myRotors().get("Rotor4");
        int[] arr = new int[] {0, 0, 0, 0, 1};
        advanc(funfMaschine, funfMaschine.myRotors(), arr);
        funfMaschine.convert('C');
        arr = new int[] {0, 0, 0, 0, 2};
        advanc(funfMaschine, funfMaschine.myRotors(), arr);
        funfMaschine.convert('C');
        arr = new int[] {0, 0, 0, 0, 3};
        advanc(funfMaschine, funfMaschine.myRotors(), arr);
        funfMaschine.convert('C');
        arr = new int[] {0, 0, 0, 0, 4};
        advanc(funfMaschine, funfMaschine.myRotors(), arr);
        funfMaschine.convert('C');
        arr = new int[] {0, 0, 0, 0, 5};
        advanc(funfMaschine, funfMaschine.myRotors(), arr);


    }





}
