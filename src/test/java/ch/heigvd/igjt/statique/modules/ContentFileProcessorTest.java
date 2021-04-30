package ch.heigvd.igjt.statique.modules;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ContentFileProcessorTest {
    @Test
    public void htmlContentTest() throws IOException {
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
        ContentFileProcessor cfp = new ContentFileProcessor();
        cfp.process(is);
        String result = cfp.getHtmlContent();

                String expected = "<h1>Mon premier article</h1>\n" +
                          "<h2>Mon sous-titre</h2>\n" +
                          "<p>This is a paragraph with a <a href=\"www.google.com\">LINK</a> inside. " +
                          "It also contains <em>italic</em> and <strong>bold</strong> text</p>\n";


        assertEquals(expected, result);
    }

    @Test
    public void yamlHeaderTest() throws IOException {
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
        ContentFileProcessor cfp = new ContentFileProcessor();
        cfp.process(is);
        String result = cfp.getArticleHeader().toString();

        String expected = "Titre : Mon premier article\n" +
            "Auteur : Bertil Chapuis\n" +
            "Date : Wed Mar 10 01:00:00 CET 2021\n" +
            "Tags : cooking, travel\n";

        assertEquals(expected, result);
    }
}

