trait Buffer {
  type T
  val element: T
}

trait SequenceBuffer extends Buffer {
  type U

  type T <: Seq[U]

  def length = element.length
}

abstract class IntSeqBuffer extends SequenceBuffer {
  type U = Int
}

object AbstractTest extends App {
  def newIntSeqBuffer(elem1: Int, elem2: Int): IntSeqBuffer = {
    new IntSeqBuffer {
      override type T = List[U]
      override val element: T = List(elem1, elem2)
    }
  }
  val t=newIntSeqBuffer(1,2);
  println(t.length)
  println(t.element)
}

