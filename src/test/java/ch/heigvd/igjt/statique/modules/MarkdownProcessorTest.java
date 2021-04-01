package ch.heigvd.igjt.statique.modules;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MarkdownProcessorTest
{
    @Test
    public void basicTagTest()
    {
        String mdTestString = "# h1 title\n" +
                "## h2 title\n" +
                "### h3 title\n" +
                "This is a paragraph with a [LINK](www.google.com) inside. It also contains *italic* and **bold** text";
        String htmlResultString = "<h1>h1 title</h1>\n" +
                "<h2>h2 title</h2>\n" +
                "<h3>h3 title</h3>\n" +
                "<p>This is a paragraph with a <a href=\"www.google.com\">LINK</a> inside. It also contains <em>italic</em> and <strong>bold</strong> text</p>\n";
        String functionResult = MarkdownProcessor.compileToHtml(mdTestString);
        assertEquals(htmlResultString, functionResult);
    }
}
