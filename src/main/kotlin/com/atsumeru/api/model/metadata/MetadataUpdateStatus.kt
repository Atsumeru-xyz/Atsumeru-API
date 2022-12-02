package com.atsumeru.api.model.metadata

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MetadataUpdateStatus : Serializable {
    @SerializedName("metadata_update_active")
    var isUpdateActive = false

    @SerializedName("running_ms")
    var runningMs: Long = 0

    var updated = 0
    var total = 0
    var percent = 0f
}