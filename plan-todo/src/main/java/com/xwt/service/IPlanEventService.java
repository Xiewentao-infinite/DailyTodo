package com.xwt.service;

import java.util.List;

import com.xwt.domain.PlanEvent;

/**
 * 事件内容Service接口
 *
 * @author xwt
 * @date 2023-12-26
 */
public interface IPlanEventService
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
     * 批量删除事件内容
     *
     * @param eventIds 需要删除的事件内容主键集合
     * @return 结果
     */
    public int deletePlanEventByEventIds(Long[] eventIds);

    /**
     * 删除事件内容信息
     *
     * @param eventId 事件内容主键
     * @return 结果
     */
    public int deletePlanEventByEventId(Long eventId);
}