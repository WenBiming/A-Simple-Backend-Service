package com.wenbiming.dopc.service

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private lateinit var orderService: OrderService
    @Autowired
    private lateinit var externalService: ExternalService  // Inject the service to test

    @Test
    fun testCalculateDeliveryFee() {
        val venueDynamic = externalService.fetchVenueDynamic("home-assignment-venue-stockholm")
        assertNotNull(venueDynamic)
        val distance = 600
        val res = venueDynamic?.let { orderService.computeDeliveryFee(distance, it) }
        println(res)
    }
}