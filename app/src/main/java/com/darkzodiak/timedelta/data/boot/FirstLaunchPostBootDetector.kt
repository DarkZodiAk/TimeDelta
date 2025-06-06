package com.darkzodiak.timedelta.data.boot

interface FirstLaunchPostBootDetector {
    fun isFirstLaunch(): Boolean
}