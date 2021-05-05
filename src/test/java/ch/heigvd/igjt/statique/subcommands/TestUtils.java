package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import picocli.CommandLine;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    static public String TEMP_DIR = "./target/tmp/";
    static public String TEST_DIR = TEMP_DIR + "test_site/";
    static public String SITE_PATH = TEST_DIR + "mon/site/";
    static public String TEMPLATE_PATH = SITE_PATH + "template/";

    public static void resetTempDir() throws IOException {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
    }

    public static void callInit() {
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
    }

    public static void initTemplate() throws IOException {
        new File(TEMPLATE_PATH).mkdirs();
        File layout = new File(TEMPLATE_PATH + "layout.html");
        FileWriter writer = new FileWriter(layout);
        writer.write(getLayoutString());
        writer.close();
        File menu = new File(TEMPLATE_PATH + "menu.html");
        writer = new FileWriter(menu);
        writer.write(getMenuString());
        writer.close();
    }

    public static File writeExpectedSiteConfig() throws IOException {
        File expectedConfigFile = new File(TEMP_DIR + "config.expected.yaml");
        FileWriter writer = new FileWriter(expectedConfigFile);
        SiteConfig siteConfig = getExpectedSiteConfig();
        Yaml yaml = new Yaml();
        writer.write(yaml.dumpAs(siteConfig, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
        writer.close();
        return expectedConfigFile;
    }

    public static SiteConfig getExpectedSiteConfig() {
        SiteConfig siteConfig = new SiteConfig();
        siteConfig.setTitre("Mon site");
        siteConfig.setDomaine("mon-site.ch");
        siteConfig.setDescription("Ici c'est mon site!");
        return siteConfig;
    }

    public static File writeExpectedIndexFile() throws IOException {
        File expectedIndexFile = new File(TEMP_DIR + "index.article.md");
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(expectedIndexFile);
        ArticleHeader header = getExpectedIndexHeader();
        writer.write(yaml.dumpAs(header, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
        writer.write("---\n" + getExpectedIndexFileContent());
        writer.close();
        return expectedIndexFile;
    }

    public static ArticleHeader getExpectedIndexHeader() {
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
        return header;
    }

    public static String getExpectedIndexFileContent() throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource("article.md").getFile());
        List<String> lines = Files.readAllLines(file.toPath());
        return String.join(System.lineSeparator(), lines);
    }

    public static String getLayoutString() throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource("layout.html").getFile());
        List<String> lines = Files.readAllLines(file.toPath());
        return String.join(System.lineSeparator(), lines);
    }

    public static String getMenuString() throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource("menu.html").getFile());
        List<String> lines = Files.readAllLines(file.toPath());
        return String.join(System.lineSeparator(), lines);
    }

    public static String getExpectedFinalDocument() throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource("article.expected.html").getFile());
        List<String> lines = Files.readAllLines(file.toPath());
        return String.join(System.lineSeparator(), lines);
    }
}
