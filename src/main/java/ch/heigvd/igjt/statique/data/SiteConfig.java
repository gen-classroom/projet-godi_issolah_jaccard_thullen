package ch.heigvd.igjt.statique.data;

/**
 * A data class encapsulating metadata for the whole site, and given in the config.yaml file.
 */
public class SiteConfig {

    private String titre;
    private String domaine;
    private String description;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getDescription() {
        return description;
    }

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
