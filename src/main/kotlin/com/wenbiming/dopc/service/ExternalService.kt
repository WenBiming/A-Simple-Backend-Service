package com.wenbiming.dopc.service

import com.wenbiming.dopc.data.VenueDynamicResponse
import com.wenbiming.dopc.data.VenueStaticResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity


@Service
class ExternalService(private val restTemplate: RestTemplate) {


    fun fetchVenueLocation(venueSlug: String): List<Double>? {
        val url = String.format("https://consumer-api.development.dev.woltapi.com" +
                "/home-assignment-api/v1/venues/%s/static", venueSlug)

        return try {
            // Get the response from the external API
            val response = restTemplate.getForObject(url, VenueStaticResponse::class.java)

            // Access the coordinates field through the nested structure
            response?.venue_raw?.location?.coordinates
        } catch (e: Exception) {
            println("wrong venue slug: ${e.message}")
            null
        }

    }



    fun fetchVenueDynamic(venueSlug: String): VenueDynamicResponse? {
        val url = String.format("https://consumer-api.development.dev.woltapi.com" +
                "/home-assignment-api/v1/venues/%s/dynamic", venueSlug)
        return try {
            val response = restTemplate.getForObject(url, VenueDynamicResponse:: class.java)
            response
        } catch (e: Exception) {
            println("wrong venue slug: ${e.message}")
            null
        }
    }


}