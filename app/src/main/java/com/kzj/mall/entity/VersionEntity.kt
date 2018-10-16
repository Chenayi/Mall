package com.kzj.mall.entity

import java.io.Serializable

class VersionEntity : Serializable {
    var version_id: Int? = null
    var update_content: String? = null
    var apk_address: String? = null
    var version_name: String? = null
    var version_code: String? = null
    var force_update: String? = null
}