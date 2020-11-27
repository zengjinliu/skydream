package comskydream.cn.skydream.web.service.thirdservice.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import comskydream.cn.skydream.component.OssProperties;
import comskydream.cn.skydream.model.dto.OssPolicyDto;
import comskydream.cn.skydream.web.service.thirdservice.OssClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Jayson
 * @date 2020/9/27 15:02
 */
@Slf4j
@Service
public class OssClientServiceImpl implements OssClientService {

    private final OssProperties ossProperties;

    public OssClientServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Override
    public OssPolicyDto policy() {
        OssPolicyDto ossPolicyDto = new OssPolicyDto();
        // host的格式为 bucketname.endpoint
        String host = "https://" + ossProperties.getBucketName() + "." + ossProperties.getEndPoint();
        String s = LocalDate.now().toString();
        // 用户上传文件时指定的前缀。配置的前缀加上时间日期
        String dir = ossProperties.getPrefix() + s;
        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndPoint(), ossProperties.getAccessKey(), ossProperties.getAccessKeySecret());
        try {
            long expire = System.currentTimeMillis() + ossProperties.getExpire() * 1000;
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, ossProperties.getMaxSize());
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(new Date(expire), policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            ossPolicyDto.setAccessKeyId(ossProperties.getAccessKey());
            ossPolicyDto.setPolicy(encodedPolicy);
            ossPolicyDto.setSignature(postSignature);
            ossPolicyDto.setDir(dir);
            ossPolicyDto.setHost(host);
            ossPolicyDto.setExpires(expire);
        } catch (Exception e) {
            log.error("签名后上传文件异常e:{}", e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return ossPolicyDto;
    }

}
