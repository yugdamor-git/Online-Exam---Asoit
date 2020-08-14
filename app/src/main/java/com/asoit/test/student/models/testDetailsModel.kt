package com.asoit.test.student.models

import com.google.firebase.Timestamp

data class testDetailsModel(
    var created_date:Timestamp? = null,
    var test_date: Timestamp? = null,
    var test_duration:Int? = null,
    var semester:String? = null,
    var branch:String? = null,
    var subject:String? = null,
    var id:String?=null
)