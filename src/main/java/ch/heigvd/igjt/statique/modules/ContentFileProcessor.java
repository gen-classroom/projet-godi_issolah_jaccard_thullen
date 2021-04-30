package ch.heigvd.igjt.statique.modules;

/*
 * Tasks
 *   Opens a given content .md file
 *   Separates the YAML and Markdown parts
 *   Uses YamlProcessor to process the YAML part
 *   Uses MarkdownProcessor to build the markdown into HTML
 *   Depending on interaction with DirTreeProcessor, returns the HTML as a string or dumps it into a html file
 */


import java.io.*;

public class ContentFileProcessor {
    private String htmlContent;
    private String articleHeader;

    public void process(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        boolean metaSection = true;
        String line;

        String metaDatas = "";
        String pageData = "";

        while((line = reader.readLine()) != null){
            if (line.equals("---"))
                metaSection = false;
            else {
                if (!metaSection)
                    pageData += line + "\n";
                else
                    metaDatas += line+ "\n";
            }
        }

        // Yaml header to html
        YamlProcessor yaml = new YamlProcessor(metaDatas);
        yaml.parseArticleHeader();
        // Markdown content to html
        htmlContent = MarkdownProcessor.compileToHtml(pageData);
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public String getArticleHeader() {
        return articleHeader;
    }
}
