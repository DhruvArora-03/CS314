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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnagramSolver {
    private final Map<String, LetterInventory> letterInventories;
    private final List<String> bigDictionary;
    
    /*
     * pre: list != null
     * @param list Contains the words to form anagrams from.
     */
    public AnagramSolver(Set<String> dictionary) {
        letterInventories = new HashMap<>();
        for (String word : dictionary) {
            letterInventories.put(word, new LetterInventory(word));
        }
        bigDictionary = new ArrayList<>(dictionary);
        bigDictionary.sort(new DictionaryWordComparator());
    }
    
    /*
     * pre: maxWords >= 0, s != null, s contains at least one
     * English letter.
     * Return a list of anagrams that can be formed from s with
     * no more than maxWords, unless maxWords is 0 in which case
     * there is no limit on the number of words in the anagram
     */
    public List<List<String>> getAnagrams(String s, int maxWords) {
        // create a smaller dictionary containing only words that could appear in an anagram
        LetterInventory inventory = new LetterInventory(s);
        List<String> smallerDict = new ArrayList<>();
    
        // check each word in the entire dictionary, if valid --> add into smaller dictionary
        for (String word : bigDictionary) {
            if (inventory.subtract(letterInventories.get(word)) != null) {
                smallerDict.add(word);
            }
        }
    
        maxWords = maxWords != 0 ? maxWords : s.length(); // true max: each char is a separate word
        List<List<String>> result = new ArrayList<>();
        anagramHelper(result, smallerDict, 0, inventory, new ArrayList<>(), maxWords);
        result.sort(new AnagramComparator());
        return result;
    }
    
    private void anagramHelper(List<List<String>> result, List<String> dict, int dictIndex,
                               LetterInventory lettersLeft, List<String> anagramSoFar,
                               int wordsLeft) {
        // base case - anagram is complete
        if (lettersLeft.isEmpty()) {
            List<String> newAnagram = new ArrayList<>(anagramSoFar);
            Collections.sort(newAnagram);
            result.add(newAnagram);
        }
        // curr word (and the rest) in dict are too short to create an anagram --> do nothing :P
        // max words reached or ran through whole dict with incomplete anagram --> do nothing :P
        else if (wordsLeft > 0 && dictIndex < dict.size() &&
                wordsLeft * dict.get(dictIndex).length() >= lettersLeft.size()) {
            for (int i = dictIndex; i < dict.size(); i++) {
                // recursive step (s) - try adding current word
                String currWord = dict.get(i);
                LetterInventory currDiff = lettersLeft.subtract(letterInventories.get(currWord));
    
                // null --> curr word contains chars not in letters left, so cannot add curr word
                if (currDiff != null) {
                    anagramSoFar.add(currWord);
                    anagramHelper(result, dict, i, currDiff, anagramSoFar, wordsLeft - 1);
                    anagramSoFar.remove(anagramSoFar.size() - 1); // backtrack
                }
            }
        }
    }
    
    private static class AnagramComparator implements Comparator<List<String>> {
        @Override
        public int compare(List<String> a, List<String> b) {
            if (a.size() != b.size()) {
                return a.size() - b.size();
            } else {
                int index = 0;
                // increment index until we find an index where the elements of a and b are diff
                while (a.get(index).equals(b.get(index))) {
                    index++;
                }
                return a.get(index).compareTo(b.get(index));
            }
        }
    }
    
    private static class DictionaryWordComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return b.length() - a.length();
        }
    }
}
