import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class FileExtension {

    private String name;
    private int offset;
    private int[][] magicCodes;
    private int maxMagicLength;
    private int minMagicLength;
    private final static int DUMMY_CHARACTER=-1;

    public FileExtension(String name, int offset, int[][] magicCodes)
    {
        this.name=name;
        this.offset=offset;
        this.magicCodes=magicCodes;
    }

    public FileExtension(String name, int offset, String[][] magicStringCodes)
    {
        this.name=name;
        this.offset=offset;
        this.magicCodes=new int[magicStringCodes.length][];
        //parsing String[][] array into int[][] array of hex values of magic codes
        //we also save minimal and maximal length of these codes
        minMagicLength=Integer.MAX_VALUE;
        maxMagicLength=-1;
        for (int i=0;i<magicCodes.length;i++)
        {
            this.magicCodes[i]=new int[magicStringCodes[i].length];
            maxMagicLength=Math.max(maxMagicLength,magicCodes[i].length);
            minMagicLength=Math.min(minMagicLength,magicCodes[i].length);
            for (int j=0;j<magicCodes[i].length;j++)
            {
                //if character in magic code is "?" we have to pass "DUMMY" to constructor
                if(magicStringCodes[i][j].equals("DUMMY"))
                {
                    this.magicCodes[i][j]=DUMMY_CHARACTER;
                }
                else
                {
                    //we decode string hex value into int
                    this.magicCodes[i][j]=Integer.decode(magicStringCodes[i][j]);
                }
            }
        }
    }
    public boolean fileMatchesExtension(File file) throws IOException {
        InputStream stream=new FileInputStream(file);
        //skipping offset
        if(stream.skip(offset)!=offset)
        {
            stream.close();
            return false;
        }
        //reading right characters from file
        int[] readCharacters=new int[maxMagicLength];
        int idx=0;
        while (stream.available()>0 && idx<maxMagicLength)
        {
            readCharacters[idx]=stream.read();
            idx++;
        }
        if (idx>=minMagicLength)
        {
            for (int[] magicCode : magicCodes) {

                //checking if arrays match
                int[] copy = Arrays.copyOfRange(readCharacters, 0, magicCode.length);
                if(checkArrayWithDummyCharacters(copy,magicCode))
                {
                    stream.close();
                    return true;
                }
            }
        }
        stream.close();
        return false;

    }
    private boolean checkArrayWithDummyCharacters(int[] arr, int[] magicCode)
    {
        for (int i=0;i<magicCode.length;i++)
        {
            if(magicCode[i]!=DUMMY_CHARACTER)
            {
                if(arr[i]!=magicCode[i])
                {
                    return false;
                }
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public int[][] getMagicCodes() {
        return magicCodes;
    }
}
