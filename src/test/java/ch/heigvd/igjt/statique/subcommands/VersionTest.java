package ch.heigvd.igjt.statique.subcommands;

import org.junit.Test;

import picocli.CommandLine;

import static org.junit.Assert.assertEquals;

public class VersionTest {
    //private Object SubCommandVersion;

    @Test
    public void shouldPrintCorrectVersion() throws Exception {
        String expected = "0.0.2-SNAPSHOT";
        SubCommandVersion params = CommandLine.populateCommand(new SubCommandVersion());
        params.call();
        String actual = params.version;
        assertEquals(expected, actual);
    }
}
