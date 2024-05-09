package com.example.joyceapplication

data class myJsonItems(val districtName:String, val issueDate:String, val issueTime:String, val issurGbn:String)
data class myJsonBody(val items: MutableList<myJsonItems>)
data class myJsonResponse(val body: myJsonBody)
data class JsonResponse(val response: myJsonResponse)

