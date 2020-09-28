package comskydream.cn.skydream.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jayson
 * @date 2020/9/27 10:34
 */
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    /**阿里云绑定的域名*/
    private String domain;

    /**阿里云路径前缀*/
    private String prefix;

    /**阿里云EndPoint*/
    private String endPoint;

    /**阿里云AccessKeyId*/
    private String accessKey;

    /**阿里云AccessKeySecret*/
    private String accessKeySecret;

    /**阿里云BucketName（存储空间名称）*/
    private String bucketName;

    /**最大上传文件大小*/
    private int maxSize;

    /**签名有效期*/
    private Long expire;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
