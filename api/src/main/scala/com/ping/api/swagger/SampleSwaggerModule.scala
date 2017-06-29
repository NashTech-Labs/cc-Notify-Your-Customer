//package com.ping.api.swagger
//
//import com.google.inject.Provides
//import com.jakehschwartz.finatra.swagger.SwaggerModule
//import io.swagger.models.{Info, Swagger}
//import io.swagger.models.auth.BasicAuthDefinition
//
//
//object SampleSwaggerModule extends SwaggerModule {
//
//  val swaggerUI = new Swagger()
//
//  @Provides
//  def swagger: Swagger = {
//
//    val info = new Info()
//      .description("The Knoldus / Knolder management API, this is a sample for swagger document generation")
//      .version("1.0.1")
//      .title("Knoldus / Knolder Management API")
//
//    swaggerUI
//      .info(info)
//      .addSecurityDefinition("sampleBasic", {
//        val d = new BasicAuthDefinition()
//        d.setType("basic")
//        d
//      })
//
//    swaggerUI
//  }
//}
