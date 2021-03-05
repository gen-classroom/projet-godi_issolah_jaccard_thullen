package com.mycompany.app;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gen", mixinStandardHelpOptions = true, version = "gen 1.0", description = "Projet de génie logiciel", subcommands = {})
class Main implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception {
        return null;
    }

    public static void main( String[] args )
    {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
