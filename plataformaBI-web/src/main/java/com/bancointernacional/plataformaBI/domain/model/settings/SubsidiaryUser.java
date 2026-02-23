package com.bancointernacional.plataformaBI.domain.model.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subsidiary_user")
public class SubsidiaryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(name = "open_id_user")
    private String openIdUser;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subsidiary_id", referencedColumnName = "subsidiary_id")
    private Subsidiary subsidiary;


    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getOpenIdUser() {
        return openIdUser;
    }

    public void setOpenIdUser(String openIdUser) {
        this.openIdUser = openIdUser;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Subsidiary getSubsidiary() {
        return subsidiary;
    }

    public void setSubsidiary(Subsidiary subsidiary) {
        this.subsidiary = subsidiary;
    }
}
