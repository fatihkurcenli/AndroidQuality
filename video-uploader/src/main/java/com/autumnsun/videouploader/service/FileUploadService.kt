package com.autumnsun.videouploader.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File


@Service
@Component
class FileUploadService {

    fun uploadFileLocal(file: MultipartFile): Boolean {
        return file.contentType?.let {
            if (it.contains("mp4")) {
                file.transferTo(File("C:\\xampp\\htdocs\\hls-server\\input\\" + file.originalFilename))
                val refactorVideoClass = RefactorVideoClass(file.originalFilename, null)
                refactorVideoClass.start()
                true
            } else {
                false
            }
        } ?: false
    }
}