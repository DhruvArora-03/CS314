/**
 * Student information for assignment:
 *
 * On OUR honor, Dhruv and Rohit, this programming assignment is our own work and WE have not
 * provided this code to any other student.
 *
 * Number of slip days used:
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;

    private int[] freqs;
    private TreeNode root;
    private HuffCode[] codes;
    private int headerFormat;

    /**
     * Preprocess data so that compression is possible --- count characters/create tree/store state
     * so that a subsequent call to compress will work. The InputStream is <em>not</em> a
     * BitInputStream, so wrap it int one as needed.
     * 
     * @param in is the stream which could be subsequently compressed
     * @param headerFormat a constant from IHuffProcessor that determines what kind of header to
     *        use, standard count format, standard tree format, or possibly some format added in the
     *        future.
     * @return number of bits saved by compression or some other measure Note, to determine the
     *         number of bits saved, the number of bits written includes ALL bits that will be
     *         written including the magic number, the header format number, the header to reproduce
     *         the tree, AND the actual data.
     * @throws IOException if an error occurs while reading from the input file.
     */
    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        myViewer.update("Running preprocessCompress");
        // find frequencies of each 8 bit chunk
        BitInputStream bitsIn = new BitInputStream(in);
        freqs = new int[ALPH_SIZE];
        int readBits = bitsIn.readBits(BITS_PER_WORD);

        while (readBits != -1) {
            freqs[readBits]++;
            readBits = bitsIn.readBits(BITS_PER_WORD);
        }

        myViewer.update("found freqs: " + Arrays.toString(freqs));

        root = createTreeFromFreqs(freqs);

        myViewer.update("created tree toString of root: " + root.toString());

        // store the codes of each character
        codes = createCodes(new HuffCode[ALPH_SIZE + 1], root, ""); // + 1 for PEOF

        myViewer.update("created codes\n" + Arrays.toString(codes));

        // save headerFormat
        this.headerFormat = headerFormat;

        bitsIn.close();

        return calculateSavedBits(headerFormat);
    }

    private int calculateSavedBits(int headerFormat) {
        int compressedBits = BITS_PER_INT * 2; // store magic num and header format

        if (headerFormat == STORE_TREE) {
            compressedBits += bitsOfTreeRepresentation();
        } else if (headerFormat == STORE_COUNTS) {
            // SCF BITS PER INT * ALPH_SIZE
            compressedBits += BITS_PER_INT * ALPH_SIZE;
        }
        int uncompressedBits = 0;
        // count bits used in compressed version
        for (int i = 0; i < freqs.length; i++) {
            // we have a code iff the freq > 0
            if (freqs[i] > 0) {
                compressedBits += freqs[i] * codes[i].numBits;
                uncompressedBits += freqs[i] * BITS_PER_WORD;
            }
        }

        compressedBits += codes[PSEUDO_EOF].numBits;

        return uncompressedBits - compressedBits;
    }

    /**
     * Get the number of bits needed to represent a tree
     * 
     * @return the number of bits that would be used to write the tree in STF
     */
    private int bitsOfTreeRepresentation() {
        int bits = 0;

        // STF BITS_PER_INT for size of tree + 1 per node, + 9 per leaf
        bits += BITS_PER_INT;

        // # of nodes is size of the tree which is the value of the root
        bits += root.getValue();

        // add 9 for every leaf, if freq > 0 --> is a leaf
        for (int freq : freqs) {
            if (freq > 0) {
                bits += 9;
            }
        }

        // (and one more for PEOF)
        bits += 9;

        return bits;
    }

    private TreeNode createTreeFromFreqs(int[] inputFreqs) {
        // add the frequencies to the queue
        PQ<TreeNode> queue = new PQ<>();

        for (int i = 0; i < inputFreqs.length; i++) {
            if (inputFreqs[i] > 0) {
                queue.enqueue(new TreeNode(i, inputFreqs[i]));
            }
        }

        // PEOF added
        queue.enqueue(new TreeNode(PSEUDO_EOF, 1));

        // create a tree from the queue
        while (!queue.isSizeOne()) {
            TreeNode left = queue.dequeue();
            TreeNode right = queue.dequeue();
            // the value of a non-leaf node is the size of the subtree at the node
            TreeNode parent = new TreeNode(left, (left.isLeaf() ? 1 : left.getValue())
                    + (right.isLeaf() ? 1 : right.getValue()) + 1, right);
            queue.enqueue(parent);
        }

        return queue.dequeue();
    }

    /**
     * Create a HuffCode[] of codes for each leaf in the tree.
     * 
     * @param codes the array of codes to be filled in
     * @param node the node we are at
     * @param code the current code we are building
     * @return the filled in codes array
     */
    private HuffCode[] createCodes(HuffCode[] codes, TreeNode node, String code) {
        // if the node is a leaf --> the code is complete and we can store it
        if (node.isLeaf()) {
            codes[node.getValue()] = new HuffCode(code);
        }
        // otherwise recurse down child trees checking for leaves
        else {
            createCodes(codes, node.getLeft(), code + "0");
            createCodes(codes, node.getRight(), code + "1");
        }
        return codes;
    }

    /**
     * Compresses input to output, where the same InputStream has previously been pre-processed via
     * <code>preprocessCompress</code> storing state used by this call. <br>
     * pre: <code>preprocessCompress</code> must be called before this method
     * 
     * @param in is the stream being compressed (NOT a BitInputStream)
     * @param out is bound to a file/stream to which bits are written for the compressed file (not a
     *        BitOutputStream)
     * @param force if this is true create the output file even if it is larger than the input file.
     *        If this is false do not create the output file if it is larger than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or writing to the
     *         output file.
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        BitInputStream bitsIn = new BitInputStream(in);
        BitOutputStream bitsOut = new BitOutputStream(out);

        // write the magic number
        bitsOut.writeBits(BITS_PER_INT, MAGIC_NUMBER);

        // write format (SCF vs STF)
        bitsOut.writeBits(BITS_PER_INT, headerFormat);

        // write counts/tree according to format
        if (headerFormat == STORE_TREE) { // write tree
            bitsOut.writeBits(BITS_PER_INT, bitsOfTreeRepresentation());
            writeTree(bitsOut, root);
        } else if (headerFormat == STORE_COUNTS) { // write counts
            for (int i = 0; i < IHuffConstants.ALPH_SIZE; i++) {
                bitsOut.writeBits(BITS_PER_INT, freqs[i]);
            }
        }

        // write data

        

        // write PEOF

        return 0;
    }

    private void writeTree(BitOutputStream bitsOut, TreeNode node) {
        if (node != null) {
            if (node.isLeaf()) {
                bitsOut.writeBits(1, 1);
                bitsOut.writeBits(9, node.getValue());
            } else {
                // preorder: this --> left --> right
                bitsOut.writeBits(1, 0);
                writeTree(bitsOut, node.getLeft());
                writeTree(bitsOut, node.getRight());
            }
        }
    }

    /**
     * Uncompress a previously compressed stream in, writing the uncompressed bits/data to out.
     * 
     * @param in is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or writing to the
     *         output file.
     */
    public int uncompress(InputStream in, OutputStream out) throws IOException {
        // check if the file is 'valid' by confirming the magic number
        BitInputStream bitsIn = new BitInputStream(in);
        BitOutputStream bitsOut = new BitOutputStream(out);
        if (!(bitsIn.readBits(BITS_PER_INT) == MAGIC_NUMBER)) {
            myViewer.showError("Error reading compressed file. \n"
                    + "File did not start with the huff magic number.");
            bitsIn.close();
            bitsOut.close();
            return -1;
        }

        // check if we are using SCF or STF and create the appropriate tree
        int format = bitsIn.readBits(BITS_PER_INT);
        TreeNode tree = null;
        if (format == STORE_TREE) {
            // read # of bits val from data
            int sizeOfTree = bitsIn.readBits(BITS_PER_INT);
            myViewer.update("Size of uncompressing tree " + sizeOfTree);
            tree = readSTF(bitsIn);
        } else if (format == STORE_COUNTS) {
            tree = readSCF(bitsIn);
        }

        myViewer.update(String.format("Uncompressed %s data and created new tree for decoding.",
                format == STORE_TREE ? "tree" : "freq"));

        // read bits and use the tree to convert to original data

        int bitsWritten = decode(tree, bitsIn, bitsOut);

        myViewer.update("uncompressing complete :)");

        bitsIn.close();
        bitsOut.close();
        return bitsWritten;
    }

    /**
     * Create a tree using SCF.
     * 
     * @param bitsIn input stream to read data from
     * @return the root of the tree represented by the data
     * @throws IOException
     */
    private TreeNode readSCF(BitInputStream bitsIn) throws IOException {
        // read and create a freq array
        int[] tempFreqs = new int[ALPH_SIZE];

        for (int k = 0; k < IHuffConstants.ALPH_SIZE; k++) {
            tempFreqs[k] = bitsIn.readBits(BITS_PER_INT);
        }

        return createTreeFromFreqs(tempFreqs);
    }

    /**
     * Create a tree using STF.
     * 
     * @param bitsIn input stream to read data from
     * @return the root of the tree represented by the data
     * @throws IOException
     */
    private TreeNode readSTF(BitInputStream bitsIn) throws IOException {
        // if the next bit represents a parent
        if (bitsIn.readBits(1) == 0) {
            TreeNode node = new TreeNode(-1, -1); // all freq = -1 because they no longer matter
            node.setLeft(readSTF(bitsIn));
            node.setRight(readSTF(bitsIn));
            return node;
        }
        // otherwise the bit we read represents a leaf
        else {
            int val = bitsIn.readBits(BITS_PER_WORD + 1);
            myViewer.update(" " + val);
            return new TreeNode(val, -1); // all freq = -1 because they no longer matter
        }
    }

    // read 1 bit at a time and walk tree
    private int decode(TreeNode treeRoot, BitInputStream bitsIn, BitOutputStream bitsOut)
            throws IOException {
        // get ready to walk tree, start at root
        boolean done = false;
        TreeNode curr = treeRoot;
        int bitsWritten = 0;
        while (!done) {
            int bit = bitsIn.readBits(1);
            if (bit == -1) {
                throw new IOException("Error reading compressed file. "
                        + "\n unexpected end of input. No PSEUDO_EOF value.");
            } else {
                // move left or right in tree based on value of bit
                if (bit == 0) {
                    curr = curr.getLeft();
                } else {
                    curr = curr.getRight();
                }

                if (curr.isLeaf()) {
                    if (curr.getValue() == PSEUDO_EOF) {
                        done = true;
                    } else {
                        // write out value in leaf to output
                        bitsOut.writeBits(BITS_PER_WORD, curr.getValue());
                        bitsWritten += BITS_PER_WORD;
                        // go back to root of tree
                        curr = treeRoot;
                    }
                }
            }
        }

        return bitsWritten;
    }

    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }

    private void showString(String s) {
        if (myViewer != null)
            myViewer.update(s);
    }

    private class HuffCode {
        int numBits;
        int value;

        HuffCode(String stringCode) {
            this.numBits = stringCode.length();
            this.value = Integer.parseInt(stringCode, 2);
        }

        @Override
        public String toString() {
            return "HuffCode [numBits=" + numBits + ", value=" + value + "]";
        }
    }
}
