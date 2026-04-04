package xyz.atsumeru.api.model.services

import com.google.gson.annotations.SerializedName
import xyz.atsumeru.api.model.covers.CoversCachingStatus
import xyz.atsumeru.api.model.importer.ImportStatus
import xyz.atsumeru.api.model.metadata.MetadataUpdateStatus
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
