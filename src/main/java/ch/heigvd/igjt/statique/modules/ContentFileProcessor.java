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
        String output = "", yaml = "", md = "";
        boolean yamlPart = true;
        String line;

        while((line = reader.readLine()) != null){
            if (line.equals("---"))
                yamlPart = false;
            else {
                if (!yamlPart)
                    md += line;
                else
                    yaml += line;
            }
        }
        
        // output += YamlProcessor.
        output += MarkdownProcessor.compileToHtml(md);
        return output;
    }
}
