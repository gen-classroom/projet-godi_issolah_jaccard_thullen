package ch.heigvd.igjt.statique.subcommands;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

import static org.junit.Assert.assertTrue;

public class InitTest {

    static private String TEMP_DIR = "./target/tmp/";
    static private String TEST_DIR = TEMP_DIR + "test_site/";
    static private String SITE_PATH = TEST_DIR + "mon/site/";

    @Test
    public void shouldCreateCorrectDirectoryTree() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File newSite = new File(SITE_PATH);
        assertTrue(Files.exists(newSite.toPath()));
    }

    @Test
    public void shouldCreateConfigFile() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File configFile = new File(SITE_PATH + "config.yaml");
        assertTrue(Files.exists(configFile.toPath()));
    }

    @Test
    public void configFileContentsShouldBeCorrect() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
        File expectedConfigFile = new File(TEMP_DIR + "config.expected.yaml");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(expectedConfigFile));
        writer.write("titre: Mon site\ndomaine: mon-site.ch\ndescription: Ici c'est mon site!\n");
        writer.close();
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File configFile = new File(SITE_PATH + "config.yaml");
        assertTrue(FileUtils.contentEquals(expectedConfigFile, configFile));
    }

    @Test
    public void shouldCreateIndexFile() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File indexFile = new File(SITE_PATH + "index.md");
        assertTrue(Files.exists(indexFile.toPath()));
    }

    @Test
    public void indexFileContentsShouldBeCorrect() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        new File(TEST_DIR).mkdirs();
        File expectedIndexFile = new File(TEMP_DIR + "index.expected.md");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(expectedIndexFile));
        writer.write("titre: La première page de mon site !\nauteur: Bilbon Sacquet\ndate: 2021-03-23" +
                "\ntags: [anneau, précieux]\n---" +
                "\nLe contenu de ma première page");
        writer.close();
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File indexFile = new File(SITE_PATH + "index.md");
        assertTrue(FileUtils.contentEquals(expectedIndexFile, indexFile));
    }
}
