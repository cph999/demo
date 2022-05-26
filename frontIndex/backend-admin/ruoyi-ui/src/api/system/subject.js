import request from '@/utils/request'

// 查询专题列表
export function listSubject(query) {
  return request({
    url: '/system/subject/list',
    method: 'get',
    params: query
  })
}

// 查询专题详细
export function getSubject(subjectId) {
  return request({
    url: '/system/subject/' + subjectId,
    method: 'get'
  })
}

// 新增专题
export function addSubject(data) {
  return request({
    url: '/system/subject',
    method: 'post',
    data: data
  })
}

// 修改专题
export function updateSubject(data) {
  return request({
    url: '/system/subject',
    method: 'put',
    data: data
  })
}

// 删除专题
export function delSubject(subjectId) {
  return request({
    url: '/system/subject/' + subjectId,
    method: 'delete'
  })
}
