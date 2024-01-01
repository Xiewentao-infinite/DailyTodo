package com.xwt.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwt.domain.PlanEvent;

/**
 * 事件内容Mapper接口
 *
 * @author xwt
 * @date 2023-12-26
 */
public interface PlanEventMapper extends BaseMapper<PlanEvent>
{
    /**
     * 查询事件内容
     *
     * @param eventId 事件内容主键
     * @return 事件内容
     */
    public PlanEvent selectPlanEventByEventId(Long eventId);

    /**
     * 查询事件内容列表
     *
     * @param planEvent 事件内容
     * @return 事件内容集合
     */
    public List<PlanEvent> selectPlanEventList(PlanEvent planEvent);

    /**
     * 新增事件内容
     *
     * @param planEvent 事件内容
     * @return 结果
     */
    public int insertPlanEvent(PlanEvent planEvent);

    /**
     * 修改事件内容
     *
     * @param planEvent 事件内容
     * @return 结果
     */
    public int updatePlanEvent(PlanEvent planEvent);

    /**
     * 删除事件内容
     *
     * @param eventId 事件内容主键
     * @return 结果
     */
    public int deletePlanEventByEventId(Long eventId);

    /**
     * 批量删除事件内容
     *
     * @param eventIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlanEventByEventIds(Long[] eventIds);
}
