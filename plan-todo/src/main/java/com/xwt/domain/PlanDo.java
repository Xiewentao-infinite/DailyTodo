package com.xwt.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xwt.common.annotation.Excel;
import com.xwt.common.core.domain.BaseEntity;

/**
 * 每日待办对象 plan_do
 *
 * @author xwt
 * @date 2024-01-01
 */
public class PlanDo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** todo表id */
    private Long doId;

    /** 事件表id */
    @Excel(name = "事件表id")
    private Long eventId;

    /** 是否完成 */
    @Excel(name = "是否完成",readConverterExp = "0=未完成,1=已完成")
    private String isDo;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date doTime;

    /** 需显示的日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需显示的日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date showDay;

    /** 待办状态（0正常 1停用） */
    @Excel(name = "待办状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 待办事件内容 */
    @TableField(exist = false)
    private String eventContent;

    public void setDoId(Long doId)
    {
        this.doId = doId;
    }

    public Long getDoId()
    {
        return doId;
    }
    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    public Long getEventId()
    {
        return eventId;
    }
    public void setIsDo(String isDo)
    {
        this.isDo = isDo;
    }

    public String getIsDo()
    {
        return isDo;
    }
    public void setDoTime(Date doTime)
    {
        this.doTime = doTime;
    }

    public Date getDoTime()
    {
        return doTime;
    }
    public void setShowDay(Date showDay)
    {
        this.showDay = showDay;
    }

    public Date getShowDay()
    {
        return showDay;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("doId", getDoId())
                .append("eventId", getEventId())
                .append("isDo", getIsDo())
                .append("doTime", getDoTime())
                .append("showDay", getShowDay())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
