package org.burbridge.sandbox.api.config

import org.burbridge.sandbox.api.domain.Task
import org.burbridge.sandbox.api.domain.system.Privilege
import org.burbridge.sandbox.api.domain.system.Role
import org.burbridge.sandbox.api.domain.system.User
import org.burbridge.sandbox.api.repository.TaskRepository
import org.burbridge.sandbox.api.repository.system.PrivilegeRepository
import org.burbridge.sandbox.api.repository.system.RoleRepository
import org.burbridge.sandbox.api.repository.system.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitialDataLoader : ApplicationListener<ContextRefreshedEvent> {

    var alreadySetup = false

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var privilegeRepository: PrivilegeRepository

    @Autowired
    lateinit var taskRepository: TaskRepository

    override fun onApplicationEvent(event: ContextRefreshedEvent) = initialize()

    @Transactional
    fun initialize() {
        if (alreadySetup) return

        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")

        val adminRole = createRoleIfNotFound("ROLE_ADMIN", listOf(readPrivilege, writePrivilege))
        val userRole = createRoleIfNotFound("ROLE_USER", listOf(readPrivilege))

        userRepository.saveAll(listOf(
                User(
                    id = 1L,
                    email = "johnburbridge@gmail.com",
                    firstName = "John",
                    lastName = "Burbridge",
                    password = "Passw0rd",
                    enabled = true,
                    tokenExpired = false,
                    roles = listOf(adminRole)
                ),
                User(
                        id = 2L,
                        email = "test@metabuild.org",
                        firstName = "Test",
                        lastName = "User",
                        password = "test",
                        enabled = true,
                        tokenExpired = false,
                        roles = listOf(userRole)
                ))
        )
        taskRepository.saveAll(setOf(
                Task(id = 2L, name = "Task1", description = "The first task", content = "This is a rather boring first post"),
                Task(id = 3L, name = "Task2", description = "The second task", content = "This is another rather boring first post"),
                Task(id = 4L, name = "Task3", description = "The 3rd task", content = "This is yet another rather boring first post!")
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
        if (!alreadySetup) return
        taskRepository.deleteAll()
        userRepository.deleteAll()
        roleRepository.deleteAll()
        privilegeRepository.deleteAll()
        alreadySetup = false
    }
}