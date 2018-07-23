package com.kzj.mall.event

import com.kzj.mall.widget.SlideDetailsLayout

class ScrollChangedEvent {
    var alpha = 0.0f
    var status: SlideDetailsLayout.Status? = null

    constructor(status: SlideDetailsLayout.Status, alpha: Float) {
        this.status = status
        this.alpha = alpha
    }
}