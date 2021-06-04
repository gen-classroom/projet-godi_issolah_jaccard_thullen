package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import ch.heigvd.igjt.statique.modules.ContentFileProcessor;
import ch.heigvd.igjt.statique.modules.TemplateEngine;
import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "build")
public class SubCommandBuild implements Callable<Integer> {

    TemplateEngine templateEngine;
    ContentFileProcessor cfp;

    @CommandLine.Parameters(index="0") String path;
    @CommandLine.Option(names = "--watch", usageHelp = true, description = "Auto-rebuild when files are changed") boolean autoRebuild;
    @Override
    public Integer call() throws Exception {
        System.out.println("Building site at '" + path + "'...");
        File sourceFile = new File(path);
        if(!sourceFile.exists()) {
            System.out.println("Could not find directory tree.");
            return -1;
        }
        File build = new File(path + "/build");

        build.mkdir();

        SiteBuilder.buildAll(path);
        if (autoRebuild)
        {
            FileWatcher fw = new FileWatcher(path);
            System.out.println("Watchdog initialized for path " + path + "\nPress Enter to terminate...");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            System.out.println("Watchdog terminated\n");
        }
        cfp = new ContentFileProcessor();

        File configFile = new File(path + "/config.yaml");

        if(!configFile.exists())
        {
            System.out.println("config file not found (config.yaml)");
            return -1;
        }

        SiteConfig siteConfig = cfp.getSiteConfigFromYaml(configFile);

        try{
            templateEngine = new TemplateEngine(siteConfig, path + "template/");
        }catch (Exception e){
            System.out.println("template file not found");
            return -1;
        }

        try{
            buildFiles(sourceFile);
        }catch (IOException e){
            System.out.println("Error : file copy failed");
            return -1;
        }

        return 1;
    }

    private void buildFiles(File sourceFile) throws IOException {
        if(sourceFile.getName().equals("build") || sourceFile.getName().equals("template"))
            return;

        File[] files = sourceFile.listFiles();
        if(files != null)
        {
            for(final File file : files)
            {
                buildFiles(file);
            }
        }

        if(sourceFile.isFile()) {
            if(FilenameUtils.getExtension(sourceFile.getName()).equals("md"))
            {
                cfp.process(new FileInputStream(sourceFile));
                String htmlContent = cfp.getHtmlContent();
                ArticleHeader articleHeader = cfp.getArticleHeader();

                String content = templateEngine.build(htmlContent,articleHeader);

                File newFile = new File(path + "/build/" + FilenameUtils.removeExtension(sourceFile.getPath()) + ".html");
                if (!newFile.exists()) {

                    new File(path + "/build/" + sourceFile.getParent()).mkdirs();

                    newFile.createNewFile();
                    OutputStream os = new FileOutputStream(newFile);
                    os.write(content.getBytes());
                    os.flush();

                } else {
                    newFile.delete();
                    newFile.createNewFile();
                    OutputStream os = new FileOutputStream(newFile);
                    os.write(content.getBytes());
                    os.flush();
                }
            }
            else{
                if(sourceFile.getName().equals("config.yaml"))
                    return;
                File newFile = new File(path + "/build/" + sourceFile.getPath());
                if (!newFile.exists()) {
                    new File(path + "/build/" + sourceFile.getParent()).mkdirs();
                    Files.copy(sourceFile.toPath(),newFile.toPath());

                } else {
                    newFile.delete();
                    Files.copy(sourceFile.toPath(),newFile.toPath());
                }
            }
        }
    }
}
