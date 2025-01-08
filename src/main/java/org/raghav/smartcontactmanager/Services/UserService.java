package org.raghav.smartcontactmanager.Services;

import org.raghav.smartcontactmanager.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserById(String userId);
    List<User> findAllUsers();
    void deleteUser(String userId);
    User updateUser(User user);
    boolean isUserExists(String userId);
    boolean isUserExistsByEmail(String email);
    User getUserByEmail(String email);
}
