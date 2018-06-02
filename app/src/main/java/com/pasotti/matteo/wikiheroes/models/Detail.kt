package com.pasotti.matteo.wikiheroes.models

import java.io.Serializable

data class Detail(val id: Int, val digitalId: String,
                  val title: String,
                  val issueNumber: String,
                  val variantDescription: String,
                  val description: String,
                  val isbn: String,
                  val fromat: String,
                  val resourceURI: String,
                  val thumbnail: Thumbnail,
                  val images: MutableList<Thumbnail>,
                  val urls: MutableList<ItemUrl>) : Serializable