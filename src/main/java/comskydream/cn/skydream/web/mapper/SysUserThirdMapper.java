package comskydream.cn.skydream.web.mapper;


import comskydream.cn.skydream.entity.SysUserThird;

public interface SysUserThirdMapper {
    /**
     * 通过主键删除
     * @param id 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增数据插入全部列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insert(SysUserThird record);

    /**
     * 通过主键查询
     * @param uid 主键uid
     * @return 主键符合的唯一Po记录
     */
    SysUserThird selectByPrimaryKey(String uid);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysUserThird record);

    /**
     *
     * @param userId
     */
    void deleteByUserId(String userId);
}