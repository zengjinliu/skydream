package comskydream.cn.skydream.service;

import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.model.SysRoleVo;

import java.util.List;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/15 15:41
 */
public interface SysRoleService {

    /**
     * 分页
     * @param sysRoleVo
     * @return
     */
    ResultPage<List<SysRoleVo>> page(SysRoleVo sysRoleVo);

    /**
     * 新增
     * @param sysRoleVo
     */
    void save(SysRoleVo sysRoleVo);
}
