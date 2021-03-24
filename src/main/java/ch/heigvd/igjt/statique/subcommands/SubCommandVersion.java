package ch.heigvd.igjt.statique.subcommands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import picocli.CommandLine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "version")
public class SubCommandVersion implements Callable<Integer>{
    String version = "";

    @Override
    public Integer call() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(new File("pom.xml"));

        final Element racine = document.getDocumentElement();
        final NodeList options = racine.getChildNodes();
        final int nbOptions = options.getLength();

        for (int i = 0; i < nbOptions; i++) {
            if (options.item(i).getNodeType() == Node.ELEMENT_NODE) {
                final Element option = (Element) options.item(i);

                if(option.getTagName().equals("version")){
                    version = option.getTextContent();
                    break;
                }
            }
        }
        System.out.println("Version " + version);
        return null;
    }
}
