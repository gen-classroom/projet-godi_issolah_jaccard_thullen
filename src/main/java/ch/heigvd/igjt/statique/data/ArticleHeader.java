package ch.heigvd.igjt.statique.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A data class encapsulating metadata given in the YAML header block of an article.
 */
public class ArticleHeader {

    private String titre;
    private String auteur;
    private Date date;
    private List<String> tags = new ArrayList<>();

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

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
