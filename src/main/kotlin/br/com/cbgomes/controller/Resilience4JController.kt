package br.com.cbgomes.controller

import br.com.cbgomes.service.Resilience4JService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture


@RestController
@RequestMapping("/api/resilience4j")
class Resilience4JController (private val resilience4JService: Resilience4JService) {


    @GetMapping("/cb")
    fun circuitBreaker(): String? {
        return resilience4JService.circuitBreaker()
    }

    @GetMapping("/bulkhead")
    fun bulkhead(): String? {
        return resilience4JService.bulkHead()
    }

    @GetMapping("/tl")
    fun timeLimiter(): CompletableFuture<String> {
        return resilience4JService.timeLimiter()
    }

    @GetMapping("/rl")
    fun rateLimiter(): String? {
        return resilience4JService.rateLimiter()
    }

    @GetMapping("/retry")
    fun retry(): String? {
        return resilience4JService.retry()
    }
}