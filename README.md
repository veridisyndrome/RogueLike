# CS-107 MP2



## Rogue Like

Notre jeu se base sur le concept du jeu de type Roguelike: il est donc constitué de différentes salles contenant des objets comme un bâton, des cœurs (représentant les points de vie) ou une clé, et des ennemis (comme des tourelles).

## But Du Jeu

Votre but sera celui de parcourir toutes les salles du jeu afin de récolter tous les objets et vaincre tous vos ennemis. 
Pour gagner il faudra récupérer une clé pour déverrouiller la salle dans laquelle se cache le BOSS FINAL: être bien équipé est nécessaire pour pouvoir gagner le combat.


## Les Contrôles

```
Commandes de base:
- Fléches directionnelles: Déplacement;
- W: Ramasse le bâton/ouvre la porte verrouillée si en possession de la bonne clé;
- X: Lance des boules de feu (bâton nécessaire);
- R: Génère un niveau aléatoire et relance la partie;
- Echap: Quitte le jeu.

Commandes pour développeurs:
- O: Ouvre un passage sur tous les côtés de la salle;
- T: Ouvre/ferme dans les salles non-complétées.
- L: Vérouille/dévérouille la porte de gauche.

N.B.: les objets non-cités sont ramassés au contact avec le joueur.

```

## Point De Vie

Vous commencez avec 3 points de vie, que vous perdrez si vous êtes touchés une flèche lancée par les tourelles ou un sort du boss final. Celui-ci est caractérisé par une force hors du commun, capable d'éliminer le joueur au moindre contact physique.
Il est possible de se soigner en trouvant les cœurs éparpillés dans le niveau.

***

## Bâton Et Boules De Feu

Au sein du niveau se cache un bâton particulier, capable de lancer de dangereuses boules de feu (un cool down de 1 seconde est cependant présent ;) ).

***

## Les Tourelles

Le niveau est habité par des ennemis: les tourelles. Pour les éliminer, vous pourrez faire usage de votre bâton ou opter pour une approche corps-à-corps en leur marchant dessus.
Néanmoins, si ces dernières devaient vous toucher vous perdriez 1 point de vie.

***



## Clé et Boss Final

Pour pouvoir combattre le boss final, vous devrez récupérer une clé, elle aussi cachée dans une salle aléatoire du niveau.
Le boss possède 6 points de vie: trouvez une stratégie pour réduire ce compteur à 0 et terminer le jeu!

***

