<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="事件表id" prop="eventId">
        <el-input
          v-model="queryParams.eventId"
          placeholder="请输入事件表id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否完成" prop="isDo">
        <el-select v-model="queryParams.isDo" placeholder="请选择是否完成" clearable>
          <el-option
            v-for="dict in dict.type.todo_plan_is_do"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="完成时间">
        <el-date-picker
          v-model="daterangeDoTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="需显示的日期" prop="showDay">
        <el-date-picker clearable
                        v-model="queryParams.showDay"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择需显示的日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="待办状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择待办状态" clearable>
          <el-option
            v-for="dict in dict.type.todo_plan_event_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['todo:do:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['todo:do:edit']"
        >修改</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['todo:do:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['todo:do:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="doList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="todo表id" align="center" prop="doId" />-->
<!--      <el-table-column label="事件表id" align="center" prop="eventId" />-->
      <el-table-column label="待办内容" align="center" prop="eventContent" />

      <el-table-column label="是否完成" align="center" prop="isDo">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.todo_plan_is_do" :value="scope.row.isDo"/>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="doTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.doTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需显示的日期" align="center" prop="showDay" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.showDay, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="待办状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.todo_plan_event_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['todo:do:edit']"
          >修改</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['todo:do:remove']"-->
<!--          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改每日待办对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="事件表id" prop="eventId">-->
<!--          <el-input v-model="form.eventId" placeholder="请输入事件表id" />-->
<!--        </el-form-item>-->
        <el-form-item label="是否完成" prop="isDo">
          <el-radio-group v-model="form.isDo">
            <el-radio
              v-for="dict in dict.type.todo_plan_is_do"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
<!--        <el-form-item label="完成时间" prop="doTime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.doTime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择完成时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="需显示的日期" prop="showDay">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.showDay"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择需显示的日期">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="待办状态" prop="status">-->
<!--          <el-radio-group v-model="form.status">-->
<!--            <el-radio-->
<!--              v-for="dict in dict.type.todo_plan_event_status"-->
<!--              :key="dict.value"-->
<!--              :label="dict.value"-->
<!--            >{{dict.label}}</el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="删除标志" prop="delFlag">-->
<!--          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />-->
<!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDo, getDo, delDo, addDo, updateDo } from "@/api/todo/do";

export default {
  name: "Do",
  dicts: ['todo_plan_event_status', 'todo_plan_is_do'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 每日待办表格数据
      doList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeDoTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eventId: null,
        isDo: null,
        doTime: null,
        showDay: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询每日待办列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDoTime && '' != this.daterangeDoTime) {
        this.queryParams.params["beginDoTime"] = this.daterangeDoTime[0];
        this.queryParams.params["endDoTime"] = this.daterangeDoTime[1];
      }
      listDo(this.queryParams).then(response => {
        this.doList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        doId: null,
        eventId: null,
        isDo: null,
        doTime: null,
        showDay: null,
        status: null,
        eventContent,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDoTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.doId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加每日待办";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const doId = row.doId || this.ids
      getDo(doId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改每日待办";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.doId != null) {
            updateDo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    // handleDelete(row) {
    //   const doIds = row.doId || this.ids;
    //   this.$modal.confirm('是否确认删除每日待办编号为"' + doIds + '"的数据项？').then(function() {
    //     return delDo(doIds);
    //   }).then(() => {
    //     this.getList();
    //     this.$modal.msgSuccess("删除成功");
    //   }).catch(() => {});
    // },

    /** 导出按钮操作 */
    handleExport() {
      this.download('todo/do/export', {
        ...this.queryParams
      }, `do_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
