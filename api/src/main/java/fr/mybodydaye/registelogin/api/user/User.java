package fr.mybodydaye.registelogin.api.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import fr.mybodydaye.registelogin.api.user_profile.UserProfile;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "L'adresse e-mail ne peut pas être vide")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Veuillez fournir une adresse e-mail valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&?!])[A-Za-z\\d@#$%^&?!]{8,}$", message = "Le mot de passe doit contenir : au moins une lettre majuscule, au moins une lettre minuscule, au moins un chiffre, au moins un caractère spécial (@#$%^&?!), et au moins huit (8) caractères.")
    private String password;

    @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
    @Pattern(regexp = "^\\+?\\d{10}$", message = "Numéro de téléphone invalide. Veuillez respecter le format \"+33 0 00 00 00\"")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "up_id")
    private UserProfile userProfile;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
