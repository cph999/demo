<template>
  <div class="app-container">

    <el-collapse v-model="activeNames">
      <el-collapse-item title="搜索栏" name="1">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="课程标题" prop="courseTitle">
            <el-input
              v-model="queryParams.courseTitle"
              placeholder="请输入课程标题"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="课程副标题" prop="courseSubtitle">
            <el-input
              v-model="queryParams.courseSubtitle"
              placeholder="请输入课程副标题"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="商品价格" prop="coursePrice">
            <el-input
              v-model="queryParams.coursePrice"
              placeholder="请输入商品价格"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="打折描述" prop="discountDesc">
            <el-input
              v-model="queryParams.discountDesc"
              placeholder="请输入打折描述"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="折后价" prop="discountPrice">
            <el-input
              v-model="queryParams.discountPrice"
              placeholder="请输入折后价"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="课程难度" prop="courseLevel">
            <el-input
              v-model="queryParams.courseLevel"
              placeholder="请输入课程难度"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="课程封面" prop="courseCover">
            <el-input
              v-model="queryParams.courseCover"
              placeholder="请输入课程封面"
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

          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="是否展示详情" prop="showDetail">
                <el-radio v-model="queryParams.showDetail" label="0">关闭</el-radio>
                <el-radio v-model="queryParams.showDetail" label="1">开启</el-radio>
              </el-form-item>
              <el-form-item label="是否轮播" prop="isCarousel">
                <el-radio v-model="queryParams.isCarousel" label="0">关闭</el-radio>
                <el-radio v-model="queryParams.isCarousel" label="1">开启</el-radio>
              </el-form-item>
              <el-form-item label="是否精品" prop="isRecommend">
                <el-radio v-model="queryParams.isRecommend" label="0">关闭</el-radio>
                <el-radio v-model="queryParams.isRecommend" label="1">开启</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="是否对外" prop="isOut">
                <el-radio v-model="queryParams.isOut" label="0">关闭</el-radio>
                <el-radio v-model="queryParams.isOut" label="1">开启</el-radio>
              </el-form-item>
              <el-form-item label="是否免费" prop="isFree">
                <el-radio v-model="queryParams.isFree" label="0">关闭</el-radio>
                <el-radio v-model="queryParams.isFree" label="1">开启</el-radio>
              </el-form-item>
              <el-form-item label="是否打折" prop="isDiscount">
                <el-radio v-model="queryParams.isDiscount" :label="0">关闭</el-radio>
                <el-radio v-model="queryParams.isDiscount" :label="1">开启</el-radio>
              </el-form-item>

            </el-col>
          </el-row>

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
          v-hasPermi="['system:course:add']"
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
          v-hasPermi="['system:course:edit']"
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
          v-hasPermi="['system:course:remove']"
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
          v-hasPermi="['system:course:export']"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="课程标题" align="center" prop="courseTitle"/>
      <el-table-column label="是否免费" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isFree == 1" circle size="mini" type="primary">是</el-button>
          <el-button v-else circle size="mini" type="warning">否</el-button>
        </template>
      </el-table-column>
      <el-table-column label="商品价格" align="center" prop="coursePrice"/>
      <el-table-column label="是否打折" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isDiscount == 1" circle size="mini" type="primary">是</el-button>
          <el-button v-else circle size="mini" type="warning">否</el-button>
        </template>
      </el-table-column>
      <el-table-column label="打折描述" align="center" prop="discountDesc">
        <template slot-scope="scope">
          <el-tag round>{{ scope.row.discountDesc }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="折后价" align="center" prop="discountPrice"/>
      <el-table-column label="是否轮播" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isCarousel == 1" circle size="mini" type="primary">是</el-button>
          <el-button v-else circle size="mini" type="warning">否</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否精品推荐" align="center" prop="isRecommend">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isRecommend == 1" circle size="mini" type="primary">是</el-button>
          <el-button v-else circle size="mini" type="warning">否</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否对外" align="center" prop="isOut">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isOut == 1" circle size="mini" type="primary">是</el-button>
          <el-button v-else circle size="mini" type="warning">否</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:course:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:course:remove']"
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

    <!-- 添加或修改课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body destroy-on-close>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程标题" prop="courseTitle">
          <el-input v-model="form.courseTitle" placeholder="请输入课程标题"/>
        </el-form-item>
        <el-form-item label="课程副标题" prop="courseSubtitle">
          <el-input v-model="form.courseSubtitle" placeholder="请输入课程副标题"/>
        </el-form-item>
        <el-form-item label="课程简介" prop="courseDesc">
          <el-input v-model="form.courseDesc" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="是否免费" prop="isFree">
          <el-switch
            v-model="form.isFree"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="商品价格" prop="coursePrice">
          <el-input v-model="form.coursePrice" placeholder="请输入商品价格"/>
        </el-form-item>
        <el-form-item label="是否打折" prop="isDiscount">
          <el-switch
            v-model="form.isDiscount"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>

        </el-form-item>
        <el-form-item label="打折描述" prop="discountDesc">
          <el-input v-model="form.discountDesc" placeholder="请输入打折描述"/>
        </el-form-item>
        <el-form-item label="折后价" prop="discountPrice">
          <el-input v-model="form.discountPrice" placeholder="请输入折后价"/>
        </el-form-item>
        <el-form-item label="课程难度" prop="courseLevel">
          <el-input v-model="form.courseLevel" placeholder="请输入课程难度"/>
        </el-form-item>
        <el-form-item label="课程封面" prop="courseCover">
          <el-input v-model="form.courseCover" placeholder="请输入课程封面"/>
        </el-form-item>
        <el-form-item label=" banner图" prop="courseBanner">
          <el-upload
            action="http://127.0.0.1:36677/upload"
            list-type="picture-card"
            name="list"
            :before-upload="beforeAvatarUpload"
            :auto-upload="false">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{file}">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url" alt=""
              >
              <span class="el-upload-list__item-actions">
        <span
          class="el-upload-list__item-preview"
          @click="handlePictureCardPreview(file)"
        >
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
          v-if="!disabled"
          class="el-upload-list__item-delete"
          @click="handleDownload(file)"
        >
          <i class="el-icon-download"></i>
        </span>
        <span
          v-if="!disabled"
          class="el-upload-list__item-delete"
          @click="handleRemove(file)"
        >
          <i class="el-icon-delete"></i>
        </span>
      </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item label="是否轮播" prop="isCarousel">
          <el-switch
            v-model="form.isCarousel"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否精品推荐" prop="isRecommend">
          <el-switch
            v-model="form.isRecommend"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否对外" prop="isOut">
          <el-switch
            v-model="form.isOut"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="排序字段" prop="orderBy">
          <el-input v-model="form.orderBy" placeholder="请输入排序字段"/>
        </el-form-item>
        <el-form-item label="是否展示详情" prop="showDetail">
          <el-switch
            v-model="form.showDetail"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label="remark 备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {addCourse, delCourse, getCourse, listCourse, updateCourse} from "@/api/system/course";

export default {
  name: "Course",
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
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
      // 课程表格数据
      courseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      imageUrl: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseTitle: null,
        courseSubtitle: null,
        courseDesc: null,
        isFree: null,
        coursePrice: null,
        isDiscount: null,
        discountDesc: null,
        discountPrice: null,
        courseLevel: null,
        courseCover: null,
        courseBanner: null,
        isCarousel: null,
        isRecommend: null,
        isOut: null,
        orderBy: null,
        showDetail: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      },
      // 表单参数
      form: {
        pageNum: 1,
        pageSize: 10,
        courseTitle: null,
        courseSubtitle: null,
        courseDesc: null,
        isFree: null,
        coursePrice: null,
        isDiscount: null,
        discountDesc: null,
        discountPrice: null,
        courseLevel: null,
        courseCover: null,
        courseBanner: null,
        isCarousel: null,
        isRecommend: null,
        isOut: null,
        orderBy: null,
        showDetail: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null,
      },
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
    /** 查询课程列表 */
    getList() {
      this.loading = true;
      listCourse(this.queryParams).then(response => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    beforeAvatarUpload(file) {
      this.form.courseBanner = file.url
    },
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        courseId: null,
        courseTitle: null,
        courseSubtitle: null,
        courseDesc: null,
        isFree: null,
        coursePrice: null,
        isDiscount: null,
        discountDesc: null,
        discountPrice: null,
        courseLevel: null,
        courseCover: null,
        courseBanner: null,
        isCarousel: null,
        isRecommend: null,
        isOut: null,
        orderBy: null,
        showDetail: null,
        remark: null,
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
      this.ids = selection.map(item => item.courseId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加课程";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const courseId = row.courseId || this.ids
      getCourse(courseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改课程";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.courseId != null) {
            updateCourse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCourse(this.form).then(response => {
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
      const courseIds = row.courseId || this.ids;
      this.$modal.confirm('是否确认删除课程编号为"' + courseIds + '"的数据项？').then(function () {
        return delCourse(courseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/course/export', {
        ...this.queryParams
      }, `course_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
