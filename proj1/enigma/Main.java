package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Jeremy Lazo
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Scanner iterations = _input.useDelimiter("\\*\\s");
        Machine thing = readConfig();
        while (iterations.hasNext()) {
            Scanner thisiter = new Scanner(iterations.next());
            String settings = thisiter.nextLine();
            setUp(thing, settings);
            while (thisiter.hasNextLine()) {
                String message = thisiter.nextLine();
                printMessageLine(thing.convert(message));
            }


        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            _config = _config.useDelimiter("\\n(?=\\s*[A-Z].*\\r*\\n*)");

            Scanner head = new Scanner(_config.next());

            _alphabet = new Alphabet(head.nextLine());
            _rotorNums = Integer.parseInt(head.next());
            _pawlNums = Integer.parseInt(head.nextLine().substring(1, 2));


            while (_config.hasNext()) {
                Rotor toAdd = readRotor();
                allRotors.add(toAdd);
            }
            _config.close();
            return new Machine(_alphabet, _rotorNums, _pawlNums, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String desc = _config.next();
            Scanner newrotor = new Scanner(desc);
            String name = newrotor.next();
            String other = newrotor.next();
            String type = other.substring(0, 1);
            String notches = other.substring(1);
            String cycles = newrotor.nextLine();
            while (newrotor.hasNextLine()) {
                cycles += newrotor.nextLine();
            }
            newrotor.close();
            switch (type) {
                case "M":
                    return new MovingRotor(name, new Permutation(cycles,
                            _alphabet), notches);
                case "N":
                    return new FixedRotor(name, new Permutation(cycles,
                            _alphabet));
                case "R":
                    return new Reflector(name, new Permutation(cycles,
                            _alphabet));
                default:
                    throw error("Something wrong here,"
                            + " got no type/wrong char from _config");
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        try {
            Scanner sc = new Scanner(settings);
            int i = 0;
            String[] str = new String[_rotorNums];
            while (i != _rotorNums) {
                str[i] = sc.next();
                i++;
            }
            M.insertRotors(str);
<<<<<<< HEAD
            M.setRotors(sc.next());
=======
            String settin = sc.next();
            if (settin.length() != _rotorNums - 1) {
                throw error("Bad Setting");
            }
            for (char c : settin.toCharArray()) {
                if (Character.isDigit(c)) {
                    throw error("Bad Setting, not all letters");
                }
            }
            M.setRotors(settin);
>>>>>>> 69eb09a94f598a981683caba408f2eedaaed951b
            if (sc.hasNextLine()) {
                M.setPlugboard(new Permutation(sc.nextLine(), _alphabet));
            } else {
                M.setPlugboard(new Permutation("", _alphabet));
            }
            sc.close();
        }  catch (NoSuchElementException excp) {
            throw error("bad setting format");
        }

    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters).
     *  lol already did this in Machine sry*/
    private void printMessageLine(String msg) {
        _output.println(msg);
    }


    /** Number of Rotors in this machine. */
    private int _rotorNums;

    /** Number of Pawls in this machine. */
    private int _pawlNums;

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** ArrayList with all possible rotors. */
    private ArrayList<Rotor> allRotors = new ArrayList<Rotor>();
}
