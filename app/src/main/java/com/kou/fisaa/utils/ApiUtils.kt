package com.kou.fisaa.utils

import android.net.Uri
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


fun createPartFromString(descriptionString: String): RequestBody {
    return RequestBody.create(
        MultipartBody.FORM, descriptionString
    )
}

fun prepareImageFilePart(partName: String, imageUri: Uri): MultipartBody.Part {

    val file = File(imageUri.path ?: "")

    // create RequestBody instance from file
    val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

