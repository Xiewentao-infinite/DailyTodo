package com.xwt.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xwt.common.annotation.Excel;
import com.xwt.common.core.domain.BaseEntity;

/**
 * 事件对象 plan_event
 *
 * @author ruoyi
 * @date 2024-01-01
 */
public class PlanEvent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 事件表id */
    private Long eventId;

    /** 事件内容 */
    @Excel(name = "事件内容")
    private String eventContent;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    private Long createUserId;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 事件状态（0正常 1停用） */
    @Excel(name = "事件状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    public Long getEventId()
    {
        return eventId;
    }
    public void setEventContent(String eventContent)
    {
        this.eventContent = eventContent;
    }

    public String getEventContent()
    {
        return eventContent;
    }
    public void setCreateUserId(Long createUserId)
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId()
    {
        return createUserId;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("eventId", getEventId())
                .append("eventContent", getEventContent())
                .append("createUserId", getCreateUserId())
                .append("endDate", getEndDate())
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
