package br.com.cbgomes.service

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.core.CoroutinesUtils
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.random.Random


@Service
class Resilience4JService {

    @CircuitBreaker(name = "circuit-breaker-instance-resilience4j-service", fallbackMethod = "circuitBreakerFallBack")
    fun circuitBreaker(): String {
       return callCircuitBreakRemote()
    }

    fun circuitBreakerFallBack(exception: Exception): String{
        return String.format("Fallback executed to circuit breaker. %s\n", exception.message)
    }

    @RateLimiter(name = "rl-instance-resilience4j-service")
    fun rateLimiter(): String {
        return String.format("Executing rate limiter method")
    }

    @TimeLimiter(name = "tl-instance-resilience4j-service")
    fun timeLimiter(): CompletableFuture<String> {
        return CompletableFuture.supplyAsync(this::timeLimiterRemoteCall)
    }


    @Retry(name = "rt-instance-resilience4j-service")
    fun retry(): String {
        return retryRemoteCall()
    }

    @Bulkhead(name = "tp-instance-resilience4j-service", type = Bulkhead.Type.THREADPOOL)
    fun bulkHead(): String {
        return "Executing bilk head remote call"
    }

    private fun retryRemoteCall(): String {
        // will fail in 80% the time
        val random: Double = Math.random()
        if (random <= 0.8) throw RuntimeException("Retry remote call fails")
        return "Executing retry call remote"
    }

    private fun timeLimiterRemoteCall(): String {
        val random: Double = Math.random()
        // will fail in 50% the time
        return if(random <= 0.5) {
            "Executing time limited call"
        }else {
            try {
                println("delaying execution")
                Thread.sleep(3000)

            }catch (interruptedException: InterruptedException){
                interruptedException.message
            }
            "Exception will by raised"
        }
    }

    //Calling test on circuit breaker
    private fun callCircuitBreakRemote(): String {
        // should fail more than 70% of time
        val random: Double = Math.random()
        if (random <= 0.7) throw RuntimeException("Fail to remote call circuit breaker")
        return "Call remote to service executed with success"
    }

}