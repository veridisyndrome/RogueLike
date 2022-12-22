# CONCEPTION



## Modifications Personnelles

Nous avons fait le choix de modifier ceci :
```
-Nous utilisons un item "coeur" afin de régénérer les points de vie de notre personnage. Pour se faire nous avons créé une sous-classe "Heart" de Items afin de modéliser cette dernière.
-Nous créons une Boss Final qui se déplace aléatoirement dans une salle en lançant dans boules de feu dans les 4 directions. Pour se faire nous avons créé une classe "boss" dans ennemies, qui vont à chaque delta Time choisir une coordonnée aléatoire.
-Nous avons animé tous nos Sprites à l'aide de switchs et de la classe animations fourniets au préalable. Ceci permet de les rendre plus réalistes.
-Nous affichons en bas à gauche de l'écran 3 coeurs représentant nos points de vie. Tout ceci est fait dans notre classe Life point (chargée de modéliser les points de vie de nos ennemis et du personnage).
```

# Classes et interfaces


## Classe LifePoint

Cette dernière permet de gérer les points de vie de notre personnage principal et de nos ennemies. Cette dernière implémente Logic et Graphics car nous souhaitons utiliser la méthode isOn et afficher nos points de vie en bas à gauche de notre écran.

***

## Classe Boss

Cette dernière hérite de la classe Enemy. Dans sa méthode update nous faisons que ce dernier se déplace aléatoire dans la salle en réutilisant une ébauche de ce que nous avions fait dans la génération de cartes aléatoires.

***

## Classe Heart

Cette dernière hérite de Item et implémente AreaInteractionVisitor. Elle permet de rajouter des points de vie à notre personnage principal, nous nous sommes inspiré de la classe Cherry pour modéliser les interactions.

***

## Classe Water

Cette dernière hérite de projectile et implémente ICRogueInteractionHandler. Elle permet de modéliser les sorts lancés par notre Boss final, nous nous sommes inspiré de la structure de notre boule de feu pour gérer les interactions et l'animation de son sprite.

***

## Classe Level0BossRoom

Cette dernière hérite de Level0EnemyRoom. Elle utilise la méthode "addEnemy" de sa classe parente afin d'initialiser cette dernière. Nous nous sommes basé sur la même structure que pour une Level0TurretRoom.

***

## Classe Level0HeartRoom

Cette dernière hérite de Level0ItemRoom. Elle utilise la méthode "addItem" de sa classe parente afin d'initialiser cette dernière. Nous nous sommes basé sur la même structure que pour une Level0ItemRoom.

***

## Classe GameOver

Cette dernière implémente Graphics et Logic afin de pouvoir afficher l'écran de fin "Game Over" en fonction des points de vie de notre personnage principal grâce à la méthode isOn. 

***

## Classe Win

Cette dernière implémente Graphics et Logic afin de pouvoir afficher l'écran de fin "You Win" en fonction de notre méthode isOn qui nous permet de savoir si le Boss Final a été battu.

***
