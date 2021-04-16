package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import picocli.CommandLine;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SiteConfig siteConfig = new SiteConfig();
        siteConfig.setTitre("Mon site");
        siteConfig.setDomaine("mon-site.ch");
        siteConfig.setDescription("Ici c'est mon site!");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(configFile))) {
            Yaml yaml = new Yaml();
            writer.write(yaml.dumpAs(siteConfig, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean mkIndexFile(File rootPath) {
        File indexFile = new File(rootPath, "index.md");
        ArticleHeader header = new ArticleHeader();
        header.setTitre("La première page de mon site !");
        header.setAuteur("Bilbon Sacquet");
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
            header.setDate(dateParser.parse("2021-03-23"));
        } catch (ParseException e) {
            System.out.println("Could not parse date: " + e.getMessage());
        }
        header.getTags().add("anneau");
        header.getTags().add("précieux");
        OutputStreamWriter writer = null;
        try {
            Yaml yaml = new Yaml();
            writer = new FileWriter(indexFile);
            writer.write(yaml.dumpAs(header, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
            writer.write("---" +
                    "\nLe contenu de ma première page\n");
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
