package com.mycompany.app.subcommands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "new")
public class SubCommandNew implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return null;
    }
}
