/*
BevoFuzz - Recursion
Version 1.2
Author: Jeriah Yu (jeriah@utexas.edu)

An automated fuzzer (infinite test generator) for CS314 Assignment 6 - Recursion.
Tests the following methods via standard iteration:
Binary Conversion (guaranteed)
String reversing (guaranteed)
nextIsDouble (guaranteed)

Tests the following methods via characteristics, special cases, and collective comparison:
Mnemonics (partially guaranteed)
Water flow off map (both guarantee and non-guaranteed)
Creating fair teams (not guaranteed, NP problem btw)
MazeSolver (both guaranteed and non-guaranteed)

Standard Operating Procedure:
Adjust constants in main() if desired. Compile BevoFuzz and your Recursive.java class.
Run BevoFuzz, select Generator to generate tests, or select Validator to check against output file.

Guarantee vs Non-Guarantee:
Guaranteed tests are tests where the fuzzer can find the correct result itself, either through
a non-recursive algorithm, or by "math." For example, the random snake test for Water flow is
guaranteed to always be true, so the fuzzer can test that.
Non-Guaranteed means that the fuzzer does not automatically know whether the result is correct, but
will still generate and run the tests on your implementation, then save the inputs and outputs
(EHT file, recall Evil Hangman) which you can share with others, who can then run your saved EHT
test using the Validator.

Results:
Each test will print PASSED or FAILED along with expected vs. actual result.
At the end, there will be a summary of number of tests passed (guaranteed), number failed, and
number generated that weren't tested (non-guaranteed). Only non-guaranteed are saved to disk.
Any exceptions will halt the program (there's no try catch).

Disclaimer:
There might be bugs. I'm not responsible for anything that goes wrong. I'm not responsible if your
computer breaks. I'm not responsible for your grade on this assignment or in CS 314.
I'm not responsible if you act academically dishonest, with or without using this tool.
You are still responsible for writing tests if required by the assignment sheet. You should NOT just
copy tests generated from this tool. You are still responsible for testing edge and corner cases yourself.
Your code may pass tests but still be inefficient, redundant, non-adherent to style, or contain logic
errors that flew under the radar of these or other tests, all of which can still get points deducted.

Details on types of tests:
Binary Conversion, String Reversing, nextIsDouble - tested via standard iterative or library functions
(Of course, your method should be recursive)

Mnemonics - the tester will check certain properties hold: that your returned array has no duplicates,
that each string is the correct length, and that there are the correct number of elements via
mathematical formula. The actual resulting array is non-guaranteed and will be saved with the input
string for validation if you share the output EHT with others.

Water flow off map:
There are several tests for this method:
Guaranteed:
    Random snake - Starts with a flat rectangular map. Randomly choses start position, then conducts
    a random walk until reaching a border, decrementing height. Unlike the game,
    not a problem if snake loops on itself. Guaranteed true.
    Random snake (no border) - Same as random snake, but walks for a random amount or until reaching
    an edge, without decrementing the edge. Guaranteed false.

Non-guaranteed:
    Noise - random noisy chaotic map with random start. No guarantees.

Creating fair teams:
Guaranteed:
    Balanced N teams positive: Array from 1-X, where X is an even number >= 4, with half the number
    of teams. Guaranteed 0: Example: 1 2 3 4 5 6, with 6/2=3 teams. Then 1+6=2+5=3+4
    Balanced 2 teams negative: Array of 2+ positive numbers and their negative counterparts. Guaranteed 0

Non-Guaranteed
    Generates random array of abilities, includes 0 and negatives.

MazeSolver:
Guaranteed:
    Random snake - Starts with all wall. Chooses random start, random walks and drops some money
    along the way. If intersect with yellow, continue and make it green.
    If intersect with green, start, or money, change into end. Guaranteed 2 (exit and coins)

    Missed coin - Runs random snake, then appends to the map 2 additional rows with a disconnected
    coin. Guaranteed 1.

    Missed exit - Similar to missed coin, but with Exit instead

Non-Guaranteed:
    Generates random map. Currently each type is equally likely (besides Start). Then adds a random start.
 */

//FYI: This code does not meet style guide (long comments, missing {}, redundancy). Don't use as example.
import java.io.*;
import java.util.*;

public class BevoFuzz {
    // Constants: adjust at will. Depending on your settings, generation could take a long time!
    // (Because recursion time complexity)
    final static int NUM_TESTS = 100; //Number of tests per each type specified above

    // Binary
    final static int BIN_MAX = 100; //Largest absolute value to generate (goes from negative to positive)

    //stringRev
    final static int MAX_LEN = 10; //Largest length of randomly generated string.

    //nextIsDouble
    final static int MAX_SIZE = 10; //Maximum size of random array.
    final static int MAX_ELEM = 100; //Largest absolute value element (goes from negative to positive)
    final static double GAUSSIAN_THRESHOLD = 0; //Chance for next element to be double.
                                                //Uses gaussian sample (mean 0 SD 1), double if greater than this.

    //mnemonics
    final static int MAX_PHONE = 999; //Largest "phone number" number

    //waterflow
    final static int MAP_LN = 5; //Maximum length of map (minimum 1)
    final static int MAP_WD = 5; //Maximum width of map (min 0)
    final static int MAP_HT = 100; //Maximum absolute value of map, both for noise and snake start.
    final static int MAX_DSC = 2; //Maximum random descent step of snake
    final static int MAX_MOVES = 25; //Maximum amount of steps for the no-border snake.

    //fairTeams
    final static int MAX_HALF_AB = 2; //Maximum of half of top ability used for balanced positive and negative
    final static int MAX_HALF_CANDS = 2; //Half of max number of candidates for balanced negative
    final static int MAX_TEAMS = 2; // Max number of teams (at least 2) for random generation

    //MazeSolver
    //Random coin dropper uses same Gaussian Threshold as nextIsDouble
    //Map generator uses same width and length as water flow.

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("BevoFuzz - Assignment 6: Recursion\nVersion 1.2\nAuthor: Jeriah Yu");
        Scanner keyboard = new Scanner(System.in);
        int choice;
        do {
            results = new int[]{0, 0, 0};
            System.out.print("1. Generate (will overwrite output.eht)\n2. Validate\n3. Exit\n> ");
            choice = Integer.parseInt(keyboard.nextLine());
            if (choice == 1)
                generator();
            else if (choice == 2)
                validator();
        } while (choice != 3);
        keyboard.close();
    }

    private static int[] results = {0, 0, 0};

    private static void generator() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("output.eht"));
        binary();
        stringRev();
        nextIsDouble();
        mnemonicsGen(os);
        randomSnake();
        randomSnakeNoEdge();
        waterFlowRandGen(os);
        fairTeamsBalPos();
        fairTeamsBalNeg();
        fairTeamsRandGen(os);
        mazeRandomSnake();
        mazeLonelyCoin();
        mazeMissedExit();
        mazeRandGen(os);

        System.out.println();
        System.out.println("Passed: " + results[0] + " Failed: " + results[1] + " Generated: " + results[2]);
        os.close();
    }

    private static void validator() throws IOException, ClassNotFoundException {
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream("output.eht"));
        try {
            while (true) {
                switch (reader.readUTF()) {
                    case "mnemonic":
                        mnemonicsVal(reader);
                        break;
                    case "canFlowOffMap":
                        waterFlowRandVal(reader);
                        break;
                    case "fairTeams":
                        fairTeamsRandVal(reader);
                        break;
                    case "maze":
                        mazeRandVal(reader);
                        break;
                }
            }
        } catch (EOFException e) {
            System.out.println("End of eht file reached");
        }

        System.out.println();
        System.out.println("Passed: " + results[0] + " Failed: " + results[1]);
        reader.close();
    }

    private static void binary() {
        for (int i = 0; i < NUM_TESTS; i++) {
            int query = rand(0, BIN_MAX);
            String expected = Integer.toBinaryString(query);
            if (rand(0, 1) == 1) {
                query *= -1;
                expected = query == 0 ? expected : "-" + expected;
            }
            String actual = Recursive.getBinary(query);
            if (actual.equals(expected))
                pr("Binary", i);
            else
                pr("Binary", i, Integer.toString(query), expected, actual);
        }
    }

    private static void stringRev() {
        for (int i = 0; i < NUM_TESTS; i++) {
            StringBuilder qBuild = new StringBuilder();
            for (int j = 0; j < rand(0, MAX_LEN); j++) {
                qBuild.append((char) rand(33, 126)); //ASCII printable chars
            }
            String query = qBuild.toString();
            String actual = Recursive.revString(query);
            String expected = qBuild.reverse().toString();
            if (actual.equals(expected))
                pr("stringRev", i);
            else
                pr("stringRev", i, query, expected, actual);
        }
    }

    private static void nextIsDouble() {
        Random random = new Random();
        for (int i = 0; i < NUM_TESTS; i++) {
            int[] query = new int[rand(0, MAX_SIZE)];
            if (query.length > 0)
                query[0] = rand(MAX_ELEM * -1, MAX_ELEM);
            int expected = 0;
            for (int j = 1; j < query.length; j++) {
                if (random.nextGaussian() > GAUSSIAN_THRESHOLD) {
                    query[j] = query[j - 1] * 2;
                    expected++;
                } else {
                    int r = rand(MAX_ELEM * -1, MAX_ELEM);
                    query[j] = r == query[j - 1] * 2 ? r - 1 : r;
                }
            }
            int actual = Recursive.nextIsDouble(query);
            if (actual == expected)
                pr("nextIsDouble", i);
            else {
                pr("nextIsDouble", i, Arrays.toString(query), Integer.toString(expected), Integer.toString(actual));
            }
        }
    }

    private static void mnemonicsGen(ObjectOutputStream os) {
        for (int i = 0; i < NUM_TESTS; i++) {
            int q = rand(0, MAX_PHONE);
            String query = Integer.toString(q);
            int expectedSize = 1;
            for (char c : query.toCharArray()) {
                if (c == '9' || c == '7') {
                    expectedSize *= 4;
                } else if (c != '0' && c != '1') {
                    expectedSize *= 3;
                }
            }
            ArrayList<String> actual = Recursive.listMnemonics(query);
            boolean problem = false;
            if (expectedSize != actual.size()) {
                pr("Mnemonics", i, query, "size = " + expectedSize, "size = " + actual.size());
                problem = true;
            } else {
                Collections.sort(actual);
                for (int j = 0; j < actual.size() - 1; j++) {
                    if (actual.get(j).equals(actual.get(j + 1))) {
                        pr("Mnemonics", i, query, "unique elements", actual.get(j) + " " + actual.get(j + 1));
                        problem = true;
                    } else if (actual.get(j).length() != query.length()) {
                        pr("Mnemonics", i, query, query.length() + " elements", actual.get(j).length() + " elements");
                        problem = true;
                    }
                }
            }
            if (!problem) {
                try {
                    // Test name, test num, test query, test output
                    os.writeUTF("mnemonic");
                    os.writeInt(i);
                    os.writeUTF(query);
                    os.writeUnshared(actual);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                pr("Mnemonics", i, 0);
            }
        }
    }

    private static void mnemonicsVal(ObjectInputStream reader)
            throws IOException, ClassNotFoundException {
        int i = reader.readInt();
        String query = reader.readUTF();
        ArrayList<String> expected = (ArrayList<String>) reader.readObject();
        ArrayList<String> actual = Recursive.listMnemonics(query);
        Collections.sort(expected);
        Collections.sort(actual);
        if (expected.equals(actual)) {
            pr("Mnemonics", i);
        } else {
            pr("Mnemonics", i, query, expected.toString(), actual.toString());
        }
    }

    private static void randomSnake() {
        for (int i = 0; i < NUM_TESTS; i++) {
            int[][] query = new int[rand(1, MAP_LN)][rand(1, MAP_WD)];
            int initialVal = rand(-1 * MAP_HT, MAP_HT);
            for (int[] j : query) {
                Arrays.fill(j, initialVal);
            }
            int startx = rand(0, query.length - 1);
            int starty = rand(0, query[0].length - 1);
            int[][] directions = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int x = startx;
            int y = starty;
            int curheight = query[x][y];
            while (!(x == 0 || x == query.length - 1 || y == 0 || y == query[0].length - 1)) {
                query[x][y] = curheight - rand(1, MAX_DSC);
                curheight = query[x][y];
                int[] dirChoice = directions[rand(0, 3)];
                x += dirChoice[0];
                y += dirChoice[1];
            }
            query[x][y] = curheight - rand(1, MAX_DSC);
            boolean actual = Recursive.canFlowOffMap(query, startx, starty);
            boolean expected = true;
            if (actual == expected)
                pr("canFlowOffMap - randomSnake", i);
            else {
                StringBuilder a = new StringBuilder();
                for (int[] arr : query) {
                    a.append(Arrays.toString(arr));
                }
                pr("canFlowOffMap - randomSnake", i, a.toString() + "Start (" + startx + ", " + starty + ")",
                   Boolean.toString(expected), Boolean.toString(actual));
            }
        }
    }

    private static void randomSnakeNoEdge() {
        for (int i = 0; i < NUM_TESTS; i++) {
            int[][] query = new int[rand(4, MAP_LN + 4)][rand(4, MAP_WD + 4)];
            int initialVal = rand(-1 * MAP_HT, MAP_HT);
            for (int[] j : query) {
                Arrays.fill(j, initialVal);
            }
            int startx = rand(1, query.length - 2);
            int starty = rand(1, query[0].length - 2);
            int[][] directions = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int x = startx;
            int y = starty;
            int curheight = query[x][y];
            int moves = 0;
            while (!(x == 0 || x == query.length - 1 || y == 0 || y == query[0].length - 1) && moves <= MAX_MOVES) {
                query[x][y] = curheight - rand(1, MAX_DSC);
                curheight = query[x][y];
                int[] dirChoice = directions[rand(0, 3)];
                x += dirChoice[0];
                y += dirChoice[1];
                moves++;
            }
            boolean actual = Recursive.canFlowOffMap(query, startx, starty);
            boolean expected = false;
            if (actual == expected)
                pr("canFlowOffMap - randomSnakeNoEdge", i);
            else {
                StringBuilder a = new StringBuilder();
                for (int[] arr : query) {
                    a.append(Arrays.toString(arr));
                }
                pr("canFlowOffMap - randomSnakeNoEdge", i, a.toString() + "Start (" + startx + ", " + starty + ")",
                   Boolean.toString(expected), Boolean.toString(actual));
            }
        }
    }

    private static void waterFlowRandGen(ObjectOutputStream os) throws IOException {
        for (int i = 0; i < NUM_TESTS; i++) {
            int[][] query = new int[rand(1, MAP_LN)][rand(1, MAP_WD)];
            for (int x = 0; x < query.length; x++) {
                for (int y = 0; y < query[0].length; y++) {
                    query[x][y] = rand(-1 * MAP_HT, MAP_HT);
                }
            }
            int startx = rand(0, query.length - 1);
            int starty = rand(0, query[0].length - 1);
            boolean actual = Recursive.canFlowOffMap(query, startx, starty);
            os.writeUTF("canFlowOffMap");
            os.writeInt(i);
            os.writeObject(query);
            os.writeInt(startx);
            os.writeInt(starty);
            os.writeBoolean(actual);
            pr("canFlowOffMap", i, 0);
        }
    }

    private static void waterFlowRandVal(ObjectInputStream reader)
            throws IOException, ClassNotFoundException {
        int i = reader.readInt();
        int[][] query = (int[][]) reader.readObject();
        int x = reader.readInt();
        int y = reader.readInt();
        boolean expected = reader.readBoolean();
        boolean actual = Recursive.canFlowOffMap(query, x, y);
        if (actual == expected) {
            pr("canFlowOffMap", i);
        } else {
            StringBuilder a = new StringBuilder();
            for (int[] arr : query) {
                a.append(Arrays.toString(arr));
            }
            pr("canFlowOffMap", i, a.toString() + "Start (" + x + ", " + y + ")",
               Boolean.toString(expected), Boolean.toString(actual));
        }
    }

    private static void fairTeamsBalPos() {
        for (int i = 0; i < NUM_TESTS; i++) {
            int lim = rand(2, MAX_HALF_AB) * 2;
            int[] query = new int[lim];
            for (int j = 1; j <= query.length; j++) {
                query[j - 1] = j;
            }
            int expected = 0;
            int actual = Recursive.minDifference(query.length / 2, query);
            if (actual == expected)
                pr("fairTeams - balancedPositive", i);
            else {
                pr("fairTeams - balancedPositive", i, Arrays.toString(query), Integer.toString(expected), Integer.toString(actual));
            }
        }
    }

    private static void fairTeamsBalNeg() {
        for (int i = 0; i < NUM_TESTS; i++) {
            int[] query = new int[rand(2, MAX_HALF_CANDS) * 2];
            for (int j = 0; j < query.length; j += 2) {
                int ability = rand(0, MAX_HALF_AB * 2);
                query[j] = ability;
                query[j + 1] = ability * -1;
            }
            int expected = 0;
            int actual = Recursive.minDifference(2, query);
            if (actual == expected)
                pr("fairTeams - balancedNegative", i);
            else {
                pr("fairTeams - balancedNegative", i, Arrays.toString(query), Integer.toString(expected), Integer.toString(actual));
            }
        }
    }

    private static void fairTeamsRandGen(ObjectOutputStream os) throws IOException {
        for (int i = 0; i < NUM_TESTS; i++) {
            int teams = rand(2, MAX_TEAMS);
            int[] abilities = new int[rand(teams + 1, MAX_HALF_CANDS * 2)];
            for (int j = 0; j < abilities.length; j++) {
                abilities[j] = rand(MAX_HALF_AB * -2, MAX_HALF_AB * 2);
            }
            int actual = Recursive.minDifference(teams, abilities);
            os.writeUTF("fairTeams");
            os.writeInt(i);
            os.writeInt(teams);
            os.writeObject(abilities);
            os.writeInt(actual);
            pr("fairTeams", i, 0);
        }
    }

    private static void fairTeamsRandVal(ObjectInputStream reader)
            throws IOException, ClassNotFoundException {
        int i = reader.readInt();
        int teams = reader.readInt();
        int[] query = (int[]) reader.readObject();
        int expected = reader.readInt();
        int actual = Recursive.minDifference(teams, query);
        if (actual == expected) {
            pr("fairTeams", i);
        } else {
            pr("fairTeams", i, Arrays.toString(query), Integer.toString(expected), Integer.toString(actual));
        }
    }

    private static void mazeRandomSnake() {
        for (int i = 0; i < NUM_TESTS; i++) {
            char[][] query = new char[rand(3, MAP_LN)][rand(3, MAP_WD)];
            for (char[] j : query) {
                Arrays.fill(j, '*');
            }
            int startx = rand(1, query.length - 2);
            int starty = rand(1, query[0].length - 2);
            query[startx][starty] = 'S';
            int[][] directions = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int moves = 0;
            int x = startx;
            int y = starty;
            boolean end = false;
            Random random = new Random();
            while (!end && moves <= MAX_MOVES) {
                int[] dirchoice = directions[rand(0, 3)];
                x += dirchoice[0];
                y += dirchoice[1];
                if (x < 0 || x > query.length - 1 || y < 0 || y > query[0].length - 1) {
                    x -= dirchoice[0];
                    y -= dirchoice[1];
                    end = true;
                } else if (query[x][y] == 'Y' || query[x][y] == 'S' || query[x][y] == '$') {
                    x -= dirchoice[0];
                    y -= dirchoice[1];
                    end = true;
                } else if (query[x][y] == '*') {
                    if (random.nextGaussian() > GAUSSIAN_THRESHOLD) {
                        query[x][y] = '$';
                    } else {
                        query[x][y] = 'Y';
                    }
                }
                moves++;
            }
            query[x][y] = 'E';
            int actual = Recursive.canEscapeMaze(query);
            int expected = 2;
            if (actual == expected)
                pr("canEscapeMaze - randomSnake", i);
            else {
                StringBuilder a = new StringBuilder();
                for (char[] arr : query) {
                    a.append(Arrays.toString(arr));
                }
                pr("canEscapeMaze - randomSnake", i, a.toString() + "Start (" + startx + ", " + starty + ")",
                   Integer.toString(expected), Integer.toString(actual));
            }
        }
    }

    private static void mazeLonelyCoin() {
        for (int i = 0; i < NUM_TESTS; i++) {
            char[][] query = new char[rand(3, MAP_LN)][rand(3, MAP_WD)];
            for (char[] j : query) {
                Arrays.fill(j, '*');
            }
            int startx = rand(1, query.length - 2);
            int starty = rand(1, query[0].length - 2);
            query[startx][starty] = 'S';
            int[][] directions = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int moves = 0;
            int x = startx;
            int y = starty;
            boolean end = false;
            Random random = new Random();
            while (!end && moves <= MAX_MOVES) {
                int[] dirchoice = directions[rand(0, 3)];
                x += dirchoice[0];
                y += dirchoice[1];
                if (x < 0 || x > query.length - 1 || y < 0 || y > query[0].length - 1) {
                    x -= dirchoice[0];
                    y -= dirchoice[1];
                    end = true;
                } else if (query[x][y] == 'Y' || query[x][y] == 'S' || query[x][y] == '$') {
                    x -= dirchoice[0];
                    y -= dirchoice[1];
                    end = true;
                } else if (query[x][y] == '*') {
                    if (random.nextGaussian() > GAUSSIAN_THRESHOLD) {
                        query[x][y] = '$';
                    } else {
                        query[x][y] = 'Y';
                    }
                }
                moves++;
            }
            query[x][y] = 'E';

            char[][] coinQuery = new char[query.length + 2][query[0].length];
            for (int j = 0; j < query.length; j++) {
                System.arraycopy(query[j], 0, coinQuery[j], 0, query[j].length);
            }
            for (int j = query.length; j < coinQuery.length; j++) {
                Arrays.fill(coinQuery[j], '*');
            }
            coinQuery[query.length + 1][rand(0, coinQuery[0].length - 1)] = '$';

            int actual = Recursive.canEscapeMaze(coinQuery);
            int expected = 1;
            if (actual == expected)
                pr("canEscapeMaze - lonelyCoin", i);
            else {
                StringBuilder a = new StringBuilder();
                for (char[] arr : coinQuery) {
                    a.append(Arrays.toString(arr));
                }
                pr("canEscapeMaze - lonelyCoin", i, a.toString() + "Start (" + startx + ", " + starty + ")",
                   Integer.toString(expected), Integer.toString(actual));
            }
        }
    }

    private static void mazeMissedExit() {
        for (int i = 0; i < NUM_TESTS; i++) {
            char[][] query = new char[rand(3, MAP_LN)][rand(3, MAP_WD)];
            for (char[] j : query) {
                Arrays.fill(j, '*');
            }
            int startx = rand(1, query.length - 2);
            int starty = rand(1, query[0].length - 2);
            query[startx][starty] = 'S';
            int[][] directions = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int moves = 0;
            int x = startx;
            int y = starty;
            boolean end = false;
            Random random = new Random();
            while (!end && moves <= MAX_MOVES) {
                int[] dirchoice = directions[rand(0, 3)];
                x += dirchoice[0];
                y += dirchoice[1];
                if (x < 0 || x > query.length - 1 || y < 0 || y > query[0].length - 1) {
                    x -= dirchoice[0];
                    y -= dirchoice[1];
                    end = true;
                } else if (query[x][y] == 'Y' || query[x][y] == 'S' || query[x][y] == '$') {
                    x -= dirchoice[0];
                    y -= dirchoice[1];
                    end = true;
                } else if (query[x][y] == '*') {
                    if (random.nextGaussian() > GAUSSIAN_THRESHOLD) {
                        query[x][y] = '$';
                    } else {
                        query[x][y] = 'Y';
                    }
                }
                moves++;
            }

            char[][] coinQuery = new char[query.length + 2][query[0].length];
            for (int j = 0; j < query.length; j++) {
                System.arraycopy(query[j], 0, coinQuery[j], 0, query[j].length);
            }
            for (int j = query.length; j < coinQuery.length; j++) {
                Arrays.fill(coinQuery[j], '*');
            }
            coinQuery[query.length + 1][rand(0, coinQuery[0].length - 1)] = 'E';

            int actual = Recursive.canEscapeMaze(coinQuery);
            int expected = 0;
            if (actual == expected)
                pr("canEscapeMaze - lonelyExit", i);
            else {
                StringBuilder a = new StringBuilder();
                for (char[] arr : coinQuery) {
                    a.append(Arrays.toString(arr));
                }
                pr("canEscapeMaze - lonelyExit", i, a.toString() + "Start (" + startx + ", " + starty + ")",
                   Integer.toString(expected), Integer.toString(actual));
            }
        }
    }

    private static void mazeRandGen(ObjectOutputStream os) throws IOException {
        for (int i = 0; i < NUM_TESTS; i++) {
            char[][] query = new char[rand(1, MAP_LN)][rand(1, MAP_WD)];
            char[] validChars = {'E', '$', 'Y', 'G', '*'};
            for (int x = 0; x < query.length; x++) {
                for (int y = 0; y < query[0].length; y++) {
                    query[x][y] = validChars[rand(0, 4)];
                }
            }
            query[rand(0, query.length - 1)][rand(0, query[0].length - 1)] = 'S';
            int actual = Recursive.canEscapeMaze(query);
            os.writeUTF("maze");
            os.writeInt(i);
            os.writeObject(query);
            os.writeInt(actual);
            pr("mazeSolver", i, 0);
        }
    }

    private static void mazeRandVal(ObjectInputStream reader)
            throws IOException, ClassNotFoundException {
        int i = reader.readInt();
        char[][] query = (char[][]) reader.readObject();
        int expected = reader.readInt();
        int actual = Recursive.canEscapeMaze(query);
        if (actual == expected) {
            pr("mazeSolver", i);
        } else {
            StringBuilder a = new StringBuilder();
            for (char[] arr : query) {
                a.append(Arrays.toString(arr));
            }
            pr("mazeSolver", i, a.toString(),
               Integer.toString(expected), Integer.toString(actual));
        }
    }

    private static int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static void pr(String test, int i) {
        System.out.println(test + " Test " + (i + 1) + "/" + NUM_TESTS + " PASSED");
        results[0]++;
    }
    private static void pr(String test, int i, String q, String e, String a) {
        System.out.println(test + " Test " + (i + 1) + "/" + NUM_TESTS + " FAILED: Query " + q +
                                   " -- Expected " + e + " -- Actual " + a);
        results[1]++;
    }
    private static void pr(String test, int i, int j) {
        System.out.println(test + " Test " + (i + 1) + "/" + NUM_TESTS + " GENERATED");
        results[2]++;
    }
}
