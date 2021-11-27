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

        // add the frequencies to the queue
        PriorityQueue<TreeNode> queue = new PriorityQueue<>();

        for (int i = 0; i < freqs.length; i++) {
            if (freqs[i] > 0) {
                queue.enqueue(new TreeNode(i, freqs[i]));
            }
        }

        // PEOF added
        queue.enqueue(new TreeNode(PSEUDO_EOF, 1));

        myViewer.update("added freqs to queue");

        // create a tree from the queue
        while (!queue.isSizeOne()) {
            TreeNode left = queue.dequeue();
            TreeNode right = queue.dequeue();
            TreeNode parent = new TreeNode(left, -1, right);
            queue.enqueue(parent);
        }

        root = queue.dequeue();

        myViewer.update("created tree toString of root: " + root.toString());

        // store the codes of each character
        codes = createCodes(new HuffCode[PSEUDO_EOF], root, "");

        myViewer.update("created codes\n" + Arrays.toString(codes));

        // TODO make method to figure out num of bits used - header, tree, data, eof
        return -1;
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
        throw new IOException("compress is not implemented");
        // return 0;
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
        if (!bitsIn.readBits(BITS_PER_WORD) == MAGIC_NUMBER) {
            viewer.showError("Error reading compressed file. \n"
                    + "File did not start with the huff magic number.");
            return -1;
        }

        // check if we are using SCF or STF and create the appropriate tree
        TreeNode tree = null;

        // read bits and use the tree to convert to original data



    }

    // read 1 bit at a time and walk tree
    private int decode(TreeNode<E> treeRoot, BitInputStream bitsIn, BitOutputStream bitsOut)
            throws IOException {
        // get ready to walk tree, start at root
        boolean done = false;
        TreeNode<E> curr = treeRoot;
        while (!done) {
            int bit = bitsIn.readBits(1);
            if (bit == -1) {
                throw new IOException(
                        "Error reading compressed file. \n unexpected end of input. No PSEUDO_EOF value.");
            } else {
                // move left or right in tree based on value of bit
                if (bit == 0) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }

                if (curr.isLeaf()) {
                    if (curr.value.equals(PSEUDO_EOF)) {
                        done = true;
                    } else {
                        // write out value in leaf to output
                        bitsOut.writeBits(BITS_PER_WORD, curr.value);
                        // get back to root of tree
                        curr = treeRoot;
                    }
                }
            }
        }
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
