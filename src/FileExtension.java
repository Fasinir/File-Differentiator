public class FileExtension {

    private String name;
    private int offset;
    private int[][] magicCodes;

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
            for (int j=0;j<magicCodes[i].length;j++)
            {
                this.magicCodes[i][j]=Integer.decode(magicStringCodes[i][j]);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int[][] getMagicCodes() {
        return magicCodes;
    }
}
