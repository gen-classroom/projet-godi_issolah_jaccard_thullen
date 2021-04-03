package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.modules.DirTreeProcessor;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "build")
public class SubCommandBuild implements Callable<Integer> {

    @CommandLine.Parameters(index="0") String path;
    @Override
    public Integer call() throws Exception {

        DirTreeProcessor dirTreeProcessor = new DirTreeProcessor(path);

        dirTreeProcessor.build();

        return 1;
    }
}
