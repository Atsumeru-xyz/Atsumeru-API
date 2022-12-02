package com.atsumeru.api.model.services

import com.atsumeru.api.model.covers.CoversCachingStatus
import com.atsumeru.api.model.importer.ImportStatus
import com.atsumeru.api.model.metadata.MetadataUpdateStatus
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ServicesStatus : Serializable {
    @SerializedName("importer")
    var importStatus: ImportStatus? = null
        get() = if (field == null) ImportStatus() else field

    @SerializedName("metadata_update")
    var metadataUpdateStatus: MetadataUpdateStatus? = null
        get() = if (field == null) MetadataUpdateStatus() else field

    @SerializedName("covers_caching")
    var coversCachingStatus: CoversCachingStatus? = null
        get() = if (field == null) CoversCachingStatus() else field
}
