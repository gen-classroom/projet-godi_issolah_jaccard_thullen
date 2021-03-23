package ch.heigvd.igjt.statique.subcommands;

import picocli.CommandLine;

import java.io.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "init")
public class SubCommandInit implements Callable<Integer> {

    @CommandLine.Parameters(index="0") String path;
    @Override
    public Integer call() {
        System.out.println("Initializing site at '" + path + "'...");
        File pathFile = new File(path);
        if (!mkDirs(pathFile)) {
            System.out.println("Could not create directory tree.");
            return -1;
        }
        if (!mkConfigFile(pathFile)) {
            System.out.println("Could not create config file.");
            return -1;
        }
        if (!mkIndexFile(pathFile)) {
            System.out.println("Could not create index file");
            return -1;
        }
        return 0;
    }

    private boolean mkDirs(File pathFile) {
        return pathFile.mkdirs();
    }

    private boolean mkConfigFile(File rootPath) {
        File configFile = new File(rootPath, "config.yaml");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(configFile));
            writer.write("titre: Mon site\ndomaine: mon-site.ch\ndescription: Ici c'est mon site!\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore){}
            }
        }
        return true;
    }

    private boolean mkIndexFile(File rootPath) {
        File indexFile = new File(rootPath, "index.md");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(indexFile));
            writer.write("titre: La première page de mon site !\nauteur: Bilbon Sacquet\ndate: 2021-03-23" +
                    "\ntags: [anneau, précieux]\n---" +
                    "\nLe contenu de ma première page");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {}
            }
        }
        return true;
    }
}
