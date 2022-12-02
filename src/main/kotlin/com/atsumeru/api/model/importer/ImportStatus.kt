package com.atsumeru.api.model.importer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImportStatus : Serializable {
    @SerializedName("import_active")
    var isImportActive = false

    @SerializedName("last_start_time")
    var lastStartTime: Long = 0

    @SerializedName("running_ms")
    var runningMs: Long = 0

    var imported = 0
    var total = 0
    var percent = 0f
}
