package com.atsumeru.api.model

import com.atsumeru.api.utils.ServiceType
import com.atsumeru.api.utils.getFirstNotEmptyValue
import com.atsumeru.api.utils.itemOrEmpty
import com.atsumeru.api.utils.startsWithIgnoreCase
import com.google.gson.annotations.SerializedName

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
