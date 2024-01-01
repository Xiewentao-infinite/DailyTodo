import request from '@/utils/request'

// 查询待办列表
export function listEvent(query) {
  return request({
    url: '/todo/event/list',
    method: 'get',
    params: query
  })
}

// 查询待办详细
export function getEvent(eventId) {
  return request({
    url: '/todo/event/' + eventId,
    method: 'get'
  })
}

// 新增待办
export function addEvent(data) {
  return request({
    url: '/todo/event',
    method: 'post',
    data: data
  })
}

// 修改待办
export function updateEvent(data) {
  return request({
    url: '/todo/event',
    method: 'put',
    data: data
  })
}

// 删除待办
export function delEvent(eventId) {
  return request({
    url: '/todo/event/' + eventId,
    method: 'delete'
  })
}
