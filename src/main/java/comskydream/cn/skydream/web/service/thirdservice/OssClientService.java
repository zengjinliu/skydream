package comskydream.cn.skydream.web.service.thirdservice;

import comskydream.cn.skydream.model.dto.OssPolicyDto;


/**
 * 图片存储签名后上传
 * @author Jayson
 * @date 2020/9/27 14:57
 */
public interface OssClientService {

    /**
     * oss上传策略生成
     * @return  OssPolicyDto
     */
    OssPolicyDto policy();

}
