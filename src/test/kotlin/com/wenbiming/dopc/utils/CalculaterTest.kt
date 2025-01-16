package com.wenbiming.dopc.utils

import org.junit.jupiter.api.Test

class CalculaterTest {
    @Test
    fun TestCalculateDistance() {
        val lat1 = 59.366102491137696
        val lon1 = 18.039073461128798
        val lat2 = 59.366102491137696
        val lon2 = -18.039073461128798
        val resInKM = haversineInKilometre(lat1, lon1, lat2, lon2)
        println(resInKM)
        val resInMetre = calculateDistInMetreDouble(lat1, lon1, lat2, lon2)
        println(resInMetre)
    }
}