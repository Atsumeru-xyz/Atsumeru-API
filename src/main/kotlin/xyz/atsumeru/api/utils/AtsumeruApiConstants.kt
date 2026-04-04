@file:JvmName("AtsumeruApiConstants")

package xyz.atsumeru.api.utils

private var coversUrl = "{host}/api/v1/books/cover/{hash}?convert={is_convert}"
private var pagesUrl = "{host}/api/v1/books/{hash}/page/{page}?convert={is_convert}"
private var downloadsUrl = "{host}/api/v1/download/{hash}/"

var USER_AGENT = "Atsumeru API Library"
var HTTP_CONNECT_TIMEOUT = 15000
var HTTP_READ_TIMEOUT = 25000

fun getCoversUrl(): String {
    return coversUrl
}

fun getPagesUrl(): String {
    return pagesUrl
}

fun getDownloadsUrl(): String {
    return downloadsUrl
}

fun setUserAgent(userAgent: String) {
    USER_AGENT = userAgent
}

fun getUserAgent(): String {
    return USER_AGENT
}

fun setHttpConnectTimeout(timeout: Int) {
    HTTP_CONNECT_TIMEOUT = timeout
}

fun setHttpReadTimeout(timeout: Int) {
    HTTP_READ_TIMEOUT = timeout
}