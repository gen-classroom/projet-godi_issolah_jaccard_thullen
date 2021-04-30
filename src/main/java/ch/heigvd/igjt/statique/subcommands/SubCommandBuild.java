package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.modules.ContentFileProcessor;
import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "build")
public class SubCommandBuild implements Callable<Integer> {

    @CommandLine.Parameters(index="0") String path;
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

        try{
            buildFiles(sourceFile);
        }catch (IOException e){
            System.out.println("Error : file copy failed");
            return -1;
        }

        return 1;
    }

    private void buildFiles(File sourceFile) throws IOException {
        if(sourceFile.getName().equals("build"))
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
                String html = ContentFileProcessor.process(new FileInputStream(sourceFile));

                File newFile = new File(path + "/build/" + FilenameUtils.removeExtension(sourceFile.getPath()) + ".html");
                if (!newFile.exists()) {

                    new File(path + "/build/" + sourceFile.getParent()).mkdirs();

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
            }else{
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
