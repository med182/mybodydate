package fr.mybodydate.registelogin.api.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "temporary_user_id")
    private String temporaryUserId;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Column(name = "recovery_code")
    private String recoveryCode;

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

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToMany(mappedBy = "users")
    private Set<Match> matches = new HashSet<>();

    public Set<Match> getBlacklistedMatches() {
        return blacklistedMatches;
    }

    public void setBlacklistedMatches(Set<Match> blacklistedMatches) {
        this.blacklistedMatches = blacklistedMatches;
    }

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;

    @ManyToMany
    @JoinTable(name = "user_blocked_contacts", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "blocked_contact_id"))

    private Set<User> blockedContacts = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JoinTable(name = "user_blacklisted_matches", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "blacklisted_match_id"))
    private Set<Match> blacklistedMatches = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Set<User> getBlockedContacts() {
        return blockedContacts;
    }

    public void setBlockedContacts(Set<User> blockedContacts) {
        this.blockedContacts = blockedContacts;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public String getTemporaryUserId() {
        return temporaryUserId;
    }

    public void setTemporaryUserId(String temporaryUserId) {
        this.temporaryUserId = temporaryUserId;
    }
}
