package com.example.data.init

import org.scalatra.ScalatraBase
import org.slf4j.LoggerFactory
import org.squeryl.{Session, SessionFactory}

/**
 * Created by xinliwang on 14/11/29.
 */
object DatabaseSessionSupport {
  val key = {
    val n = getClass.getName
    println(n)
    if (n.endsWith("$")) n.dropRight(1) else n
  }
}

trait DatabaseSessionSupport {
  this: ScalatraBase =>

  import DatabaseSessionSupport._

  val logger = LoggerFactory.getLogger(getClass);

  def dbSession ={
    logger.info("request key [{}]",key);
   request.get(key).orNull.asInstanceOf[Session]}


  before() {
    request(key) = SessionFactory.newSession
    dbSession.bindToCurrentThread
  }

  after() {
    dbSession.close
    dbSession.unbindFromCurrentThread
  }

}
