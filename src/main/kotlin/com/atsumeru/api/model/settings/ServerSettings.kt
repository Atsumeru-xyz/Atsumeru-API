package com.atsumeru.api.model.settings

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ServerSettings : Serializable {
    @SerializedName("allow_loading_list_with_volumes")
    var allowLoadingListWithVolumes: Boolean = false

    @SerializedName("allow_loading_list_with_chapters")
    var allowLoadingListWithChapters: Boolean = false

    @SerializedName("disable_request_logging_into_console")
    var disableRequestLoggingIntoConsole: Boolean = false

    @SerializedName("disable_bonjour_service")
    var disableBonjourService: Boolean = false

    @SerializedName("disable_file_watcher")
    var disableFileWatcher: Boolean = false

    @SerializedName("disable_watch_for_modified_files")
    var disableWatchForModifiedFiles: Boolean = false

    @SerializedName("disable_chapters")
    var disableChapters: Boolean = false
}