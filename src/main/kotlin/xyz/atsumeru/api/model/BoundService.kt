package xyz.atsumeru.api.model

import com.google.gson.annotations.SerializedName
import xyz.atsumeru.api.utils.ServiceType
import xyz.atsumeru.api.utils.getFirstNotEmptyValue
import xyz.atsumeru.api.utils.itemOrEmpty
import xyz.atsumeru.api.utils.startsWithIgnoreCase

class BoundService {
    var id: String? = null
        get() = getFirstNotEmptyValue(field, link!!)?.let { getRealId(it) }

    var link: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("service_type")
    var serviceType: ServiceType = ServiceType.UNKNOWN

    constructor(serviceType: ServiceType, idOrLink: String) {
        this.serviceType = serviceType
        id = getRealId(idOrLink)
        link = serviceType.createUrl(id!!)
    }

    constructor(serviceType: ServiceType?, id: String?, link: String?) {
        this.serviceType = serviceType!!
        this.id = id
        this.link = link
    }

    private fun getRealId(idOrLink: String): String? {
        return if (startsWithIgnoreCase(idOrLink, "http")) serviceType.extractId(idOrLink) else idOrLink
    }
}
