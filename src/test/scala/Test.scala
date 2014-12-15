import scala.io.Source
import scalaz.effect.IO

/**
 * Created by developer on 14-12-15.
 */
object Test {
  def main(args: Array[String]) {
    val lines = Source.fromFile("/home/developer/workspace/scalatra-app/src/test/test.txt").getLines().toList;
    lines.foreach(line => {
      println("insert into order_id_syn(order_id,source)values")
      line.split(",").foreach(a => println("('" + a + "','DMX'),"))
    }
    )
  }
}
