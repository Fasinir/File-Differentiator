import java.io.*;
import java.util.Arrays;

public class mainVoid {
    public static void main(String[] args)
    {
        /*
        try
        {
            File file = new File("examples/jpg1.jpg");
            InputStream inputStream = new FileInputStream(file);
            printHexStream(inputStream,10);
        } catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOexception");
            e.printStackTrace();
        }

         */
        FileExtension gifObj=new FileExtension("gif",0,
                new String[][]{
                        {"0x47","0x49","0x46","0x38","0x37","0x61"},
                        {"0x47","0x49","0x46","0x38","0x39","0x61"},
                });
        FileExtension jpgObj=new FileExtension("jpg",0,
                new String[][]
                        {
                                {"0xff","0xd8","0xff","0xd8",},
                                {"0xff","0xd8","0xff","0xe0","0x00","0x10","0x4a","0x46","0x49","0x46","0x00","0x01",},
                                {"0xff","0xd8","0xff","0xee",},
                        });
        System.out.println(Arrays.deepToString(gifObj.getMagicCodes()));
        System.out.println(Arrays.deepToString(jpgObj.getMagicCodes()));
    }

    //helper function for testing
    public static void printHexStream(final InputStream inputStream, final int numberOfColumns) throws IOException {
        long streamPtr=0;
        while (inputStream.available() > 0) {
            final long col = streamPtr++ % numberOfColumns;
            System.out.printf("%02x ",inputStream.read());
            if (col == (numberOfColumns-1)) {
                System.out.printf("\n");
            }
        }
    }
}
