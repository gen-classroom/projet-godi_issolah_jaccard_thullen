package ch.heigvd.igjt.statique.subcommands;

import org.junit.Test;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

import static org.junit.Assert.assertTrue;

public class InitTest {

    static private String SITE_DIR = "./target/tmp/test_site/";

    @Test
    public void shouldCreateCorrectDirectoryTree() throws Exception {
        FileUtils.deleteDirectory(new File(SITE_DIR));
        new File(SITE_DIR).mkdir();
        File config = new File(SITE_DIR);
        String[] args = {SITE_DIR + "mon/site"};
        SubCommandInit params = CommandLine.populateCommand(new SubCommandInit(), args);
        params.call();
        File newSite = new File(SITE_DIR + "mon/site");
        assertTrue(Files.exists(newSite.toPath()));
    }
}
