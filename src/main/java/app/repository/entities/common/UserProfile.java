package app.repository.entities.common;

import app.repository.enumerations.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_PROFILE")
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", columnDefinition = "enum ('ADMIN','USER')", unique = true, nullable = false)
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserProfile() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;

        UserProfile that = (UserProfile) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getRole() == that.getRole();

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfile{");
        sb.append("id=").append(id);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
