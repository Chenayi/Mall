package com.kzj.mall.event

import android.view.View

class AddGroupCartEvent {
    var startView: View? = null
    var combinationId: String? = null


    constructor(startView: View?, combinationId: String?) {
        this.startView = startView
        this.combinationId = combinationId
    }

    constructor()
}