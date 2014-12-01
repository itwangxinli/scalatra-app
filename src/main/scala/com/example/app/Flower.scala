package com.example.app

/**
 * Created by xinliwang on 14/12/1.
 */
case class Flower(slug: String, name: String)

object FlowerData{
  var all = List(
    Flower("yellow-tulip", "Yellow Tulip"),
    Flower("red-rose", "Red Rose"),
    Flower("black-rose", "Black Rose"))
}
