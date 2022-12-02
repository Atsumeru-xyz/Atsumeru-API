package com.atsumeru.api.manager

import com.atsumeru.api.model.server.Server
import java.util.*
import kotlin.collections.HashMap

class ServerManager {
    private var current: Int = 0
    @Volatile
    private var lastServerId: Int = -1
    private val serverMap = HashMap<Int, Server>()

    fun createNewServerId(): Int {
        if (lastServerId < 0) {
            lastServerId = getLastServerId();
        }

        lastServerId += 1
        return lastServerId
    }

    private fun getLastServerId(): Int {
        if (serverMap.isEmpty()) {
            return 0
        }

        val servers = listServers();
        return servers[servers.size - 1].id
    }

    fun listServers(): MutableList<Server> {
        val servers = serverMap.values.toMutableList()
        servers.sortWith(Comparator { server1, server2 -> server1.id.compareTo(server2.id) })
        return servers
    }

    fun getCurrentServer(): Server? {
        return serverMap[current]
    }

    fun changeServer(id: Int): String? {
        current = id
        return serverMap[id]?.host
    }

    fun removeServer(server: Server) {
        serverMap.remove(server.id)
    }

    fun addServer(server: Server) {
        serverMap[server.id] = server
    }

    fun addServers(servers: List<Server>) {
        servers.forEach {
            addServer(it)
        }
    }

    fun createBasicAuth() : String? {
        var server = serverMap[current]
        if (server == null && serverMap.size == 1) {
            server = serverMap.values.firstOrNull()
        }
        return server?.createBasicCredentials()
    }
}