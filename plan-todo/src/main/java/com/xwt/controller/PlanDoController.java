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
import com.xwt.domain.PlanDo;
import com.xwt.service.IPlanDoService;
import com.xwt.common.utils.poi.ExcelUtil;
import com.xwt.common.core.page.TableDataInfo;

/**
 * 每日待办Controller
 *
 * @author xwt
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/todo/do")
public class PlanDoController extends BaseController
{
    @Autowired
    private IPlanDoService planDoService;

    /**
     * 查询每日待办列表
     */
    @PreAuthorize("@ss.hasPermi('todo:do:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlanDo planDo)
    {
        startPage();
        List<PlanDo> list = planDoService.selectPlanDoList(planDo);
        return getDataTable(list);
    }

    /**
     * 导出每日待办列表
     */
    @PreAuthorize("@ss.hasPermi('todo:do:export')")
    @Log(title = "每日待办", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlanDo planDo)
    {
        List<PlanDo> list = planDoService.selectPlanDoList(planDo);
        ExcelUtil<PlanDo> util = new ExcelUtil<PlanDo>(PlanDo.class);
        util.exportExcel(response, list, "每日待办数据");
    }

    /**
     * 获取每日待办详细信息
     */
    @PreAuthorize("@ss.hasPermi('todo:do:query')")
    @GetMapping(value = "/{doId}")
    public AjaxResult getInfo(@PathVariable("doId") Long doId)
    {
        return success(planDoService.selectPlanDoByDoId(doId));
    }

    /**
     * 新增每日待办
     */
    @PreAuthorize("@ss.hasPermi('todo:do:add')")
    @Log(title = "每日待办", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlanDo planDo)
    {
        return toAjax(planDoService.insertPlanDo(planDo));
    }

    /**
     * 修改每日待办
     */
    @PreAuthorize("@ss.hasPermi('todo:do:edit')")
    @Log(title = "每日待办", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlanDo planDo)
    {
        return toAjax(planDoService.updatePlanDo(planDo));
    }

    /**
     * 删除每日待办
     */
    @PreAuthorize("@ss.hasPermi('todo:do:remove')")
    @Log(title = "每日待办", businessType = BusinessType.DELETE)
    @DeleteMapping("/{doIds}")
    public AjaxResult remove(@PathVariable Long[] doIds)
    {
        return toAjax(planDoService.deletePlanDoByDoIds(doIds));
    }
}
