package com.ping.models


case class EmailInfo(clientId:Int,to:List[String],cc:List[String],bcc:List[String],subject:String,content:String)
