package ch.heigvd.igjt.statique.subcommands;

import picocli.CommandLine;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "clean")
public class SubCommandClean implements Callable<Integer> {


    @CommandLine.Parameters(index="0") String path;

    /**
     * Delete the build folder
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("Cleaning site at '" + path + "'...");
        File pathFile = new File(path+ "/build");
        if(!pathFile.exists()) {
            System.out.println("Could not find directory tree.");
            return -1;
        }
        delete(pathFile);
        return 0;
    }

    /**
     * Delete the file recursively
     * @param pathFile
     */
    void delete(File pathFile)
    {
        File[] files = pathFile.listFiles();
        if(files != null)
        {
            for(final File file : files)
            {
                delete(file);
            }
        }
        pathFile.delete();
    }
}
