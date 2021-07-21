import java.io.*;
import java.util.Arrays;

public class mainVoid {
    private static final String extensionsFilepath="src/extensions.txt";
    public static void main(String[] args) throws Exception {
        FileDifferentiator fd= new FileDifferentiator();
        try {
            fd.parseExtensionsFromFile(extensionsFilepath);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        fd.checkFiles(args);
    }

    //helper function for testing
    /*
    public static void printHexStream(final InputStream inputStream, final int numberOfColumns) throws IOException {
        long streamPtr=0;
        int maxCount=32;
        int count=0;
        while (inputStream.available() > 0 && count<maxCount) {
            final long col = streamPtr++ % numberOfColumns;
            System.out.printf("%02x ",inputStream.read());
            if (col == (numberOfColumns-1)) {
                System.out.printf("\n");
            }
            count++;
        }
    }

     */
}
