package comskydream.cn.skydream.web.mapper;

import comskydream.cn.skydream.entity.SysUserRole;

public interface SysUserRoleMapper {
    /**
     * 通过主键删除
     * @param id 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增数据插入有数据的列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insertSelective(SysUserRole record);

    /**
     * 通过主键查询
     * @param id 主键id
     * @return 主键符合的唯一Po记录
     */
    SysUserRole selectByPrimaryKey(String id);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysUserRole record);

    /**
     * 根据用户删除角色
     * @param userId
     */
    void deleteByUserId(String userId);


}