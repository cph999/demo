<template>
<div class="app-container">
<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<el-form-item label="课程标题" prop="csmngCode">
  <el-input v-model="queryParams.csmngCode" placeholder="请输入课程标题" clearable @keyup.enter.native="handleQuery" />
</el-form-item>

<el-form-item label="专题名称" prop="status">
  <el-select v-model="queryParams.status" placeholder="请选择专题名称" clearable>
    <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
      :value="dict.value" />
  </el-select>
</el-form-item>
<el-form-item>
  <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
  <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
</el-form-item> 
</el-form>

<el-row :gutter="10" class="mb8">
<el-col :span="1.5">
  <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
    v-hasPermi="['course:csmng:add']">新增</el-button>
</el-col>
<el-col :span="1.5">
  <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
    v-hasPermi="['course:csmng:edit']">修改</el-button>
</el-col>
<el-col :span="1.5">
  <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
    v-hasPermi="['course:csmng:remove']">删除</el-button>
</el-col>
<el-col :span="1.5">
  <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
    v-hasPermi="['course:csmng:export']">导出</el-button>
</el-col>
<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
</el-row>

<el-table v-loading="loading" :data="csmngList" @selection-change="handleSelectionChange">
<el-table-column type="selection" width="55" align="center" />
<el-table-column label="课程标题" align="center" prop="csmngId" />
<el-table-column label="是否对外" align="center" prop="csmngCode" />
<el-table-column label="是否免费" align="center" prop="csmngName" />
<el-table-column label="是否打折" align="center" prop="csmngSort" />
<el-table-column label="课程价格" align="center" prop="csmngSort" />
<el-table-column label="折后价" align="center" prop="csmngSort" />
<el-table-column label="降价描述" align="center" prop="csmngSort" />
<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
  <template slot-scope="scope">
    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
      v-hasPermi="['course:csmng:edit']">修改</el-button>
    <el-button size="mini" type="text" icon="el-icon-document" @click="handleSection(scope.row)"
      v-hasPermi="['course:csmng:remove']">章节</el-button>
    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
      v-hasPermi="['course:csmng:remove']">删除</el-button>
  </template>
</el-table-column>
</el-table>

<pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
@pagination="getList" />

<!-- 添加或修改岗位对话框 -->
<el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="Dialog Title">
<el-row :gutter="15">
  <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
    <el-col :span="24">
      <el-form-item label="选择分类：" prop="field101">
        <el-select v-model="formData.field101" placeholder="请选择" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in field101Options" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="课程名称：" prop="field103">
        <el-input v-model="formData.field103" placeholder="请输入课程名称：" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="课程副标题：" prop="field104">
        <el-input v-model="formData.field104" placeholder="请输入课程副标题：" clearable
          :style="{width: '100%'}"></el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="课程简介：" prop="field105">
        <el-input v-model="formData.field105" type="textarea" placeholder="请输入课程简介："
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="课程难度：" prop="field107">
        <el-select v-model="formData.field107" placeholder="请选择课程难度：" clearable
          :style="{width: '100%'}">
          <el-option v-for="(item, index) in field107Options" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="商品价格：" prop="field108">
        <el-radio-group v-model="formData.field108" size="medium">
          <el-radio v-for="(item, index) in field108Options" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="打折描述：" prop="field111">
        <el-input v-model="formData.field111" type="textarea" placeholder="限时优惠"
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="排序：" prop="field109">
        <el-input v-model="formData.field109" placeholder="请输入排序：" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="是否轮播：" prop="field112">
        <el-radio-group v-model="formData.field112" size="medium">
          <el-radio v-for="(item, index) in field112Options" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="是否精品推荐：" prop="field115">
        <el-radio-group v-model="formData.field115" size="medium">
          <el-radio v-for="(item, index) in field115Options" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="是否免费：" prop="field116">
        <el-radio-group v-model="formData.field116" size="medium">
          <el-radio v-for="(item, index) in field116Options" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="封面上传：" prop="field119">
        <el-upload ref="field119" :file-list="field119fileList" :action="field119Action" multiple
          :before-upload="field119BeforeUpload" list-type="picture-card">
          <i class="el-icon-plus"></i>
          <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的文件</div>
        </el-upload>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="轮播图上传：" prop="field120">
        <el-upload ref="field120" :file-list="field120fileList" :action="field120Action" multiple
          :before-upload="field120BeforeUpload" list-type="picture-card">
          <i class="el-icon-plus"></i>
          <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的文件</div>
        </el-upload>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="PC轮播图上传：" prop="field121">
        <el-upload ref="field121" :file-list="field121fileList" :action="field121Action" multiple
          :before-upload="field121BeforeUpload" list-type="picture-card">
          <i class="el-icon-plus"></i>
          <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的文件</div>
        </el-upload>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="是否展示详情" prop="field122">
        <el-radio-group v-model="formData.field122" size="medium">
          <el-radio v-for="(item, index) in field122Options" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="课程详情介绍：" prop="field123">
        <el-upload ref="field123" :file-list="field123fileList" :action="field123Action"
          :before-upload="field123BeforeUpload">
          <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
        </el-upload>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="播放量：" prop="field124">
        <el-input v-model="formData.field124" placeholder="请输入播放量：" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="上线状态：" prop="field125">
        <el-select v-model="formData.field125" placeholder="请选择上线状态：" clearable
          :style="{width: '100%'}">
          <el-option v-for="(item, index) in field125Options" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="21">
      <el-form-item label="备注：" prop="field126">
        <el-input v-model="formData.field126" type="textarea" placeholder="请输入备注："
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
    </el-col>
  </el-form>
</el-row>
<div slot="footer">
  <el-button @click="close">取消</el-button>
  <el-button type="primary" @click="handleConfirm">确定</el-button>
</div>
</el-dialog>

</div>
</template>

<script>
import { listCsmng, getCsmng, delCsmng, addCsmng, updateCsmng } from "@/api/course/csmng";

export default {
name: "Csmng",
dicts: ['sys_normal_disable'],
data() {
return {
formData: {
  field101: 1,
  field103: undefined,
  field104: undefined,
  field105: undefined,
  field107: '一般',
  field108: 2,
  field111: undefined,
  field109: 210,
  field112: 1,
  field115: 1,
  field116: 1,
  field119: null,
  field120: null,
  field121: null,
  field122: 2,
  field123: null,
  field124: '',
  field125: 1,
  field126: undefined,
},
rules: {
  field101: [],
  field103: [],
  field104: [],
  field105: [],
  field107: [],
  field108: [],
  field111: [],
  field109: [],
  field112: [],
  field115: [],
  field116: [],
  field122: [],
  field124: [],
  field125: [],
  field126: [],
},
field119Action: 'https://jsonplaceholder.typicode.com/csmngs/',
field119fileList: [],
field120Action: 'https://jsonplaceholder.typicode.com/csmngs/',
field120fileList: [],
field121Action: 'https://jsonplaceholder.typicode.com/csmngs/',
field121fileList: [],
field123Action: 'https://jsonplaceholder.typicode.com/csmngs/',
field123fileList: [],
field101Options: [{
  "label": "影视后期",
  "value": 1
}, {
  "label": "选项二",
  "value": 2
}],
field107Options: [{
  "label": "一般",
  "value": 1
}, {
  "label": "难",
  "value": 2
}],
field108Options: [{
  "label": "原价",
  "value": 1
}, {
  "label": "折扣",
  "value": 2
}],
field112Options: [{
  "label": "是",
  "value": 1
}, {
  "label": "否",
  "value": 2
}],
field115Options: [{
  "label": "是",
  "value": 1
}, {
  "label": "否",
  "value": 2
}],
field116Options: [{
  "label": "是",
  "value": 1
}, {
  "label": "否",
  "value": 2
}],
field122Options: [{
  "label": "是",
  "value": 1
}, {
  "label": "否",
  "value": 2
}],
field125Options: [{
  "label": "上线",
  "value": 1
}, {
  "label": "下线",
  "value": 2
}],
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
// 岗位表格数据
csmngList: [],
// 弹出层标题
title: "",
// 是否显示弹出层
open: false,
// 查询参数
queryParams: {
  pageNum: 1,
  pageSize: 10,
  csmngCode: undefined,
  csmngName: undefined,
  status: undefined
},
// 表单参数
form: {},


};
}, 

computed: {},
watch: {},
created() {
this.getList();
},
methods: {
/**表单相关 */
onOpen() {},
onClose() {
this.$refs['elForm'].resetFields()
},
close() {
this.$emit('update:visible', false)
},
handleConfirm() {
this.$refs['elForm'].validate(valid => {
  if (!valid) return
  this.close()
})
},
field119BeforeUpload(file) {
let isRightSize = file.size / 1024 / 1024 < 2
if (!isRightSize) {
  this.$message.error('文件大小超过 2MB')
}
return isRightSize
},
field120BeforeUpload(file) {
let isRightSize = file.size / 1024 / 1024 < 2
if (!isRightSize) {
  this.$message.error('文件大小超过 2MB')
}
return isRightSize
},
field121BeforeUpload(file) {
let isRightSize = file.size / 1024 / 1024 < 2
if (!isRightSize) {
  this.$message.error('文件大小超过 2MB')
}
return isRightSize
},
field123BeforeUpload(file) {
let isRightSize = file.size / 1024 / 1024 < 2
if (!isRightSize) {
  this.$message.error('文件大小超过 2MB')
}
return isRightSize
},

/** 查询课程列表 */
getList() {
this.loading = true;
listcsmng(this.queryParams).then(response => {
  this.csmngList = response.rows;
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
  csmngId: undefined,
  csmngCode: undefined,
  csmngName: undefined,
  csmngSort: 0,
  status: "0",
  remark: undefined
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
this.ids = selection.map(item => item.csmngId)
this.single = selection.length != 1
this.multiple = !selection.length
},
/** 新增按钮操作 */
handleAdd() {
this.reset();
this.open = true;
this.title = "添加岗位";
},
/** 修改按钮操作 */
handleUpdate(row) {
this.reset();
const csmngId = row.csmngId || this.ids
getcsmng(csmngId).then(response => {
  this.form = response.data;
  this.open = true;
  this.title = "修改岗位";
});
},
/** 提交按钮 */
submitForm: function () {
this.$refs["form"].validate(valid => {
  if (valid) {
    if (this.form.csmngId != undefined) {
      updatecsmng(this.form).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    } else {
      addcsmng(this.form).then(response => {
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
const csmngIds = row.csmngId || this.ids;
this.$modal.confirm('是否确认删除岗位编号为"' + csmngIds + '"的数据项？').then(function () {
  return delcsmng(csmngIds);
}).then(() => {
  this.getList();
  this.$modal.msgSuccess("删除成功");
}).catch(() => { });
},
/** 导出按钮操作 */
handleExport() {
this.download('system/csmng/export', {
  ...this.queryParams
}, `csmng_${new Date().getTime()}.xlsx`)
}
}
};
</script>
  <style>
.el-upload__tip {
line-height: 1.2;
}

</style>

