package ch.heigvd.igjt.statique.subcommands;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import picocli.CommandLine;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuildTest {

    static private String TEMP_DIR = "./target/tmp/";
    static private String SITE_PATH = TEMP_DIR + "mon/site/";

    @Test
    public void allFileAreBuild() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        String rootDirectory = SITE_PATH;
        new File(rootDirectory).mkdirs();

        String directory = rootDirectory + "test/";
        String fileA1 = rootDirectory + "test/fileA1.md";
        String fileA2 = rootDirectory + "fileA2.md";
        new File(rootDirectory).mkdirs();
        new File(directory).mkdirs();
        new File(fileA1).createNewFile();
        new File(fileA2).createNewFile();

        String[] args = {rootDirectory};
        SubCommandBuild params = CommandLine.populateCommand(new SubCommandBuild(), args);
        params.call();

        boolean test = true;

        if(!new File(rootDirectory + "build/" + rootDirectory + "test/fileA1.html").exists())
        {
            test = false;
        }
        if(!new File(rootDirectory + "build/" + rootDirectory + "fileA2.html").exists())
        {
            test = false;
        }

        assertTrue(test);
    }

    @Test
    public void VerifyIfTheFolderDoesntExist() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        String rootDirectory = SITE_PATH;

        String[] args = {rootDirectory};
        SubCommandBuild params = CommandLine.populateCommand(new SubCommandBuild(), args);
        assertEquals((int) params.call(), -1);
    }
}
