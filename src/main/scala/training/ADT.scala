package training

import java.time.LocalDate

object ADT {

  /*
   * When basic types are not enough, one can combine existing types to create new ones.
   * In Scala, there are many ways of assembling types to create new types.
   *
   * Types that can hold values from multiple types in a fixed number of places are called *product types*.
   * They are named after the Cartesian Product (then set of distinct couples formed from the values of two sets)
   */

  // Case classes are product types
  case class Book(title: String, publishedOn: LocalDate)

  // Tuples too!
  val position: (Float, Float) = (3.456f, 1.18f)

  /*
   * One can also assemble types in a disjoint fashion, by enumerating the possible terms of the new type.
   * The new type can only be one of term at any given time.
   */
  sealed trait Beatle
  case object John   extends Beatle
  case object Paul   extends Beatle
  case object George extends Beatle
  case object Ringo  extends Beatle

  /*
   * This type of structure is called a *sum type*. They are sometimes referred to as *variants*,
   * *disjoint unions* or * discriminated unions*.
   *
   * You already know some of them!
   */

  sealed trait Option[+A]
  case class Some[+A](value: A) extends Option[A]
  case object None              extends Option[Nothing]

  /*
   * The use of the `sealed` keyword implies that all children of the trait must be defined in the same source file.
   * This allows the compiler to perform proper exhaustivity checks when using these types. We'll get to it later.
   */

  // -----

  /*
   * An algebraic data type (ADT) is a combination of sum types and product types.
   * (A sum type of product type, or a product type of sum types).
   *
   * Here is how we can model a binary tree using an ADT:
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
