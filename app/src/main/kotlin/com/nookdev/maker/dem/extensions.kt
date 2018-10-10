package com.nookdev.maker.dem

fun wtf(message: String = "Something went wrong"): Nothing {
    throw WhatTheHeckException(message)
}
