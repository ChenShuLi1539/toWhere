package com.gangoffive.project.demo.controller;


import com.gangoffive.project.demo.tool.Ossoperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/oss")
@CrossOrigin()
public class OssController {

    @Autowired
    Ossoperate ossoperate;

    @PostMapping("/upfile")
    @ResponseBody
    public String upfile(@RequestBody Map<String, String> map){
        return ossoperate.upfile(map.get("content"));
    }

    @PostMapping("/getfile")
    @ResponseBody
    public String getfile(@RequestBody Map<String, String> map) throws IOException {
        return ossoperate.getfile(map.get("objectName"));
    }

    @PostMapping("/upInstream")
    @ResponseBody
    public String upInstream(MultipartFile file,int studentId,String Name,int NoticeId) throws IOException {
        return ossoperate.upInstream(file,studentId,Name,NoticeId);
    }
}
