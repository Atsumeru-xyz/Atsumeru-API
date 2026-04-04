package xyz.atsumeru.api.model

import xyz.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class GenreModel : Serializable {
    var name: String? = null
        get() = itemOrEmpty(field)

    var id: Int = 0
}