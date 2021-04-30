package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        File expectedIndexFile = new File(TEMP_DIR + "index.expected.md");
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

    public static String getExpectedIndexFileContent() {
        String content = "Le contenu de ma première page de mon site !";
        return content;
    }

    public static String getLayoutString() {
        String layout = "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                        "<title>{{siteTitle}} | {{pageTitle}}</title>" +
                "</head>" +
                "<body>" +
                    "{{> menu}}" +
                    "{{content }}" +
                "</body>" +
                "</html>";
        return layout;
    }

    public static String getMenuString() {
        String menu = "<ul>" +
                "<li><a href=\"/index.html\">home</a></li>" +
                "<li><a href=\"/content/page.html\">page</a></li>" +
                "</ul>";
        return menu;
    }
}
