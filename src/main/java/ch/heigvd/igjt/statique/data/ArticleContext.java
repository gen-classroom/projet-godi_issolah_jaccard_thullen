package ch.heigvd.igjt.statique.data;

/**
 * A context object for use with HandleBars. The class members declared here map
 * to proprieties to be injected in the final HTML document.
 *
 * @author Basile Thullen
 */
public class ArticleContext {

    private String siteTitle;
    private String pageTitle;
    private String content;

    /**
     * The constructor to the ArticleContext class
     * @param siteTitle the title of the site
     * @param pageTitle the title of the page
     * @param content the HTML-formatted content of the page
     */
    public ArticleContext(String siteTitle, String pageTitle, String content) {
        this.siteTitle = siteTitle;
        this.pageTitle = pageTitle;
        this.content = content;
    }

    /**
     * Returns the site title for this ArticleContext instance
     * @return the site title for this ArticleContext instance
     */
    public String getSiteTitle() {
        return siteTitle;
    }

    /**
     * Returns the page title for this ArticleContext instance
     * @return the page title for this ArticleContext instance
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * Returns the HTML-formatted content for this page
     * @return the HTML-formatted content for this page
     */
    public String getContent() {
        return content;
    }
}
