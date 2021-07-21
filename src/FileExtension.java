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
        for (int i=0;i<magicCodes.length;i++)
        {
            this.magicCodes[i]=new int[magicStringCodes[i].length];
            minMagicLength=Integer.MAX_VALUE;
            for (int j=0;j<magicCodes[i].length;j++)
            {
                maxMagicLength=Math.max(maxMagicLength,magicCodes[i].length);
                minMagicLength=Math.min(minMagicLength,magicCodes[i].length);
                this.magicCodes[i][j]=Integer.decode(magicStringCodes[i][j]);
            }
        }
    }
    public boolean fileMatchesExtension(File file) throws IOException {
        InputStream stream=new FileInputStream(file);
        if(stream.skip(offset)!=offset)
        {
            stream.close();
            return false;
        }
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

                int[] copy = Arrays.copyOfRange(readCharacters, 0, magicCode.length);
                if(Arrays.equals(copy,magicCode))
                {
                    stream.close();
                    return true;
                }
            }
        }
        stream.close();
        return false;

    }

    public String getName() {
        return name;
    }

    public int[][] getMagicCodes() {
        return magicCodes;
    }
}
