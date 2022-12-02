package com.atsumeru.api.model.server

import com.atsumeru.api.utils.getUrlHost
import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import okhttp3.Credentials
import java.io.Serializable

class Server private constructor() : Serializable  {
    var id: Int = 0

    var name: String? = null
        get() = itemOrEmpty(field)

    var host: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("basic_credentials")
    var basicCredentials: Pair<String, String>? = null
        get() = if (field == null) Pair("", "") else field

    @SerializedName("is_encrypted")
    var isEncrypted: Boolean = false


    constructor(id: Int, name: String, host: String, basicCredentials: Pair<String, String>, isEncrypted: Boolean): this() {
        this.id = id
        this.name = name
        this.host = getUrlHost(host)
        this.basicCredentials = basicCredentials
        this.isEncrypted = isEncrypted
    }

    fun getPingUrl(): String {
        return "$host/api/server/ping"
    }

    fun createBasicCredentials(): String {
        return Credentials.basic(basicCredentials!!.first, basicCredentials!!.second)
    }
}