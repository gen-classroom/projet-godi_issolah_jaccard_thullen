package ch.heigvd.igjt.statique.subcommands;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import ch.heigvd.igjt.statique.modules.ContentFileProcessor;
import ch.heigvd.igjt.statique.modules.FileWatcher;
import ch.heigvd.igjt.statique.modules.SiteBuilder;
import ch.heigvd.igjt.statique.modules.TemplateEngine;
import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "build")
public class SubCommandBuild implements Callable<Integer> {

    @CommandLine.Parameters(index="0") String path;
    @CommandLine.Option(names = "--watch", usageHelp = true, description = "Auto-rebuild when files are changed") boolean autoRebuild;
    @Override
    public Integer call() throws Exception {
        System.out.println("Building site at '" + path + "'...");
        File sourceFile = new File(path);
        if(!sourceFile.exists()) {
            System.out.println("Could not find directory tree.");
            return -1;
        }

        SiteBuilder.buildAll(sourceFile);
        if (autoRebuild) {
            FileWatcher fw = new FileWatcher(path);
            System.out.println("Watchdog initialized for path " + path + "\nPress Enter to terminate...");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            System.out.println("Watchdog terminated\n");
        }

        return 1;
    }
}
