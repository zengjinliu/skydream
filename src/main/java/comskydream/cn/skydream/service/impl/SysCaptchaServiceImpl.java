package comskydream.cn.skydream.service.impl;

import com.google.code.kaptcha.Producer;
import comskydream.cn.skydream.entity.SysCaptcha;
import comskydream.cn.skydream.mapper.SysCaptchaMapper;
import comskydream.cn.skydream.service.SysCaptchaService;
import comskydream.cn.skydream.utils.DateUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jayson
 * @date 2020/9/5  16:53
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {

    @Autowired
    private Producer producer;
    @Autowired
    private SysCaptchaMapper sysCaptchaMapper;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("uuid 不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        //生成验证码实体类，5分钟后过期
        SysCaptcha sysCaptcha = SysCaptcha.builder().code(code)
                .uuid(uuid)
                .expireTime(DateUtils.add(5)).build();
        sysCaptchaMapper.insert(sysCaptcha);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        //查询验证码是否存在
        SysCaptcha sysCaptcha = this.sysCaptchaMapper.selectByPrimaryKey(uuid);
        if(Objects.isNull(sysCaptcha)){
            return false;
        }
        //删除验证码
        this.sysCaptchaMapper.deleteByPrimaryKey(uuid);
        //判断验证码是否一致，且没有超过过期时间
        LocalDateTime expireTime = sysCaptcha.getExpireTime();
        if(sysCaptcha.getCode().equalsIgnoreCase(code) && DateUtils.getMillis(expireTime)>=System.currentTimeMillis()){
            return true;
        }
        return false;
    }
}
