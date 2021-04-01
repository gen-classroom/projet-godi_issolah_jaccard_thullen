package ch.heigvd.igjt.statique.modules;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * @author Anthony Jaccard
 * Compiles a markdown string into a html string
 */
public class MarkdownProcessor
{
    /**
     * Returns a string that contains the HTML corresponding to the markdown given in mdString
     * @param mdString The markdown to process
     * @return a String
     *
     * Based on the github page of the commonmark library: https://github.com/commonmark/commonmark-java
     */
    static String compileToHtml(String mdString)
    {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(mdString);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
