package com.wenbiming.dopc.data

data class VenueStaticResponse(
    val venue_raw: VenueRawStatic
)

data class VenueRawStatic(
    val location: Location
)

data class Location(
    val coordinates: List<Double> // List of coordinates [lat, lon]
)

