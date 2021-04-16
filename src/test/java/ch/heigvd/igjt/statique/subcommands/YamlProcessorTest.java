package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import ch.heigvd.igjt.statique.modules.YamlProcessor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YamlProcessorTest {

    static private String TEMP_DIR = "./target/tmp/";
    static private String TEST_DIR = TEMP_DIR + "test_site/";
    static private String SITE_PATH = TEST_DIR + "mon/site/";

    @Test
    public void yamlProcessorShouldParseConfigFileCorrectly() throws IOException {
        InitTest.resetTempDir();
        InitTest.callInit();
        File expectedConfigFile = InitTest.writeExpectedSiteConfig();
        YamlProcessor processor = new YamlProcessor(expectedConfigFile);
        SiteConfig siteConfig = processor.parseSiteConfig();
        assertEquals("Mon site", siteConfig.getTitre());
        assertEquals("mon-site.ch", siteConfig.getDomaine());
        assertEquals("Ici c'est mon site!", siteConfig.getDescription());
    }

    @Test
    public void yamlProcessorShouldParseIndexFileCorrectly() throws IOException, ParseException {
        InitTest.resetTempDir();
        InitTest.callInit();
        File expectedIndexFile = InitTest.writeExpectedIndexFile();
        YamlProcessor processor = new YamlProcessor(expectedIndexFile);
        ArticleHeader header = processor.parseArticleHeader();
        assertEquals("La première page de mon site !", header.getTitre());
        assertEquals("Bilbon Sacquet", header.getAuteur());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2021-03-23"), header.getDate());
        assertTrue(header.getTags().contains("anneau"));
        assertTrue(header.getTags().contains("précieux"));
    }
}
