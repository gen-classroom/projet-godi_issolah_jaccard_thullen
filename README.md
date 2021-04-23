# HEIG-VD - Génie Logiciel 2021

Basile Thullen, Maude Issolah, Anthony Jaccard, Matthieu Godi

Format de fichier pour la configuration du site : YAML

Format de fichier pour le contenu : markdown

**[Lien vers le rapport](https://github.com/gen-classroom/rapport-godi_issolah_jaccard_vison)**

## Structure d'une page

Les métadonnées avant le séparateur `---` sont saisies au format YAML.

Les métadonnées disponibles sont :

`titre` : le titre de la page

`auteur` : l'auteur de la page

`date` : la date de publication de la page

`tags` : une liste de tags associés à cette page

Le contenu est saisi au format markdown.

Exemple :

```
titre: Mon premier article
auteur: Bertil Chapuis
date: 2021-03-10
tags:
  - cooking
  - travel
---
# Mon premier article
## Mon sous-titre
Le contenu de mon article.
![Une image](./image.png)
```

## Workflow du Template Engine

![](pics/Template_workflow.png)

## Diagramme de classe & diagramme de séquence

![](pics/class_diagram.png)

## Installation

### Création de l'exécutable

Lancer "mvn clean package"

### Exécution

lancer avec "java -jar nomProgramme"
