package com.caldev.wishlister.models;

import com.caldev.wishlister.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties({ "roles" })
    private List<UserEntity> userEntities;

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleName getRole() {
        return roleName;
    }

    public void setRole(RoleName roleName) {
        this.roleName = roleName;
    }

    public List<UserEntity> getUsers() {
        return userEntities;
    }

    public void setUsers(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName=" + roleName +
                '}';
    }
}
