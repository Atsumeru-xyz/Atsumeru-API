package xyz.atsumeru.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import xyz.atsumeru.api.manager.ServerManager
import xyz.atsumeru.api.model.server.Server
import xyz.atsumeru.api.network.AtsumeruServer
import xyz.atsumeru.api.utils.HTTP_CONNECT_TIMEOUT
import xyz.atsumeru.api.utils.HTTP_READ_TIMEOUT
import xyz.atsumeru.api.utils.USER_AGENT
import java.util.concurrent.TimeUnit

@Suppress("unused")
object AtsumeruAPI {
    private lateinit var serverManager: ServerManager

    @JvmStatic
    fun init(servers: List<Server>, builder: OkHttpClient.Builder, isDebug: Boolean) {
        serverManager = ServerManager(
            builder.connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply { level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE })
                .addInterceptor { chain ->
                    var request = chain.request()
                    val requestBuilder = request.newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", USER_AGENT)

                    request = requestBuilder.build()
                    chain.proceed(request)
                }
        )
        serverManager.addServers(servers)
    }

    @JvmStatic
    fun atServer(server: Server): AtsumeruServer? {
        return serverManager.getServer(server)
    }

    @JvmStatic
    fun atServer(id: String): AtsumeruServer? {
        return serverManager.getServer(id)
    }

    @JvmStatic
    fun getServerManager(): ServerManager {
        return serverManager
    }
}