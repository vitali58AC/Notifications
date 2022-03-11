package com.example.notifications.data.utils

class IncorrectMimeTypeException: Exception() {

    override val message: String
        get() = "Incorrect MIME type!"
}