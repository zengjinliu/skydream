package comskydream.cn.skydream.service.thirdservice.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import comskydream.cn.skydream.auth.TokenGenerator;
import comskydream.cn.skydream.component.WeiBoConfiguration;
import comskydream.cn.skydream.constant.RedisConstant;
import comskydream.cn.skydream.model.dto.WeiBoDto;
import comskydream.cn.skydream.model.vo.SysUserVo;
import comskydream.cn.skydream.model.vo.ThirdLoginVo;
import comskydream.cn.skydream.service.sys.SysUserService;
import comskydream.cn.skydream.service.sys.SysUserTokenService;
import comskydream.cn.skydream.service.thirdservice.ThirdPartyWeiBoService;
import comskydream.cn.skydream.utils.HttpUtils;
import comskydream.cn.skydream.utils.NameGeneratorUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Jayson
 * @date 2020/9/20  17:12
 */
@Service
@Slf4j
public class ThirdPartyWeiBoServiceImpl implements ThirdPartyWeiBoService {

    @Autowired
    private WeiBoConfiguration weiBoConfiguration;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private NameGeneratorUtils nameGeneratorUtils;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public WeiBoDto getAccessToken(String code) {
        Gson gson = new Gson();
        String accessToken = null;
        WeiBoDto weiBoDto = null;
        try {
            accessToken = weiBoConfiguration.getAccessToken(code);
            weiBoDto = gson.fromJson(accessToken, WeiBoDto.class);
            //存入redis中
            redisTemplate.opsForValue().set(RedisConstant.WEIBO_REDIRET_INFO,weiBoDto,weiBoDto.getExpires_in(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("通过code:{},获取accessToken失败,异常信息e:{}", code, e.getMessage());
        }
        return weiBoDto;
    }

    @Override
    public ThirdLoginVo build() throws Exception {
        WeiBoDto dto = (WeiBoDto) redisTemplate.opsForValue().get(RedisConstant.WEIBO_REDIRET_INFO);
        if(Objects.isNull(dto)){
            return null;
        } else{
            ThirdLoginVo loginVo = createLoginVo(dto);
            SysUserVo userVo = createUserVo(loginVo);
            sysUserService.save(userVo);
            //保存到用户表中
            return loginVo;
        }
    }


    private ThirdLoginVo createLoginVo(WeiBoDto dto) throws Exception {
        ThirdLoginVo vo = new ThirdLoginVo();
        String id = UuidUtils.id();
        String token = sysUserTokenService.createToken(id);
        String username = nameGeneratorUtils.generatorName();
        //调用第三方登录api根据access_token查询用户名
        Map<String,Object> map = new HashMap<>();
        map.put("access_token",dto.getAccess_token());
        map.put("uid",dto.getUid());
        String json = httpUtils.doGet("https://api.weibo.com/2/users/show.json", map);
        JsonObject object = JsonParser.parseString(json).getAsJsonObject();
        String thirdName = object.get("name").getAsString();
        vo.setUserId(id).setUsername(username).setToken(token).setThirdName(thirdName);
        return vo;
    }

    private SysUserVo createUserVo(ThirdLoginVo loginVo){
        SysUserVo vo = new SysUserVo();
        vo.setUserId(loginVo.getUserId())
                .setToken(loginVo.getToken()).setUsername(loginVo.getUsername())
                .setCreateTime(LocalDateTime.now());
        return vo;
    }

}
