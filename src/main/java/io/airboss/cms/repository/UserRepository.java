package io.airboss.cms.repository;

import io.airboss.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    // Métodos personalizados
    User findByEmail(String email);
}
