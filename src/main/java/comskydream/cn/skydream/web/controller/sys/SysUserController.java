package comskydream.cn.skydream.web.controller.sys;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.custom.UserExport;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.vo.PasswordVo;
import comskydream.cn.skydream.model.vo.SysUserVo;
import comskydream.cn.skydream.web.service.sys.SysUserService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

/**
 * @author Jayson
 * @date 2020/9/8  23:24
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserExport userExport;


    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public ResultPage page(@RequestBody SysUserVo sysUserVo){
        //分页查询
        ResultPage<List<SysUserVo>> list = sysUserService.pageList(sysUserVo);
        return list;
    }

    @RequiresPermissions(value = "user:edit")
    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    public ResultJson updatePwd(@RequestBody PasswordVo passwordVo){
        //更新密码
        sysUserService.updatePwd(passwordVo);
        return ResultJson.success();
    }

    @RequiresPermissions("user:add")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultJson add(@RequestBody SysUserVo sysUser){
        sysUserService.save(sysUser);
        return ResultJson.success();
    }

    @RequiresPermissions(value = "user:del")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultJson del(@RequestBody List<String> userIds){
        sysUserService.delete(userIds);
        return ResultJson.success();
    }

    @RequiresPermissions(value = "user:edit")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultJson update(@RequestBody SysUserVo sysUser){
        sysUserService.update(sysUser);
        return ResultJson.success();
    }


    @RequestMapping(value = "/checkNameExist",method = RequestMethod.GET)
    public ResultJson<Boolean> checkNameExist(@RequestParam("username")String username){
        Boolean flag = sysUserService.checkNameExist(username);
        return ResultJson.success(flag);
    }


    @RequestMapping(value = "/queryById",method = RequestMethod.GET)
    public ResultJson<SysUser> queryById(@RequestParam("userId") String userId){
        //TODO 角色回显
        SysUser user = sysUserService.getOne(SysUser.builder().userId(userId).build());
        return ResultJson.success(user);
    }

    @RequestMapping(value = "/queryAllPerms",method = RequestMethod.GET)
    public ResultJson queryAllPerms(){
        Set<String> set = sysUserService.queryAllPermission(SysUserUtils.getUserId());
        return ResultJson.success(set);
    }

    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public void doExport(HttpServletResponse res, HttpServletRequest req) throws IOException {
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/x-download");
        String filename = "用户列表.xls";
        filename = URLEncoder.encode(filename,"UTF-8");
        res.addHeader("Content-disposition","attachment;filename="+filename);
        ServletOutputStream outputStream = res.getOutputStream();
        userExport.exportUser(outputStream,filename);
    }

}
