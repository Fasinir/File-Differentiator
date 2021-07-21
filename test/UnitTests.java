import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class UnitTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
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
                            {"0xff","0xd8","0xff","0xe1","DUMMY","DUMMY","0x45","0x78","0x69","0x66","0x00","0x00",}
                    });
    String gifProper="examples/gif1.gif";
    String gifFake="examples/gif2.gif";
    String jpgProper1="examples/jpg1.jpg";
    String jpgProper2="examples/jpg2.jpg";
    String jpgFake="examples/jpg3.jpg";
    String txtProper="examples/txt1.txt";
    String bmpProper="examples/bmp1.bmp";
    @Test
    public void assertionTests()
    {
        FileDifferentiator fd= new FileDifferentiator();
        try {
            fd.parseExtensionsFromFile("src/extensions.txt");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String[] jpgTest={jpgFake};
        Assert.assertThrows(Exception.class,()->
        {
           fd.checkFiles(jpgTest);
        });
        String[] bmpTest={bmpProper};
        Assert.assertThrows(Exception.class,()->
        {
            fd.checkFiles(bmpTest);
        });
    }
    @Test
    public void matchingTests()
    {
        try {
            Assert.assertTrue(jpgObj.fileMatchesExtension(new File(jpgProper1)));
            Assert.assertTrue(jpgObj.fileMatchesExtension(new File(jpgProper2)));
            Assert.assertTrue(gifObj.fileMatchesExtension(new File(gifProper)));
        } catch (IOException e)
        {
            System.out.println("IOexception during testing");
            e.printStackTrace();
        }
    }
    @Test
    public void mismatchingTests()
    {
        try {
            Assert.assertFalse(jpgObj.fileMatchesExtension(new File(jpgFake)));
            Assert.assertFalse(gifObj.fileMatchesExtension(new File(gifFake)));

            Assert.assertFalse(gifObj.fileMatchesExtension(new File(jpgProper1)));
            Assert.assertFalse(gifObj.fileMatchesExtension(new File(jpgProper2)));
            Assert.assertFalse(jpgObj.fileMatchesExtension(new File(gifProper)));

            Assert.assertFalse(gifObj.fileMatchesExtension(new File(bmpProper)));
            Assert.assertFalse(jpgObj.fileMatchesExtension(new File(bmpProper)));
        } catch (IOException e)
        {
            System.out.println("IOexception during testing");
            e.printStackTrace();
        }
    }
}
