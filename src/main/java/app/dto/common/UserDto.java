package app.dto.common;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import app.validate.PasswordMatches;
import app.validate.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String login;
    @NotNull
    @NotEmpty
    @ValidEmail
    @Size(min = 1)
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String password;
    @Size(min = 3)
    private String matchingPassword;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String role;

    public UserDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;

        UserDto userDto = (UserDto) o;

        if (getLogin() != null ? !getLogin().equals(userDto.getLogin()) : userDto.getLogin() != null) return false;
        if (getEmail() != null ? !getEmail().equals(userDto.getEmail()) : userDto.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(userDto.getPassword()) : userDto.getPassword() != null)
            return false;
        if (getMatchingPassword() != null ? !getMatchingPassword().equals(userDto.getMatchingPassword()) : userDto.getMatchingPassword() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(userDto.getLastName()) : userDto.getLastName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(userDto.getFirstName()) : userDto.getFirstName() != null)
            return false;
        if (getPatronymic() != null ? !getPatronymic().equals(userDto.getPatronymic()) : userDto.getPatronymic() != null)
            return false;
        return getRole() != null ? getRole().equals(userDto.getRole()) : userDto.getRole() == null;

    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getMatchingPassword() != null ? getMatchingPassword().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getPatronymic() != null ? getPatronymic().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }
}
