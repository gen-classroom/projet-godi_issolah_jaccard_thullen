package ch.heigvd.igjt.statique.data;

/**
 * A context object for use with HandleBars. The class members declared here map
 * to proprieties to be injected in the final HTML document.
 */
public class ArticleContext {

    private String siteTitle;
    private String pageTitle;
    private String content;

    public ArticleContext(String siteTitle, String pageTitle, String content) {
        this.siteTitle = siteTitle;
        this.pageTitle = pageTitle;
        this.content = content;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getContent() {
        return content;
    }
}
