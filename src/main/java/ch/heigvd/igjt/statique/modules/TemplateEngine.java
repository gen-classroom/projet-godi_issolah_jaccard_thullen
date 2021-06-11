package ch.heigvd.igjt.statique.modules;

import ch.heigvd.igjt.statique.data.ArticleContext;
import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

/**
 * @author Basile Thullen
 * The TemplateEngine class produces an HTML-formatted file from a given template,
 * SiteConfig, article content and ArticleHeader.
 */
public class TemplateEngine {

    private SiteConfig siteConfig;
    private Handlebars handlebars;
    private TemplateLoader templateLoader;

    /**
     * The constructor for the TemplateEngine class
     * @param siteConfig The SiteConfig for this page (should be the same for all
     *                   pages of the same site)
     * @param templatePath The path where the template "layout.html" shall be found
     */
    public TemplateEngine(SiteConfig siteConfig, String templatePath){
        this.siteConfig = siteConfig;
        this.templateLoader = new FileTemplateLoader(templatePath, ".html");
        this.handlebars = new Handlebars(templateLoader);
        this.handlebars.setPrettyPrint(true);
    }

    /**
     * Produces an HTML-formatted document from the given content and ArticleHeader
     * @param content the HTML-formatted content for this page
     * @param header the ArticleHeader for this page
     * @return a String containing the final HTML-formatted article text
     * @throws IOException if HandleBars can't find the template
     */
    public String build(String content, ArticleHeader header) throws IOException {
        Template layoutTemplate = handlebars.compile("layout");
        ArticleContext data = new ArticleContext(siteConfig.getTitre(), header.getTitre(), content);
        String result = layoutTemplate.apply(data);
        return result;
    }

    /**
     * Returns the SiteConfig for this TemplateEngine instance
     * @return the SiteConfig object for this TemplateEngine instance
     */
    public SiteConfig getSiteConfig() {
        return this.siteConfig;
    }

}
