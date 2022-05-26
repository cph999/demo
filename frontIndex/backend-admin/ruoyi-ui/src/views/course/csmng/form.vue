<template>
  <div>
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
export default {
  inheritAttrs: false,
  components: {},
  props: [],
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
      field119Action: 'https://jsonplaceholder.typicode.com/posts/',
      field119fileList: [],
      field120Action: 'https://jsonplaceholder.typicode.com/posts/',
      field120fileList: [],
      field121Action: 'https://jsonplaceholder.typicode.com/posts/',
      field121fileList: [],
      field123Action: 'https://jsonplaceholder.typicode.com/posts/',
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
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
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
  }
}

</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
