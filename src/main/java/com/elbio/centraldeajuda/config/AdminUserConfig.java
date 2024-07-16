package com.elbio.centraldeajuda.config;

import com.elbio.centraldeajuda.entities.Role;
import com.elbio.centraldeajuda.entities.User;
import com.elbio.centraldeajuda.repository.RoleRepository;
import com.elbio.centraldeajuda.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Inserir roles
        Role adminRole = new Role();
        adminRole.setRoleId(1L);
        adminRole.setName(Role.Values.ADMIN.name());
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleId(2L);
        userRole.setName(Role.Values.USUARIO.name());
        roleRepository.save(userRole);

        Role managerRole = new Role();
        managerRole.setRoleId(3L);
        managerRole.setName(Role.Values.GERENCIADOR.name());
        roleRepository.save(managerRole);

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
