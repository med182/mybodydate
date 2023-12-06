package fr.mybodydate.registelogin.api.security;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.user.User;
import fr.mybodydate.registelogin.api.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmailOrPhoneNumber(username);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        String userIdentifier = (user.getEmail() != null) ? user.getPhoneNumber() : user.getPassword();

        return new org.springframework.security.core.userdetails.User(
                userIdentifier,
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))

        );

    }

}
