# Manuel utilisateur

## Création du site

### Initialisation de l'application

Lors de la première utilisation de l'application pour la création d'un site, il faut initialiser la structure du générateur.

**Commande**:

`java -jar nomProgramme init chemin_vers_dossier_final`



Cette commande va créer les fichiers nécessaires:

- `config.yml`

  Ce fichier contient les données de configuration du site

  ````
  domaine:www.mon-site.com
  titre:"Mon site"
  ````

  

- `index.md`

  Ce fichier est un modèle pour la création des pages de site statique.

  Il est séparé en deux par la ligne: `---`

  La partie supérieure contient les données du header de la page, et la partie inférieure le contenu de la page web.

  ````
  Titre: La première page de mon site !
  Auteur: Bilbon Sacquet
  Date: 2021-03-23
  Tags: anneau, précieux
  ---
  Le contenu de ma première page.
  ````



### Création du template

Pour l'utilisation du template, il faut créer un dossier `template` ainsi que les fichiers `menu.html` et  `layout.html` en respectant l'arborescence suivante:

````
template/
   menu.html
   layout.html
index.md
config.yaml
````



Example du fichier `layout.html`

Les valeurs entre `{}` sont des variables, qui reprennent les données des fichiers `.md` .

La variable `{{> menu}}` insert le contenu du fichier `menu.html` à l'emplacement de cette dernière. Il est possible d'ajouter n'importe quel fichier `html` de cette manière, en respectant les balises ainsi que le nom du fichier sans son extension.

````
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>{{siteTitle}} | {{pageTitle}}</title>
    </head>
<body>
{{> menu}}
{{{content}}}
</body>
</html>
````



Example du fichier `menu.html`

````html
<ul>
    <li><a href="index.html">home</a></li>
    <li><a href="/content/page.html">page</a></li>
</ul>
````



### Création de pages

Il faut créer un fichier `.md` par page du site web selon le modèle index.md.

Il est possible d'utiliser toutes les balises markdown pour le contenu du site. Elles seront remplacées par leur équivalent `html` (lien, liste...).

Attention à respecter le chemin des éventuelles images dans les liens du fichier `.md`.



### Compilation du site

`java -jar nomProgramme build chemin_vers_dossier_final `

Cette commande va créer un dossier `build` contenant le site final.



## Affichage de la version

il est possible d'afficher la version du programme.

`java -jar nomProgramme version`



## Nettoyage du projet

Il est possible de supprimer le dossier `build` et tout son contenu.

`java -jar nomProgramme clean`



## Affichage du site

Il est possible de créer un serveur http en local pour pouvoir afficher son site dans un navigateur.

`java -jar nomProgramme serve chemin_dossier_build`

La valeur par défaut du chemin vers le dossier build est : `.`

Url du serveur local: `localhost:8080`





