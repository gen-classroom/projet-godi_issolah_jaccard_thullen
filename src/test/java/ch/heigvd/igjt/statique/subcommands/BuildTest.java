package ch.heigvd.igjt.statique.subcommands;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuildTest {

    static private String TEMP_DIR = "./target/tmp/";
    static private String SITE_PATH = TEMP_DIR + "mon/site/";

    @Test
    public void allFileAreBuild() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        String rootDirectory = SITE_PATH;
        new File(rootDirectory).mkdirs();


        String index = rootDirectory + "index.md";
        String config = rootDirectory + "config.yaml";
        String content = rootDirectory + "content/";
        String page = rootDirectory + "content/page.md";
        String image = rootDirectory + "content/image.png";
        String template = rootDirectory + "template/";
        String menu = rootDirectory + "template/menu.html";
        String layout = rootDirectory + "template/layout.html";

        new File(rootDirectory).mkdirs();
        new File(content).mkdirs();
        new File(template).mkdirs();

        new File(image).createNewFile();

        {
            File pageFile = new File(page);
            pageFile.createNewFile();
            FileOutputStream Output = new FileOutputStream(pageFile);
            Output.write(("titre: Mon premier article\n" +
                    "auteur: Bertil Chapuis\n" +
                    "date: 2021-03-10\n" +
                    "---\n" +
                    "# Mon titre\n" +
                    "## Mon sous-titre\n" +
                    "Le contenu de mon article.\n" +
                    "![Une image](./image.png)").getBytes());
            Output.close();
        }

        {
            File indexFile = new File(index);
            indexFile.createNewFile();
            FileOutputStream Output = new FileOutputStream(indexFile);
            Output.write(("titre: Index\n" +
                            "auteur: Bertil Chapuis\n" +
                            "date: 2021-03-10\n" +
                            "---\n" +
                            "# Mon titre\n" +
                            "## Mon sous-titre").getBytes());
            Output.close();
        }

        {
            File configFile = new File(config);
            configFile.createNewFile();
            FileOutputStream Output = new FileOutputStream(configFile);
            Output.write(("domaine: www.mon-site.com\n" +
                    "titre: \"Mon site\"").getBytes());
            Output.close();
        }

        {
            File menuFile = new File(menu);
            menuFile.createNewFile();
            FileOutputStream Output = new FileOutputStream(menuFile);
            Output.write(("<ul>\n" +
                    " <li><a href=\"/index.html\">home</a></li>\n" +
                    " <li><a href=\"/content/page.html\">page</a></li>\n" +
                    "</ul>").getBytes());
            Output.close();
        }

        {
            File layoutFile = new File(layout);
            layoutFile.createNewFile();
            FileOutputStream Output = new FileOutputStream(layoutFile);
            Output.write(("<html lang=\"en\">\n" +
                    "<head>\n" +
                    " <meta charset=\"utf-8\">\n" +
                    " <title>{{ siteTitle }} | {{ pageTitle }}</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    " {{> menu}}\n" +
                    " {{{ content }}}\n" +
                    "</body>\n" +
                    "</html>").getBytes());
            Output.close();
        }

        String[] args = {rootDirectory};
        SubCommandBuild params = CommandLine.populateCommand(new SubCommandBuild(), args);
        params.call();

        boolean test = true;

        if(!new File(rootDirectory + "build/" + rootDirectory + "test/fileA1.html").exists())
        {
            test = false;
        }
        if(!new File(rootDirectory + "build/" + rootDirectory + "fileA2.html").exists())
        {
            test = false;
        }

        assertTrue(test);
    }

    @Test
    public void VerifyIfTheFolderDoesntExist() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIR));
        String rootDirectory = SITE_PATH;

        String[] args = {rootDirectory};
        SubCommandBuild params = CommandLine.populateCommand(new SubCommandBuild(), args);
        assertEquals((int) params.call(), -1);
    }
}
