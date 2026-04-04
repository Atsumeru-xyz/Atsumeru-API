package xyz.atsumeru.api.network

import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.atsumeru.api.listeners.UploadProgressListener
import xyz.atsumeru.api.model.*
import xyz.atsumeru.api.model.category.Category
import xyz.atsumeru.api.model.filesystem.DirectoryListing
import xyz.atsumeru.api.model.filesystem.DirectoryRequest
import xyz.atsumeru.api.model.importer.FolderProperty
import xyz.atsumeru.api.model.importer.ImportStatus
import xyz.atsumeru.api.model.info.ServerInfo
import xyz.atsumeru.api.model.info.UserAccessConstants
import xyz.atsumeru.api.model.metacategory.Metacategory
import xyz.atsumeru.api.model.metadata.MetadataUpdateStatus
import xyz.atsumeru.api.model.server.Server
import xyz.atsumeru.api.model.services.ServicesStatus
import xyz.atsumeru.api.model.settings.ServerSettings
import xyz.atsumeru.api.model.sync.History
import xyz.atsumeru.api.model.user.AccessToken
import xyz.atsumeru.api.model.user.User
import xyz.atsumeru.api.utils.LibraryPresentation
import xyz.atsumeru.api.utils.Sort
import xyz.atsumeru.api.utils.asProgressRequestBody
import java.io.File

class AtsumeruServer(val server: Server, builder: OkHttpClient.Builder) {
    private val httpClient = builder.authenticator(Authenticator { _: Route?, response: Response ->
        return@Authenticator response.request.newBuilder()
            .header("Authorization", server.createBasicCredentials()).build()
    }).build()

    private val service: AtsumeruService = Retrofit.Builder()
        .baseUrl(server.host)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AtsumeruService::class.java)

    //*****************************//
    //*          Server           *//
    //*****************************//
    fun getServerInfo(): Single<ServerInfo> {
        return service.getServerInfo()
    }

    fun clearServerCache(): Single<AtsumeruMessage> {
        return service.clearServerCache()
    }

    //*****************************//
    //*         Book List         *//
    //*****************************//
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
        return service.getBooksList(
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
    fun getBooksByBoundService(boundServiceName: String, boundServiceId: String): Single<List<Serie>> {
        return service.getBooksByBoundService(boundServiceName, boundServiceId)
    }

    fun checkLinksDownloaded(links: List<String>): Single<DownloadedLinks> {
        return service.checkLinksDownloaded(HashMap<String, String>().apply {
            put(
                "links",
                links.joinToString(",")
            )
        })
    }

    //*****************************//
    //*         Filters           *//
    //*****************************//
    fun getFiltersList(
        contentType: String?,
        category: String?,
        libraryPresentation: LibraryPresentation
    ): Single<List<Filters>> {
        return service.getFiltersList(contentType, category, libraryPresentation)
    }

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
        return service.getFilteredList(
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
        return service.getFilteredList(
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
    fun getMetacategoriesList(): Single<List<Metacategory>> {
        return service.getMetacategoriesList()
    }

    fun getMetacategoryEntries(metacategoryId: String): Single<List<Metacategory>> {
        return service.getMetacategoryEntries(metacategoryId)
    }

    fun getMetacategoryEntryBooks(
        metacategoryId: String,
        metacategoryEntryId: String,
        page: Int,
        limit: Int,
        withVolumesAndHistory: Boolean
    ): Single<List<Serie>> {
        return service.getMetacategoryEntryBooks(
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
    fun getCategoriesList(): Single<List<Category>> {
        return service.getCategoriesList()
    }

    fun setCategories(contentIdsWithCategories: Map<String, String>): Single<AtsumeruMessage> {
        return service.setCategories(contentIdsWithCategories)
    }

    fun orderCategories(changedCategories: List<Category>): Single<AtsumeruMessage> {
        return service.orderCategories(changedCategories)
    }

    fun createCategory(categoryName: String): Single<AtsumeruMessage> {
        return service.createCategory(categoryName)
    }

    fun editCategory(categoryId: String, categoryName: String): Single<AtsumeruMessage> {
        return service.editCategory(categoryId, categoryName)
    }

    fun deleteCategory(categoryId: String): Single<AtsumeruMessage> {
        return service.deleteCategory(categoryId)
    }

    //*****************************//
    //*    Hub: New and Latest    *//
    //*****************************//
    fun getBooksNewArrivals(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        ascendingOrder: Boolean = false,
        page: Int = 1,
        limit: Int = 50
    ): Single<List<Serie>> {
        return service.getBooksNewArrivals(libraryPresentation, ascendingOrder, page, limit)
    }

    fun getBooksLatestUpdates(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        ascendingOrder: Boolean = false,
        page: Int = 1,
        limit: Int = 50
    ): Single<List<Serie>> {
        return service.getBooksLatestUpdates(libraryPresentation, ascendingOrder, page, limit)
    }

    //*****************************//
    //*       Hub: History        *//
    //*****************************//
    fun getBooksHistory(
        libraryPresentation: LibraryPresentation = LibraryPresentation.SERIES,
        page: Int = 1,
        limit: Int = 50
    ): Single<List<Serie>> {
        return service.getBooksHistory(libraryPresentation, page, limit)
    }

    //*****************************//
    //*          Books            *//
    //*****************************//
    fun getBookDetails(bookHash: String): Single<Serie> {
        return service.getBookDetails(bookHash)
    }

    fun getBookSeries(serieHash: String): Single<List<Serie>> {
        return service.getBookSeries(serieHash)
    }

    fun deleteBook(bookHash: String): Single<AtsumeruMessage> {
        return service.deleteBook(bookHash)
    }

    //*****************************//
    //*         Volumes           *//
    //*****************************//
    fun getBookVolumes(bookHash: String): Single<List<Volume>> {
        return service.getBookVolumes(bookHash)
    }

    fun getBookVolume(archiveHash: String): Single<Volume> {
        return service.getBookVolume(archiveHash)
    }

    //*****************************//
    //*         Chapters          *//
    //*****************************//
    fun getBookChapters(bookHash: String): Single<List<Chapter>> {
        return service.getBookChapters(bookHash)
    }

    fun getVolumeChapters(archiveHash: String): Single<List<Chapter>> {
        return service.getVolumeChapters(archiveHash)
    }

    fun getBookChapter(chapterHash: String): Single<Chapter> {
        return service.getBookChapter(chapterHash)
    }

    //*****************************//
    //*         FileSystem        *//
    //*****************************//
    fun getDirectoryListing(requestPath: String?): Single<DirectoryListing> {
        return service.getDirectoryListing(DirectoryRequest(requestPath))
    }

    //*****************************//
    //*         Users             *//
    //*****************************//
    fun getAboutMe(): Single<User> {
        return service.getAboutMe()
    }

    fun getAccessToken(): Single<AccessToken> {
        return service.getAccessToken()
    }

    fun getUserList(): Single<List<User>> {
        return service.getUserList()
    }

    fun getUserAccessConstants(): Single<UserAccessConstants> {
        return service.getUserAccessConstants()
    }

    fun createUser(user: User): Single<AtsumeruMessage> {
        return service.createUser(user)
    }

    fun updateUser(user: User): Single<AtsumeruMessage> {
        return service.updateUser(user)
    }

    fun deleteUser(userId: Long): Single<AtsumeruMessage> {
        return service.deleteUser(userId)
    }

    //*****************************//
    //*         Services          *//
    //*****************************//
    fun getServicesStatus(): Single<ServicesStatus> {
        return service.getServicesStatus()
    }

    //*****************************//
    //*         Metadata          *//
    //*****************************//
    fun getMetadataUpdateStatus(): Single<MetadataUpdateStatus> {
        return service.getMetadataUpdateStatus()
    }

    fun updateMetadata(
        serie: Serie,
        serieOnly: Boolean = false,
        saveIntoArchives: Boolean = false,
        saveIntoDBOnly: Boolean = false
    ): Single<AtsumeruMessage> {
        return service.updateMetadata(serie, serieOnly, saveIntoArchives, saveIntoDBOnly)
    }

    fun createUniqueIds(
        saveIntoArchives: Boolean = false,
        saveIntoDBOnly: Boolean = false,
        force: Boolean = false
    ): Single<AtsumeruMessage> {
        return service.createUniqueIds(saveIntoArchives, saveIntoDBOnly, force)
    }

    fun injectAllFromDatabase(): Single<AtsumeruMessage> {
        return service.injectAllFromDatabase()
    }

    //*****************************//
    //*         Importer          *//
    //*****************************//
    fun getImporterStatus(): Single<ImportStatus> {
        return service.getImporterStatus()
    }

    fun getImporterFoldersList(): Single<List<FolderProperty>> {
        return service.getImporterFoldersList()
    }

    fun addImporterFolder(folderProperty: FolderProperty): Single<AtsumeruMessage> {
        return service.addImporterFolder(folderProperty)
    }

    fun removeImporterFolder(folderHash: String): Single<AtsumeruMessage> {
        return service.removeImporterFolder(folderHash)
    }

    fun importerScan(): Single<AtsumeruMessage> {
        return service.importerScan()
    }

    fun importerRescan(updateCovers: Boolean): Single<AtsumeruMessage> {
        return service.importerRescan(updateCovers)
    }

    fun importerRescan(folderHash: String, fullRescan: Boolean, updateCovers: Boolean): Single<AtsumeruMessage> {
        return service.importerRescan(folderHash, fullRescan, updateCovers)
    }

    //*****************************//
    //*         Settings          *//
    ///****************************//
    fun getServerSettings(): Single<ServerSettings> {
        return service.getServerSettings()
    }

    fun updateServerSettings(serverSettings: ServerSettings): Single<AtsumeruMessage> {
        return service.updateServerSettings(serverSettings)
    }

    //*****************************//
    //*           Sync            *//
    ///****************************//
    fun getUpdateReadHistory(archiveHash: String, chapterHash: String? = null, page: Int = 1): Single<AtsumeruMessage> {
        return service.getUpdateReadHistory(archiveHash, chapterHash, page)
    }

    fun postUpdateReadHistory(values: Map<String, String>): Single<AtsumeruMessage> {
        return service.postUpdateReadHistory(values)
    }

    fun pullBookHistory(bookOrArchiveHash: String): Single<List<History>> {
        return service.pullBookHistory(bookOrArchiveHash)
    }

    //*****************************//
    //*         Upload            *//
    //*****************************//
    fun uploadFile(
        serieHash: String,
        filePath: String,
        progressListener: UploadProgressListener,
        overrideFiles: Boolean = false,
        repackFiles: Boolean = false
    ): Single<AtsumeruMessage> {
        val file = File(filePath)
        val body = MultipartBody.Builder()
            .addFormDataPart("hash", serieHash)
            .addFormDataPart(
                "file",
                file.name,
                file.asProgressRequestBody("application/zip".toMediaTypeOrNull(), progressListener)
            )
            .build()

        val httpBuilder = (server.host + "/api/v1/uploader/upload").toHttpUrlOrNull()!!
            .newBuilder()
            .addQueryParameter("override", overrideFiles.toString())
            .addQueryParameter("repack", repackFiles.toString())

        val request: Request = Request.Builder()
            .url(httpBuilder.build())
            .post(body)
            .build()

        return Single.create { subscriber: SingleEmitter<AtsumeruMessage> ->
            val response = httpClient.newCall(request).execute()
            val message = GsonBuilder().create().fromJson(response.body.string(), AtsumeruMessage::class.java)
            subscriber.onSuccess(message)
        }
    }
}