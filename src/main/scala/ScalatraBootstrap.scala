import com.example.app._
import com.example.data.init.DatabaseInit
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle with DatabaseInit {
  override def init(context: ServletContext) {
    configureDb()
    context mount (new ArticlesController, "/*")
  }

  override def destroy(context: ServletContext): Unit = {
    closeDbConnection()
  }
}
