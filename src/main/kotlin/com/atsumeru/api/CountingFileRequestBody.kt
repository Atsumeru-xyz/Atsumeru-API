package com.atsumeru.api

import com.atsumeru.api.listeners.UploadProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import okio.Source
import java.io.Closeable
import java.io.File
import java.io.IOException

class CountingFileRequestBody(
    private val file: File,
    private val contentType: String,
    private val listener: UploadProgressListener
) :
    RequestBody() {
    override fun contentLength(): Long {
        return file.length()
    }

    override fun contentType(): MediaType? {
        return MediaType.parse(contentType)
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        var source: Source? = null
        try {
            val fileSize = file.length()
            source = Okio.source(file)
            var total: Long = 0
            var read: Long
            while (source.read(sink.buffer(), SEGMENT_SIZE).also { read = it } != -1L) {
                total += read
                sink.flush()
                listener.onProgress(total.toFloat() / fileSize)
            }
        } finally {
            closeQuietly(source)
        }
    }

    private fun closeQuietly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (ignored: IOException) {
                // ignore
            }
        }
    }

    companion object {
        private const val SEGMENT_SIZE = 2048L
    }
}