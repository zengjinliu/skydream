package comskydream.cn.skydream.web.service.sys;

import java.awt.image.BufferedImage;

/**
 * @author Jayson
 * @date 2020/9/5  16:45
 */
public interface SysCaptchaService {

    /**
     * 获取验证码图片
     * @param uuid
     * @return
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码校验
     * @param uuid 随机码
     * @param code 验证码
     * @return true成功，false失败
     */
    boolean validate(String uuid,String code);

}
