package recursions

import list.implementation.*
import org.scalatest.funsuite.AnyFunSuite

class ProblemsListsTest extends AnyFunSuite :

  test("duplicate Num") :
    assert(ProblemsLists.duplicateNum(5, 3) === SinglyLinkedIntList(5, 5, 5))


  test("duplicate NumbersFulfillingPredicate") :
    assert(ProblemsLists.duplicateNumbersFulfillingPredicate(x=>(x %2 ==0), SinglyLinkedIntList(1,4,3,5,8)) === SinglyLinkedIntList(1, 4, 4, 3, 5, 8, 8))


  test("combinations sorted") :

    val l = SinglyLinkedIntList(1, 2, 3)
    val res = ProblemsLists.combinations(l).map(_.insertionSort)
    val expected = Set(Cons(1, Cons(2, Cons(3, Empty))), Cons(1, Cons(2, Empty)), Cons(1, Cons(3, Empty)), Cons(1, Empty), Cons(2, Cons(3, Empty)), Cons(2, Empty), Cons(3, Empty), Empty)
    assert(res.toSet === expected)

  test("combinations unsorted") :

    val l= SinglyLinkedIntList(3,1,2)
    val res= ProblemsLists.combinations(l).map(_.insertionSort)
    val expected= Set(Cons(1, Cons(2, Cons(3, Empty))), Cons(1, Cons(2, Empty)), Cons(1, Cons(3, Empty)), Cons(1, Empty), Cons(2, Cons(3, Empty)), Cons(2, Empty), Cons(3, Empty), Empty)
    assert (res.toSet===expected)

  test("combinations Empty") :

    val l = SinglyLinkedIntList()
    val res = ProblemsLists.combinations(l)
    val expected = Set(Empty)
    assert(res.toSet === expected)

