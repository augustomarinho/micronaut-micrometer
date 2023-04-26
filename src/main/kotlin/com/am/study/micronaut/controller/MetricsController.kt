package com.am.study.micronaut.controller

import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import java.time.Instant
import java.util.concurrent.TimeUnit


@Controller("/v1/api/metrics")
class MetricsController constructor(private val meterRegistry: MeterRegistry) {

    @Get(value = "/histogram")
    fun histogram(@QueryValue name: String): HttpResponse<String> {

        DistributionSummary
            .builder("my.ratio")
            .baseUnit("request")
            .tag("name", name)
            .publishPercentiles(0.5, 0.90, 0.95, 0.99)
            .serviceLevelObjectives(5.0)
            .register(meterRegistry)
            .record(1.0)
        return HttpResponse.ok(Instant.now().toString())
    }

    @Get(value = "/timer")
    fun timer(@QueryValue name: String): HttpResponse<String> {

        Timer
            .builder("my.requests")
            .tag("name", name)
            .publishPercentiles(0.5, 0.90, 0.95, 0.99)
            .register(meterRegistry)
            .record(Instant.now().epochSecond, TimeUnit.SECONDS)
        return HttpResponse.ok(Instant.now().toString())
    }

    @Get(value = "/counter")
    fun counter(@QueryValue name: String): HttpResponse<String> {

        meterRegistry.counter(
            "my.summary",
            listOf(Tag.of("name", name))
        ).increment()
        return HttpResponse.ok(Instant.now().toString())
    }
}