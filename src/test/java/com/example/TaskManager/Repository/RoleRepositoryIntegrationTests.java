package com.example.TaskManager.Repository;

import com.example.TaskManager.Model.Entities.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoleRepositoryIntegrationTests {

    RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryIntegrationTests(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    public void testRoleCreationAndReturning(){
        Role testRole = new Role(1L,"admin");
        roleRepository.save(testRole);
        Optional<Role> role = roleRepository.findById(1L);
        assertThat(role.get()).isEqualTo(testRole);
    }

    @Test
    public void testCreatingMultipleRolesAndReturning(){
        Role testRole = new Role(1L,"admin");
        Role testRole2 = new Role(2L,"admin2");
        Role testRole3 = new Role(3L,"admin3");

        roleRepository.save(testRole);
        roleRepository.save(testRole2);
        roleRepository.save(testRole3);

        Iterable<Role> roles = roleRepository.findAll();

        assertThat(roles).hasSize(3).contains(testRole,testRole2,testRole3);
    }

    @Test
    public void testUpdatingRoles(){
        Role testRole = new Role(1L,"admin");
        roleRepository.save(testRole);
        testRole.setName("USER");
        roleRepository.save(testRole);
        Optional<Role> role = roleRepository.findById(1L);
        assertThat(role.get()).isEqualTo(testRole);
    }

    @Test
    public void testDeletingRole(){
        Role testRole = new Role(1L,"admin");
        Role testRole2 = new Role(2L,"admin2");
        roleRepository.save(testRole);
        roleRepository.save(testRole2);
        roleRepository.delete(testRole);
        assertThat(roleRepository.findAll()).hasSize(1);
    }

    @Test
    public void testFindingRoleByName(){
        Role testRole = new Role(1L,"admin");
        Role testRole2 = new Role(2L,"admin2");
        roleRepository.save(testRole);
        roleRepository.save(testRole2);
        Optional<Role> role = roleRepository.findByName(testRole.getName());
        assertThat(role.get()).isEqualTo(testRole);
    }
}
