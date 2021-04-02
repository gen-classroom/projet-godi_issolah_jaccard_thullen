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

    static String process(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String output = "", metaDatas = "", pageData = "";
        boolean metaSection = true;
        String line;

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

        // YAML ignored for now
        output += MarkdownProcessor.compileToHtml(pageData);
        return output;
    }
}
