package com.swati.loginapplication.utils

import android.text.TextUtils
import java.util.regex.Pattern


class Validator {

    companion object {


        fun isValidUserName(text: String): Boolean {
            val userNamePattern: Pattern = Pattern.compile(
                "[a-zA-Z0-9[^\\s]]{2,}"
            )
            return !TextUtils.isEmpty(text) && userNamePattern.matcher(text).matches()
        }

        fun isValidPassword(text: String): Boolean {
            val passwordPattern: Pattern = Pattern.compile(
                "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@!?_])"
                        + "(?=\\S+$).{5,}$"
            )
            return !TextUtils.isEmpty(text) && passwordPattern.matcher(text).matches()
        }

    }
}