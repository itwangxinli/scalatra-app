package com.example.model

import com.example.model.Library._
import org.squeryl.PrimitiveTypeMode._


object LibraryService {
  def insert(authors: Author) = transaction(
    author.insert(authors)
  )
}
