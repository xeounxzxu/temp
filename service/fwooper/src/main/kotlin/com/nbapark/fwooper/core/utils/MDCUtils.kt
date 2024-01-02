package com.nbapark.fwooper.core.utils

import org.slf4j.MDC

object MDCUtils {

    private val USER_ID = "USER_ID"

    private val USER_NIKENAME = "USER_NIKENAME"

    fun getUserId() = MDC.get(USER_ID)

    fun getUserNickName() = MDC.get(USER_NIKENAME)

    fun setUserId(userId: Long) = MDC.put(USER_ID, userId.toString())

    fun setUserNickName(userNikeName: String) = MDC.put(USER_NIKENAME, userNikeName)

    fun clear() = MDC.clear()
}
