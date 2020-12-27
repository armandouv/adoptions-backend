package org.mascotadopta.adoptionsplatform.users.details;

import org.mascotadopta.adoptionsplatform.users.User;
import org.mascotadopta.adoptionsplatform.users.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService
{
    private final UsersRepository usersRepository;
    
    public ApplicationUserDetailsService(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Optional<User> user = this.usersRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("User does not exist");
        return new ApplicationUserDetails(user.get());
    }
}
