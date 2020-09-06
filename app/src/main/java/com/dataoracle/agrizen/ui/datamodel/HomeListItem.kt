package com.dataoracle.agrizen.ui.datamodel

class HomeListItem(id : String, url: String, name: String) {
    var itemId: String? = null
    var thumbNailUrl: String? = null
    var nameItem: String? = null

    init {
        itemId = id
        thumbNailUrl = url
        nameItem = name
    }
}