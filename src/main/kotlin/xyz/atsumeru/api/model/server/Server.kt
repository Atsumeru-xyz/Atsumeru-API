package xyz.atsumeru.api.model.server

import com.google.gson.annotations.SerializedName
import okhttp3.Credentials
import xyz.atsumeru.api.model.user.BasicCredentials
import xyz.atsumeru.api.utils.getUrlHost
import xyz.atsumeru.api.utils.itemOrEmpty
import java.io.Serializable

class Server private constructor() : Serializable  {
    lateinit var id: String

    var order: Int = 0

    var name: String? = null
        get() = itemOrEmpty(field)

    var host: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("basic_credentials")
    var basicCredentials: BasicCredentials? = null
        get() = if (field == null) BasicCredentials("", "") else field

    constructor(id: String, name: String, host: String, basicCredentials: BasicCredentials): this() {
        this.id = id
        this.name = name
        this.host = getUrlHost(host)
        this.basicCredentials = basicCredentials
    }

    fun getPingUrl(): String {
        return "$host/api/server/ping"
    }

    fun createBasicCredentials(): String {
        return Credentials.basic(basicCredentials!!.userName, basicCredentials!!.password)
    }
}