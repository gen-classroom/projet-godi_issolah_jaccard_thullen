package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import picocli.CommandLine;

import static org.junit.Assert.assertTrue;

public class InitTest {

    static private String TEMP_DIR = "./target/tmp/";
    static private String TEST_DIR = TEMP_DIR + "test_site/";
    static private String SITE_PATH = TEST_DIR + "mon/site/";

    public static void resetTempDir() throws IOException {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
    }

    public static void callInit() {
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
    }

    public static File writeExpectedSiteConfig() throws IOException {
        File expectedConfigFile = new File(TEMP_DIR + "config.expected.yaml");
        FileWriter writer = new FileWriter(expectedConfigFile);
        SiteConfig siteConfig = new SiteConfig();
        siteConfig.setTitre("Mon site");
        siteConfig.setDomaine("mon-site.ch");
        siteConfig.setDescription("Ici c'est mon site!");
        Yaml yaml = new Yaml();
        writer.write(yaml.dumpAs(siteConfig, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
        writer.close();
        return expectedConfigFile;
    }

    public static File writeExpectedIndexFile() throws IOException {
        File expectedIndexFile = new File(TEMP_DIR + "index.expected.md");
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(expectedIndexFile);
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
        writer.write(yaml.dumpAs(header, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
        writer.write("---" +
                "\nLe contenu de ma première page\n");
        writer.close();
        return expectedIndexFile;
    }

    @Test
    public void shouldCreateCorrectDirectoryTree() throws Exception {
        resetTempDir();
        callInit();
        File newSite = new File(SITE_PATH);
        assertTrue(Files.exists(newSite.toPath()));
    }

    @Test
    public void shouldCreateConfigFile() throws Exception {
        resetTempDir();
        callInit();
        File configFile = new File(SITE_PATH + "config.yaml");
        assertTrue(Files.exists(configFile.toPath()));
    }

    @Test
    public void configFileContentsShouldBeCorrect() throws Exception {
        resetTempDir();
        File expectedConfigFile =  writeExpectedSiteConfig();
        callInit();
        File configFile = new File(SITE_PATH + "config.yaml");
        assertTrue(FileUtils.contentEquals(expectedConfigFile, configFile));
    }

    @Test
    public void shouldCreateIndexFile() throws Exception {
        resetTempDir();
        callInit();
        File indexFile = new File(SITE_PATH + "index.md");
        assertTrue(Files.exists(indexFile.toPath()));
    }

    @Test
    public void indexFileContentsShouldBeCorrect() throws Exception {
        resetTempDir();
        File expectedIndexFile = writeExpectedIndexFile();
        callInit();
        File indexFile = new File(SITE_PATH + "index.md");
        assertTrue(FileUtils.contentEquals(expectedIndexFile, indexFile));
    }
}
