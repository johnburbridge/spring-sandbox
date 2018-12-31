package org.burbridge.sandbox.api.security

import org.burbridge.sandbox.api.domain.core.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class AppUserPrincipal(val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities = user.roles.map { role ->
            SimpleGrantedAuthority(role.name)
        }
        return Collections.unmodifiableCollection(authorities)
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
