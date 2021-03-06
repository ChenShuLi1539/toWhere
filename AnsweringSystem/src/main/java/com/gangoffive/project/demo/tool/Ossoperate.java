package com.gangoffive.project.demo.tool;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.gangoffive.project.demo.mapper.UserMapper;
import com.gangoffive.project.demo.mapper.VideoNoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Service
public class Ossoperate {
    protected static final Logger log = LoggerFactory.getLogger(Ossoperate.class);

    @Value("oss-cn-beijing.aliyuncs.com")
    String endpoint;
    @Value("LTAI4GFoNN3ZAHUK3go7nGff")
    String accessKeyId;
    @Value("CrdmfPDPztpEItsSGyJKRy72H7Wspl")
    String accessKeySecret;
    @Value("langwenjun")
    String bucketName;

    @Autowired
    UserMapper userMapper;
    @Autowired
    VideoNoticeMapper videoNoticeMapper;

    //文件存储目录
    String filedir = "gangoffive/video/";




    //这里是后期维护函数，非工作人员请不要调用
    public String upFileByByte(String content){
        System.out.println("endpoint:"+endpoint);
        System.out.println("bucketName:"+bucketName);
        return "loginRegistBiz.login(map)";
    }


    public String upfile(String content){
        System.out.println("endpoint:"+endpoint);
        System.out.println("bucketName:"+bucketName);
        System.out.println("????");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        System.out.println("content:"+content);


//        Random random = new Random();
//        ArrayList<Integer> list=userMapper.getallId();
//        for (Integer i:list) {
//            content="D:\\testdata\\teacher\\"+random.nextInt(40)+".jpg";
//            File files=new File(content);
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "gangoffive/image/"+i+".jpg",files);
//            ossClient.putObject(putObjectRequest);
//            userMapper.updateImg(i,"https://langwenjun.oss-cn-beijing.aliyuncs.com/"+"gangoffive/image/"+i+".jpg");
//            System.out.println(content);
//        }
        ossClient.shutdown();


//        File files=new File(content);
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "gangoffive/image/"+files.getName(),files);
// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);
// 上传文件。
//        System.out.println(ossClient.putObject(putObjectRequest));
//// 关闭OSSClient。
//        ossClient.shutdown();
        return "loginRegistBiz.login(map)";
    }

    public String getfile(String objectName) throws IOException {
        objectName="gangoffive/image/"+objectName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        System.out.println("content:"+ossObject.getObjectContent());
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            System.out.println(line);
        }
        ossClient.shutdown();
        return "ok";
    }

    public String upInstream(MultipartFile file,int studentId,String Name,int NoticeId) throws IOException {
//        System.out.println("studentId:"+studentId);
//        System.out.println("Name:"+Name);
//        System.out.println("NoticeId:"+NoticeId);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String videoInfor=Name+"-"+NoticeId+"-"+studentId+now.replace(" ","").replace(":","").replace("-","")+".webm";
//        System.out.println(videoInfor);

        Timestamp time = Timestamp.valueOf(now);
//        System.out.println(time);

        try{
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            byte[] filestream=file.getBytes();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "gangoffive/video/"+videoInfor, new ByteArrayInputStream(filestream));
            ossClient.putObject(putObjectRequest);
            videoNoticeMapper.addVideoTime(Name,studentId,time,"https://langwenjun.oss-cn-beijing.aliyuncs.com/gangoffive/video/"+videoInfor,NoticeId);
            ossClient.shutdown();
        }catch (Exception e){
            System.out.println("文件有问题");
            return "文件传输有问题";

        }

        return "文件传输成功"+"url:"+"https://langwenjun.oss-cn-beijing.aliyuncs.com/gangoffive/video/"+videoInfor;
    }
}
