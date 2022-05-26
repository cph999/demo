<template>
  <div class="app-container">
    <el-collapse v-model="activeNames">
      <el-collapse-item title="搜索栏" name="1">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="专题名称" prop="subjectTitle">
            <el-input
              v-model="queryParams.subjectTitle"
              placeholder="请输入专题名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="排序字段" prop="orderBy">
            <el-input
              v-model="queryParams.orderBy"
              placeholder="请输入排序字段"
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
          v-hasPermi="['system:subject:add']"
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
          v-hasPermi="['system:subject:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:subject:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:subject:export']"
        >导出</el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="subjectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="专题名称" align="center" prop="subjectTitle" />
      <el-table-column label="专题描述" align="center" prop="subjectDesc" />
      <el-table-column label="banner" align="center">
        <template slot-scope="scope">

          <el-image :src="images[scope.$index]"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建人" align="center" prop="createdBy" />
      <el-table-column label="是否启用" align="center" prop="enable" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            disabled
            :active-value="1"
            :inactive-value="0">
          </el-switch>
        </template>

      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:subject:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:subject:remove']"
          >删除</el-button>
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

    <!-- 添加或修改专题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="专题名称" prop="subjectTitle">
          <el-input v-model="form.subjectTitle" placeholder="请输入专题名称" />
        </el-form-item>
        <el-form-item label="专题描述" prop="subjectDesc">
          <el-input v-model="form.subjectDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="banner图" prop="subjectBanner">
          <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{file}">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url" alt=""
              >
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="排序字段" prop="orderBy">
          <el-input v-model="form.orderBy" placeholder="请输入排序字段" />
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-switch
            v-model="subjectList.delFlag"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创建人" prop="createdBy">
          <el-input v-model="form.createdBy" placeholder="请输入创建人" />
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
          <el-input v-model="form.updatedBy" placeholder="请输入更新人" />
        </el-form-item>
        <el-form-item label="更新时间" prop="updatedTime">
          <el-date-picker clearable
            v-model="form.updatedTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否启用" prop="enable">
          <el-switch
            v-model="form.enable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="0">
          </el-switch>
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
import { listSubject, getSubject, delSubject, addSubject, updateSubject } from "@/api/system/subject";


export default {
  name: "Subject",
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
      // 专题表格数据
      subjectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        subjectTitle: null,
        subjectDesc: null,
        subjectBanner: null,
        orderBy: null,
        subjectStatus: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        enable: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //折叠框
      activeNames: ['1'],
      images:['https://w.wallhaven.cc/full/pk/wallhaven-pk1vjp.jpg','https://w.wallhaven.cc/full/m9/wallhaven-m95vwm.jpg','https://w.wallhaven.cc/full/6o/wallhaven-6o3gmw.png','https://w.wallhaven.cc/full/x8/wallhaven-x8o3ld.jpg','https://wallhaven.cc/w/6o3gql','https://w.wallhaven.cc/full/q2/wallhaven-q2kv95.png','https://w.wallhaven.cc/full/pk/wallhaven-pk15e9.jpg','https://static.runoob.com/images/demo/demo1.jpg','https://static.runoob.com/images/demo/demo2.jpg','https://static.runoob.com/images/demo/demo3.jpg'],
      showImage:''
    };
  },
  created() {
    this.getList();
    this.showImage = this.images[Math.round(Math.random()*3)]
    console.log(Math.round(Math.random()*3))
  },
  methods: {
    /** 查询专题列表 */
    getList() {
      this.loading = true;
      listSubject(this.queryParams).then(response => {
        this.subjectList = response.rows;
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
        subjectId: null,
        subjectTitle: null,
        subjectDesc: null,
        subjectBanner: null,
        orderBy: null,
        subjectStatus: "0",
        delFlag: null,
        remark: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
        enable: null
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
      this.ids = selection.map(item => item.subjectId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加专题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subjectId = row.subjectId || this.ids
      getSubject(subjectId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改专题";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subjectId != null) {
            updateSubject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSubject(this.form).then(response => {
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
      const subjectIds = row.subjectId || this.ids;
      this.$modal.confirm('是否确认删除专题编号为"' + subjectIds + '"的数据项？').then(function() {
        return delSubject(subjectIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/subject/export', {
        ...this.queryParams
      }, `subject_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
.mb8 {
  margin-top:15px;
}
</style>
