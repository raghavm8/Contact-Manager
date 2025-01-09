package org.raghav.smartcontactmanager.Implementations;

import org.raghav.smartcontactmanager.Entities.Roles;
import org.raghav.smartcontactmanager.Entities.User;
import org.raghav.smartcontactmanager.Exceptions.ResourceNotFoundException;
import org.raghav.smartcontactmanager.Repositories.UserRepository;
import org.raghav.smartcontactmanager.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        String UserId = UUID.randomUUID().toString();
        user.setId(UserId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRolesList(List.of(Roles.ROLE_USER));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.deleteById(user.getId());
    }

    @Override
    public User updateUser(User user) {
        User user2 = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Info: update user2 from user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // Info: save the user in database
        User savedUser = userRepository.save(user2);

        return savedUser;
    }

    @Override
    public boolean isUserExists(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user == null ? false : true;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }
}
