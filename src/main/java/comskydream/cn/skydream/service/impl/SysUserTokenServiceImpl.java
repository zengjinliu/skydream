package comskydream.cn.skydream.service.impl;

import comskydream.cn.skydream.auth.TokenGenerator;
import comskydream.cn.skydream.constant.TokenConstant;
import comskydream.cn.skydream.entity.SysUserToken;
import comskydream.cn.skydream.mapper.SysUserTokenMapper;
import comskydream.cn.skydream.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jayson
 * @date 2020/9/5  22:12
 */
@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public String createToken(String userId) {
        SysUserToken userToken = new SysUserToken();
        //有效时间
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TokenConstant.EXPIRE_TIME);
        //生成token
        String token = TokenGenerator.generateValue();
        //默认设置token过期时间为12小时
        SysUserToken sysUserToken = this.sysUserTokenMapper.selectByPrimaryKey(userId);
        if(Objects.isNull(sysUserToken)){
            userToken.setUserId(userId).setToken(token)
                    .setExpireTime(expireTime).setUpdateTime(LocalDateTime.now());
            this.sysUserTokenMapper.insertSelective(userToken);
        } else {
            //存在则更新token
            sysUserToken.setToken(token).setUpdateTime(LocalDateTime.now()).setExpireTime(expireTime);
            this.sysUserTokenMapper.updateByPrimaryKeySelective(sysUserToken);
        }
        return token;
    }

    @Override
    public void logout(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //修改token
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setUserId(userId).setToken(token);
        //更新token
        sysUserTokenMapper.updateByPrimaryKeySelective(sysUserToken);
    }

    @Override
    public SysUserToken getOne(SysUserToken userToken) {
        return sysUserTokenMapper.selectOne(userToken);
    }
}
