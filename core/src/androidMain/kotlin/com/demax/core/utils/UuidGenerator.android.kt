package com.demax.core.utils

import java.util.UUID

actual object UuidGenerator {
    actual fun generate(): String {
        return UUID.randomUUID().toString()
    }
}
