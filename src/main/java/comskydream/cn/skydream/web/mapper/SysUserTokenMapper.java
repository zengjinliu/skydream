package comskydream.cn.skydream.web.mapper;


import comskydream.cn.skydream.entity.SysUserToken;

public interface SysUserTokenMapper {
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
    int insertSelective(SysUserToken record);

    /**
     * 通过主键查询
     * @param userId 主键id
     * @return 主键符合的唯一Po记录
     */
    SysUserToken selectByPrimaryKey(String userId);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysUserToken record);

    /**
     * 查询单个数据航
     * @param record
     * @return
     */
    SysUserToken selectOne(SysUserToken record);
}