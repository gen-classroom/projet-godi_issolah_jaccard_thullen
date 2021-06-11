package ch.heigvd.igjt.statique.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A data class encapsulating metadata given in the YAML header block of an article.
 *
 * @author Basile Thullen
 */
public class ArticleHeader {

    private String titre;
    private String auteur;
    private Date date;
    private List<String> tags = new ArrayList<>();

    /**
     * Returns the title for this ArticleHeader instance
     * @return the title for this ArticleHeader instance
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Sets the title for this ArticleHeader instance
     * @param titre the new title for this ArticleHeader instance
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Returns the author for this ArticleHeader instance
     * @return the author for this ArticleHeader instance
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * Sets the author for this ArticleHeader instance
     * @param auteur the new author for this ArticleHeader instance
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Returns the date for this ArticleHeader instance
     * @return the date for this ArticleHeader instance
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date for this ArticleHeader instance
     * @param date the new date for this ArticleHeader instance
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns a list of tags for this ArticleHeader instance
     * @return a list of tags for this ArticleHeader instance
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets a list of tags for this ArticleHeader instance
     * @param tags the new list of tags for this ArticleHeader instance
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre : ").append(titre).append("\n")
                .append("Auteur : ").append(auteur).append("\n")
                .append("Date : ").append(date).append("\n")
                .append("Tags : ").append(String.join(", ", tags)).append("\n");
        return sb.toString();
    }
}
