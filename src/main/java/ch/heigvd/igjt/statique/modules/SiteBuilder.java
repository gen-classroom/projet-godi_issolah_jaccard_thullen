package ch.heigvd.igjt.statique.modules;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;

public class SiteBuilder
{
    /**
     * Builds all the files contained in the rootPath folder and stores them in a "build/" subfolder. If the "build/" subfolder already exists, its content is cleared before processing. If it doesn't exist, it will be created
     * @param rootFolder
     */
    public static void buildAll(File rootFolder) throws IOException {
        //Setup build folder
        File buildFolder = new File(rootFolder + "/build");
        if(!buildFolder.mkdir())
        {
            clearContent(buildFolder);
        }
        //Get site config
        File configFile = new File(rootFolder.getPath() + "/config.yaml");
        if(!configFile.exists())
        {
            System.out.println("config file not found (config.yaml)");
            return;
        }
        //Setup template engine
        TemplateEngine templateEngine;
        ContentFileProcessor cfp = new ContentFileProcessor();
        SiteConfig siteConfig = cfp.getSiteConfigFromYaml(configFile);
        try{
            templateEngine = new TemplateEngine(siteConfig, rootFolder.getPath() + "template/");
        }catch (Exception e){
            System.out.println("template file not found");
            return;
        }
        //Build folder content
        File[] content = rootFolder.listFiles();
        for(File f: content)
        {
            buildFile(buildFolder, f, templateEngine, cfp);
        }
    }

    /**
     * Processes the given file then adds it to the "build/" subfolder. The File object must have been created with a path relative to the folder containing the "build/" subfolder. The "build/" subfolder must already exist. If the file given is a folder, the folder is processed recursively
     * @param buildFolder the folder to copy the file in when processed
     * @param file the file to be processed
     * @param templateEngine the template engine to be used to process a content file. Can be null if the file doesn't need to be compiled
     * @param cfp the content file processor to use when compiling a content file. If the file isn't a content file, this argument may be null
     */
    public static void buildFile(File buildFolder, File file, TemplateEngine templateEngine, ContentFileProcessor cfp) throws IOException
    {
        if(file.getName().equals("build") || file.getName().equals("template")) return;

        File[] files = file.listFiles();
        if(files != null)
        {
            File newBuildFolder = new File(buildFolder.getPath()+ "/" + file.getName());
            newBuildFolder.mkdir();
            for(final File f : files)
            {
                buildFile(newBuildFolder, f, templateEngine, cfp);
            }
        }

        if(file.isFile()) {
            if(FilenameUtils.getExtension(file.getName()).equals("md"))
            {
                cfp.process(new FileInputStream(file));
                String htmlContent = cfp.getHtmlContent();
                ArticleHeader articleHeader = cfp.getArticleHeader();

                String content = templateEngine.build(htmlContent,articleHeader);

                File newFile = new File(buildFolder.getPath() + FilenameUtils.removeExtension(file.getPath()) + ".html");
                if (!newFile.exists()) {

                    newFile.createNewFile();
                    OutputStream os = new FileOutputStream(newFile);
                    os.write(content.getBytes());
                    os.flush();
                    os.close();

                } else {
                    newFile.delete();
                    newFile.createNewFile();
                    OutputStream os = new FileOutputStream(newFile);
                    os.write(content.getBytes());
                    os.flush();
                    os.close();
                }
            }
            else{
                copyToBuild(buildFolder, file);
            }
        }
    }

    /**
     * Copies the given file in the "build/" subfolder. The subfolder must already exist in the root folder used to open the file
     * @param buildFolder the root folder to copy the file in
     * @param file the file to copy
     */
    public static void copyToBuild(File buildFolder, File file) throws IOException {
        if(file.getName().equals("config.yaml")) return;
        File newFile = new File(buildFolder.getPath() + file.getPath());
        if (!newFile.exists()) {
            Files.copy(file.toPath(),newFile.toPath());

        } else {
            newFile.delete();
            Files.copy(file.toPath(),newFile.toPath());
        }
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
