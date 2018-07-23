package com.kzj.mall.event

import android.view.View

class AddCartEvent {
    var startView: View? = null
    var isGroup: Boolean? = null


    constructor(isGroup: Boolean?, startView: View?) {
        this.startView = startView
        this.isGroup=isGroup
    }

    constructor()
}