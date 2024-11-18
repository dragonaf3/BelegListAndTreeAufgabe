package tree.implementation

import tree.traits.IntTree

object BinaryTree:
  def apply(xs: Int*): BinaryTree =
    def treeInitialization(tree: BinaryTree, xs: Seq[Int]): BinaryTree = xs match
      case Seq() => tree
      case _ => treeInitialization(tree.insert(xs.head).asInstanceOf[BinaryTree], xs.tail)

    treeInitialization(Empty, xs)

abstract class BinaryTree extends IntTree:

  def findSuccessor: BinaryTree = this match
    case Empty => throw new Error("Kann Nachfolger in einem leeren Baum nicht finden")
    case NonEmpty(_, _, Empty) => throw new Error("Kein Nachfolger vorhanden")
    case NonEmpty(_, _, right: NonEmpty) =>
      // Lokale Hilfsfunktion, um das minimale Element zu finden
      def findMin(tree: BinaryTree): BinaryTree = tree match
        case NonEmpty(_, Empty, _) => tree
        case NonEmpty(_, left: BinaryTree, _) => findMin(left.asInstanceOf[BinaryTree])
        case Empty => throw new Error("Baum ist leer")

      findMin(right.asInstanceOf[BinaryTree])

  override def delete(i: Int): IntTree = this match
    case Empty => throw new Error(s"Element $i nicht im Baum gefunden")
    case NonEmpty(value, left, right) =>
      if i < value then
        NonEmpty(value, left.delete(i).asInstanceOf[BinaryTree], right.asInstanceOf[BinaryTree])
      else if i > value then
        NonEmpty(value, left.asInstanceOf[BinaryTree], right.delete(i).asInstanceOf[BinaryTree])
      else
        (left, right) match
          case (Empty, Empty) => Empty
          case (Empty, _) => right
          case (_, Empty) => left
          case _ =>
            val (successorValue, newRight) = deleteMin(right.asInstanceOf[BinaryTree])
            NonEmpty(successorValue, left.asInstanceOf[BinaryTree], newRight.asInstanceOf[BinaryTree])

  // Hilfsfunktion zum Löschen des minimalen Elements
  private def deleteMin(tree: BinaryTree): (Int, BinaryTree) = tree match
    case NonEmpty(value, Empty, right) => (value, right.asInstanceOf[BinaryTree])
    case NonEmpty(value, left, right) =>
      val (minValue, newLeft) = deleteMin(left.asInstanceOf[BinaryTree])
      (minValue, NonEmpty(value, newLeft.asInstanceOf[BinaryTree], right.asInstanceOf[BinaryTree]))
    case Empty => throw new Error("Kann das minimale Element in einem leeren Baum nicht löschen")

  // Rest der Klasse bleibt unverändert
  override def map(mapFun: Int => Int): IntTree = this match
    case Empty => Empty
    case NonEmpty(value, left, right) =>
      NonEmpty(
        mapFun(value),
        left.map(mapFun).asInstanceOf[BinaryTree],
        right.map(mapFun).asInstanceOf[BinaryTree]
      )

  override def filter(filterFun: Int => Boolean): IntTree = this match
    case Empty => Empty
    case NonEmpty(value, left, right) =>
      val filteredLeft = left.filter(filterFun).asInstanceOf[BinaryTree]
      val filteredRight = right.filter(filterFun).asInstanceOf[BinaryTree]
      if filterFun(value) then
        NonEmpty(value, filteredLeft, filteredRight)
      else
        combineTrees(filteredLeft, filteredRight)

  private def combineTrees(leftTree: BinaryTree, rightTree: BinaryTree): BinaryTree = leftTree match
    case Empty => rightTree
    case NonEmpty(value, left, right) =>
      val newRight = combineTrees(right.asInstanceOf[BinaryTree], rightTree)
      NonEmpty(value, left.asInstanceOf[BinaryTree], newRight)

  override def tree2List: List[Int] = this match
    case Empty => List()
    case NonEmpty(value, left, right) =>
      left.tree2List ::: (value :: right.tree2List)

  override def isBinaryTree: Boolean = this match
    case Empty => true
    case NonEmpty(value, left: BinaryTree, right: BinaryTree) =>
      val leftCheck = left match
        case Empty => true
        case NonEmpty(leftValue, _, _) => leftValue < value
      val rightCheck = right match
        case Empty => true
        case NonEmpty(rightValue, _, _) => rightValue > value
      leftCheck && rightCheck && left.isBinaryTree && right.isBinaryTree

  override def height: Int = this match
    case Empty => -1
    case NonEmpty(_, left, right) =>
      1 + Math.max(left.height, right.height)
