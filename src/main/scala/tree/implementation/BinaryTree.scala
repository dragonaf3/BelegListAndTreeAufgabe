package tree.implementation

import tree.traits.IntTree

/**
 *
 * A companion object for the singly linked list.
 * This enables creating lists list this: val list = SinglyLinkedIntList(1,2,3)
 * which results in Cons(1,Cons(2,Cons(3,Empty))))
 */
object BinaryTree:
  /** The apply function is a special function in scala.
   *
   * It can be invoked with SinglyLinkedIntList.apply(args) or simply SinglyLinkedIntList(args).
   * This particular implementation of it is also a variadic function, i.e.
   * a function which accepts one or more arguments of the same type (integers) as parameters.
   */
  //inside this method xs is of type Seq[int]
  def apply(xs: Int*): BinaryTree =
    def treeInitialization(tree: BinaryTree, xs: Seq[Int]): BinaryTree = xs match
      case Seq() => tree
      //: _* results in the sequence being passed as multiple parameters - (1,2,3) instead of Seq[Int]{1,2,3}
      case _ => treeInitialization(tree.insert(xs.head).asInstanceOf[BinaryTree], xs.tail)

    treeInitialization(Empty, xs)

abstract class BinaryTree extends IntTree:

  /* Helper function used by the delete-operation

  Function finds the node with the next higher value
  compared to the root node of the binary tree (see tests)
  */
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