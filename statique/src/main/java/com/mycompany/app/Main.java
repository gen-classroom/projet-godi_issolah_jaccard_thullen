package com.mycompany.app;

import com.mycompany.app.subcommands.SubCommandBuild;
import com.mycompany.app.subcommands.SubCommandClean;
import com.mycompany.app.subcommands.SubCommandNew;
import com.mycompany.app.subcommands.SubCommandServe;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "statique", mixinStandardHelpOptions = true, version = "gen 1.0", description = "Projet de génie logiciel", subcommands = {SubCommandClean.class, SubCommandBuild.class, SubCommandServe.class, SubCommandNew.class})

class Main implements Callable<Integer>
{

    public static void main( String[] args )
    {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
