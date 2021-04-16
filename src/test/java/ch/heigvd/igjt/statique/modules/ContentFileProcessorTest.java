package ch.heigvd.igjt.statique.modules;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ContentFileProcessorTest {
    @Test
    public void basicFileTest() throws IOException {
        String virtualPage = "titre: Mon premier article\n" +
                "auteur: Bertil Chapuis\n" +
                "date: 2021-03-10\n" +
                "tags:\n" +
                "  - cooking\n" +
                "  - travel\n" +
                "---\n" +
                "# Mon premier article\n" +
                "## Mon sous-titre\n" +
                " This is a paragraph with a [LINK](www.google.com) inside. It also contains *italic* and **bold** text";
        InputStream is = new ByteArrayInputStream(virtualPage.getBytes());
        String result = ContentFileProcessor.process(is);

        String expected = "<h1>Mon premier article</h1>\n" +
                          "<h2>Mon sous-titre</h2>\n" +
                          "<p>This is a paragraph with a <a href=\"www.google.com\">LINK</a> inside. " +
                          "It also contains <em>italic</em> and <strong>bold</strong> text</p>\n";


        assertEquals(expected, result);
    }
}
