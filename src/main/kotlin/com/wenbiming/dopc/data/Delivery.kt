package com.wenbiming.dopc.data

data class DeliveryResponse(
    val total_price: Long,
    val small_order_surcharge: Int,
    val cart_value: Int,
    val delivery: Delivery
)

data class Delivery(
    val fee: Int,
    val distance: Double
)