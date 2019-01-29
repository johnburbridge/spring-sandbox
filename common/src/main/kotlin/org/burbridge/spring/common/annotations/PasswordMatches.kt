package org.burbridge.spring.common.annotations

import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordMatchesValidator::class])
annotation class PasswordMatches (
    val message: String = "The emails do not match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)
