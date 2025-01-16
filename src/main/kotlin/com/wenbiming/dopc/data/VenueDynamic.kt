package com.wenbiming.dopc.data

import java.util.Objects

data class VenueDynamicResponse(
    val venue_raw: VenueRawDynamic
)

data class VenueRawDynamic(
    val delivery_specs: DeliverySpecsDynamic
)

data class DeliverySpecsDynamic(
    val order_minimum_no_surcharge: Long,
    val delivery_pricing: DeliveryPricingDynamic
)

data class DeliveryPricingDynamic(
    val base_price: Long,
    val distance_ranges: List<DistanceRangesItem>
)

data class DistanceRangesItem(
    val min: Long,
    val max: Long,
    val a: Long,
    val b: Long,
    val flag: Boolean?
)