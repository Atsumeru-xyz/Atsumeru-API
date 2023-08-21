package com.atsumeru.api.utils

import com.atsumeru.api.listeners.UploadProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio.source
import java.io.File
import java.util.regex.Pattern

enum class LibraryPresentation {
    SERIES, SINGLES, ARCHIVES, SERIES_AND_SINGLES
}

enum class ReadableType {
    VOLUMES, CHAPTERS
}

enum class Sort {
    TITLE, POPULARITY, YEAR, COUNTRY, LANGUAGE, PUBLISHER, SERIE, PARODY, VOLUMES_COUNT, CHAPTERS_COUNT, SCORE, CREATED_AT, UPDATED_AT, LAST_READ
}

enum class ImportType {
    NEW, FULL, FULL_WITH_COVERS
}

enum class ServiceType(private val formatUrl: String, private val idPattern: Pattern) {
    UNKNOWN("", Pattern.compile("")),
    MYANIMELIST(
        "https://myanimelist.net/manga/%s",
        Pattern.compile("manga/(\\d+)")
    ),
    SHIKIMORI(
        "https://shikimori.me/mangas/%s",
        Pattern.compile("/(\\d+)|/\\w(\\d+)")
    ),
    KITSU(
        "https://kitsu.io/manga/%s",
        Pattern.compile("manga/(.*)/|manga/(.*)")
    ),
    ANILIST(
        "https://anilist.co/manga/%s",
        Pattern.compile("manga/(\\d+)")
    ),
    MANGAUPDATES(
        "https://www.mangaupdates.com/series/%s",
        Pattern.compile("series/(.*?)/|series/(.*?)\$")
    ),
    ANIMEPLANET(
        "https://www.anime-planet.com/manga/%s",
        Pattern.compile("manga/(.*)/|manga/(.*)")
    ),
    COMICVINE(
        "https://comicvine.gamespot.com/comic/%s/",
        Pattern.compile("(\\d+-\\d+)")
    ),
    COMICSDB(
        "https://comicsdb.ru/publishers/%s",
        Pattern.compile("publishers/(.*)")
    ),
    HENTAG(
        "https://hentag.com/vault/%s",
        Pattern.compile("vault/(.*)")
    );

    fun extractId(str: String): String? {
        val matcher = idPattern.matcher(str)
        if (matcher.find()) {
            val firstGroup = matcher.group(1)
            var secondGroup: String? = null
            try {
                secondGroup = matcher.group(2)
            } catch (ignored: Exception) {
            }
            return getFirstNotEmptyValue(firstGroup, secondGroup)
        }
        return null
    }

    fun createUrl(id: String): String {
        return String.format(formatUrl, id)
    }
}

fun getFirstNotEmptyValue(vararg values: String?): String? {
    for (value in values) {
        if (isNotEmpty(value)) {
            return value
        }
    }
    return null
}

fun isNotEmpty(str: String?): Boolean {
    return str != null && str.trim { it <= ' ' }.isNotEmpty()
}

fun startsWithIgnoreCase(first: String, second: String): Boolean {
    return isNotEmpty(first) && isNotEmpty(second) && first.toLowerCase().startsWith(second.toLowerCase())
}

fun itemOrEmpty(str: String?): String {
    return str ?: ""
}

fun getUrlHost(url: String): String? {
    var i1 = url.indexOf("://")
    if (i1 < 0) {
        return null
    }
    i1 += 3
    val i2 = url.indexOf("/", i1)
    return if (i2 < 0) {
        url
    } else url.substring(0, i2)
}

fun File.asProgressRequestBody(
    contentType: MediaType? = null,
    progressListener: UploadProgressListener
): RequestBody {
    return object : RequestBody() {
        override fun contentType() = contentType

        override fun contentLength() = length()

        override fun writeTo(sink: BufferedSink) {
            source(this@asProgressRequestBody).use { source ->
                var total: Long = 0
                var read: Long = 0
                while (source.read(sink.buffer(), 2048).apply {
                        read = this
                    } != -1L) {
                    total += read
                    sink.flush()
                    progressListener.onProgress(total.toFloat() / length())
                }
            }
        }
    }
}