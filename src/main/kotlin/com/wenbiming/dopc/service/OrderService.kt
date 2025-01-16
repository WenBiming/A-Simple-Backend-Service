package com.wenbiming.dopc.service

import com.wenbiming.dopc.data.*
import com.wenbiming.dopc.utils.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.round

@Service
class OrderService {
    fun computeSmallOrderSurcharge(userCartValue: Long, venueDynamicResponse: VenueDynamicResponse): Long{
        val orderMinimumNoSurcharge: Long = venueDynamicResponse.venue_raw.delivery_specs.order_minimum_no_surcharge
        if (userCartValue > orderMinimumNoSurcharge) {
            return 0
        }

        return orderMinimumNoSurcharge - userCartValue
    }

    fun computeDeliveryFee(distance: Int, venueDynamicResponse: VenueDynamicResponse): Long? {

        val distanceRanges: List<DistanceRangesItem> = venueDynamicResponse.venue_raw.delivery_specs.delivery_pricing.distance_ranges
        if (distanceRanges.size < 2) {
            return null
        }

        val lastRange: DistanceRangesItem = distanceRanges.last()
        val maxDistanceExclusive: Long = lastRange.min
        if (distance < 0 || distance >= maxDistanceExclusive) {
            return null
        }

        val targetRange = distanceRanges.find {it.min <= distance && it.max > distance}
        if (targetRange == null) {
            return null
        }

        val a: Long = targetRange.a
        val b: Long = targetRange.b
        val basePrice: Long = venueDynamicResponse.venue_raw.delivery_specs.delivery_pricing.base_price
        val fee: Long = basePrice + a + round(b * distance / 10.0).toLong()
        return fee

    }

    fun computeDistance(userLocation: List<Double>, venueLocation: List<Double>): Int {
        val distance: Int = calculateDistInMetreInteger(userLocation[1], userLocation[0], venueLocation[1], venueLocation[0])
        return distance
    }

}