package tree.implementation

import tree.traits.IntTree

case object Empty extends BinaryTree:
  override def root: Int = throw new IllegalArgumentException("root.nil")

  override def insert(elem: Int): IntTree = NonEmpty(elem, Empty, Empty)

  override def isEmpty = true

  override def contains(elem: Int): Boolean = false

  override def size: Int = 0

  override def height: Int = 0

  override def delete(i: Int): IntTree = this

  override def map(mapFun: Int => Int): IntTree = this

  override def filter(filterFun: Int => Boolean): IntTree = this

  override def tree2List: List[Int] = List()

  override def isBinaryTree: Boolean = true

