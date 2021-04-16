package ch.heigvd.igjt.statique.modules;

/*
* Task
*   Take a YAML string or YAML-formatted file and uses SnakeYAML to display it in a human readable format
 */

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class YamlProcessor {

    private Yaml yaml;
    String yamlStr = "";

    public YamlProcessor(String yamlStr) {
        this.yamlStr = yamlStr;
    }

    public YamlProcessor(File yamlF) {
        try {
            String tmpYamlStr;
            BufferedReader reader = new BufferedReader(new FileReader(yamlF));
            StringBuilder yamlStrSB = new StringBuilder();
            while ((tmpYamlStr = reader.readLine()) != null) {
                yamlStrSB.append(tmpYamlStr).append(System.lineSeparator());
            }
            yamlStr = yamlStrSB.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find YAML document at " + yamlF.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error while reading YAML document at " + yamlF.getAbsolutePath());
        }
    }

    public SiteConfig parseSiteConfig() {
        yaml = new Yaml(new Constructor(SiteConfig.class));
        return yaml.load(yamlStr);
    }

    public ArticleHeader parseArticleHeader() {
        yaml = new Yaml(new Constructor(ArticleHeader.class));
        String[] yamlSplit = yamlStr.split("---");
        if (yamlSplit.length > 1) {
            yamlStr = yamlSplit[0];
        }
        return yaml.load(yamlStr);
    }
}
