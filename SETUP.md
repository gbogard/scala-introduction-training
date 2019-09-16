## Mise en place de l'environnement de développement

### Installation du JDK

Scala est un langage s'exécutant traditionnellement sur la Java Virtual Machine (JVM) (bien que des variantes
pour LLVM et Javascript existent).
Le code Scala est d'abord compilé en Java bytecode qui est éxécuté par la JVM. Pour développer en Scala, vous aurez besoin
d'un JDK version 8 sur votre machine.

Vous pouvez trouver OpenJDK, une implémentaion open-source du JDK, à [cette adresse](https://adoptopenjdk.net/).
Pour MacOS et Windows, ils suffit d'installer le programme. Pour Linux, reportez-vous aux instructions spécifiques à votre
distribution.

Si tout fonctionne, `javac -version` devrait donner quelque chose comme ceci:

```
javac 1.8.0_212
```

### Installation de sbt

Sbt est l'outil de build par défaut pour Scala. Il permet entre autres de récupérer les dépendances externes et de
construire votre application.

Sur MacOS, avec Homebrew, on peut installer sbt comme ceci:

```
brew install sbt@1
```

Pour les autres plateformes, les instructions d'installation se trouvent [ici](https://www.scala-sbt.org/1.0/docs/Setup.html).

Vérifiez que tout fonctionne :

```
sbt sbtVersion
```

```
[info] Set current project to guillaumebogard (in build file:/Users/guillaumebogard/)
[info] 1.2.8
```

### Les commandes de base de sbt

Lancer le projet par défaut

```
sbt run
```

Compiler le projet par défaut uniquement

```
sbt compile
```

Lancer les tests

```
sbt test
```

Compiler les tests

```
sbt compile:test
```

Déclencher les tests à chaque changement de source

```
sbt ~test
```

Chacune de ces commandes peut-être lancée directement au format `sbt <commande>` dans un terminal, mais vous pouvez aussi choisir de lancer un terminal sbt interactif 

```
sbt
```

puis de lancer la commande qui vous intéresse (`run`, `compile` ...)

### Installation d'un éditeur

Longtemps en Scala, Intellij IDEA était le choix par défaut, le seul éditeur vraiment viable. Aujourd'hui grâce aux efforts de la communauté, un serveur de langue pour Scala permet
d'obtenir une expérience de développement de qualité sur tous les éditeurs majeurs (VS Code, Vim, Emacs ...)

#### Option 1 : Intellij IDEA Comunity Edition

Á  télécharger [ici](https://www.jetbrains.com/idea/download/). Au premier lancement, Intellij vous proposera d'installer certains plugins populaires. Pensez à installer le plugin
Scala.

#### Option 2 : Metals 

[Metals](https://scalameta.org/metals/docs/editors/overview.html) est le nom du language server pour Scala. Son installation est plus ou moins difficile selon les éditeurs.
VS Code est de loin le plus facile à configurer, il suffit d'installer l'extension Metals depuis le Marketplace. 
Lorsque vous ouvrirez ce projet dans votre editeur, on devrait vous proposer d'importer le build sbt. Pensez à accepter. Metals va télecharger la bonne version de Scala, les dépendances
externes et génerer la SemanticDB qui lui sert à fournir l'auto-completion.