package com.atsumeru.api.model.info

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ServerInfo : Serializable {
    var name: String? = null
        get() = itemOrEmpty(field)

    var version: String? = null
        get() = itemOrEmpty(field)
    
    @SerializedName("version_name")
    var versionName: String? = null
        get() = itemOrEmpty(field)

    @Expose
    @SerializedName("has_password")
    var hasPassword = false

    @Expose
    @SerializedName("debug_mode")
    var debugMode = false

    var stats: Stats? = null
        get() = if (field == null) Stats() else field
}