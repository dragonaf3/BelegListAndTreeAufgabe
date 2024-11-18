package tree.implementation

import tree.traits.IntTree

case class NonEmpty(val elem: Int, left: IntTree, right: IntTree) extends BinaryTree:

  override def isEmpty = false

  override def root: Int = elem

  override def contains(elem: Int): Boolean =
    if elem == root then true
    else if elem < root then left.contains(elem)
    else right.contains(elem)

  override def insert(elem: Int): IntTree =
    if elem < root then NonEmpty(root, left.insert(elem).asInstanceOf[BinaryTree], right)
    else if elem > root then NonEmpty(root, left, right.insert(elem).asInstanceOf[BinaryTree])
    else this

  override def size: Int = 1 + left.size + right.size

  override def height: Int = 1 + Math.max(left.height, right.height)