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
import java.util.HashMap;
import java.util.NoSuchElementException;

public class HuffmanTree {
    
    // used when traversing the map while reading bits and writing out the table
    //0 = left and 1 = right
    private final char ZERO = '0';
    private final char ONE = '1';
    // used to represent an 'unimportant' node when rebuilding a tree from a header
    private final int TRAVERSAL_NODE = -1;
    // fill in for the frequency when rebuilding a tree from a header
    private final int FREQ_ZERO = 0;
    
    private TreeNode root;
    
    /*
     * Constructor to build a HuffmanTree from a priority queue
     * pre: queue != null && queue.size() > 0
     * post: a full tree is constructed based on frequency of the nodes
     */
    public HuffmanTree(PQueue<TreeNode> queue) {
        if (queue == null || queue.size() <= 0) {
            throw new IllegalArgumentException("Queue cannot be null or empty");
        }
        
        while (queue.size() > 1) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            queue.add(new TreeNode(left, left.getFrequency() + right.getFrequency(), right));
        }
        
        root = queue.poll();
    }
    
    /*
     * Constructor to build a HuffmanTree when decompressing a STF file
     * pre: none
     * post: a reconstructed tree from the data in the file
     */
    public HuffmanTree(BitInputStream bitsIn) throws IOException {
        root = reconstructTree(bitsIn, root);
    }
    
    /*
     * pre: node != null (covered by calling method)
     * post: creates 'null' nodes if they have children and fills in data for leaf nodes
     */
    private TreeNode reconstructTree(BitInputStream bitsIn, TreeNode node) throws IOException {
        // indicates we have found a leaf with data
        if (bitsIn.readBits(1) == 1) {
            // + 1 bits because of the pseudo_eof value
            node = new TreeNode(bitsIn.readBits(IHuffConstants.BITS_PER_WORD + 1), 0);
            return node;
        }
        node = new TreeNode(TRAVERSAL_NODE, FREQ_ZERO);
        node.setLeft(reconstructTree(bitsIn, node.getLeft()));
        node.setRight(reconstructTree(bitsIn, node.getRight()));
        
        return node;
    }
    
    
    /*
     * pre: node != null
     * post: returns a mapping for all the elements in the HuffmanTree
     */
    public HashMap<Integer, String> mapBuilder() {
        if (root == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        
        HashMap<Integer, String> map = new HashMap<>();
        buildHelper(root, "", map);
        
        return map;
    }
    
    /*
     * pre: node != null, bits != null, and map !=null (all covered in calling method)
     * post: recursive method to construct the map representation of this tree
     */
    private void buildHelper(TreeNode node, String bits, HashMap<Integer, String> map) {
        if (!node.isLeaf()) {
            buildHelper(node.getLeft(), bits + ZERO, map);
            buildHelper(node.getRight(), bits + ONE, map);
        } else {
            map.put(node.getValue(), bits);
        }
    }
    
    /*
     * Method to return the total number of bits a file originally took up
     * As the tree contains all the elements of the file and their frequencies,
     * This is found by just multiplying the total occurence of them all by the
     * bits needed for each (subtracting one byte for psuedo_eof)
     * pre: none
     * post: bits this file originally used
     */
    public int originalBits() {
        return root.getFrequency() * IHuffConstants.BITS_PER_WORD - IHuffConstants.BITS_PER_WORD;
    }
    
    /*
     * Method to return the number of bits necessary to construct the tree in the header
     * pre: none
     * post: the bits needed to put this tree in the header
     */
    public int headerBits() {
        return countHeader(root);
    }
    
    /*
     * pre: node != null (tree will not have null nodes by constructor)
     * post: traverses the tree and counts up the for it
     */
    private int countHeader(TreeNode node) {
        if (node.isLeaf()) {
            // As stated in howto, need 9 bits for the value in the node and 1 for the node itself
            return 1 + 1 + IHuffConstants.BITS_PER_WORD;
        }
        
        // else, keep traversing the tree but add 1 for each node passed
        return 1 + countHeader(node.getLeft()) + countHeader(node.getRight());
    }
    
    
    /*
     * Method to traverse the tree and write it out to the header of a compressed file
     * pre: none
     * post: writes the tree to the header in bits
     */
    public void traverseCompress(BitOutputStream bitsOut) {
        compressHelp(root, bitsOut);
    }
    
    /*
     * Helper for the traverse method.
     * pre: none
     * post: prints 1 for a leaf along with its data and prints 0 if not a leaf
     */
    private void compressHelp(TreeNode node, BitOutputStream bitsOut) {
        if (node.isLeaf()) {
            // 1 indicates a leaf node
            bitsOut.writeBits(1, 1);
            // + 1 bits because of the pseudo_eof value
            bitsOut.writeBits(IHuffConstants.BITS_PER_WORD + 1, node.getValue());
        } else {
            // 0 indicates an internal node
            bitsOut.writeBits(1, 0);
            compressHelp(node.getLeft(), bitsOut);
            compressHelp(node.getRight(), bitsOut);
        }
    }
    
    
    /*
     * Method to traverse thetree to print out data to a file in order to decompress it
     * pre: none
     * post: write out the file and then return the bit count of the new file
     */
    public int traverseDecompress(BitInputStream bitsIn, BitOutputStream bitsOut) 
                                   throws IOException {
        TreeNode temp = root;
        int count = 0;
        int leftOrRight = 0;
        while (leftOrRight != -1) {
            // Get directions for tree
            leftOrRight = bitsIn.readBits(1);
            if (leftOrRight == 1) {
                temp = temp.getRight();
            } else { // bit must then be 1, so right
                temp = temp.getLeft();
            }
            // If this is a leaf, want to print data out
            if (temp.isLeaf()) {
                if (temp.getValue() != IHuffConstants.PSEUDO_EOF) {
                    bitsOut.writeBits(IHuffConstants.BITS_PER_WORD, temp.getValue());
                    count += IHuffConstants.BITS_PER_WORD;
                } else {
                    // if current one is psuedo_eof,it will end after this.
                    leftOrRight = -1;
                }
                temp = root;
            }
        }
        return count;
    }
}
