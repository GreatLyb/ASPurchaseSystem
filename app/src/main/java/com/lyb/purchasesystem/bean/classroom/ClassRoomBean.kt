package com.lyb.purchasesystem.bean.classroom

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-07 10:12
 */
class ClassRoomBean {
    var classRoomId: String//教室ID
    var classRoomName: String//教室名称
    var trainer: String//培训人
    var department: String//部门
    var departmentID: String//部门ID
    var phoneNum: String//联系电话
    var startTime: String//开始时间
    var endTime: String//结束时间

    constructor(classRoomId: String, classRoomName: String, trainer: String, department: String, departmentID: String, phoneNum: String, startTime: String, endTime: String) {
        this.classRoomId = classRoomId
        this.classRoomName = classRoomName
        this.trainer = trainer
        this.department = department
        this.departmentID = departmentID
        this.phoneNum = phoneNum
        this.startTime = startTime
        this.endTime = endTime
    }
}