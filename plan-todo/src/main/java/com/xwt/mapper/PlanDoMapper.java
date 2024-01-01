package com.xwt.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwt.domain.PlanDo;

/**
 * 每日待办Mapper接口
 *
 * @author xwt
 * @date 2024-01-01
 */
public interface PlanDoMapper extends BaseMapper<PlanDo>
{
    /**
     * 查询每日待办
     *
     * @param doId 每日待办主键
     * @return 每日待办
     */
    public PlanDo selectPlanDoByDoId(Long doId);

    /**
     * 查询每日待办列表
     *
     * @param planDo 每日待办
     * @return 每日待办集合
     */
    public List<PlanDo> selectPlanDoList(PlanDo planDo);

    /**
     * 新增每日待办
     *
     * @param planDo 每日待办
     * @return 结果
     */
    public int insertPlanDo(PlanDo planDo);

    /**
     * 修改每日待办
     *
     * @param planDo 每日待办
     * @return 结果
     */
    public int updatePlanDo(PlanDo planDo);

    /**
     * 删除每日待办
     *
     * @param doId 每日待办主键
     * @return 结果
     */
    public int deletePlanDoByDoId(Long doId);

    /**
     * 批量删除每日待办
     *
     * @param doIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlanDoByDoIds(Long[] doIds);
}
