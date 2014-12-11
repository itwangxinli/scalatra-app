package com.example.data.init

import com.example.study.MySqlUtf8Adapter
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.squeryl.{Session, SessionFactory}


trait DatabaseInit {
  val logger = LoggerFactory.getLogger(getClass)


  val databaseUsername = "root"
  val databasePassword = "derbysoft"
  val databaseConnection = "jdbc:mysql://127.0.0.1:3306/squeryl?useUnicode=true&characterEncoding=utf8&autoReconnect=true"
  val dataBaseDriver = "com.mysql.jdbc.Driver"

  var cpds = new ComboPooledDataSource

  def configureDb() {
    cpds.setDriverClass("org.h2.Driver")
    cpds.setJdbcUrl(databaseConnection)
    cpds.setUser(databaseUsername)
    cpds.setPassword(databasePassword)

    cpds.setMinPoolSize(1)
    cpds.setAcquireIncrement(1)
    cpds.setMaxPoolSize(50)

    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      Session.create(cpds.getConnection, new MySqlUtf8Adapter)
    }
  }

  def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close()
  }
}