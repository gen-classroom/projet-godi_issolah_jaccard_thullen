package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.modules.DirTreeProcessor;
import ch.heigvd.igjt.statique.modules.SiteBuilder;
import picocli.CommandLine;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "build")
public class SubCommandBuild implements Callable<Integer> {

    @CommandLine.Parameters(index="0") String path;
    @CommandLine.Option(names = "--watch", usageHelp = true, description = "Auto-rebuild when files are changed") boolean autoRebuild;
    @Override
    public Integer call() throws Exception {

        SiteBuilder.buildAll(path);
        if (autoRebuild)
        {
            FileWatcher fw = new FileWatcher(path);
            System.out.println("Watchdog initialized for path " + path + "\nPress Enter to terminate...");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            System.out.println("Watchdog terminated\n");
        }

        return 0;
    }
}
