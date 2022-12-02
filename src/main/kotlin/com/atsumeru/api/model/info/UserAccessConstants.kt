package com.atsumeru.api.model.info

import com.atsumeru.api.model.GenreModel
import com.atsumeru.api.model.category.Category
import com.google.gson.annotations.Expose
import java.io.Serializable

class UserAccessConstants : Serializable {
    @Expose
    var roles: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @Expose
    var authorities: List<String>? = null
        get() = if (field == null) ArrayList() else field

    @Expose
    var categories: List<Category>? = null
        get() = if (field == null) ArrayList() else field

    @Expose
    var genres: List<GenreModel>? = null
        get() = if (field == null) ArrayList() else field

    @Expose
    var tags: List<String>? = null
        get() = if (field == null) ArrayList() else field
}
