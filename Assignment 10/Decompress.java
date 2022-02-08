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

public class Decompress {
    
    private IHuffViewer viewer;
    private int headerFormat;
    private int magicNum;
    
    /*
     * constructor that reads the header's format and checks for the magic number
     */
    public Decompress(InputStream in) throws IOException{
        BitInputStream bitsIn = new BitInputStream(in);
        // Gets the magic number and check if this file can be uncompressed
        magicNum = bitsIn.readBits(IHuffConstants.BITS_PER_INT);
        if (magicNum != IHuffConstants.MAGIC_NUMBER) {
            GUIHuffViewer magicError = new GUIHuffViewer("Magic number error");
            magicError.showError("Error reading compressed file. \n" +
                                 "File did not start with the huff magic number.");
        }
        // Reads the format of the header (counts or tree)
        headerFormat = bitsIn.readBits(IHuffConstants.BITS_PER_INT);
    }
    
    /*
     * Main method to decompress the file
     * pre: none
     * post: takes the compressed file and decompresses it into a new one
     */
    public int decompress(InputStream in, OutputStream out) throws IOException {
        BitInputStream bitsIn = new BitInputStream(in);
        BitOutputStream bitsOut = new BitOutputStream(out);
        HuffmanTree decodeTree;
        
        if (headerFormat == IHuffConstants.STORE_COUNTS) {
            int[] freqArray = freqConstructor(bitsIn, bitsOut);
            decodeTree = arrayToTree(bitsIn, freqArray);
        } else {
            bitsIn.readBits(IHuffConstants.BITS_PER_INT);
            decodeTree = new HuffmanTree(bitsIn);
        }
        return decodeTree.traverseDecompress(bitsIn, bitsOut);
    }
    
    /*
     * pre: freqArray != null (covered in calling method)
     * post: returns the tree representation of the frequency array from the header (if SCF)
     */
    private HuffmanTree arrayToTree(BitInputStream bitsIn, int[] freqArray) throws IOException {
        PQueue<TreeNode> queue = new PQueue<>();
        for (int data = 0; data < IHuffConstants.ALPH_SIZE + 1; data++) {
            if (freqArray[data] > 0) {
                queue.add(new TreeNode(data, freqArray[data]));
            }
        }
        
        return new HuffmanTree(queue);
    }
    
    /*
     * pre: none
     * post: returns the frequency array from the header as an actual array
     */
    private int[] freqConstructor(BitInputStream bitsIn, BitOutputStream bitsOut)
                                  throws IOException {
        int[] freqArray = new int[IHuffConstants.ALPH_SIZE + 1];
        for (int data = 0; data < IHuffConstants.ALPH_SIZE; data++) {
            int frequency = bitsIn.readBits(IHuffConstants.BITS_PER_INT);
            freqArray[data] = frequency;
        }
        freqArray[IHuffConstants.ALPH_SIZE] = 1;
        
        return freqArray;
    }
}
