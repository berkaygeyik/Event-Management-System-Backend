package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.Users;

import javax.transaction.Transactional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
    boolean existsByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
