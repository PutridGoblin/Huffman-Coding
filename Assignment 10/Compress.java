/*  Student information for assignment:
 *
 *  On <MY> honor, <Tyler Meador>, this programming assignment is <MY> own work
 *  and <I> have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose turnin account is being used)
 *  UTEID: ttm553
 *  email address: meador.tyler0@gmail.com
 *  Grader name: Nina
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class Compress {
    
    private int bitsSaved;
    private int compressedBits;
    private int format;
    private int[] freqArray;
    private HashMap<Integer, String> mapping;
    private HuffmanTree huffTree;
    
    /*
     * Acts as the preprocess for SimpleHuffProcessor, sets up our priority queue,
     * the frequency array, the tree and its mapping, and the bits of the file before
     * and after the compression
     */
    public Compress(InputStream in, int headerFormat) throws IOException {
        PQueue<TreeNode> queue = queueBuilder(in);
        format = headerFormat;
        huffTree = new HuffmanTree(queue);
        mapping = huffTree.mapBuilder();
        compressedBits = compressedBitHelper();
        bitsSaved = huffTree.originalBits() - compressedBits;
    }
    
    /*
     * Simple constructor to build the priority queue for this file.
     * pre: none
     * post: returns the priority queue built using the frequency array's data
     */
    private PQueue<TreeNode> queueBuilder(InputStream in) throws IOException{
        freqBuilder(in);
        PQueue<TreeNode> queue = new PQueue<>();
        for (int data = 0; data < IHuffConstants.ALPH_SIZE + 1; data++) {
            if (freqArray[data] > 0) {
                queue.add(new TreeNode(data, freqArray[data]));
            }
        }
        
        return queue;
    }
    
    /*
     * Method to build an array for the frequency of bits for the file
     * pre: none
     * post: an array of the elements of the file and their frequency in the file
     */
    private void freqBuilder(InputStream in) throws IOException{
        BitInputStream bits = new BitInputStream(in);
        freqArray = new int[IHuffConstants.ALPH_SIZE + 1];
        boolean done = false;
        while (!done) {
            int inBits = bits.readBits(IHuffConstants.BITS_PER_WORD);
            // We have reached the end of the file
            if (inBits == -1) {
                done = true;
            // We have a set of bits, increment its frequency
            } else {
                freqArray[inBits]++;
            }
        }
        // Set up the psuedo_eof
        freqArray[IHuffConstants.ALPH_SIZE] = 1;
        bits.close();
    }
    
    /*
     * pre: none
     * post: returns the bits of the file if it were to be compressed
     */
    private int compressedBitHelper() {
        int bits = 0;
        
        // Add the number of bits (length of string) multiplied by its number of occurences
        for (int key : mapping.keySet()) {
            bits += mapping.get(key).length() * freqArray[key];
        }
        // add magical number and storage type bits
        bits += IHuffConstants.BITS_PER_INT * 2;
        // add the number of bits of the header data
        if (format == IHuffConstants.STORE_COUNTS) {
            bits += IHuffConstants.ALPH_SIZE * IHuffConstants.BITS_PER_INT;
        } else { // header type is tree
            bits += huffTree.headerBits() + IHuffConstants.BITS_PER_INT;
        }
        
        return bits;
    }
    
    
    /*
     * Method to return the amount of bitsSaved through the compression
     * (for preprocess method in SimpleHuffProcessor)
     * pre: none
     * post: returns bits saved through compression
     */
    public int bitsSaved() {
        return bitsSaved;
    }
    
    
    /*
     * pre: bitsSaved > 0 && force == true (don't want to compress unless it is better
     * for the storage. If force == true, then we want to compress anyways)
     * post: does the actual writing to the file to compress it
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        if (bitsSaved <= 0 && !force) {
            throw new IOException("Compressed file has " + bitsSaved * (-1) +
                                  " more bits than the uncompressed file." +
                                  " Select \"force compression\" option to compress.");
        }
        
        BitOutputStream bitsOut = new BitOutputStream(out);
        // Writes the magic numbers
        bitsOut.writeBits(IHuffConstants.BITS_PER_INT, IHuffConstants.MAGIC_NUMBER);
        // Writes the format of file
        bitsOut.writeBits(IHuffConstants.BITS_PER_INT, format);
        
        formatCheck(bitsOut);
        
        BitInputStream bitsIn = new BitInputStream(in);
        writeData(bitsIn, bitsOut);

        // Encode result for the pseudo_eof constant
        String bitRepresentation = mapping.get(IHuffConstants.PSEUDO_EOF);
        stringToBits(bitRepresentation, bitsOut);
        bitsOut.close();
        
        return compressedBits;
    }
    
    /*
     * Method to check the header format and either write a table or tree in the header 
     */
    private void formatCheck(BitOutputStream bitsOut) {
     // Writes the rest of header
        if (format == IHuffConstants.STORE_COUNTS) {
            for (int freqEncode = 0; freqEncode < IHuffConstants.ALPH_SIZE; freqEncode++) {
                bitsOut.writeBits(IHuffConstants.BITS_PER_INT, freqArray[freqEncode]);
            }
        } else if (format == IHuffConstants.STORE_TREE) {
            bitsOut.writeBits(IHuffConstants.BITS_PER_INT, huffTree.headerBits());
            huffTree.traverseCompress(bitsOut);
        }
    }
    
    /*
     * Method to write the actual data out as bits to the file
     */
    private void writeData(BitInputStream bitsIn, BitOutputStream bitsOut) throws IOException {
        boolean done = false;
        while (!done) {
            int tempBits = bitsIn.readBits(IHuffConstants.BITS_PER_WORD);
            if (tempBits == -1) {
                done = true;
            } else {
                // To get the required encoding from the map to compress
                String bits = mapping.get(tempBits);
                stringToBits(bits, bitsOut);
            }
        }
    }
    
    /*
     * Method to write the actual bits that the string represents
     */
    private void stringToBits(String bitRepresentation, BitOutputStream bitsOut) {
        final char ZERO = '0';
        for (int bit = 0; bit < bitRepresentation.length(); bit++) {
            if (bitRepresentation.charAt(bit) == ZERO)
                bitsOut.writeBits(1, 0);
            else
                bitsOut.writeBits(1, 1);

        }
    }
}