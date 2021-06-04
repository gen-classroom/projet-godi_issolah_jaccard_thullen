package ch.heigvd.igjt.statique.subcommands;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static ch.heigvd.igjt.statique.subcommands.TestUtils.*;
import static org.junit.Assert.assertTrue;

public class InitTest {

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
