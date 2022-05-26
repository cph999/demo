<template>
  <div class="app-container">
    <el-collapse v-model="activeNames">
      <el-collapse-item title="搜索栏" name="1">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="课程id" prop="courseId">
            <el-input
              v-model="queryParams.courseId"
              placeholder="请输入课程id"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="章节名称" prop="sectionName">
            <el-input
              v-model="queryParams.sectionName"
              placeholder="请输入章节名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="章节序列" prop="orderBy">
            <el-input
              v-model="queryParams.orderBy"
              placeholder="请输入章节序列"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="父章节id" prop="parentId">
            <el-input
              v-model="queryParams.parentId"
              placeholder="请输入父章节id"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="创建人" prop="createdBy">
            <el-input
              v-model="queryParams.createdBy"
              placeholder="请输入创建人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="创建时间" prop="createdTime">
            <el-date-picker clearable
                            v-model="queryParams.createdTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择创建时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="更新人" prop="updatedBy">
            <el-input
              v-model="queryParams.updatedBy"
              placeholder="请输入更新人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="更新时间" prop="updatedTime">
            <el-date-picker clearable
                            v-model="queryParams.updatedTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择更新时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

      </el-collapse-item>
    </el-collapse>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:section:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:section:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:section:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:section:export']"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="sectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="章节id" align="center" prop="sectionId"/>
      <el-table-column label="课程id" align="center" prop="courseId"/>
      <el-table-column label="章节名称" align="center" prop="sectionName"/>
      <el-table-column label="章节类型0父章节1字章节" align="center" prop="sectionType">
        <template slot-scope="scope">
          <el-button v-if="scope.row.sectionType == 1" circle size="mini" type="primary">父章节</el-button>
          <el-button v-else circle size="mini" type="warning">子章节</el-button>
        </template>
      </el-table-column>

      <el-table-column label="试看" align="center" prop="canTry">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.canTry"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="章节序列" align="center" prop="orderBy"/>
      <el-table-column label="父章节id" align="center" prop="parentId"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:section:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:section:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改课程章节对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程id" prop="courseId">
          <el-input v-model="form.courseId" placeholder="请输入课程id"/>
        </el-form-item>
        <el-form-item label="章节名称" prop="sectionName">
          <el-input v-model="form.sectionName" placeholder="请输入章节名称"/>
        </el-form-item>

        <el-form-item label="试看" prop="canTry">
          <el-switch
            v-model="form.canTry"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="章节序列" prop="orderBy">
          <el-input v-model="form.orderBy" placeholder="请输入章节序列"/>
        </el-form-item>
        <el-form-item label="父章节id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父章节id"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志"/>
        </el-form-item>
        <el-form-item label="创建人" prop="createdBy">
          <el-input v-model="form.createdBy" placeholder="请输入创建人"/>
        </el-form-item>
        <el-form-item label="创建时间" prop="createdTime">
          <el-date-picker clearable
                          v-model="form.createdTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新人" prop="updatedBy">
          <el-input v-model="form.updatedBy" placeholder="请输入更新人"/>
        </el-form-item>
        <el-form-item label="更新时间" prop="updatedTime">
          <el-date-picker clearable
                          v-model="form.updatedTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择更新时间">
          </el-date-picker>
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
import {addSection, delSection, getSection, listSection, updateSection} from "@/api/system/section";

export default {
  name: "Section",
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
      // 课程章节表格数据
      sectionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseId: null,
        sectionName: null,
        sectionType: null,
        sectionVideo: null,
        canTry: null,
        orderBy: null,
        parentId: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //折叠框
      activeNames: ['1']
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询课程章节列表 */
    getList() {
      this.loading = true;
      listSection(this.queryParams).then(response => {
        this.sectionList = response.rows;
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
        sectionId: null,
        courseId: null,
        sectionName: null,
        sectionType: null,
        sectionVideo: null,
        canTry: null,
        orderBy: null,
        parentId: null,
        remark: null,
        delFlag: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sectionId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加课程章节";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sectionId = row.sectionId || this.ids
      getSection(sectionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改课程章节";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sectionId != null) {
            updateSection(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSection(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const sectionIds = row.sectionId || this.ids;
      this.$modal.confirm('是否确认删除课程章节编号为"' + sectionIds + '"的数据项？').then(function () {
        return delSection(sectionIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/section/export', {
        ...this.queryParams
      }, `section_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
