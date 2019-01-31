package org.burbridge.spring.common.annotations

import org.burbridge.spring.common.dto.UserDto
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordMatchesValidator : ConstraintValidator<PasswordMatches, Any> {
    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        val userDto = value as UserDto
        return userDto.password.equals(userDto.matchingPassword)
    }
}
