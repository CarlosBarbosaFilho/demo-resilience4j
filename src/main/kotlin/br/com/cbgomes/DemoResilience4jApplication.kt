package br.com.cbgomes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoResilience4jApplication

fun main(args: Array<String>) {
    runApplication<DemoResilience4jApplication>(*args)
}
