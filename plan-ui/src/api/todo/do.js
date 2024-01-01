import request from '@/utils/request'

// 查询每日待办列表
export function listDo(query) {
  return request({
    url: '/todo/do/list',
    method: 'get',
    params: query
  })
}

// 查询每日待办详细
export function getDo(doId) {
  return request({
    url: '/todo/do/' + doId,
    method: 'get'
  })
}

// 新增每日待办
export function addDo(data) {
  return request({
    url: '/todo/do',
    method: 'post',
    data: data
  })
}

// 修改每日待办
export function updateDo(data) {
  return request({
    url: '/todo/do',
    method: 'put',
    data: data
  })
}

// 删除每日待办
export function delDo(doId) {
  return request({
    url: '/todo/do/' + doId,
    method: 'delete'
  })
}
