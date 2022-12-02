package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class AtsumeruMessage : Serializable {
    var code = -1

    var message: String? = null
        get() = itemOrEmpty(field)

    fun isOk(): Boolean {
        return code in 200..299
    }
}
