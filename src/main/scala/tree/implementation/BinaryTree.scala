package tree.implementation

import tree.traits.IntTree

/**
 * A companion object for the singly linked list.
 * This enables creating lists list this: val list = SinglyLinkedIntList(1,2,3)
 * which results in Cons(1,Cons(2,Cons(3,Empty))))
 */
object BinaryTree :
  def apply(xs: Int*): BinaryTree =
    def treeInitialization(tree: BinaryTree, xs: Seq[Int]): BinaryTree = xs match
      case Seq() => tree
      case _     => treeInitialization(tree.insert(xs.head).asInstanceOf[BinaryTree], xs.tail)

    treeInitialization(Empty, xs)

abstract class BinaryTree extends IntTree :

  // Find the node with the next higher value compared to the root node of the binary tree
  def findSuccessor: BinaryTree = this match
    case NonEmpty(_, _, right: NonEmpty) => right.findMin
    case _ => throw new NoSuchElementException("No successor found")

  def findMin: BinaryTree = this match
    case NonEmpty(_, Empty, _) => this
    case NonEmpty(_, left: NonEmpty, _) => left.findMin
    case _ => throw new NoSuchElementException("Tree is empty")

  override def delete(i: Int): IntTree = this match
    case Empty => this
    case NonEmpty(value, left, right) =>
      if i < value then NonEmpty(value, left.delete(i).asInstanceOf[BinaryTree], right.asInstanceOf[BinaryTree])
      else if i > value then NonEmpty(value, left.asInstanceOf[BinaryTree], right.delete(i).asInstanceOf[BinaryTree])
      else (left, right) match
        case (Empty, Empty) => Empty
        case (Empty, _)     => right
        case (_, Empty)     => left
        case _              =>
          val successor = right.findMin.asInstanceOf[NonEmpty]
          NonEmpty(successor.root, left.asInstanceOf[BinaryTree], right.delete(successor.root).asInstanceOf[BinaryTree])

  override def map(mapFun: Int => Int): IntTree = this match
    case Empty => Empty
    case NonEmpty(value, left, right) =>
      NonEmpty(mapFun(value), left.map(mapFun).asInstanceOf[BinaryTree], right.map(mapFun).asInstanceOf[BinaryTree])

  override def filter(filterFun: Int => Boolean): IntTree = this match
    case Empty => Empty
    case NonEmpty(value, left, right) =>
      val filteredLeft = left.filter(filterFun).asInstanceOf[BinaryTree]
      val filteredRight = right.filter(filterFun).asInstanceOf[BinaryTree]
      if filterFun(value) then NonEmpty(value, filteredLeft, filteredRight)
      else filteredLeft.merge(filteredRight)

  def merge(other: BinaryTree): BinaryTree = (this, other) match
    case (Empty, _) => other
    case (_, Empty) => this
    case (NonEmpty(value, left, right), _) =>
      val mergedRight = right.merge(other).asInstanceOf[BinaryTree]
      NonEmpty(value, left.asInstanceOf[BinaryTree], mergedRight)

  override def tree2List: List[Int] = this match
    case Empty => List()
    case NonEmpty(value, left, right) => left.tree2List ::: (value :: right.tree2List)

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