package com.autumnsun.videouploader.controller

import com.autumnsun.videouploader.model.SuccessErrorModel
import com.autumnsun.videouploader.service.FileUploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/videos")
class VideoController {

    @Autowired
    private lateinit var fileUploadService: FileUploadService

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    fun uploadVideo(@RequestParam file: MultipartFile): SuccessErrorModel {
        val success = fileUploadService.uploadFileLocal(file)
        val model = SuccessErrorModel()
        if (success) {
            model.success = "Success"
        } else {
            model.error = "Failed"
        }
        return model
    }
}