package com.bjyw.bjckyh.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

object FileProviderUtil {
    fun getFileUri(
        context: Context?,
        file: File?,
        authority: String?
    ): Uri? {
        var uri: Uri? = null
        uri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(context!!, authority!!, file!!)
        } else {
            Uri.fromFile(file)
        }
        return uri
    }
}
