package com.xwt.service.impl;


import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwt.common.utils.DateUtils;
import com.xwt.common.utils.SecurityUtils;
import com.xwt.common.utils.StringUtils;
import com.xwt.domain.PlanEvent;
import com.xwt.mapper.PlanEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwt.mapper.PlanDoMapper;
import com.xwt.domain.PlanDo;
import com.xwt.service.IPlanDoService;

/**
 * 每日待办Service业务层处理
 *
 * @author xwt
 * @date 2024-01-01
 */
@Service
public class PlanDoServiceImpl implements IPlanDoService
{
    @Autowired
    private PlanDoMapper doMapper;

    @Autowired
    private PlanEventMapper eventMapper;

    /**
     * 查询每日待办
     *
     * @param doId 每日待办主键
     * @return 每日待办
     */
    @Override
    public PlanDo selectPlanDoByDoId(Long doId)
    {
        return doMapper.selectPlanDoByDoId(doId);
    }

    /**
     * 查询每日待办列表
     *
     * @param planDo 每日待办
     * @return 每日待办
     */
    @Override
    public List<PlanDo> selectPlanDoList(PlanDo planDo)
    {
        // TODO: 2024/1/1  每次查询都去看下有没有待办需要创建
        // 1.没有结束日期的
        selectIsNotEndDate();
        // 2.结束日期在今天之前的

        // 3.前一天没有手动创建的待办没有办的
        List<PlanDo> list = doMapper.selectPlanDoList(planDo);
        for (PlanDo pd : list) {
            String eventContent = eventMapper.selectOne(new QueryWrapper<PlanEvent>().eq("event_id",pd.getEventId())).getEventContent();
            pd.setEventContent(eventContent);
        }
        return list;
    }

    /**
     * 新增每日待办
     *
     * @param planDo 每日待办
     * @return 结果
     */
    @Override
    public int insertPlanDo(PlanDo planDo)
    {
        planDo.setCreateTime(DateUtils.getNowDate());
        planDo.setShowDay(DateUtils.getNowDate());
        planDo.setCreateBy(SecurityUtils.getUsername());
        return doMapper.insertPlanDo(planDo);
    }

    /**
     * 查询是否需要更新待办事件列表
     * @return
     */
    public void selectIsNotEndDate(){
        // 1.没有结束日期的
        // 查询今天有没有创建 没有则创建
        QueryWrapper<PlanEvent> notNull = new QueryWrapper<PlanEvent>()
                .eq("create_user_id", SecurityUtils.getUserId())
                .isNull("end_date");
        List<PlanEvent> notEndList = eventMapper.selectList(notNull);

        if (StringUtils.isNotEmpty(notEndList))
        {
            autoInsertNotEndDate(notEndList);
        }
    }

    /**
     * 自动创建
     */
    public void autoInsertNotEndDate(List<PlanEvent> eventList)
    {
        // 1.没有结束日期的
        // 查询今天有没有创建 没有则创建
        String now = DateUtil.now();
        Date nowDay = DateUtil.parse(now, "yyyy-MM-dd");
        for (PlanEvent event : eventList) {
            // 查询今天有没有创建 没有则创建
            QueryWrapper<PlanDo> wrapper = new QueryWrapper<PlanDo>()
                    .eq("event_id", event.getEventId())
                    .eq("show_day",nowDay);
            PlanDo planDo = doMapper.selectOne(wrapper);
            if (StringUtils.isNull(planDo)){
                PlanDo todo = new PlanDo();
                todo.setEventId(event.getEventId());
                todo.setShowDay(DateUtils.getNowDate());
                todo.setCreateBy(SecurityUtils.getUsername() + "-Auto");
                insertPlanDo(todo);
            }

        }

    }

    /**
     * 修改每日待办
     *
     * @param planDo 每日待办
     * @return 结果
     */
    @Override
    public int updatePlanDo(PlanDo planDo)
    {
        planDo.setUpdateTime(DateUtils.getNowDate());
        return doMapper.updatePlanDo(planDo);
    }

    /**
     * 批量删除每日待办
     *
     * @param doIds 需要删除的每日待办主键
     * @return 结果
     */
    @Override
    public int deletePlanDoByDoIds(Long[] doIds)
    {
        return doMapper.deletePlanDoByDoIds(doIds);
    }

    /**
     * 删除每日待办信息
     *
     * @param doId 每日待办主键
     * @return 结果
     */
    @Override
    public int deletePlanDoByDoId(Long doId)
    {
        return doMapper.deletePlanDoByDoId(doId);
    }
}
