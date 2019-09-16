package training

import java.time.LocalDate

object ADT {

  /*
   * Lorsque les types de base ne suffisent plus, on peut combiner des types pour en obtenir
   * de nouveaux. Ces types riches permettent de modéliser notre domaine avec précision tout
   * en profitant de la sécurité offerte par la phase de compilation.
   *
   * En Scala, il existe plusieurs manières "d'assembler" les valeurs de différents type
   * en un seul type.
   *
   * Les types qui combinent les valeurs de plusieurs types sont appelés "type produits"
   * en référence au produit cartésien (l'ensemble des couples de valeurs de plusieurs ensembles)
   */

  // Les case classes
  case class Book(title: String, publishedOn: LocalDate)

  // Les tuples
  val position: (Float, Float) = (3.456f, 1.18f)

  /*
   * Il est aussi possible de combiner des types de manières disjointe, c'est à dire par enumeration des
   * valeurs distinctes de ce type.
   */

  sealed trait Beatle
  case object John   extends Beatle
  case object Paul   extends Beatle
  case object George extends Beatle
  case object Ringo  extends Beatle

  /*
   * Ce type de structure, appellé "type somme", est parfois appelé Enum, ou Union discriminée, ou
   * Union disjointe.
   *
   * Vous en connaissez déjà!
   */

  sealed trait Option[+A]
  case class Some[+A](value: A) extends Option[A]
  case object None              extends Option[Nothing]

  /*
   * L'utilisation d'un sealed trait implique que tous les membres du type somme soient dans le même fichier.
   * Ceci permet au compilateur de vérifier l'exhaustivité lors du pattern matching, on y reviendra.
   */

  // -----

  /*
   * Un type de données algébrique (Algebraic data type ou ADT) est un type qui est une combinaison
   * de types sommes et de types produits
   *
   * Ici, la représentation d'un arbre binaire contenant des entiers
   *
   *        8
   *     +-----+
   *    3|     10
   *  +----+     ++
   *  1    6      14
   */
  sealed trait Tree
  case object Empty extends Tree
  case class Leaf(
      value: Int,
      left: Tree = Empty,
      right: Tree = Empty
  ) extends Tree

  val tree = Leaf(
    8,
    Leaf(3, Leaf(1), Leaf(6)),
    Leaf(10, right = Leaf(14))
  )
}
