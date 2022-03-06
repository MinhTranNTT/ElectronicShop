package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired private UserRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userMinh = new User("minhtran@gmai.com", "minhtran", "Tran", "Minh");
		userMinh.addRole(roleAdmin);
		
		User savedUser = repo.save(userMinh);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2021", "Ravi", "Beckam");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User userSaved = repo.save(userRavi);
		
		assertThat(userSaved.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUser() {
		User userMinh = repo.findById(1).get();
		System.out.println(userMinh);
		assertThat(userMinh).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userMinh = repo.findById(1).get();
		userMinh.setEnabled(true);
		userMinh.setEmail("minhprogramming@gmail.com");
		
		repo.save(userMinh);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSales = new Role(2);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSales);
		
		repo.save(userRavi);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "obama@gmail.com";
		User userByEmail = repo.getUserByEmail(email);
		
		assertThat(userByEmail).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);

		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	
	

}
