package comskydream.cn.skydream.mapper;


import comskydream.cn.skydream.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
    /**
     * 通过主键删除
     * @param userId 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String userId);

    /**
     * 新增数据插入有数据的列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insertSelective(SysUser record);

    /**
     * 通过主键查询
     * @param userId 主键id
     * @return 主键符合的唯一Po记录
     */
    SysUser selectByPrimaryKey(String userId);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 条件查询返回多个对象
     * @param record 修改记录参数
     * @return 对象集合
     */
    List<SysUser> list(SysUser record);

    /**
     * 条件查询返回单个
     * @param record 记录参数
     * @return 对象
     */
    SysUser getOne(SysUser record);

}