package com.example.app

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra

import org.scalatra.json._

/**
 * Created by xinliwang on 14/12/1.
 */
class FlowerController extends ScalatraWebAppStack with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal
  before() {
    contentType = formats("json")
  }
  get("/") {
    FlowerData.all
  }

}
