# CONCEPTION



## Modifications Personnelles

Nous avons fait le choix de modifier ceci :
```
- Nous utilisons une sous-classe de Item, Heart, pouvant régénérer les points de vie de notre personnage.
- Nous créons une classe Boss Final, qui se déplace aléatoirement dans la salle (qui choisit une orientation vers laquelle se déplacer tout les deltaTime) en lançant des boules d'eau dans les 4 directions.
- Nous avons animé tous nos Sprites à l'aide de switchs et de la classe animations fournies au préalable. Ceci permet de les rendre plus réalistes et agréables.
- Nous affichons en bas à gauche de l'écran 3 coeurs représentant nos points de vie. Tout ceci est fait dans notre classe Life point, chargée de modéliser les points de vie des ennemis et du personnage.
- Nous avons choisis que les Level0TurretRoom peuvent contenir entre 1 et 3 Turret.
- Nous avons fait en sorte que les Turret bloquent les flèches reçues par les autres Turret afin d'éviter toute sorte de double dégâts non désidés.
```

# Classes et interfaces


## Classe LifePoint

Elle permet de gérer les points de vie de notre personnage principal et de nos ennemies. Elle implémente Logic et Graphics pour pouvoir faire usage de la méthode isOn et afficher les points de vie du personnage en haut à gauche de l'écran.

## Classe BossLifePoint

Elle hérite de la classe précédente et se comporte de manière analogue à celle-ci pour modéliser la vie du boss final.

***

## Classe Boss

Elle hérite de la classe Enemy. Sa méthode update lui permet de se déplacer aléatoirement dans la salle: cela est en réutilisant une ébauche de ce que nous avions fait dans la génération aléatoire de niveau.

***

## Classe Heart

Elle hérite de Item et implémente AreaInteractionVisitor. Elle permet d'incrémenter les points de vie du personnage. Nous nous sommes inspirés de la classe Cherry pour en modéliser les interactions.

***

## Classe Water

Elle hérite de projectile et implémente ICRogueInteractionHandler. Elle permet de modéliser les sorts lancés par le Boss final. Nous nous sommes inspirés de la structure de la boule de feu pour en gérer les interactions.

***

## Classe Level0BossRoom

Elle hérite de Level0EnemyRoom. Elle utilise la méthode "addEnemy" de sa super classe pour pouvoir les générer dans leur salle associée. Nous nous sommes inspirés de la structure de Level0TurretRoom.

***

## Classe Level0HeartRoom

Elle hérite de Level0ItemRoom. Elle utilise la méthode "addItem" de sa super classe pour pouvoir les générer dans leur salle associée. Nous nous sommes inspirés de la structure de Level0ItemRoom.

***

## Classe GameOver

Elle implémente Graphics et Logic afin de pouvoir afficher l'écran de fin "Game Over" lorsque les points de vie du personnage son réduit à 0 (grâce à la méthode isOn). 

***

## Classe Win

Elle implémente Graphics et Logic afin de pouvoir afficher l'écran de fin "You Win" lorsque le Boss Final est vaincu (grâce à la méthode isOn).

***
