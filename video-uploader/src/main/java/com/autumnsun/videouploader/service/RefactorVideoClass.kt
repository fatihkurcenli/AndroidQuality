package com.autumnsun.videouploader.service

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class RefactorVideoClass() : Thread() {
    private lateinit var videoName: String
    private var segmentedName: String? = null
    private var parseDynamicVideoRange: Int = 10

    constructor(videoName: String, parseDynamicVideoRange: Int?) : this() {
        this.videoName = videoName
        this.segmentedName = videoName.split(".").toString()
        parseDynamicVideoRange?.let {
            this.parseDynamicVideoRange = it
        }
    }

    override fun run() {
        val commandFFmpeg =
            "ffmpeg -y -i $videoName -preset slow -g 48 -sc_threshold 0 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -s:v:0 1920*1080 -b:v:0 1800k -s:v:1 1280*720 -b:v:1 1200k -s:v:2 858*480 -b:v:2 750k -s:v:3 630*360 -b:v:3 550k -s:v:4 426*240 -b:v:4 400k -s:v:5 256*144 -b:v:5 200k -c:a copy -var_stream_map \"v:0,a:0,name:1080p v:1,a:1,name:720p v:2,a:2,name:480p v:3,a:3,name:360p v:4,a:4,name:240p v:5,a:5,name:144p\" -master_pl_name master.m3u8 -f hls -hls_time $parseDynamicVideoRange -hls_playlist_type vod -hls_list_size 0 -hls_segment_filename \"v%v/$segmentedName%d.ts\" v%v/index.m3u8"

        val builder = ProcessBuilder(
            "cmd.exe",
            "/c",
            "cd \"C:\\xampp\\htdocs\\hls-server\\input\" && $commandFFmpeg"
        )
        builder.redirectErrorStream(true)
        val progress = builder.start()
        val readBuffer = BufferedReader(InputStreamReader(progress.inputStream))
        var line: String?
        while (true) {
            line = readBuffer.readLine()
            if (line == null) {
                val temMp4Delete = File("C:\\xampp\\htdocs\\hls-server\\input\\$videoName")
                if (temMp4Delete.exists()) {
                    temMp4Delete.delete()
                }
                println("Video progress done")
                println("Now you can upload S3 AWS,Google Cloud Storage, Azure Storage or your server Storage")
                println("You have to parent folder and get master.m3u8 video url!")
                println("End set the your db video url")
                break
            }
            println(line)
        }
    }
}