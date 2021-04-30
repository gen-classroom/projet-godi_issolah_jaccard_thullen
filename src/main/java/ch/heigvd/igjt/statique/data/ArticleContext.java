package ch.heigvd.igjt.statique.data;

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

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
