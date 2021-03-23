package ch.heigvd.igjt.statique.subcommands;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

import static org.junit.Assert.assertTrue;

public class InitTest {

    static private String TEST_DIR = "./target/tmp/test_site/";
    static private String SITE_PATH = TEST_DIR + "mon/site";

    @Test
    public void shouldCreateCorrectDirectoryTree() throws Exception {
        FileUtils.deleteDirectory(new File(TEST_DIR));
        new File(TEST_DIR).mkdir();
        String[] args = {SITE_PATH};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File newSite = new File(SITE_PATH);
        assertTrue(Files.exists(newSite.toPath()));
    }

    @Test
    public void shouldCreateCondfigFile() throws IOException {
        FileUtils.deleteDirectory(new File(TEST_DIR));
    }
}
