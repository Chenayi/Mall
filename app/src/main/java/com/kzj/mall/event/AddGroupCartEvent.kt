package com.kzj.mall.event

import android.view.View

class AddGroupCartEvent {
    var startView: View? = null


    constructor(startView: View?) {
        this.startView = startView
    }

    constructor()
}