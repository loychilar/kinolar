package com.example.kinolar.models

class Film {
    var id:Int?=null
    var name:String?=null
    var yili:String?=null
    var category:Category?=null
    constructor()
    constructor(id: Int?, name: String?, yili: String?, category: Category?) {
        this.id = id
        this.name = name
        this.yili = yili
        this.category = category
    }

    constructor(category: Category? , name: String?, yili: String?) {
        this.category = category
        this.name = name
        this.yili = yili
    }


}