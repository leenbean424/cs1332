import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Eileen Liu
 * @version 1.0
 * @userid eliu80
 * @GTID 903561092
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern length cannot be length 0");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        int patternInd = 0;
        int tInd = 0;
        LinkedList<Integer> match = new LinkedList<Integer>();

        if (text.length() < pattern.length()) {
            return match;
        }
        int[] failure = buildFailureTable(pattern, comparator);
        while (tInd <= text.length() - pattern.length()) {
            while (patternInd < pattern.length()
                    && comparator.compare(pattern.charAt(patternInd),
                    text.charAt(patternInd + tInd)) == 0) {
                patternInd++;
            }
            if (patternInd == 0) {
                tInd++;
            } else {
                if (patternInd == pattern.length()) {
                    match.add(tInd);
                }

                int shift = failure[patternInd - 1];
                tInd = tInd - shift + patternInd;
                patternInd = shift;
            }
        }
        return match;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        int[] table = new int[pattern.length()];
        if (pattern.length() == 0) {
            return table;
        }

        int y = 1;
        int x = 0;
        table[x] = 0;

        while (y < pattern.length()) {

            if (comparator.compare(pattern.charAt(y), pattern.charAt(x)) == 0) {
                table[y] = x + 1;
                y++;
                x++;
            } else if (x == 0) {
                table[y] = 0;
                y++;
            } else {
                x = table[x - 1];
            }
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern length cannot be length 0");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        LinkedList<Integer> match = new LinkedList<Integer>();
        Map<Character, Integer> last = buildLastTable(pattern);

        int patternL = pattern.length();
        int textL = text.length();


        if (patternL > textL) {
            return match;
        }
        int x = 0;
        while (x <= textL - patternL) {
            int y = patternL - 1;
            if (patternL + x > textL) {
                return match;
            }
            while (y >= 0 && comparator.compare(text.charAt(x + y), pattern.charAt(y)) == 0) {
                y--;
            }
            if (y == -1) {
                match.add(x);
                x++;
            } else {
                int shift = last.getOrDefault(text.charAt(x + y), -1);
                if (shift < y) {
                    x += y - shift;
                } else {
                    x++;
                }
            }
        }
        return match;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            map.put(pattern.charAt(i), i);
        }
        return map;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern length cannot be length 0");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        LinkedList<Integer> list = new LinkedList<Integer>();
        int patternL = pattern.length();
        int textL = text.length();
        if (patternL > textL) {
            return list;
        }

        int baseEXP = 1;
        int patternHash = 0;
        int textHash = 0;
        for (int i = patternL - 1; i >= 0; i--) {
            if (i < patternL - 1) {
                baseEXP *= BASE;
            }
            textHash += text.charAt(i) * baseEXP;
            patternHash += pattern.charAt(i) * baseEXP;
        }
        int textInd = 0;
        int patternInd = 0;

        while (textInd <= textL - patternL) {

            if (textHash == patternHash) {
                patternInd = 0;

                while (patternInd < patternL
                        && comparator.compare(text.charAt(patternInd + textInd),
                        pattern.charAt(patternInd)) == 0) {
                    patternInd++;
                }
                if (patternInd == patternL) {
                    list.add(textInd);
                }
            }
            textInd++;
            if (textInd <= textL - patternL) {
                textHash = (textHash - (text.charAt(textInd - 1) * baseEXP)) * BASE
                        + text.charAt(textInd + patternL - 1);
            }
        }
        return list;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }
}
