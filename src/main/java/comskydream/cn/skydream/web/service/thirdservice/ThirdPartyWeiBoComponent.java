package comskydream.cn.skydream.web.service.thirdservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import comskydream.cn.skydream.component.WeiBoConfiguration;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.entity.SysUserThird;
import comskydream.cn.skydream.web.mapper.SysUserMapper;
import comskydream.cn.skydream.web.mapper.SysUserThirdMapper;
import comskydream.cn.skydream.model.dto.WeiBoDto;
import comskydream.cn.skydream.model.vo.ComprehensiveVo;
import comskydream.cn.skydream.model.vo.SysUserVo;
import comskydream.cn.skydream.web.service.sys.SysUserTokenService;
import comskydream.cn.skydream.utils.HttpUtils;
import comskydream.cn.skydream.utils.NameGeneratorUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/20  17:12
 */
@Component
@Slf4j
public class ThirdPartyWeiBoComponent extends AbstractThirdUserInfo{

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
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserThirdMapper thirdMapper;

    @Override
    protected Map<String, String> queryName(String uid, String accessToken) {
        Map<String,String> result = new HashMap<>(4);
        Map<String,Object> map = new HashMap<>(4);
        map.put("access_token",uid);
        map.put("uid",accessToken);
        try {
            String apiUrl ="https://api.weibo.com/2/users/show.json?uid="+uid+"&access_token="+accessToken;
            String json  = httpUtils.doGet(apiUrl);
            JsonObject object = JsonParser.parseString(json).getAsJsonObject();
            String name = object.get("name").getAsString();
            String pic = object.get("profile_image_url").getAsString();
            result.put("name",name);
            result.put("pic",pic);
        } catch (Exception e) {
            log.error("根据uid:{},accessToken:{},请求第三方用户信息异常e:{}",uid,accessToken,e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ComprehensiveVo build(String code) {
        ComprehensiveVo vo = new ComprehensiveVo();
        WeiBoDto dto = this.getAccessToken(code);
        Map<String,String> map = this.queryName(dto.getUid(),dto.getAccess_token());
        String name = map.get("name");
        String pic = map.get("pic");
        SysUserVo sysUserVo = this.saveOrUpdate(dto, name, pic);
        //保存到系统表
        vo.setT(dto);
        vo.setName(map.get("name"));
        vo.setToken(sysUserVo.getToken());
        vo.setUserId(sysUserVo.getUserId());
        vo.setUsername(sysUserVo.getUsername());
        return vo;
    }


    private WeiBoDto getAccessToken(String code) {
        Gson gson = new Gson();
        String accessToken = null;
        WeiBoDto weiBoDto = null;
        try {
            accessToken = weiBoConfiguration.getAccessToken(code);
            weiBoDto = gson.fromJson(accessToken, WeiBoDto.class);
        } catch (Exception e) {
            log.error("通过code:{},获取accessToken失败,异常信息e:{}", code, e.getMessage());
        }
        return weiBoDto;
    }

    private SysUserVo saveOrUpdate(WeiBoDto dto, String name,String pic){
        SysUserVo vo = new SysUserVo();
        //三方名字
        SysUserThird third = thirdMapper.selectByPrimaryKey(dto.getUid());
        if (third != null) {
            //更新用户
            String userId = third.getUserId();
            String username = sysUserMapper.selectByPrimaryKey(userId).getUsername();
            //更新token
            String token = this.sysUserTokenService.createToken(userId);
            thirdMapper.updateByPrimaryKeySelective(third);
            vo.setToken(token).setUserId(userId).setUsername(username);
        } else {
            //构造系统用户，userToken,UserThird
            SysUser s = this.createSysUser(pic);
            sysUserMapper.insertSelective(s);
            String token = sysUserTokenService.createToken(s.getUserId());
            thirdMapper.insert(this.createSysUserThird(dto,s.getUserId(),pic,name));
            vo.setUserId(s.getUserId()).setToken(token).setUsername(s.getUsername());
        }
        return vo;
    }

    private SysUser createSysUser(String pic){
        SysUser sysUser = new SysUser();
        sysUser.setUserId(UuidUtils.id()).setPic(pic)
                .setUsername(nameGeneratorUtils.generatorName())
                .setCreateTime(LocalDateTime.now());
        return sysUser;
    }

    private SysUserThird createSysUserThird(WeiBoDto dto,String userId,String pic,String name){
        SysUserThird third = new SysUserThird();
        third.setId(UuidUtils.id())
                .setUid(dto.getUid())
                .setUserId(userId)
                .setAccessToken(dto.getAccess_token())
                .setPic(pic)
                .setName(name)
                .setExpiresIn(dto.getExpires_in());
        return third;
    }
}
