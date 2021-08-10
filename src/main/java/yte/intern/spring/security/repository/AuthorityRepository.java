package yte.intern.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.security.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
