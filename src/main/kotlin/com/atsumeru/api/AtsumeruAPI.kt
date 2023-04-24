package com.atsumeru.api

import com.atsumeru.api.listeners.UploadProgressListener
import com.atsumeru.api.manager.ServerManager
import com.atsumeru.api.model.*
import com.atsumeru.api.model.category.Category
import com.atsumeru.api.model.filesystem.DirectoryListing
import com.atsumeru.api.model.filesystem.DirectoryRequest
import com.atsumeru.api.model.importer.FolderProperty
import com.atsumeru.api.model.importer.ImportStatus
import com.atsumeru.api.model.info.ServerInfo
import com.atsumeru.api.model.info.UserAccessConstants
import com.atsumeru.api.model.metacategory.Metacategory
import com.atsumeru.api.model.metadata.MetadataUpdateStatus
import com.atsumeru.api.model.services.ServicesStatus
import com.atsumeru.api.model.settings.ServerSettings
import com.atsumeru.api.model.sync.History
import com.atsumeru.api.model.user.User
import com.atsumeru.api.network.AtsumeruService
import com.atsumeru.api.utils.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("unused")
object AtsumeruAPI {
    private lateinit var atsumeruService: AtsumeruService
    private lateinit var serverManager: ServerManager
    private lateinit var restAdapter: Retrofit
    private lateinit var httpClient: OkHttpClient
    private lateinit var gson: Gson
    private var isDebug: Boolean = false

    @JvmStatic
    fun init(builder: OkHttpClient.Builder, isDebug: Boolean) {
        this.isDebug = isDebug
        serverManager = ServerManager()

        gson = GsonBuilder().create()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        httpClient = builder.connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val requestBuilder = request.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", USER_AGENT)

                request = requestBuilder.build()
                chain.proceed(request)
            }
            .authenticator(Authenticator { _: Route, response: Response ->
                val credentials = serverManager.createBasicAuth() ?: return@Authenticator null
                return@Authenticator response.request().newBuilder().header("Authorization", credentials).build()
            })
            .build()

        restAdapter = Retrofit.Builder()
            .baseUrl(getEndpointUrl())
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        atsumeruService = restAdapter.create(AtsumeruService::class.java)
    }

    @JvmStatic
    fun changeServer(id: Int) {
        val baseUrl = serverManager.changeServer(id)
        if (baseUrl != null) {
            setMainUrl(baseUrl)
            atsumeruService = restAdapter.newBuilder().baseUrl(baseUrl).build()
                .create(AtsumeruService::class.java)
        }
    }

    @JvmStatic
    fun getServerManager(): ServerManager {
        return serverManager
    }

    //*****************************//
    //*          Server           *//
    //*****************************//
    @JvmStatic
    fun getServerInfo(): Single<ServerInfo> {
        return atsumeruService.getServerInfo()
    }

    @JvmStatic
    fun clearServerCache(): Single<AtsumeruMessage> {
        return atsumeruService.clearServerCache()
    }

    //*****************************//
    //*         Book List         *//
    //*****************************//
    @JvmStatic
    fun getBooksList(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        search: String? = null,
        contentType: String? = null,
        category: String? = null,
        sort: Sort = Sort.CREATED_AT,
        ascending: Boolean = false,
        page: Int = 1,
        limit: Int = 30,
        withVolumesAndHistory: Boolean = false,
        getAll: Boolean = false
    ): Single<List<Serie>> {
        return atsumeruService.getBooksList(
            libraryPresentation,
            search,
            contentType,
            category,
            sort,
            ascending,
            page,
            limit,
            withVolumesAndHistory,
            getAll
        )
    }

    //*****************************//
    //*   Books By Bound Service  *//
    //*****************************//
    @JvmStatic
    fun getBooksByBoundService(boundServiceName: String, boundServiceId: String): Single<List<Serie>> {
        return atsumeruService.getBooksByBoundService(boundServiceName, boundServiceId)
    }

    @JvmStatic
    fun checkLinksDownloaded(links: List<String>): Single<DownloadedLinks> {
        return atsumeruService.checkLinksDownloaded(HashMap<String, String>().apply {
            put(
                "links",
                links.joinToString(",")
            )
        })
    }

    //*****************************//
    //*         Filters           *//
    //*****************************//
    @JvmStatic
    fun getFiltersList(
        contentType: String?,
        category: String?,
        libraryPresentation: LibraryPresentation
    ): Single<List<Filters>> {
        return atsumeruService.getFiltersList(contentType, category, libraryPresentation)
    }

    @JvmStatic
    fun getFilteredList(
        contentType: String?,
        category: String?,
        libraryPresentation: LibraryPresentation,
        search: String?,
        sort: Sort?,
        ascending: Boolean?,
        filters: Map<String, String>,
        page: Int,
        limit: Int,
        withVolumesAndHistory: Boolean
    ): Single<List<Serie>> {
        return atsumeruService.getFilteredList(
            contentType,
            category,
            libraryPresentation,
            search,
            sort,
            ascending,
            filters,
            page,
            limit,
            withVolumesAndHistory
        )
    }

    @JvmStatic
    fun getFilteredList(
        contentType: String?, libraryPresentation: LibraryPresentation, search: String?, sort: Sort?,
        ascending: Boolean?, status: String?, translationStatus: String?, plotType: String?,
        censorship: String?, color: String?, ageRating: String?,
        authors: List<String>?, authorsMode: String?, artists: List<String>?, artistsMode: String?,
        publishers: List<String>?, publishersMode: String?, translators: List<String>?, translatorsMode: String?,
        genres: List<String>?, genresMode: String?, tags: List<String>?, tagsMode: String?,
        countries: List<String>?, countriesMode: String?, languages: List<String>?, languagesMode: String?,
        events: List<String>?, eventsMode: String?, characters: List<String>?, charactersMode: String?,
        series: List<String>?, seriesMode: String?, parodies: List<String>?, parodiesMode: String?,
        circles: List<String>?, circlesMode: String?, magazines: List<String>?, magazinesMode: String?, years: String?,
        page: Int, limit: Int, withVolumesAndHistory: Boolean
    ): Single<List<Serie>> {
        return atsumeruService.getFilteredList(
            contentType,
            libraryPresentation,
            search,
            sort,
            ascending,
            status,
            translationStatus,
            plotType,
            censorship,
            color,
            ageRating,
            authors,
            authorsMode,
            artists,
            artistsMode,
            publishers,
            publishersMode,
            translators,
            translatorsMode,
            genres,
            genresMode,
            tags,
            tagsMode,
            countries,
            countriesMode,
            languages,
            languagesMode,
            events,
            eventsMode,
            characters,
            charactersMode,
            series,
            seriesMode,
            parodies,
            parodiesMode,
            circles,
            circlesMode,
            magazines,
            magazinesMode,
            years,
            page,
            limit,
            withVolumesAndHistory
        )
    }

    //*****************************//
    //*      Metacategories       *//
    //*****************************//
    @JvmStatic
    fun getMetacategoriesList(): Single<List<Metacategory>> {
        return atsumeruService.getMetacategoriesList()
    }

    @JvmStatic
    fun getMetacategoryEntries(metacategoryId: String): Single<List<Metacategory>> {
        return atsumeruService.getMetacategoryEntries(metacategoryId)
    }

    @JvmStatic
    fun getMetacategoryEntryBooks(
        metacategoryId: String,
        metacategoryEntryId: String,
        page: Int,
        limit: Int,
        withVolumesAndHistory: Boolean
    ): Single<List<Serie>> {
        return atsumeruService.getMetacategoryEntryBooks(
            metacategoryId,
            metacategoryEntryId,
            page,
            limit,
            withVolumesAndHistory
        )
    }

    //*****************************//
    //*        Categories         *//
    //*****************************//
    @JvmStatic
    fun getCategoriesList(): Single<List<Category>> {
        return atsumeruService.getCategoriesList()
    }

    @JvmStatic
    fun setCategories(contentIdsWithCategories: Map<String, String>): Single<AtsumeruMessage> {
        return atsumeruService.setCategories(contentIdsWithCategories)
    }

    @JvmStatic
    fun orderCategories(changedCategories: List<Category>): Single<AtsumeruMessage> {
        return atsumeruService.orderCategories(changedCategories)
    }

    @JvmStatic
    fun createCategory(categoryName: String): Single<AtsumeruMessage> {
        return atsumeruService.createCategory(categoryName)
    }

    @JvmStatic
    fun editCategory(categoryId: String, categoryName: String): Single<AtsumeruMessage> {
        return atsumeruService.editCategory(categoryId, categoryName)
    }

    @JvmStatic
    fun deleteCategory(categoryId: String): Single<AtsumeruMessage> {
        return atsumeruService.deleteCategory(categoryId)
    }

    //*****************************//
    //*    Hub: New and Latest    *//
    //*****************************//
    @JvmStatic
    fun getBooksNewArrivals(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        ascendingOrder: Boolean = false,
        page: Int = 1,
        limit: Int = 50
    ): Single<List<Serie>> {
        return atsumeruService.getBooksNewArrivals(libraryPresentation, ascendingOrder, page, limit)
    }

    @JvmStatic
    fun getBooksLatestUpdates(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        ascendingOrder: Boolean = false,
        page: Int = 1,
        limit: Int = 50
    ): Single<List<Serie>> {
        return atsumeruService.getBooksLatestUpdates(libraryPresentation, ascendingOrder, page, limit)
    }

    //*****************************//
    //*       Hub: History        *//
    //*****************************//
    @JvmStatic
    fun getBooksHistory(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        page: Int = 1,
        limit: Int = 50
    ): Single<List<Serie>> {
        return atsumeruService.getBooksHistory(libraryPresentation, page, limit)
    }

    //*****************************//
    //*          Books            *//
    //*****************************//
    @JvmStatic
    fun getBookDetails(bookHash: String): Single<Serie> {
        return atsumeruService.getBookDetails(bookHash)
    }

    @JvmStatic
    fun getBookSeries(serieHash: String): Single<List<Serie>> {
        return atsumeruService.getBookSeries(serieHash)
    }

    @JvmStatic
    fun deleteBook(bookHash: String): Single<AtsumeruMessage> {
        return atsumeruService.deleteBook(bookHash)
    }

    //*****************************//
    //*         Volumes           *//
    //*****************************//
    @JvmStatic
    fun getBookVolumes(bookHash: String): Single<List<Volume>> {
        return atsumeruService.getBookVolumes(bookHash)
    }

    @JvmStatic
    fun getBookVolume(archiveHash: String): Single<Volume> {
        return atsumeruService.getBookVolume(archiveHash)
    }

    //*****************************//
    //*         Chapters          *//
    //*****************************//
    @JvmStatic
    fun getBookChapters(bookHash: String): Single<List<Chapter>> {
        return atsumeruService.getBookChapters(bookHash)
    }

    @JvmStatic
    fun getVolumeChapters(archiveHash: String): Single<List<Chapter>> {
        return atsumeruService.getVolumeChapters(archiveHash)
    }

    @JvmStatic
    fun getBookChapter(chapterHash: String): Single<Chapter> {
        return atsumeruService.getBookChapter(chapterHash)
    }

    //*****************************//
    //*         FileSystem        *//
    //*****************************//
    @JvmStatic
    fun getDirectoryListing(requestPath: String?): Single<DirectoryListing> {
        return atsumeruService.getDirectoryListing(DirectoryRequest(requestPath))
    }

    //*****************************//
    //*         Users             *//
    //*****************************//
    @JvmStatic
    fun getUserList(): Single<List<User>> {
        return atsumeruService.getUserList()
    }

    @JvmStatic
    fun getUserAccessConstants(): Single<UserAccessConstants> {
        return atsumeruService.getUserAccessConstants()
    }

    @JvmStatic
    fun createUser(user: User): Single<AtsumeruMessage> {
        return atsumeruService.createUser(user)
    }

    @JvmStatic
    fun updateUser(user: User): Single<AtsumeruMessage> {
        return atsumeruService.updateUser(user)
    }

    @JvmStatic
    fun deleteUser(userId: Long): Single<AtsumeruMessage> {
        return atsumeruService.deleteUser(userId)
    }

    //*****************************//
    //*         Services          *//
    //*****************************//
    @JvmStatic
    fun getServicesStatus(): Single<ServicesStatus> {
        return atsumeruService.getServicesStatus()
    }

    //*****************************//
    //*         Metadata          *//
    //*****************************//
    @JvmStatic
    fun getMetadataUpdateStatus(): Single<MetadataUpdateStatus> {
        return atsumeruService.getMetadataUpdateStatus()
    }

    @JvmStatic
    fun updateMetadata(
        serie: Serie,
        serieOnly: Boolean = false,
        saveIntoArchives: Boolean = false,
        saveIntoDBOnly: Boolean = false
    ): Single<AtsumeruMessage> {
        return atsumeruService.updateMetadata(serie, serieOnly, saveIntoArchives, saveIntoDBOnly)
    }

    @JvmStatic
    fun createUniqueIds(
        saveIntoArchives: Boolean = false,
        saveIntoDBOnly: Boolean = false,
        force: Boolean = false
    ): Single<AtsumeruMessage> {
        return atsumeruService.createUniqueIds(saveIntoArchives, saveIntoDBOnly, force)
    }

    @JvmStatic
    fun injectAllFromDatabase(): Single<AtsumeruMessage> {
        return atsumeruService.injectAllFromDatabase()
    }

    //*****************************//
    //*         Importer          *//
    //*****************************//
    @JvmStatic
    fun getImporterStatus(): Single<ImportStatus> {
        return atsumeruService.getImporterStatus()
    }

    @JvmStatic
    fun getImporterFoldersList(): Single<List<FolderProperty>> {
        return atsumeruService.getImporterFoldersList()
    }

    @JvmStatic
    fun addImporterFolder(folderProperty: FolderProperty): Single<AtsumeruMessage> {
        return atsumeruService.addImporterFolder(folderProperty)
    }

    @JvmStatic
    fun removeImporterFolder(folderHash: String): Single<AtsumeruMessage> {
        return atsumeruService.removeImporterFolder(folderHash)
    }

    @JvmStatic
    fun importerScan(): Single<AtsumeruMessage> {
        return atsumeruService.importerScan()
    }

    @JvmStatic
    fun importerRescan(updateCovers: Boolean): Single<AtsumeruMessage> {
        return atsumeruService.importerRescan(updateCovers)
    }

    @JvmStatic
    fun importerRescan(folderHash: String, fullRescan: Boolean, updateCovers: Boolean): Single<AtsumeruMessage> {
        return atsumeruService.importerRescan(folderHash, fullRescan, updateCovers)
    }

    //*****************************//
    //*         Settings          *//
    ///****************************//
    @JvmStatic
    fun getServerSettings(): Single<ServerSettings> {
        return atsumeruService.getServerSettings()
    }

    @JvmStatic
    fun updateServerSettings(serverSettings: ServerSettings): Single<AtsumeruMessage> {
        return atsumeruService.updateServerSettings(serverSettings)
    }

    //*****************************//
    //*           Sync            *//
    ///****************************//
    @JvmStatic
    fun getUpdateReadHistory(archiveHash: String, chapterHash: String? = null, page: Int = 1): Single<AtsumeruMessage> {
        return atsumeruService.getUpdateReadHistory(archiveHash, chapterHash, page)
    }

    @JvmStatic
    fun postUpdateReadHistory(values: Map<String, String>): Single<AtsumeruMessage> {
        return atsumeruService.postUpdateReadHistory(values)
    }

    @JvmStatic
    fun pullBookHistory(bookOrArchiveHash: String): Single<List<History>> {
        return atsumeruService.pullBookHistory(bookOrArchiveHash)
    }

    //*****************************//
    //*         Upload            *//
    //*****************************//
    @JvmStatic
    fun uploadFile(
        serieHash: String,
        filePath: String,
        progressListener: UploadProgressListener,
        overrideFiles: Boolean = false,
        repackFiles: Boolean = false
    ): Single<AtsumeruMessage>? {
        val file = File(filePath)
        val body = MultipartBody.Builder()
            .addFormDataPart("hash", serieHash)
            .addFormDataPart(
                "file",
                file.name,
                file.asProgressRequestBody(MediaType.parse("application/zip"), progressListener)
            )
            .build()

        val httpBuilder = HttpUrl.parse(getMainUrl() + "/api/v1/uploader/upload")!!
            .newBuilder()
            .addQueryParameter("override", overrideFiles.toString())
            .addQueryParameter("repack", repackFiles.toString())

        val request: Request = Request.Builder()
            .url(httpBuilder.build())
            .post(body)
            .build()

        return Single.create { subscriber: SingleEmitter<AtsumeruMessage> ->
            val response = httpClient.newCall(request).execute()
            val message = gson.fromJson(response.body()?.string(), AtsumeruMessage::class.java)
            subscriber.onSuccess(message)
        }
    }
}