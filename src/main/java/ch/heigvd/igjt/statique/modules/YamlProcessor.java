package ch.heigvd.igjt.statique.modules;

import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

/**
 * @author Basile Thullen
 * Parse a yaml file or string into a SiteConfig or ArticleHeader object
 */
public class YamlProcessor {

    private Yaml yaml;
    String yamlStr = "";

    /**
     * A constructor for the YamlProcessor class
     * @param yamlStr a yaml-formatted string to process
     */
    public YamlProcessor(String yamlStr) {
        this.yamlStr = yamlStr;
    }

    /**
     * A constructor for the YamlProcessor class
     * @param yamlF a file containing a yaml-formatted data to process
     */
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

    /**
     * Parse the yaml data given to the constructor into a SiteConfig object
     * @return the SiteConfig object for this instance's yaml data
     */
    public SiteConfig parseSiteConfig() {
        yaml = new Yaml(new Constructor(SiteConfig.class));
        return yaml.load(yamlStr);
    }

    /**
     * Parse the yaml data given to the constructor into a ArticleHeader object
     * @return the ArticleHeader object for this instance's yaml data
     */
    public ArticleHeader parseArticleHeader() {
        yaml = new Yaml(new Constructor(ArticleHeader.class));
        String[] yamlSplit = yamlStr.split("---");
        if (yamlSplit.length > 1) {
            yamlStr = yamlSplit[0];
        }
        return yaml.load(yamlStr);
    }
}
