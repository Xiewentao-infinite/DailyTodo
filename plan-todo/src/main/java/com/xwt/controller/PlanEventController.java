package com.xwt.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xwt.common.annotation.Log;
import com.xwt.common.core.controller.BaseController;
import com.xwt.common.core.domain.AjaxResult;
import com.xwt.common.enums.BusinessType;
import com.xwt.domain.PlanEvent;
import com.xwt.service.IPlanEventService;
import com.xwt.common.utils.poi.ExcelUtil;
import com.xwt.common.core.page.TableDataInfo;

/**
 * 事件内容Controller
 *
 * @author xwt
 * @date 2023-12-26
 */
@RestController
@RequestMapping("/todo/event")
public class PlanEventController extends BaseController
{
    @Autowired
    private IPlanEventService planEventService;

    /**
     * 查询事件内容列表
     */
    @PreAuthorize("@ss.hasPermi('todo:event:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlanEvent planEvent)
    {
        startPage();
        List<PlanEvent> list = planEventService.selectPlanEventList(planEvent);
        return getDataTable(list);
    }

    /**
     * 导出事件内容列表
     */
    @PreAuthorize("@ss.hasPermi('todo:event:export')")
    @Log(title = "事件内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlanEvent planEvent)
    {
        List<PlanEvent> list = planEventService.selectPlanEventList(planEvent);
        ExcelUtil<PlanEvent> util = new ExcelUtil<PlanEvent>(PlanEvent.class);
        util.exportExcel(response, list, "事件内容数据");
    }

    /**
     * 获取事件内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:event:query')")
    @GetMapping(value = "/{eventId}")
    public AjaxResult getInfo(@PathVariable("eventId") Long eventId)
    {
        return success(planEventService.selectPlanEventByEventId(eventId));
    }

    /**
     * 新增事件内容
     */
    @PreAuthorize("@ss.hasPermi('todo:event:add')")
    @Log(title = "事件内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlanEvent planEvent)
    {
        return toAjax(planEventService.insertPlanEvent(planEvent));
    }

    /**
     * 修改事件内容
     */
    @PreAuthorize("@ss.hasPermi('todo:event:edit')")
    @Log(title = "事件内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlanEvent planEvent)
    {
        return toAjax(planEventService.updatePlanEvent(planEvent));
    }

    /**
     * 删除事件内容
     */
    @PreAuthorize("@ss.hasPermi('todo:event:remove')")
    @Log(title = "事件内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eventIds}")
    public AjaxResult remove(@PathVariable Long[] eventIds)
    {
        return toAjax(planEventService.deletePlanEventByEventIds(eventIds));
    }
}