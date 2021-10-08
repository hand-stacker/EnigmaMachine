package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.ArrayList;
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
    private Rotor _I = new MovingRotor("I",
            new Permutation(NAVALA.get("I"), _alph), "Q");
    private Rotor _II = new MovingRotor("II",
            new Permutation(NAVALA.get("II"), _alph), "E");
    private Rotor _III = new MovingRotor("III",
            new Permutation(NAVALA.get("III"), _alph), "V");
    private Rotor _IV = new MovingRotor("IV",
            new Permutation(NAVALA.get("IV"), _alph), "J");
    private Rotor _V = new MovingRotor("V",
            new Permutation(NAVALA.get("V"), _alph), "Z");
    private Rotor _VI = new MovingRotor("VI",
            new Permutation(NAVALA.get("VI"), _alph), "ZM");
    private Rotor _VII = new MovingRotor("VII",
            new Permutation(NAVALA.get("VII"), _alph), "ZM");
    private Rotor _VIII = new MovingRotor("VIII",
            new Permutation(NAVALA.get("VIII"), _alph), "ZM");
    private Rotor _Beta = new FixedRotor("Beta",
            new Permutation(NAVALA.get("Beta"), _alph));
    private Rotor _Gamma = new FixedRotor("Gamma",
            new Permutation(NAVALA.get("Gamma"), _alph));
    private Rotor _B = new Reflector("B",
            new Permutation(NAVALA.get("B"), _alph));
    private Rotor _C = new Reflector("C",
            new Permutation(NAVALA.get("C"), _alph));
    private Rotor _First = new Reflector("First",
            new Permutation("(AI) (EO) (UY)", _vowels));
    private Rotor _Second = new FixedRotor("Second",
            new Permutation("(AIU) (EO)", _vowels));
    private Rotor _Third = new MovingRotor("Third",
            new Permutation("(AO) (EY)", _vowels), "");
    private Rotor _Fourth = new MovingRotor("Fourth",
            new Permutation("(UY) (IA)", _vowels), "");
    private Rotor _Epsilon = new FixedRotor("Epsilon",
            new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", _alph));
    private Rotor _VOID1 = new MovingRotor("VOID1",
            new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", _alph), "");
    private Rotor _VOID2 = new MovingRotor("VOID2",
            new Permutation("(ZYXWVUTSRQPONMLKJIHGFEDCBA)", _alph), "");
    private Rotor _VOID = new MovingRotor("VOID",
            new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) "
                    + "(HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", _alph), "");

    private ArrayList<Rotor> allRotors = new ArrayList<Rotor>(20);

    private void constructAllRotors() {
        allRotors.add(_I);
        allRotors.add(_II);
        allRotors.add(_III);
        allRotors.add(_IV);
        allRotors.add(_V);
        allRotors.add(_VI);
        allRotors.add(_VII);
        allRotors.add(_VIII);
        allRotors.add(_Beta);
        allRotors.add(_Gamma);
        allRotors.add(_B);
        allRotors.add(_C);
        allRotors.add(_First);
        allRotors.add(_Second);
        allRotors.add(_Third);
        allRotors.add(_Fourth);
        allRotors.add(_Epsilon);
        allRotors.add(_VOID1);
        allRotors.add(_VOID2);
        allRotors.add(_VOID);



    }

    public void advanc(Machine mach, HashMap hash, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
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
        assertEquals("Wrong...", "CPKUR MPUXR HEGBW CUQTJ",
                funfMaschine.convert("WHERE DID ALL THE FISH GO"));
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
        assertEquals("Wrong...", "ILBDA AMTAZ",
                funfMaschine.convert("HELLO WORLD"));
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

    @Test
    public void checkPlugs() {
        constructAllRotors();
        Machine plugged = new Machine(_alph, 5, 3, allRotors);
        String[] str = {"B", "Epsilon", "VOID2", "VOID1", "VOID"};
        plugged.insertRotors(str);
        plugged.setRotors("AAAA");
        Permutation perm = new Permutation("(AE) (BN) (CK)"
                + " (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", _alph);
        plugged.setPlugboard(perm);
    }





}
