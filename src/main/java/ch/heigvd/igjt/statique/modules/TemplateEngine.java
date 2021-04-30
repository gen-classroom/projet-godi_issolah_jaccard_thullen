package ch.heigvd.igjt.statique.modules;

import ch.heigvd.igjt.statique.data.ArticleContext;
import ch.heigvd.igjt.statique.data.ArticleHeader;
import ch.heigvd.igjt.statique.data.SiteConfig;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateEngine {

    private SiteConfig siteConfig;
    private String layout;
    private Handlebars handlebars;
    private TemplateLoader templateLoader;
    private String templatePath;

    public TemplateEngine(SiteConfig siteConfig, String layout, String templatePath){
        this.siteConfig = siteConfig;
        this.layout = layout;
        this.templateLoader = new FileTemplateLoader(templatePath, ".html");
        this.handlebars = new Handlebars(templateLoader);
        this.handlebars.setPrettyPrint(true);
        this.templatePath = templatePath;
    }

    public String build(String content, ArticleHeader header) throws IOException {
        Template layoutTemplate = handlebars.compile("layout");
        ArticleContext data = new ArticleContext(siteConfig.getTitre(), header.getTitre(), content);
        String result = layoutTemplate.apply(data);
        return result;
    }

}
