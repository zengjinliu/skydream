package comskydream.cn.skydream.mapper;


import comskydream.cn.skydream.entity.SysRole;

public interface SysRoleMapper {
    /**
     * 通过主键删除
     * @param roleId 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String roleId);

    /**
     * 新增数据插入有数据的列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insertSelective(SysRole record);

    /**
     * 通过主键查询
     * @param roleId 主键id
     * @return 主键符合的唯一Po记录
     */
    SysRole selectByPrimaryKey(String roleId);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysRole record);


}