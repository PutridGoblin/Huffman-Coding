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
import java.util.ArrayList;

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private Compress compressor;
    private Decompress decompressor;

    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        return compressor.compress(in, out, force);
    }

    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        compressor = new Compress(in, headerFormat);
        return compressor.bitsSaved();
    }

    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }

    public int uncompress(InputStream in, OutputStream out) throws IOException {
        decompressor = new Decompress(in);
        return decompressor.decompress(in, out);
    }

    private void showString(String s){
        if(myViewer != null)
            myViewer.update(s);
    }
}
