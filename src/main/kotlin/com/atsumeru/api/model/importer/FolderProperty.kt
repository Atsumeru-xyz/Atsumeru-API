package com.atsumeru.api.model.importer

import com.atsumeru.api.utils.itemOrEmpty
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class FolderProperty : Serializable {
    var path: String? = null
        get() = itemOrEmpty(field)

    var hash: String? = null
        get() = itemOrEmpty(field)

    @SerializedName("singles")
    var isSingles = false

    @SerializedName("singles_in_root")
    var isSinglesInRoot = false

    @SerializedName("singles_if_in_root_with_folders")
    var isSinglesIfInRootWithFolders = false

    @SerializedName("series_count")
    var seriesCount: Long = -1

    @SerializedName("singles_count")
    var singlesCount: Long = -1

    @SerializedName("archives_count")
    var archivesCount: Long = -1

    @SerializedName("chapters_count")
    var chaptersCount: Long = -1

    fun createHash() {
        hash = md5Hex(path!!)
    }

    fun md5Hex(str: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            val hash = md.digest(str.toByteArray(StandardCharsets.UTF_8))
            val hexString = StringBuffer()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xFF and hash[i].toInt())
                if (hex.length == 1) {
                    hexString.append('0')
                }
                hexString.append(hex)
            }
            return hexString.toString()
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        } catch (ex: NoSuchAlgorithmException) {
            ex.printStackTrace()
        }
        return ""
    }
}
