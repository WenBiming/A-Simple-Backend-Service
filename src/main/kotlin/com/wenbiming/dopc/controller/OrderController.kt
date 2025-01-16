package com.wenbiming.dopc.controller

import com.wenbiming.dopc.service.ExternalService
import com.wenbiming.dopc.service.OrderService
import com.wenbiming.dopc.data.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class OrderController(private val externalService: ExternalService, private val orderService: OrderService) {

    @GetMapping("/delivery-order-price")
    suspend fun getDeliveryOrderPrice(
        @RequestParam venue_slug: String,
        @RequestParam cart_value: Int,
        @RequestParam user_lat: Double,
        @RequestParam user_lon: Double
    ): ResponseEntity<Any> = coroutineScope {
        // Extracted values from the request
        println("Venue Slug: $venue_slug")
        println("Cart Value: $cart_value")
        println("User Latitude: $user_lat")
        println("User Longitude: $user_lon")

        // Perform both I/O tasks in parallel using async
        val venueLocationDefer = async { externalService.fetchVenueLocation(venue_slug) }
        val venueDynamicResponseDefer = async { externalService.fetchVenueDynamic(venue_slug) }

        // Wait for both tasks to complete
        val (venueLocationAny, venueDynamicResponseAny) = awaitAll(venueLocationDefer, venueDynamicResponseDefer)

        if (venueLocationAny == null) {
            val errorResponse = mapOf("error" to "can't find venue location.")
            return@coroutineScope ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }

        if (venueDynamicResponseAny == null) {
            val errorResponse = mapOf("error" to "can't find venue dynamic response.")
            return@coroutineScope ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }

        val venueLocation = venueLocationAny as List<Double>
        val venueDynamicResponse = venueDynamicResponseAny as VenueDynamicResponse

        val cartValue: Long = cart_value.toLong()

        // [longitude, latitude]
        val userLocation: List<Double> = listOf(user_lon, user_lat)
        val distUserVenue = orderService.computeDistance(userLocation, venueLocation)
        val deliveryFee: Long? = orderService.computeDeliveryFee(distUserVenue, venueDynamicResponse)
        if (deliveryFee == null) {
            val errorResponse = mapOf("error" to "user is too far away from venue")
            return@coroutineScope ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }

        val smallOrderSurcharge: Long = orderService.computeSmallOrderSurcharge(cartValue, venueDynamicResponse)

        val totalPrice: Long = cartValue + smallOrderSurcharge + deliveryFee
        val delivery: Delivery = Delivery(
            fee = deliveryFee.toInt(),
            distance = distUserVenue
        )

        val response: DeliveryResponse = DeliveryResponse(
            total_price = totalPrice,
            small_order_surcharge = smallOrderSurcharge.toInt(),
            cart_value = cartValue.toInt(),
            delivery = delivery
        )

        return@coroutineScope ResponseEntity(response, HttpStatus.OK)
    }
}