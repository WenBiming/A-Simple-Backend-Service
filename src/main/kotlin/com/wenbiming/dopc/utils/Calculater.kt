package com.wenbiming.dopc.utils

import kotlin.math.*

fun haversineInKilometre(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371 // Radius of the Earth in km
    val dLat = Math.toRadians(lat2 - lat1) // Difference in latitude
    val dLon = Math.toRadians(lon2 - lon1) // Difference in longitude

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // Distance in kilometers
}

fun calculateDistInMetreDouble(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    return haversineInKilometre(lat1, lon1, lat2, lon2) * 1000
}

fun calculateDistInMetreInteger(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
    return round(haversineInKilometre(lat1, lon1, lat2, lon2) * 1000).toInt()
}