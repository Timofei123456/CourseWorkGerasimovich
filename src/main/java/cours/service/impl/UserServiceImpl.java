package cours.service.impl;

import cours.entity.Role;
import cours.entity.User;
import cours.repository.UserRepository;
import cours.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User read(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void edit(User entity) {
        User user = userRepository.findById(entity.getId()).orElseThrow(IllegalArgumentException::new);
        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());
        userRepository.save(user);
    }
    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public User createUser (String username, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword)); // Шифруем пароль
        user.setRole(Role.valueOf("ROLE_USER")); // Устанавливаем роль
        userRepository.save(user); // Сохраняем пользователя
        return user;
    }
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
