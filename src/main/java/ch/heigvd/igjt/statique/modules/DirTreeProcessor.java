package ch.heigvd.igjt.statique.modules;

/*
* Tasks
*   Opens a given directory tree
*   Copies recursively the files in the directory in a "build" directory
*   Uses the ContentFileProcessor to build the .md content files into .html files
*/

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;

public class DirTreeProcessor {
    private String mainPath;

    public DirTreeProcessor(String mainPath)
    {
        this.mainPath = mainPath;
    }

    public void build() throws IOException {
        File sourceFile = new File(mainPath);

        File build = new File(mainPath + "/build");

        build.mkdir();

        buildFiles(sourceFile);
    }

    private void buildFiles(File sourceFile) throws IOException {
        File[] files = sourceFile.listFiles();
        if(files != null)
        {
            for(final File file : files)
            {
                buildFiles(file);
            }
        }

        if(sourceFile.isFile()) {
            String html = ContentFileProcessor.process(new FileInputStream(sourceFile));

            File newFile = new File(mainPath + "/build/" + FilenameUtils.removeExtension(sourceFile.getPath()) + ".html");
            if (!newFile.exists()) {

                new File(mainPath + "/build/" + sourceFile.getParent()).mkdirs();

                newFile.createNewFile();
                OutputStream os = new FileOutputStream(newFile);
                os.write(html.getBytes());
                os.flush();

            } else {
                newFile.delete();
                newFile.createNewFile();
                OutputStream os = new FileOutputStream(newFile);
                os.write(html.getBytes());
                os.flush();
            }
        }
    }
}
