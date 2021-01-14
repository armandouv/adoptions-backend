package org.mascotadopta.adoptionsplatform.users.details;

import org.mascotadopta.adoptionsplatform.users.User;
import org.mascotadopta.adoptionsplatform.users.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom UserDetailsService to retrieve Users from User's repository.
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService
{
    /**
     * User's JPA repository.
     */
    private final UsersRepository usersRepository;
    
    /**
     * Single constructor.
     *
     * @param usersRepository User's JPA repository.
     */
    public ApplicationUserDetailsService(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }
    
    /**
     * Loads a User from <code>usersRepository</code>.
     *
     * @param email Email of the User.
     * @return UserDetails of the User.
     * @throws UsernameNotFoundException If the User doesn't exist.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Optional<User> user = this.usersRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("User does not exist");
        return new ApplicationUserDetails(user.get());
    }
}
