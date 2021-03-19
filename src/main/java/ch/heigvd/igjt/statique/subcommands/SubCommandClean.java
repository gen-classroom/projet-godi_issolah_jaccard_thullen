package ch.heigvd.igjt.statique.subcommands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "clean")
public class SubCommandClean implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return null;
    }
}
