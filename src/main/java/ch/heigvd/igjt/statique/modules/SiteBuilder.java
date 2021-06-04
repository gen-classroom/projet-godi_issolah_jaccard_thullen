package ch.heigvd.igjt.statique.modules;

import org.apache.commons.io.FilenameUtils;

import java.io.*;

public class SiteBuilder
{
    /**
     * Builds all the files contained in the rootPath folder and stores them in a "build/" subfolder. If the "build/" subfolder already exists, its content is cleared before processing. If it doesn't exist, it will be created
     * @param rootPath
     */
    public static void buildAll(String rootPath) throws IOException {
        File root = new File(rootPath);
        File[] content = root.listFiles();
        File buildFolder = new File(rootPath + "/build");
        if(!buildFolder.mkdir())
        {
            clearContent(buildFolder);
        }

        for(File f: content)
        {
            buildFile(buildFolder, f);
        }
    }

    /**
     * Processes the given file then adds it to the "build/" subfolder. The File object must have been created with a path relative to the folder containing the "build/" subfolder. The "build/" subfolder must already exist. If the file given is a folder, the folder is processed recursively
     * @param buildFolder the folder to copy the file in when processed
     * @param file the file to be processed
     */
    public static void buildFile(File buildFolder, File file) throws IOException
    {
        if (file.isFile())
        {
            if (FilenameUtils.getExtension(file.getPath()).equals("md"))
            {
                String html = ContentFileProcessor.process(new FileInputStream(file));
                File newFile = new File(buildFolder.getPath() + FilenameUtils.removeExtension(file.getPath()) + ".html");

                newFile.createNewFile();
                OutputStream os = new FileOutputStream(newFile);
                os.write(html.getBytes());
                os.flush();
                os.close();
            }
            else
            {
                copy(buildFolder, file);
            }
        }
        else
        {
            File newBuildFolder = new File(buildFolder.getPath() + file.getName());
            newBuildFolder.mkdir();
            File[] content = file.listFiles();
            if (content != null)
            {
                for (File f : content)
                {
                    buildFile(newBuildFolder, file);
                }
            }
        }
    }

    /**
     * Copies the given file in the "build/" subfolder. The subfolder must already exist in the root folder used to open the file
     * @param buildFolder the root folder to copy the file in
     * @param file the file to copy
     */
    public static void copy(File buildFolder, File file) throws IOException {
        File newFile = new File(buildFolder.getPath() + file.getName());
        newFile.createNewFile();
        OutputStream os = new FileOutputStream(newFile);
        InputStream is = new FileInputStream(file);
        os.write(is.readAllBytes());
        os.flush();
        os.close();
        is.close();
    }

    /**
     * Clears the content of the given folder
     * @param folder the folder to clear
     * @return true if the content was successfully cleared, false otherwise
     */
    public static boolean clearContent(File folder)
    {
         File[] content = folder.listFiles();
         if(content != null)
         {
             for(File f: content)
             {
                 if(!recursiveDelete(f)) return false;
             }
         }
         return true;
    }

    /**
     * Recursively deletes the directory and its content
     * @param rootFolder the folder to delete
     * @return true if the folder was successfully deleted, false otherwise
     */
    public static boolean recursiveDelete(File rootFolder)
    {
        File[] content = rootFolder.listFiles();
        if(content != null)
        {
            for(File f: content) {
                if (!recursiveDelete(f)) return false;
            }
        }
        return rootFolder.delete();
    }
}
