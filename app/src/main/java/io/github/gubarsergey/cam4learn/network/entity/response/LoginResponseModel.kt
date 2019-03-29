package io.github.gubarsergey.cam4learn.network.entity.response

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(@SerializedName("JWT") val token: String)