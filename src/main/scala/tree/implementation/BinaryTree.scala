//package tree.implementation
//
//import tree.traits.IntTree
//
///**
// * A companion object for the singly linked list.
// * This enables creating lists list this: val list = SinglyLinkedIntList(1,2,3)
// * which results in Cons(1,Cons(2,Cons(3,Empty))))
// */
//object BinaryTree :
//
//  /** The apply function is a special function in scala.
//   * It can be invoked with SinglyLinkedIntList.apply(args) or simply SinglyLinkedIntList(args).
//   * This particular implementation of it is also a variadic function, i.e.
//   * a function which accepts one or more arguments of the same type (integers) as parameters.
//   */
//  //inside this method xs is of type Seq[int]
//  def apply(xs: Int*): BinaryTree =
//
//    def treeIntialization(tree: BinaryTree, xs: Int*): BinaryTree = xs match
//
//      case Seq() => tree
//      //: _* results in the sequence being passed as multiple parameters - (1,2,3) instead of Seq[Int]{1,2,3}
//      case _ => treeIntialization(tree.insert(xs.head).asInstanceOf[BinaryTree], xs.tail*)
//
//    treeIntialization(Empty, xs*)
//
//abstract class BinaryTree extends IntTree :
//
//  /* Helper function used by the delete-operation
//  *  Function finds the node with the next higher value
//  *  compared to the root node of the binary tree (see tests)
//  * */
//  def findSuccessor: BinaryTree = this match
//    case nonEmpty: NonEmpty => nonEmpty.right match
//      case Empty => throw new NoSuchElementException("No successor in an empty tree")
//      case rightNonEmpty: NonEmpty =>
//        if (rightNonEmpty.left == Empty) rightNonEmpty
//        else rightNonEmpty.left.findSuccessor.asInstanceOf[BinaryTree]
//    case Empty => throw new NoSuchElementException("Cannot find successor in an empty tree")
//
//  override def delete(i: Int): IntTree = this match
//    case nonEmpty: NonEmpty =>
//      if (i < nonEmpty.elem) NonEmpty(nonEmpty.elem, nonEmpty.left.delete(i).asInstanceOf[BinaryTree], nonEmpty.right)
//      else if (i > nonEmpty.elem) NonEmpty(nonEmpty.elem, nonEmpty.left, nonEmpty.right.delete(i).asInstanceOf[BinaryTree])
//      else nonEmpty.right match
//        case Empty => nonEmpty.left
//        case rightNonEmpty: NonEmpty =>
//          val successor = rightNonEmpty.findSuccessor.asInstanceOf[NonEmpty]
//          NonEmpty(successor.elem, nonEmpty.left, rightNonEmpty.delete(successor.elem).asInstanceOf[BinaryTree])
//    case Empty => Empty
//
//  override def map(mapFun: Int => Int): IntTree = this match
//    case nonEmpty: NonEmpty =>
//      NonEmpty(mapFun(nonEmpty.elem), nonEmpty.left.map(mapFun).asInstanceOf[BinaryTree], nonEmpty.right.map(mapFun).asInstanceOf[BinaryTree])
//    case Empty => Empty
//
//  override def filter(filterFun: Int => Boolean): IntTree = this match
//    case nonEmpty: NonEmpty =>
//      val filteredLeft = nonEmpty.left.filter(filterFun).asInstanceOf[BinaryTree]
//      val filteredRight = nonEmpty.right.filter(filterFun).asInstanceOf[BinaryTree]
//      if (filterFun(nonEmpty.elem)) NonEmpty(nonEmpty.elem, filteredLeft, filteredRight)
//      else filteredLeft.insertTree(filteredRight)
//    case Empty => Empty
//
//  def insertTree(other: IntTree): BinaryTree = this match
//    case nonEmpty: NonEmpty =>
//      NonEmpty(nonEmpty.elem, nonEmpty.left.insertTree(other).asInstanceOf[BinaryTree], nonEmpty.right)
//    case Empty => other.asInstanceOf[BinaryTree]
//
//  override def tree2List: List[Int] = this match
//    case nonEmpty: NonEmpty => nonEmpty.left.tree2List ++ List(nonEmpty.elem) ++ nonEmpty.right.tree2List
//    case Empty => List()
//
//  override def isBinaryTree: Boolean =
//    def isValid(tree: IntTree, min: Option[Int], max: Option[Int]): Boolean = tree match
//      case nonEmpty: NonEmpty =>
//        (min.forall(nonEmpty.elem > _) && max.forall(nonEmpty.elem < _)) &&
//          isValid(nonEmpty.left, min, Some(nonEmpty.elem)) &&
//          isValid(nonEmpty.right, Some(nonEmpty.elem), max)
//      case Empty => true
//
//    isValid(this, None, None)
