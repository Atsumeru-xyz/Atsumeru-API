package xyz.atsumeru.api.manager

import okhttp3.OkHttpClient
import xyz.atsumeru.api.model.server.Server
import xyz.atsumeru.api.network.AtsumeruServer
import java.util.stream.Collectors

class ServerManager(val builder: OkHttpClient.Builder) {
    private val servers = HashMap<String, AtsumeruServer>()

    fun getServer(server: Server): AtsumeruServer? {
        return servers[server.id]
    }

    fun getServer(id: String): AtsumeruServer? {
        return servers[id]
    }

    fun listServers(): MutableList<Server> {
        val servers = servers.values.stream().map { it.server }.collect(Collectors.toList())
        servers.sortWith(Comparator { server1, server2 -> server1.order.compareTo(server2.order) })
        return servers
    }

    fun addServer(server: Server) {
        servers[server.id] = AtsumeruServer(server, builder.build().newBuilder())
    }

    fun addServers(servers: List<Server>) {
        servers.forEach {
            addServer(it)
        }
    }

    fun removeServer(server: Server) {
        removeServer(server.id)
    }

    fun removeServer(id: String) {
        servers.remove(id)
    }
}