package org.burbridge.sandbox.api.config

import mu.KotlinLogging
import org.burbridge.sandbox.api.domain.Task
import org.burbridge.sandbox.api.domain.core.Privilege
import org.burbridge.sandbox.api.domain.core.Role
import org.burbridge.sandbox.api.domain.core.User
import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.sandbox.api.repository.core.PrivilegeRepository
import org.burbridge.sandbox.api.repository.core.RoleRepository
import org.burbridge.sandbox.api.repository.core.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Component
class InitialDataLoader : ApplicationListener<ContextRefreshedEvent> {

    var alreadySetup = false

    @Value("\${sandbox.user.username}")
    lateinit var sandboxUserUsername: String

    @Value("\${sandbox.user.password}")
    lateinit var sandboxUserPassword: String


    @Value("\${sandbox.admin.username}")
    lateinit var sandboxAdminUsername: String

    @Value("\${sandbox.admin.password}")
    lateinit var sandboxAdminPassword: String


    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var privilegeRepository: PrivilegeRepository

    @Autowired
    lateinit var taskRepository: TaskRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    override fun onApplicationEvent(event: ContextRefreshedEvent) = initialize()

    @Transactional
    fun initialize() {
        logger.debug { "initialize(): alreadySetup = $alreadySetup" }
        if (alreadySetup) return

        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")

        val adminRole = createRoleIfNotFound("ROLE_ADMIN", listOf(readPrivilege, writePrivilege))
        val userRole = createRoleIfNotFound("ROLE_USER", listOf(readPrivilege))

        userRepository.saveAll(listOf(
                User(
                    id = 1L,
                    email = sandboxAdminUsername,
                    firstName = "John",
                    lastName = "Burbridge",
                    password = encoder.encode(sandboxAdminPassword),
                    enabled = true,
                    tokenExpired = false,
                    roles = arrayListOf(adminRole)
                ),
                User(
                        id = 2L,
                        email = sandboxUserUsername,
                        firstName = "Test",
                        lastName = "User",
                        password = encoder.encode(sandboxUserPassword),
                        enabled = true,
                        tokenExpired = false,
                        roles = arrayListOf(userRole)
                ))
        )
        taskRepository.saveAll(setOf(
                Task(id = 100L, name = "Task1", description = "The first task", content = "This is a rather boring first post"),
                Task(id = 101L, name = "Task2", description = "The second task", content = "This is another rather boring first post"),
                Task(id = 102L, name = "Task3", description = "The 3rd task", content = "This is yet another rather boring first post!")
        ))
        alreadySetup = true
    }

    @Transactional
    fun createPrivilegeIfNotFound(name: String): Privilege {

        var privilege = privilegeRepository.findByName(name)
        if (privilege == null) {
            privilege = Privilege(name = name)
            privilegeRepository.save(privilege)
        }
        return privilege
    }

    @Transactional
    fun createRoleIfNotFound(name: String, privileges: Collection<Privilege>): Role {

        var role = roleRepository.findByName(name)
        if (role == null) {
            role = Role(name = name)
            role.privileges = privileges
            roleRepository.save(role)
        }
        return role
    }

    fun clean() {
        logger.debug { "clean(): alreadySetup = $alreadySetup" }
        if (!alreadySetup) return
        taskRepository.deleteAll()
        userRepository.deleteAll()
        roleRepository.deleteAll()
        privilegeRepository.deleteAll()
        alreadySetup = false
    }
}