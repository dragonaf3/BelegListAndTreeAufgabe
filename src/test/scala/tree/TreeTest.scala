package tree

import org.scalatest.funsuite.AnyFunSuite
import tree.implementation.{BinaryTree, Empty, NonEmpty}

class TreeTest extends AnyFunSuite :

  test("Tree initialization test") :
    val result= Empty.insert(3).insert(11).insert(5).insert(15).insert(4).insert(12).insert(1)
    assert(result===NonEmpty(3,NonEmpty(1,Empty,Empty),NonEmpty(11,NonEmpty(5,NonEmpty(4,Empty,Empty),Empty),NonEmpty(15,NonEmpty(12,Empty,Empty),Empty))))

  test("Tree initialization test apply"):
    val result = BinaryTree(3,11,5,15,4,12,1)
    assert(result === NonEmpty(3, NonEmpty(1, Empty, Empty), NonEmpty(11, NonEmpty(5, NonEmpty(4, Empty, Empty), Empty), NonEmpty(15, NonEmpty(12, Empty, Empty), Empty))))

  test("Tree initialization test duplicates"):
    val result = BinaryTree(3, 11, 5, 15, 3, 4, 12, 1, 15)
    assert(result === NonEmpty(3, NonEmpty(1, Empty, Empty), NonEmpty(11, NonEmpty(5, NonEmpty(4, Empty, Empty), Empty), NonEmpty(15, NonEmpty(12, Empty, Empty), Empty))))

  test("size of the tree") :
    val tree = BinaryTree(3,11,5,15,4,12,1)
    assert(tree.size===7)

  test("height of the tree"):
    val tree = BinaryTree(3, 11, 5, 15, 4, 12, 1)
    assert(tree.height === 3)

  test("contains an element"):
    val tree = BinaryTree(3, 11, 5, 15, 4, 12, 1)
    assert(tree.contains(15) === true)

  test("not contains an element"):
    val tree = BinaryTree(3, 11, 5, 15, 4, 12, 1)
    assert(tree.contains(17) === false)

  test("find Successor 0"):
    intercept[Error] :
      val tree = BinaryTree()
      tree.findSuccessor

  test("find Successor 1"):
    intercept [Error] :
      val tree = BinaryTree(12, 5, 3, 7, 6, 8)
      tree.findSuccessor

  test("find Successor 2"):
    val tree = BinaryTree(20, 15, 25, 22, 30, 18, 19, 12)
    println(tree)
    assert(tree.findSuccessor === NonEmpty(22, Empty, Empty))

  test("find Successor 3"):
   val tree = BinaryTree(3, 11, 5, 15, 4, 12, 1)
   assert(tree.findSuccessor === NonEmpty(4, Empty, Empty))

  test("find Successor 4"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 12, 1)
    assert(tree.findSuccessor === NonEmpty(4,Empty,NonEmpty(5,Empty,Empty)))

  test("delete Leaf"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 12, 1)
    val expected= BinaryTree(3, 11, 8, 4, 5, 12, 1)
    assert(tree.delete(15) === expected)

  test("delete node missing right"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 12, 1)
    val expected = BinaryTree(3, 11, 8, 15, 4, 12, 1)
    assert(tree.delete(5) === expected)

  test("delete node missing left"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    val expected = BinaryTree(3, 11, 8, 15, 4, 16, 1)
    assert(tree.delete(5) === expected)

  test("delete middle node"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    val expected = BinaryTree(4, 11, 8, 5, 15, 16, 1)
    assert(tree.delete(3) === expected)

  test("delete nothing"):
    intercept[Error] :
      val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
      tree.delete(20)

  test("isBinaryTree true") :
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    assert(tree.isBinaryTree)

  test("isBinaryTree false 1") :
    val tree:BinaryTree = NonEmpty(3,NonEmpty(7,Empty,Empty),NonEmpty(11,NonEmpty(5,NonEmpty(4,Empty,Empty),Empty),NonEmpty(15,NonEmpty(12,Empty,Empty),Empty)))
    assert(!tree.isBinaryTree)

  test("isBinaryTree false 2"):
    val tree: BinaryTree = NonEmpty(3, NonEmpty(1, Empty, Empty), NonEmpty(11, NonEmpty(5, NonEmpty(4, Empty, Empty), Empty), NonEmpty(15, Empty, NonEmpty(12, Empty, Empty))))
    assert(!tree.isBinaryTree)

  test("tree2List") :
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    val expected= List(1,3,4,5,8,11,15,16)
    assert(tree.tree2List === expected)

  test("map x=>x*x"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    val expected =  BinaryTree(9, 121, 64, 225, 16, 25, 256, 1)
    assert(tree.map(x=>x*x) === expected)

  test("filter x=>x % 2==0") :
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    val result= tree.filter(x=>x%2==0)
    val expected= List(4,8,16)
    assert(result.tree2List===expected)
    assert(result.isBinaryTree)
/*
  test("foldLeft associative function") :
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    assert(tree.foldLeft(1)((x,y)=>x+y)===63)

  test("reduceLeft associative function"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    assert(tree.reduceLeft((x, y) => x + y) === 63)

  test("reduceLeft non-associative function"):
    val tree = BinaryTree(3, 11, 8, 15, 4, 5, 16, 1)
    assert(tree.reduceLeft((x, y) => x - y) === 63)
*/