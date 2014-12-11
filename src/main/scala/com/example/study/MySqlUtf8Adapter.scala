package com.example.study

import org.squeryl.{Schema, Table}
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.internals.StatementWriter


class MySqlUtf8Adapter extends MySQLAdapter {
  override def writeCreateTable[T](t: Table[T], sw: StatementWriter, schema: Schema) = {
    super.writeCreateTable(t, sw, schema);
    sw.write(" ENGINE=InnoDB DEFAULT CHARSET=UTF8 ")
  }
}

