package xyz.atsumeru.api.model.user

import xyz.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class AccessToken : Serializable {
    var token: String? = null
        get() = itemOrEmpty(field)
}