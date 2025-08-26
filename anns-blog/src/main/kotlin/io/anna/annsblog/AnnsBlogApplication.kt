package io.anna.annsblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnnsBlogApplication

fun main(args: Array<String>) {
    runApplication<AnnsBlogApplication>(*args)

}