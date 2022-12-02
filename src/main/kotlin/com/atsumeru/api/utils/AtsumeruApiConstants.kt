@file:JvmName("AtsumeruApiConstants")
package com.atsumeru.api.utils

private var mainUrl = "http://localhost:13337"
private var endpointUrl = "$mainUrl/api/v1/"
private var coversUrl = "$mainUrl/api/v1/books/cover/{hash}?convert={is_convert}"
private var pagesUrl = "$mainUrl/api/v1/books/{hash}/page/{page}?convert={is_convert}"
private var downloadsUrl = "$mainUrl/api/v1/download/{hash}/"

var USER_AGENT = "Atsumeru API Library"
var HTTP_CONNECT_TIMEOUT = 15000
var HTTP_READ_TIMEOUT = 25000

fun setMainUrl(url: String) {
    mainUrl = url
    endpointUrl = "$mainUrl/api/v1/"
    coversUrl = "$mainUrl/api/v1/books/cover/{hash}?convert={is_convert}"
    pagesUrl = "$mainUrl/api/v1/books/{hash}/page/{page}?convert={is_convert}"
    downloadsUrl = "$mainUrl/api/v1/download/{hash}/"
}

fun getMainUrl(): String {
    return mainUrl
}

fun getEndpointUrl(): String {
    return endpointUrl
}

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