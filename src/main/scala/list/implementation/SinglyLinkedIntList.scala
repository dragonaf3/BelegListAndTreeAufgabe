package list.implementation

import list.traits.IntList

/**
 * A companion object for the singly linked list.
 * This enables creating lists list this: val list = SinglyLinkedIntList(1,2,3)
 * which results in Cons(1,Cons(2,Cons(3,Empty))))
 */
object SinglyLinkedIntList :

  /** The apply function is a special function in scala.
   * It can be invoked with SinglyLinkedIntList.apply(args) or simply SinglyLinkedIntList(args).
   * This particular implementation of it is also a variadic function, i.e.
   * a function which accepts one or more arguments of the same type (integers) as parameters.
   */
  //inside this method xs is of type Seq[int]
  def apply(xs: Int*): SinglyLinkedIntList = xs match
    case Seq() => Empty
    //: _* results in the sequence being passed as multiple parameters - (1,2,3) instead of Seq[Int]{1,2,3}
    case _ => Cons(xs.head, SinglyLinkedIntList(xs.tail*))


abstract class SinglyLinkedIntList extends IntList :

  override def prefix(other: IntList): IntList = other match
    case Empty => this
    case Cons(h,t) => Cons(h,prefix(t))

  override def size: Int = this match
    case Empty => 0
    case Cons(_, tail) => 1 + tail.size

  override def map(mapFunc: Int => Int): IntList = this match
    case Empty => Empty
    case Cons(head, tail) => Cons(mapFunc(head), tail.map(mapFunc))

  override def filter(filterFunc: Int => Boolean): IntList = this match
    case Empty => Empty
    case Cons(head, tail) =>
      if (filterFunc(head)) Cons(head, tail.filter(filterFunc))
      else tail.filter(filterFunc)

  override def foldRight(initial: Int)(reduceFunc: (Int, Int) => Int): Int = this match
    case Empty => initial
    case Cons(head, tail) => reduceFunc(head, tail.foldRight(initial)(reduceFunc))

  /* Do not use a fold-operator for the implementation!*/
  override def reduceRight(reduceFunc: (Int, Int) => Int): Int = this match
    case Cons(head, Empty) => head
    case Cons(head, tail) => reduceFunc(head, tail.reduceRight(reduceFunc))
    case Empty => throw new UnsupportedOperationException("Empty list can't be reduced")

  override def forAll(predicateFunc: Int => Boolean): Boolean = this match
    case Empty => true
    case Cons(head, tail) => predicateFunc(head) && tail.forAll(predicateFunc)

  override def foldLeft(initial: Int)(reduceFunc: (Int, Int) => Int): Int = this match
    case Empty => initial
    case Cons(head, tail) => tail.foldLeft(reduceFunc(initial, head))(reduceFunc)

  /* Do not use a fold-operator for the implementation!*/
  override def reduceLeft(reduceFunc: (Int, Int) => Int): Int = this match
    case Cons(head, Empty) => head
    case Cons(head, tail) => tail.foldLeft(head)(reduceFunc)
    case Empty => throw new UnsupportedOperationException("Empty list can't be reduced")

  override def insertSorted(elem: Int): IntList = this match
    case Empty => Cons(elem, Empty)
    case Cons(head, tail) =>
      if (elem <= head) Cons(elem, this)
      else Cons(head, tail.insertSorted(elem))

  override def insertionSort: IntList = this match
    case Empty => Empty
    case Cons(head, tail) => tail.insertionSort.insertSorted(head)

  override def flip: IntList = {
    def flipHelper(original: IntList, reversed: IntList): IntList = original match
      case Empty => reversed
      case Cons(head, tail) => flipHelper(tail, Cons(head, reversed))

    flipHelper(this, Empty)
  }
