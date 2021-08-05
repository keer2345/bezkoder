package com.keer.springjwt.repository;

import com.keer.springjwt.models.ERole;
import com.keer.springjwt.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(ERole name);

}
