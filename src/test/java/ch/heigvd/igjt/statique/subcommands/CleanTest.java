package ch.heigvd.igjt.statique.subcommands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import picocli.CommandLine;

/**
 * Unit test for simple App.
 */
public class CleanTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void VerifyIfTheFolderIsDelete() throws Exception {
        String rootDirectory = "mon/site/";
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

    @Test
    public void VerifyIfTheFolderDoesntExist() throws Exception {
        String rootDirectory = "mon/site/";
        new File(rootDirectory).mkdirs();

        String[] args = {rootDirectory};
        SubCommandClean params = CommandLine.populateCommand(new SubCommandClean(), args);
        if(params.call() == 0){
            assertTrue(false);
        }else{
            assertTrue(true);
        }
    }
}
