# Guide utilisateur

## Installation

### Prérequis

## Lancement 

Vous pouvez lancer l'application de deux manières différentes :

### Lancement en ligne de commande

Vous pouvez lancer l'application via un terminal, de cette façon vous pourrez entrer jusque 3 paramètres, 
-c pour la capacité de la baignoire
-r pour le débit du robinet
-f pour le débit de la fuite

Aucun de ces paramètres n'est obligatoire, si des paramètres ne sont pas entrés, des valeurs par défaut seront appliquées :

Certaines règles sont à respecter : 
> Le débit du robinet doit être supérieur à celui de la fuite 
> Aucune des valeurs ne doit être nulle
> Le débit du robinet et de la fuite doivent être inférieurs à la capacité de la baignoire
> La capacité maximale de la baignoire est de 5000
> Le débit maximal du robinet ET de la fuite est de 500

### Lancement avec fichier binaire

L'application peut être lancée simplement avec l'executable App.bat, cependant avec cette option vous ne pourrez entrer de paramètres et commencerez avec les valeurs par défaut.

## Utilisation de l'interface

### Contrôle de l'execution

Cliquez simplement sur le bouton "lancer" pour faire couler le robinet, la progressBar vous indique la progression du remplissage de la baignoire.
Vous pouvez mettre en pause le processus avec le bouton "pause", la progressBar va alors prendre une couleur rouge, notez bien que ce bouton stoppe 
le robinet ET la fuite, la baignoire ne se videra donc pas tant que la pause est effective.
Le bouton "stop" arrête le processus et vide totalement la baignoire, ce qui vous permet de recommencer le remplissage à partir de zéro en recliquant sur "lancer".

### Mise à jour des valeurs 

Vous pouvez modifier la capacité de la baignoire, ainsi que les débits du robinet et de la fuite à l'aide des trois sliders en bas de la fenêtre
Le bouton "mise à jour" vous permet d'appliquer vos modifications.

Les règles évoquées précedemment sont toujours valables, de même que vous ne pouvez pas modifiez les valeurs si le robinet est en train de couler (mais il est possible de 
le faire si il est en pause), un label s'affiche sous le bouton pour vous indiquer si vos changements ont été pris en compte ou non, et si non, pour quelle raison.

