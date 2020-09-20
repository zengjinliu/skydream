package comskydream.cn.skydream.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.component.MessageSendConfiguration;
import comskydream.cn.skydream.constant.RedisConstant;
import comskydream.cn.skydream.constant.SysConstant;
import comskydream.cn.skydream.converter.SysUserConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.entity.SysUserRole;
import comskydream.cn.skydream.mapper.SysUserMapper;
import comskydream.cn.skydream.mapper.SysUserRoleMapper;
import comskydream.cn.skydream.model.dto.MsgCodeDto;
import comskydream.cn.skydream.model.vo.PasswordVo;
import comskydream.cn.skydream.model.vo.SysUserVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.service.SysUserService;
import comskydream.cn.skydream.service.SysUserTokenService;
import comskydream.cn.skydream.utils.DateUtils;
import comskydream.cn.skydream.utils.NameGeneratorUtils;
import comskydream.cn.skydream.utils.SysUserUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jayson
 * @date 2020/9/5  22:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserConverter userConverter;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private NameGeneratorUtils nameGeneratorUtils;
    @Autowired
    private MessageSendConfiguration messageSendConfiguration;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SysUserTokenService sysUserTokenService;


    @Override
    public ResultPage<List<SysUserVo>> pageList(SysUserVo sysUserVo) {
        SysUser sysUser = userConverter.toPo(sysUserVo);
        PageHelper.startPage(sysUserVo.getPage(), sysUserVo.getRows());
        List<SysUser> list = sysUserMapper.list(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return userConverter.toPageVo(pageInfo);
    }

    @Override
    public SysUser getOne(SysUser user) {
        return sysUserMapper.getOne(user);
    }

    @Override
    public List<SysUser> list(SysUser sysUser) {
        return sysUserMapper.list(sysUser);
    }

    @Override
    public Set<String> queryAllPermission(String userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if (userId.equals(SysConstant.ADMIN)) {
            List<SysMenu> menuList = sysMenuService.queryList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public List<String> queryAllMenuIds(String userId) {
        return sysUserMapper.getAllMenuIds(userId);
    }

    @Override
    public void updatePwd(PasswordVo passwordVo) {
        SysUser user = SysUserUtils.getUser();
        String npwd = new Sha256Hash(passwordVo.getNpwd(), user.getSalt()).toHex();
        SysUser sysUser = SysUser.builder().userId(user.getUserId()).password(npwd).build();
        this.sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(SysUserVo sysUser) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setUserId(UuidUtils.id()).setCreateTime(LocalDateTime.now())
                .setPassword(new Sha256Hash(sysUser.getPassword(), salt).toHex());
        SysUser po = userConverter.toPo(sysUser);
        po.setSalt(salt).setCreateBy(SysUserUtils.getUserId());
        //保存用户与角色的关系
        List<SysUserRole> build = this.build(po.getUserId(), sysUser.getRoleIds());
        if (!CollectionUtils.isEmpty(build)) {
            build.forEach(userRoleMapper::insertSelective);
        }
        return sysUserMapper.insertSelective(po);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> userIds) {
        userIds.forEach(sysUserMapper::deleteByPrimaryKey);
        //删除用户与角色的关系
        userIds.forEach(userRoleMapper::deleteByUserId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(SysUserVo sysUser) {
        SysUser po = userConverter.toPo(sysUser);
        //更新用户与角色的关系
        userRoleMapper.deleteByUserId(sysUser.getUserId());
        List<SysUserRole> list = this.build(sysUser.getUserId(), sysUser.getRoleIds());
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(userRoleMapper::insertSelective);
        }
        return sysUserMapper.updateByPrimaryKeySelective(po);
    }

    @Override
    public Boolean checkNameExist(String username) {
        SysUser user = this.getOne(SysUser.builder().username(username).build());
        return user != null;
    }

    @Override
    public ResultJson msgLogin(String phone, String msgCode) {
        //TODO 防止脚本刷验证码考虑后期采用图片验证码的形式
        Gson gson = new Gson();
        //1.(校验验证码是否失效)
        String s = redisTemplate.opsForValue().get(RedisConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(s)) {
            //2.验证验证码是否一致（在有效期内）
            MsgCodeDto dto = gson.fromJson(s, MsgCodeDto.class);
            if (!dto.getMsgCode().equals(msgCode)) {
                return ResultJson.error("验证码输入有误");
            }
            //3.拿到手机号后先去数据库查询是否已经存在，如果不存在就以手机号码随机注册一个，如果存在就直接登录
            SysUserVo sysUserVo = this.phoneLogin(phone);
            String token = sysUserTokenService.createToken(sysUserVo.getUserId());
            sysUserVo.setToken(token);
            return ResultJson.success(sysUserVo);
        }
        return ResultJson.error("验证码已失效，请重新获取");
    }

    @Override
    public Boolean sendMsgCode(String phone) {
        Gson gson = new Gson();
        Long millis = DateUtils.getMillis(LocalDateTime.now());
        String s = redisTemplate.opsForValue().get(RedisConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.isNotBlank(s)) {
            //该手机号码请求过验证码
            MsgCodeDto msgCodeDto = gson.fromJson(s, MsgCodeDto.class);
            if (millis - msgCodeDto.getTime() < RedisConstant.SMS_CODE_VALID_PERIOD) {
                //60s内不能再发送,前端提示请求频率过高
                return false;
            }
        }
        //随机生成6位数字验证码,
        String msgCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        MsgCodeDto dto = MsgCodeDto.builder().time(millis).msgCode(msgCode).build();
        String json = gson.toJson(dto);
        //redis 缓存验证码，防止相同的phone在60秒内在此请求
        redisTemplate.opsForValue().set(RedisConstant.SMS_CODE_CACHE_PREFIX + phone, json, RedisConstant.SMS_CODE_VALID_PERIOD, TimeUnit.SECONDS);
        messageSendConfiguration.sendMsg(phone, msgCode);
        return true;
    }

    private SysUserVo phoneLogin(String phone) {
        SysUser user = sysUserMapper.getOne(SysUser.builder().phone(phone).build());
        if (Objects.isNull(user)) {
            //不存在则随机生成用户名和密码
            String salt = RandomStringUtils.randomAlphanumeric(20);
            //密码
            String password = RandomStringUtils.randomAlphanumeric(6);
            //用户名
            String name = nameGeneratorUtils.generatorName();
            user.setUserId(UuidUtils.id()).setCreateTime(LocalDateTime.now()).setUsername(name)
                    .setPassword(new Sha256Hash(password, salt).toHex())
                    .setPhone(phone);
        }
        return userConverter.toVo(user);
    }

    private List<SysUserRole> build(String userId, List<String> roleIds) {
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SysUserRole> list = roleIds.stream().map(e -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setId(UuidUtils.id()).setRoleId(e).setUserId(userId);
                return sysUserRole;
            }).collect(Collectors.toList());
            return list;
        }
        return null;
    }
}
