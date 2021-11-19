/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, Dhruv Arora, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: da32895
 *  email address: dhruvarora@utexas.edu
 *  TA name: Grace
 *  Number of slip days I am using: 0
 */

import java.util.Arrays;

public class LetterInventory {
    private static final int LENGTH_OF_ALPHABET = 26;
    private static final char MIN_LETTER = 'a';
    private static final char MAX_LETTER = 'z';
    
    // store freq of each character based on index 0 - 25 corresponding to letters 'a' --> 'z'
    private final int[] LETTER_FREQS;
    // total number of characters whose data is stored in LETTER_FREQS
    private final int TOTAL_NUM_LETTERS;
    
    /**
     * Create a LetterInventory from an input String of a word to count letters from
     *
     * @param word String word/phrase used to generate the letter counts, word != null
     */
    public LetterInventory(String word) {
        // check preconditions
        if (word == null) {
            throw new IllegalArgumentException("cannot have word == null for LetterInventory()");
        }
        
        LETTER_FREQS = new int[LENGTH_OF_ALPHABET];
        int letterCount = 0; // temp variable to so that the class's instance var can stay final
        
        // check if each letter in the param is an english char, if so store it into data
        for (char letter : word.toLowerCase().toCharArray()) {
            if (isLetter(letter)) {
                LETTER_FREQS[letter - MIN_LETTER]++;
                letterCount++;
            }
        }
        
        TOTAL_NUM_LETTERS = letterCount;
    }
    
    /**
     * Constructor made to help preserve immutability, only to be used by add() and subtract()
     *
     * @param letterFreqs     int[] of size 26 containing freq of each letter a to z
     * @param totalNumLetters total number of characters whose data is stored in letterFreqs
     */
    private LetterInventory(int[] letterFreqs, int totalNumLetters) {
        // assume preconditions are already considered since it can only be called by us
        
        this.LETTER_FREQS = letterFreqs;
        this.TOTAL_NUM_LETTERS = totalNumLetters;
    }
    
    /**
     * Determine if a char represents an a lowercase English letter
     *
     * @param ch char to be checked for being a letter, ch should be lowercase if possible
     * @return true/false if ch is a lowercase letter from a to z
     */
    private boolean isLetter(char ch) {
        return ch >= MIN_LETTER && ch <= MAX_LETTER;
    }
    
    /**
     * Get the frequency of a certain letter
     *
     * @param letter char letter to find freq of, letter must be an english letter
     * @return int freq of letter in the LetterInventory
     */
    public int get(char letter) {
        // check preconditions
        char lowercaseLetter = Character.toLowerCase(letter);
        if (!isLetter(lowercaseLetter)) {
            throw new IllegalArgumentException("Cannot use non-alphabetic char with get(letter)");
        }
        
        // diff of chars tells distance from MIN_LETTER, which is also the same as index
        return LETTER_FREQS[lowercaseLetter - MIN_LETTER];
    }
    
    /**
     * Get the total number of letters that the inventory has freq data for
     *
     * @return int total num of letters that this LetterInventory has stored in freq data
     */
    public int size() {
        return TOTAL_NUM_LETTERS;
    }
    
    /**
     * Check if the LetterInventory is empty
     *
     * @return true/false if size() <= 0
     */
    public boolean isEmpty() {
        // size > 0 --> not empty
        return size() <= 0;
    }
    
    /**
     * Get an alphabetical String representation of the string according to the stored freq data
     *
     * @return alphabetical String representation of the string according to the stored freq data
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        // avoid unnecessary looping
        if (isEmpty()) {
            return "";
        }
        
        // go through each letter and append appropriate num of copies of the curr char
        for (int letter = 0; letter < LETTER_FREQS.length; letter++) {
            for (int freq = 0; freq < LETTER_FREQS[letter]; freq++) {
                // since chars implicitly convert to ints --> we can 'add' them to calc new chars
                builder.append((char) (letter + MIN_LETTER));
            }
        }
        
        return builder.toString();
    }
    
    /**
     * Create a new LetterInventory with the combined frequencies of the calling and parameter objs
     *
     * @param other LetterInventory to 'add' to the calling obj, other != null
     * @return new LetterInventory obj with the combined frequencies of the calling and param objs
     */
    public LetterInventory add(LetterInventory other) {
        // check preconditions
        if (other == null) {
            throw new IllegalArgumentException("Cannot have other == null for add(other)");
        }
        
        int[] newLetterFreqs = new int[LENGTH_OF_ALPHABET];
        int newTotalLetterCount = 0;
        
        for (int letter = 0; letter < LETTER_FREQS.length; letter++) {
            int freqDiff = LETTER_FREQS[letter] + other.LETTER_FREQS[letter];
            
            // store new letter counts
            newLetterFreqs[letter] = freqDiff;
            newTotalLetterCount += freqDiff;
        }
        
        return new LetterInventory(newLetterFreqs, newTotalLetterCount);
    }
    
    /**
     * Create a new LetterInventory with diff of frequencies between the calling and parameter objs
     *
     * @param other LetterInventory to 'subtract' from the calling obj, other != null
     * @return new LetterInventory obj with the diff of freqs between the calling and param objs
     */
    public LetterInventory subtract(LetterInventory other) {
        // check preconditions
        if (other == null) {
            throw new IllegalArgumentException("Cannot have other == null for subtract(other)");
        }
        
        int[] newLetterFreqs = new int[LENGTH_OF_ALPHABET];
        int newTotalLetterCount = 0;
        
        for (int letter = 0; letter < LETTER_FREQS.length; letter++) {
            int freqDiff = LETTER_FREQS[letter] - other.LETTER_FREQS[letter];
            
            // if resulting letter count < 0 --> return null
            if (freqDiff < 0) {
                return null;
            }
            
            // store new letter counts
            newLetterFreqs[letter] = freqDiff;
            newTotalLetterCount += freqDiff;
        }
        
        return new LetterInventory(newLetterFreqs, newTotalLetterCount);
    }
    
    /**
     * Check if the calling object is equivalent to another LetterInventory
     *
     * @param other Object to compare to the calling object
     * @return true/false if other is a LetterInventory that has the same freq data as this obj
     */
    @Override
    public boolean equals(Object other) {
        // handle null case
        if (other == null) {
            return false;
        } else if (other instanceof LetterInventory) {
            // check if every letter has the same frequency in both inventories
            return Arrays.equals(this.LETTER_FREQS, ((LetterInventory) other).LETTER_FREQS);
        }
        
        // if other is not a LetterInventory --> let super.equals() handle it
        return super.equals(other);
    }
}
