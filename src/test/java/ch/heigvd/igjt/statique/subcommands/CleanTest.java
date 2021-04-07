package ch.heigvd.igjt.statique.subcommands;

import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import picocli.CommandLine;

/**
 * Unit test for simple App.
 */
public class CleanTest
{
    static private String TEMP_DIR = "./target/tmp/";
    static private String SITE_PATH = TEMP_DIR + "mon/site/";

    @Test
    public void VerifyIfTheFolderDoesntExist() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        String rootDirectory = SITE_PATH;
        new File(rootDirectory).mkdirs();

        String[] args = {rootDirectory};
        SubCommandClean params = CommandLine.populateCommand(new SubCommandClean(), args);
        assertTrue(params.call() == -1);
    }


    @Test
    public void VerifyIfTheFolderIsDelete() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        String rootDirectory = SITE_PATH;
        new File(rootDirectory).mkdirs();

        String dirBuild = rootDirectory + "/build";
        String fileA1 = dirBuild + "/fileA1.txt";
        String fileA2 = dirBuild + "/fileA2.txt";
        new File(dirBuild).mkdirs();
        new File(fileA1).createNewFile();
        new File(fileA2).createNewFile();

        String[] args = {rootDirectory};
        SubCommandClean params = CommandLine.populateCommand(new SubCommandClean(), args);
        params.call();

        boolean test = new File(dirBuild).exists();

        assertTrue(!test);
    }


}
