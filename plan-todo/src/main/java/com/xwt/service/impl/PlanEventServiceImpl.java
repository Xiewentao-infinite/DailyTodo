package com.xwt.service.impl;

import java.util.List;
import com.xwt.common.utils.DateUtils;
import com.xwt.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwt.mapper.PlanEventMapper;
import com.xwt.domain.PlanEvent;
import com.xwt.service.IPlanEventService;

/**
 * 事件内容Service业务层处理
 *
 * @author xwt
 * @date 2023-12-26
 */
@Service
public class PlanEventServiceImpl implements IPlanEventService
{
    @Autowired
    private PlanEventMapper planEventMapper;

    /**
     * 查询事件内容
     *
     * @param eventId 事件内容主键
     * @return 事件内容
     */
    @Override
    public PlanEvent selectPlanEventByEventId(Long eventId)
    {
        return planEventMapper.selectPlanEventByEventId(eventId);
    }

    /**
     * 查询事件内容列表
     *
     * @param planEvent 事件内容
     * @return 事件内容
     */
    @Override
    public List<PlanEvent> selectPlanEventList(PlanEvent planEvent)
    {
        return planEventMapper.selectPlanEventList(planEvent);
    }

    /**
     * 新增事件内容
     *
     * @param planEvent 事件内容
     * @return 结果
     */
    @Override
    public int insertPlanEvent(PlanEvent planEvent)
    {
        planEvent.setCreateTime(DateUtils.getNowDate());
        planEvent.setCreateUserId(SecurityUtils.getUserId());
        planEvent.setCreateBy(SecurityUtils.getUsername());
        return planEventMapper.insertPlanEvent(planEvent);
    }

    /**
     * 修改事件内容
     *
     * @param planEvent 事件内容
     * @return 结果
     */
    @Override
    public int updatePlanEvent(PlanEvent planEvent)
    {
        planEvent.setUpdateTime(DateUtils.getNowDate());
        return planEventMapper.updatePlanEvent(planEvent);
    }

    /**
     * 批量删除事件内容
     *
     * @param eventIds 需要删除的事件内容主键
     * @return 结果
     */
    @Override
    public int deletePlanEventByEventIds(Long[] eventIds)
    {
        return planEventMapper.deletePlanEventByEventIds(eventIds);
    }

    /**
     * 删除事件内容信息
     *
     * @param eventId 事件内容主键
     * @return 结果
     */
    @Override
    public int deletePlanEventByEventId(Long eventId)
    {
        return planEventMapper.deletePlanEventByEventId(eventId);
    }
}
