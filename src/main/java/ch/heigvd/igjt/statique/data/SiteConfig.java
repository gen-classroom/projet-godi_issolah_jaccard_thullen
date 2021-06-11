package ch.heigvd.igjt.statique.data;

/**
 * A data class encapsulating metadata for the whole site, and given in the config.yaml file.
 *
 * @author Basile Thullen
 */
public class SiteConfig {

    private String titre;
    private String domaine;
    private String description;

    /**
     * Returns the title for this SiteConfig instance
     * @return the title for this SiteConfig instance
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Set the title for this SiteConfig instance
     * @param titre the new title for this SiteConfig instance
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Returns the domain name for this SiteConfig instance
     * @return the domain name for this SiteConfig instance
     */
    public String getDomaine() {
        return domaine;
    }

    /**
     * Sets the domain name for this SiteConfig instance
     * @param domaine the new domain name for this SiteConfig instance
     */
    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    /**
     * Returns the description for this SiteConfig instance
     * @return the description for this SiteConfig instance
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this SiteConfig instance
     * @param description the new description for this SiteConfig instance
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre : ").append(titre).append("\n");
        sb.append("Domaine : ").append(domaine).append("\n");
        sb.append("Description : ").append(description).append("\n");
        return sb.toString();
    }
}
