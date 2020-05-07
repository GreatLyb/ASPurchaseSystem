package com.lyb.purchasesystem.bean.classroom

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-05-07 10:12
 */
class ClassRoomBean {
    lateinit var classRoomId: String//教室ID
    lateinit var classRoomName: String//教室名称
    lateinit var trainer: String//培训人
    lateinit var department: String//部门
    lateinit var departmentID: String//部门ID
    lateinit var phoneNum: String//联系电话
    lateinit var startTime: String//开始时间
    lateinit var endTime: String//结束时间

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