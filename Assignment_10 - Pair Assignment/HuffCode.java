/**
 * Student information for assignment:
 *
 * On OUR honor, Dhruv and Rohit, this programming assignment is our own work and WE have not
 * provided this code to any other student.
 *
 * Number of slip days used: 1
 *
 * Student 1 (Student whose turnin account is being used) <br>
 * UTEID: da32895 <br>
 * email address: dhruvarora@utexas.edu <br>
 * Grader name: Grace <br>
 *
 * Student 2 <br>
 * UTEID: ra38355 <br>
 * email address: anantha.rohit.11@gmail.com <br>
 */

public class HuffCode {
    private final int NUM_BITS;
    private final int VALUE;

    /**
     * Constructor for HuffCode.
     * 
     * @param stringCode the string representation of the HuffCode
     */
    public HuffCode(String stringCode) {
        this.NUM_BITS = stringCode.length();
        this.VALUE = Integer.parseInt(stringCode, 2);
    }

    /**
     * Returns the number of bits in the code.
     * 
     * @return the numBits
     */
    public int getNumBits() {
        return NUM_BITS;
    }

    /**
     * Returns the value of the code.
     * 
     * @return the value
     */
    public int getValue() {
        return VALUE;
    }
}
