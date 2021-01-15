package org.mascotadopta.adoptionsplatform.users.dto;

/**
 * Necessary data to create a <code>User</code>.
 */
public class CreateUserDto
{
    /**
     * Email to register the User with.
     */
    private final String email;
    
    /**
     * Secure password to use. It must contain at least 12 characters, an uppercase letter, a lowercase letter, a symbol
     * and a number.
     */
    private final String password;
    
    /**
     * The first name of the <code>User</code> to register.
     */
    private final String firstName;
    
    /**
     * The last name of the <code>User</code> to register.
     */
    private final String lastName;
    
    /**
     * Single constructor.
     *
     * @param email     Email to register the User with.
     * @param password  Secure password to use. It must contain at least 12 characters, an uppercase letter, a lowercase
     *                  letter, a symbol and a number.
     * @param firstName The first name of the <code>User</code> to register.
     * @param lastName  The last name of the <code>User</code> to register.
     */
    public CreateUserDto(String email, String password, String firstName, String lastName)
    {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * @return The email of the User.
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * @return The password of the User.
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * @return The first name of the User.
     */
    public String getFirstName()
    {
        return firstName;
    }
    
    /**
     * @return The last name of the User.
     */
    public String getLastName()
    {
        return lastName;
    }
}
