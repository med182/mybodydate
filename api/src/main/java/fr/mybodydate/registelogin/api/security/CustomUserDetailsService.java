package fr.mybodydate.registelogin.api.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.mybodydate.registelogin.api.model.User;
import fr.mybodydate.registelogin.api.repository.IUserRepository;

import java.util.Arrays;
import java.util.List;
// import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhoneNumber(username, username);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        String userIdentifier = (user.getEmail() != null) ? user.getEmail() : user.getPhoneNumber();

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
                userIdentifier,
                user.getPassword(),
                authorities);
    }
}
