import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDifferentiator {
    List<FileExtension> fileExtensions;
    private final static int textFileNullOffset=8000;
    public FileDifferentiator()
    {
        this.fileExtensions=new ArrayList<>();
    }
    public void addFileExtension(FileExtension extension)
    {
        this.fileExtensions.add(extension);
    }

    public String matchedExtension(String filePath) throws Exception {
        File file = new File(filePath);
        for (FileExtension extension:fileExtensions)
        {
            if(extension.fileMatchesExtension(file))
            {
                return extension.getName();
            }
        }
        if(isTextFile(file))
        {
            return "txt";
        }
        throw new Exception("Could not find matching extension for this file "+filePath);
    }

    //there is no clear way of checking whether a file is really a text file
    //one way which github uses is to check if any of first 8k characters is a NUL
    private boolean isTextFile(File file) throws IOException {
        InputStream stream= new FileInputStream(file);
        int counter=0;
        while (stream.available()>0 && counter<textFileNullOffset)
        {
            if(stream.read()==0)
            {
                return false;
            }
            counter++;
        }
        return true;
    }
    public void checkFiles(String[] args) throws Exception {
        for (String arg:args)
        {
            int lastDot=arg.lastIndexOf('.');
            if(lastDot==-1)
            {
                System.out.println("File "+arg+" doesn't have an extension");
            }
            else
            {
                String extension= arg.substring(lastDot+1);
                String matchedExtension=matchedExtension(arg);

                if(extension.equals(matchedExtension))
                {
                    System.out.println(arg+" has proper extension");
                }
                else
                {
                    System.out.println(arg+" has extension "+extension+" while actually it's a "+matchedExtension);
                }

            }
        }
    }
    public void parseExtensionsFromFile(String filePath) throws Exception {
        File file=new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line=br.readLine())!=null)
        {
            String name=line;
            if ((line=br.readLine())==null)
            {
                throw new Exception("Bad parsing");
            }
            int offset= Integer.parseInt(line);
            if ((line=br.readLine())==null)
            {
                throw new Exception("Bad parsing");
            }
            int numberOfCodes=Integer.parseInt(line);
            String[][] magicCodes=new String[numberOfCodes][];
            for (int i=0;i<numberOfCodes;i++)
            {
                if ((line=br.readLine())==null)
                {
                    throw new Exception("Bad parsing");
                }
                magicCodes[i]=line.split(" ");
            }
            addFileExtension(new FileExtension(name,offset,magicCodes));
        }
    }
}
