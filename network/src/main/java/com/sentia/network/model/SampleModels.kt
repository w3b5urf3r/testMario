package com.sentia.network.model

import com.squareup.moshi.Json

/**
 * Created by mariolopez on 27/12/17.
 */
data class SampleModels(@Json(name = "something") val s1: String,
                        @Json(name = "somekeyint") val int1: Int,
                        @Json(name = "double1") val double1: Int)


/*
 install JSON To Kotlin Class plugin which converts json string to Kotlin data class with Moshi annotation.
 https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-

 The json string is from https://jsonplaceholder.typicode.com/users
 JsonPlaceholder: Fake Online REST API for Testing and Prototyping
 https://jsonplaceholder.typicode.com/
 */
data class DummyUser(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String,
        @Json(name = "username")
        val username: String,
        @Json(name = "email")
        val email: String,
        @Json(name = "address")
        val address: Address,
        @Json(name = "phone")
        val phone: String,
        @Json(name = "website")
        val website: String,
        @Json(name = "company")
        val company: Company
)

data class Address(
        @Json(name = "street")
        val street: String,
        @Json(name = "suite")
        val suite: String,
        @Json(name = "city")
        val city: String,
        @Json(name = "zipcode")
        val zipcode: String,
        @Json(name = "geo")
        val geo: Geo
)

data class Geo(
        @Json(name = "lat")
        val lat: String,
        @Json(name = "lng")
        val lng: String
)

data class Company(
        @Json(name = "name")
        val name: String,
        @Json(name = "catchPhrase")
        val catchPhrase: String,
        @Json(name = "bs")
        val bs: String
)