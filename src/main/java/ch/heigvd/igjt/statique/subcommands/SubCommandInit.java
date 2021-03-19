package ch.heigvd.igjt.statique.subcommands;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "init")
public class SubCommandInit implements Callable<Integer> {

    @CommandLine.Parameters(index="0") String path;
    @Override
    public Integer call() throws Exception {
        new File(path).mkdirs();
        return null;
    }
}
