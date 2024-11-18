//package tree.implementation
//
//import tree.traits.IntTree
//
//case class NonEmpty(val elem: Int, left: IntTree, right: IntTree) extends BinaryTree :
//
//  override def isEmpty = false
//
//  override def root: Int = elem
//
//  override infix def contains(i: Int): Boolean =
//    if (i == elem) true
//    else if (i < elem) left.contains(i)
//    else right.contains(i)
//
//  override def insert(i: Int): IntTree =
//    if (i < elem) NonEmpty(elem, left.insert(i), right)
//    else if (i > elem) NonEmpty(elem, left, right.insert(i))
//    else this
//
//  override def size: Int = 1 + left.size + right.size
//
//  override def height: Int = 1 + Math.max(left.height, right.height)
