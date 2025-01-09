package org.raghav.smartcontactmanager.Repositories;

import org.raghav.smartcontactmanager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findByEmail(String Email);
    // public User findByEmailAndPassword(String email, String password);
}
