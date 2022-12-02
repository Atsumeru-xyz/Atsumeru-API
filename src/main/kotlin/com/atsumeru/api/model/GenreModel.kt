package com.atsumeru.api.model

import com.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class GenreModel : Serializable {
    var name: String? = null
        get() = itemOrEmpty(field)

    var id: Int = 0
}