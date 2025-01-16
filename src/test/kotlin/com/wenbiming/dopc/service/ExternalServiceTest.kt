package com.wenbiming.dopc.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)  // This allows Mockito annotations like @Mock
@SpringBootTest
class ExternalServiceTest {
    @Autowired
    private lateinit var externalService: ExternalService  // Inject the service to test

    @Test
    fun testGetLocation() {
        val m = externalService.fetchVenueLocation("home-assignment-venue-stockholm");
        assertNotNull(m)
    }

    @Test
    fun testGetVenueDynamic() {
        val venueDynamicObj = externalService.fetchVenueDynamic("home-assignment-venue-stockholm")
        assertNotNull(venueDynamicObj)
    }

}