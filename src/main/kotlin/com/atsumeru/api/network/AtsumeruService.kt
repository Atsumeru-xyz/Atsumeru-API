package com.atsumeru.api.network

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
import com.atsumeru.api.utils.LibraryPresentation
import com.atsumeru.api.utils.Sort
import io.reactivex.Single
import retrofit2.http.*

interface AtsumeruService {

    //*****************************//
    //*          Server           *//
    //*****************************//
    @GET("/api/server/info")
    fun getServerInfo(): Single<ServerInfo>

    @GET("/api/server/clear_cover_cache")
    fun clearServerCache(): Single<AtsumeruMessage>

    //*****************************//
    //*         Book List         *//
    //*****************************//
    @GET("/api/v1/books")
    fun getBooksList(@Query("presentation") libraryPresentation: LibraryPresentation,
                     @Query("search") search: String?,
                     @Query("type") contentType: String?,
                     @Query("category") category: String?,
                     @Query("sort") sort: Sort?,
                     @Query("asc") ascending: Boolean?,
                     @Query("page") page: Int,
                     @Query("limit") limit: Int,
                     @Query("with_volumes") withVolumesAndHistory: Boolean,
                     @Query("all") all: Boolean): Single<List<Serie>>

    //*****************************//
    //*   Books By Bound Service  *//
    //*****************************//
    @GET("/api/v1/books/{bound_service_name}/{bound_service_id}")
    fun getBooksByBoundService(@Path("bound_service_name") boundServiceName: String,
                               @Path("bound_service_id") boundServiceId: String): Single<List<Serie>>

    @FormUrlEncoded
    @POST("/api/v1/books/check_downloaded")
    fun checkLinksDownloaded(@FieldMap filters: Map<String, String>): Single<DownloadedLinks>

    //*****************************//
    //*         Filters           *//
    //*****************************//
    @GET("/api/v1/books/filters")
    fun getFiltersList(@Query("type") contentType: String?,
                       @Query("category") category: String?,
                       @Query("presentation") libraryPresentation: LibraryPresentation): Single<List<Filters>>

    @FormUrlEncoded
    @POST("/api/v1/books/filtered")
    fun getFilteredList(@Query("type") contentType: String?,
                        @Query("category") category: String?,
                        @Query("presentation") libraryPresentation: LibraryPresentation,
                        @Query("search") search: String?,
                        @Query("sort") sort: Sort?,
                        @Query("asc") ascending: Boolean?,
                        @FieldMap filters: Map<String, String>,
                        @Query("page") page: Int,
                        @Query("limit") limit: Int,
                        @Query("with_volumes") withVolumesAndHistory: Boolean): Single<List<Serie>>

    @GET("/api/v1/books/filtered")
    fun getFilteredList(@Query("content_type") contentType: String?,
                        @Query("presentation") libraryPresentation: LibraryPresentation,
                        @Query("search") search: String?,
                        @Query("sort") sort: Sort?,
                        @Query("asc") ascending: Boolean?,
                        @Query("status") status: String?,
                        @Query("translation_status") translationStatus: String?,
                        @Query("plot_type") plotType: String?,
                        @Query("censorship") censorship: String?,
                        @Query("color") color: String?,
                        @Query("age_rating") ageRating: String?,
                        @Query("authors") authors: List<String>?,
                        @Query("authors_mode") authorsMode: String?,
                        @Query("artists") artists: List<String>?,
                        @Query("artists_mode") artistsMode: String?,
                        @Query("publishers") publishers: List<String>?,
                        @Query("publishers_mode") publishersMode: String?,
                        @Query("translators") translators: List<String>?,
                        @Query("translators_mode") translatorsMode: String?,
                        @Query("genres") genres: List<String>?,
                        @Query("genres_mode") genresMode: String?,
                        @Query("tags") tags: List<String>?,
                        @Query("tags_mode") tagsMode: String?,
                        @Query("countries") countries: List<String>?,
                        @Query("countries_mode") countriesMode: String?,
                        @Query("languages") languages: List<String>?,
                        @Query("languages_mode") languagesMode: String?,
                        @Query("events") events: List<String>?,
                        @Query("events_mode") eventsMode: String?,
                        @Query("characters") characters: List<String>?,
                        @Query("characters_mode") charactersMode: String?,
                        @Query("series") series: List<String>?,
                        @Query("series_mode") seriesMode: String?,
                        @Query("parodies") parodies: List<String>?,
                        @Query("parodies_mode") parodiesMode: String?,
                        @Query("circles") circles: List<String>?,
                        @Query("circles_mode") circlesMode: String?,
                        @Query("magazines") magazines: List<String>?,
                        @Query("magazines_mode") magazinesMode: String?,
                        @Query("years") years: String?,
                        @Query("page") page: Int,
                        @Query("limit") limit: Int,
                        @Query("with_volumes") withVolumesAndHistory: Boolean): Single<List<Serie>>

    //*****************************//
    //*      Metacategories       *//
    //*****************************//
    @GET("/api/v1/books/metacategories")
    fun getMetacategoriesList(): Single<List<Metacategory>>

    @GET("/api/v1/books/metacategories/{metacategory_id}")
    fun getMetacategoryEntries(@Path("metacategory_id") metacategoryId: String): Single<List<Metacategory>>

    @GET("/api/v1/books/metacategories/{metacategory_id}/{metacategory_entry_id}")
    fun getMetacategoryEntryBooks(@Path("metacategory_id") metacategoryId: String,
                                  @Path("metacategory_entry_id") metacategoryEntryId: String,
                                  @Query("page") page: Int,
                                  @Query("limit") limit: Int,
                                  @Query("with_volumes") withVolumesAndHistory: Boolean): Single<List<Serie>>

    //*****************************//
    //*        Categories         *//
    //*****************************//
    @GET("/api/v1/books/categories")
    fun getCategoriesList(): Single<List<Category>>

    @FormUrlEncoded
    @POST("/api/v1/books/categories/set")
    fun setCategories(@FieldMap contentIdsWithCategories: Map<String, String>): Single<AtsumeruMessage>

    @POST("/api/v1/books/categories/order")
    fun orderCategories(@Body changedCategories: List<Category>): Single<AtsumeruMessage>

    @PUT("/api/v1/books/categories/create")
    fun createCategory(@Query("name") categoryName: String): Single<AtsumeruMessage>

    @PATCH("/api/v1/books/categories/edit")
    fun editCategory(@Query("id") categoryId: String,
                     @Query("name") categoryName: String): Single<AtsumeruMessage>

    @DELETE("/api/v1/books/categories/delete")
    fun deleteCategory(@Query("id") categoryId: String): Single<AtsumeruMessage>

    //*****************************//
    //*    Hub: New and Latest    *//
    //*****************************//
    @GET("/api/v1/books/new")
    fun getBooksNewArrivals(@Query("presentation") libraryPresentation: LibraryPresentation,
                            @Query("asc") ascendingOrder: Boolean,
                            @Query("page") page: Int,
                            @Query("limit") limit: Int): Single<List<Serie>>

    @GET("/api/v1/books/updates")
    fun getBooksLatestUpdates(@Query("presentation") libraryPresentation: LibraryPresentation,
                              @Query("asc") ascendingOrder: Boolean,
                              @Query("page") page: Int,
                              @Query("limit") limit: Int): Single<List<Serie>>

    //*****************************//
    //*       Hub: History        *//
    //*****************************//
    @GET("/api/v1/books/history")
    fun getBooksHistory(@Query("presentation") libraryPresentation: LibraryPresentation,
                        @Query("page") page: Int,
                        @Query("limit") limit: Int): Single<List<Serie>>

    //*****************************//
    //*          Books            *//
    //*****************************//
    @GET("/api/v1/books/{book_hash}")
    fun getBookDetails(@Path("book_hash") bookHash: String): Single<Serie>

    @GET("/api/v1/books/{serie_hash}/serie")
    fun getBookSeries(@Path("serie_hash") serieHash: String): Single<List<Serie>>

    @DELETE("/api/v1/books/delete/{book_hash}")
    fun deleteBook(@Path("book_hash") bookHash: String): Single<AtsumeruMessage>

    //*****************************//
    //*         Volumes           *//
    //*****************************//
    @GET("/api/v1/books/{book_hash}/volumes")
    fun getBookVolumes(@Path("book_hash") bookHash: String): Single<List<Volume>>

    @GET("/api/v1/books/volumes/{archive_hash}")
    fun getBookVolume(@Path("archive_hash") archiveHash: String): Single<Volume>

    //*****************************//
    //*         Chapters          *//
    //*****************************//
    @GET("/api/v1/books/{book_hash}/chapters")
    fun getBookChapters(@Path("book_hash") bookHash: String): Single<List<Chapter>>

    @GET("/api/v1/books/volumes/{archive_hash}/chapters")
    fun getVolumeChapters(@Path("archive_hash") archiveHash: String): Single<List<Chapter>>

    @GET("/api/v1/books/chapters/{chapter_hash}")
    fun getBookChapter(@Path("chapter_hash") chapterHash: String): Single<Chapter>

    //*****************************//
    //*         FileSystem        *//
    //*****************************//
    @POST("/api/v1/files/explore")
    fun getDirectoryListing(@Body directoryRequest: DirectoryRequest): Single<DirectoryListing>

    //*****************************//
    //*         Users             *//
    //*****************************//
    @GET("/api/v1/users/list")
    fun getUserList(): Single<List<User>>

    @GET("/api/v1/users/constants")
    fun getUserAccessConstants(): Single<UserAccessConstants>

    @PUT("/api/v1/users/create")
    fun createUser(@Body user: User): Single<AtsumeruMessage>

    @PATCH("/api/v1/users/update")
    fun updateUser(@Body user: User): Single<AtsumeruMessage>

    @DELETE("/api/v1/users/delete")
    fun deleteUser(@Query("user_id") userId: Long): Single<AtsumeruMessage>

    //*****************************//
    //*         Services          *//
    //*****************************//
    @GET("/api/v1/services/status")
    fun getServicesStatus(): Single<ServicesStatus>

    //*****************************//
    //*         Metadata          *//
    //*****************************//
    @GET("/api/v1/metadata/status")
    fun getMetadataUpdateStatus(): Single<MetadataUpdateStatus>

    @PATCH("/api/v1/metadata/update")
    fun updateMetadata(@Body serie: Serie,
                       @Query("serie_only") serieOnly: Boolean,
                       @Query("into_archives") saveIntoArchives: Boolean,
                       @Query("into_db_only") saveIntoDBOnly: Boolean): Single<AtsumeruMessage>

    @GET("/api/v1/metadata/create_unique_hashes")
    fun createUniqueIds(@Query("into_archives") saveIntoArchives: Boolean,
                        @Query("into_db_only") saveIntoDBOnly: Boolean,
                        @Query("force") force: Boolean): Single<AtsumeruMessage>

    @GET("/api/v1/metadata/inject_all")
    fun injectAllFromDatabase(): Single<AtsumeruMessage>

    //*****************************//
    //*         Importer          *//
    //*****************************//
    @GET("/api/v1/importer/status")
    fun getImporterStatus(): Single<ImportStatus>

    @GET("/api/v1/importer/list")
    fun getImporterFoldersList(): Single<List<FolderProperty>>

    @POST("/api/v1/importer/add")
    fun addImporterFolder(@Body folderProperty: FolderProperty): Single<AtsumeruMessage>

    @DELETE("/api/v1/importer/remove/{folder_hash}")
    fun removeImporterFolder(@Path("folder_hash") folderHash: String): Single<AtsumeruMessage>

    @GET("/api/v1/importer/scan")
    fun importerScan(): Single<AtsumeruMessage>

    @GET("/api/v1/importer/rescan")
    fun importerRescan(@Query("update_covers") updateCovers: Boolean): Single<AtsumeruMessage>

    @GET("/api/v1/importer/rescan/{folder_hash}")
    fun importerRescan(@Path("folder_hash") folderHash: String,
                       @Query("fully") fullRescan: Boolean,
                       @Query("update_covers") updateCovers: Boolean): Single<AtsumeruMessage>

    //*****************************//
    //*         Settings          *//
    ///****************************//
    @GET("/api/v1/settings/get")
    fun getServerSettings(): Single<ServerSettings>

    @POST("/api/v1/settings/update")
    fun updateServerSettings(@Body serverSettings: ServerSettings): Single<AtsumeruMessage>

    //*****************************//
    //*           Sync            *//
    //*****************************//
    @GET("/api/v1/books/sync/push")
    fun getUpdateReadHistory(@Query("archive_hash") archiveHash: String,
                             @Query("chapter_hash") chapterHash: String?,
                             @Query("page") page: Int): Single<AtsumeruMessage>

    @FormUrlEncoded
    @POST("/api/v1/books/sync/push")
    fun postUpdateReadHistory(@FieldMap values: Map<String, String>): Single<AtsumeruMessage>

    @GET("/api/v1/books/sync/pull/{book_or_archive_hash}")
    fun pullBookHistory(@Path("book_or_archive_hash") bookOrArchiveHash: String): Single<List<History>>

    //*****************************//
    //*         Upload            *//
    //*****************************//
//    @Multipart
//    @POST("/api/v1/uploader/upload")
//    fun uploadFile(@Part("hash") hashBody: RequestBody, @Part("file") body: MultipartBody): Single<AtsumeruMessage>
}
