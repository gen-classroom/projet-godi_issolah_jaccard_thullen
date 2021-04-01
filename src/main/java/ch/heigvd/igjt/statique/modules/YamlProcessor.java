package ch.heigvd.igjt.statique.modules;

/*
* Task
*   Take a YAML string or YAML-formatted file and uses SnakeYAML to display it in a human readable format
 */

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class YamlProcessor {

    private Yaml yaml = new Yaml();
    String yamlStr;

    public YamlProcessor(String yamlStr) {
        this.yamlStr = yamlStr;
    }

    public YamlProcessor(File yamlF) {
        try {
            String tmpYamlStr;
            BufferedReader reader = new BufferedReader(new FileReader(yamlF));
            while ((tmpYamlStr = reader.readLine()) != null) {
                yamlStr += tmpYamlStr;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find YAML document at " + yamlF.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error while reading YAML document at " + yamlF.getAbsolutePath());
        }
    }

    public SiteConfig parseSiteConfig() {
        return yaml.load(yamlStr);
    }

    public ArticleHeader parseArticleHeader() {
        return yaml.load(yamlStr);
    }
}
