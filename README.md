# Introduction à la programmation fonctionnelle en Scala

Cette formation est destinée a être animée sur une durée de deux jours. Elle couvre les bases de la syntaxe
du langage et quelques principes fondamentaux de la programmation fonctionnelle. Elle s'adresse à des personnes
ayant déjà une expérience de la programmation impérative ou orientée-objet.

Cette formation a pour objectif d'enseigner l'essentiel de la syntaxe de Scala et les principes fondamentaux
de la programmation fonctionnelle à travers la réalisation d'un jeu de Morpion (Tic Tac Toe) dans un style
purement fonctionnel et convenablement testé.

[Mise en place de l'environnement de développement](./SETUP.md)

## Notions

- Qu'est-ce que Scala ?
- Syntaxe du langage
- Qu'est-ce que la programmation fonctionnelle ?
- Récursion
- Algebraic Data types
- Introduction aux IO monads
- Introduction aux streams fs2

## Structure de la formation

Cette formation encourage les développeurs la *logique métier* de *l'infrastructure* (les interactions avec le monde
extérieur). Le découpage des projets sbt reflète ces principes.

Il y a 4 sous-projets sbt:
  - le projet racine (`root`) dans `src` : sert d'introduction à la syntaxe du langage. Un fichier par concept,
chaque fichier contient des commentaires détaillés.
  - le projet `ticTacToeCore` dans `tic-tac-toe` : contient les entités et la logique du jeu de morpion
  - le projet `ticTacToeCli` dans `tic-tac-toe-cli` : expose le jeu de morpion sous la forme d'une interface
en ligne de commande. Les deux joueurs jouent sur la même machine.
  - le projet `ticTacToeNetworkServer` dans `tic-tac-toe-server` : expose le jeu sous la forme d'un serveur TCP. Deux
joueurs doivent se connecter au serveur via `netcat` ou `telnet`. Les deux joueurs jouent chacun sur leur machine. Le
serveur TCP 

## Lancer les projets

### Tests du projet racine

```
sbt test
```

### Tests de la logique du jeu

```
sbt ticTacToeCore/test
```

### Lancer le jeu en ligne de commande

```
sbt ticTacToeCli/run
```

### Lancer le jeu en réseau (TCP)

```
sbt ticTacToeNetworkServer/run
```

Pour se connecter 

```
nc localhost 4567
```

La partie se lance lorsque deux joueurs sont connectés au serveur

## Ressources utiles

- [Tour of Scala](https://docs.scala-lang.org/tour/tour-of-scala.html)
- [Scala exercises](https://www.scala-exercises.org/)
- [Article sur fs2 sur FreeCodeCamp](https://www.freecodecamp.org/news/a-streaming-library-with-a-superpower-fs2-and-functional-programming-6f602079f70a/)
- [Algebraic Data Types](https://en.wikipedia.org/wiki/Algebraic_data_type)
- [IO monad: which, why and how](https://kubuszok.com/2019/io-monad-which-why-and-how/)

## LICENCE

Creative Commons `BY-SA` (Attribution et partage dans les mêmes conditions)

[ ![LICENSE](https://i.creativecommons.org/l/by-sa/4.0/88x31.png) ](http://creativecommons.org/licenses/by-sa/4.0/)
