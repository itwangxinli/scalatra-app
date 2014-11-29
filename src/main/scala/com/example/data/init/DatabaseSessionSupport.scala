package com.example.data.init

import org.scalatra.ScalatraBase
import org.squeryl.{Session, SessionFactory}

/**
 * Created by xinliwang on 14/11/29.
 */
object DatabaseSessionSupport {
  val key = {
    val n = getClass.getName
    if (n.endsWith("$")) n.dropRight(1) else n
  }
}

trait DatabaseSessionSupport { this: ScalatraBase =>
  import DatabaseSessionSupport._

  def dbSession = request.get(key).orNull.asInstanceOf[Session]

  before() {
    request(key) = SessionFactory.newSession
    dbSession.bindToCurrentThread
  }

  after() {
    dbSession.close
    dbSession.unbindFromCurrentThread
  }

}
