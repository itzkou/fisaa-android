package com.kou.fisaa.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


fun createPartFromString(descriptionString: String): RequestBody {
    return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString)
}

fun prepareImageFilePart(partName: String, file: File): MultipartBody.Part {

    // create RequestBody instance from file
    val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}



