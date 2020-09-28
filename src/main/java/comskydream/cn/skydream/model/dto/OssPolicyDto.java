package comskydream.cn.skydream.model.dto;

import lombok.Data;

/**
 * Oss签名后返回的对象
 *
 * @author Jayson
 * @date 2020/9/27 14:58
 */
@Data
public class OssPolicyDto {
    /**
     * 访问身份验证中用到用户标识
     */
    private String accessKeyId;

    /**
     * 用户表单上传的策略,经过base64编码过的字符串
     */
    private String policy;

    /**
     * "对policy签名后的字符串
     */
    private String signature;

    /**
     * 上传文件夹路径前缀
     */
    private String dir;

    /**
     * oss对外服务的访问域名
     */
    private String host;

    /**
     * 签名的有效期
     */
    private long expires;

}
