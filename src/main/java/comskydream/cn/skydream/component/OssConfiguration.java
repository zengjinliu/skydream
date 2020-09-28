package comskydream.cn.skydream.component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/27 10:37
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(value = OssProperties.class)
public class OssConfiguration {

    private final OssProperties ossProperties;

    public OssConfiguration(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }


    @Bean
    public OSS ossClient(){
        return new OSSClientBuilder().build(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getAccessKeySecret());
    }



    /**
     * 创建bucketName bucket也就是存储空间，可以建立多个
     *
     * @param bucketName 存储空间
     */
    public void createBucket(String bucketName) {
//        Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
//        阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
//        String accessKeyId = "<yourAccessKeyId>";
//        String accessKeySecret = "<yourAccessKeySecret>";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getAccessKeySecret());
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 直接上传(不是很安全)
     *
     * @param objectName
     * @throws Exception
     */
//    public void uploadFile(String objectName) throws Exception {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
////        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
//        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
////        String accessKeyId = "<yourAccessKeyId>";
////        String accessKeySecret = "<yourAccessKeySecret>";
////        String bucketName = "<yourBucketName>";
//        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
////        String objectName = "<yourObjectName>";
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getAccessKeySecret());
//        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
//        InputStream inputStream = new FileInputStream("C:\\Users\\87137\\Pictures\\Saved Pictures\\jay.jpg");
//        ossClient.putObject(ossProperties.getBucketName(), ossProperties.getObjectName() + objectName, inputStream);
//        // 关闭OSSClient。
//        ossClient.shutdown();
//    }

}
