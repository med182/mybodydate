package fr.mybodydate.api.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "L'adresse e-mail ne peut pas être vide")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Veuillez fournir une adresse e-mail valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&?!])[A-Za-z\\d@#$%^&?!]{8,}$", message = "Le mot de passe doit contenir : au moins une lettre majuscule, au moins une lettre minuscule, au moins un chiffre, au moins un caractère spécial (@#$%^&?!), et au moins huit (8) caractères.")
    private String password;

    @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
    @Pattern(regexp = "^\\+?\\d{10}$", message = "Numéro de téléphone invalide. Veuillez respecter le format \"+33 0 00 00 00\"")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "id")
    private UserProfile userProfile;
    
    @OneToOne
    @JoinColumn(name = "userConsent")
    private Consent userConsent;

    @OneToOne
    @JoinColumn(name = "consentEngagement")
    private Consent consentEngagement;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription")
    private Subscription subscription;
    
    @OneToMany(mappedBy = "senderUser", cascade = CascadeType.ALL)
    private List<Like> senderUser;

    @OneToMany(mappedBy = "receiverUser", cascade = CascadeType.ALL)
    private List<Like> receiverUser;

    @ManyToMany
    @JoinTable(
      name = "match", 
      joinColumns = @JoinColumn(name = "user_id"), 
      inverseJoinColumns = @JoinColumn(name = "match_id"))
    private List<Match> matches = new ArrayList<>();

    public User(
            @NotBlank(message = "L'adresse e-mail ne peut pas être vide") @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Veuillez fournir une adresse e-mail valide") String email,
            @NotBlank(message = "Le mot de passe ne peut pas être vide") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&?!])[A-Za-z\\d@#$%^&?!]{8,}$", message = "Le mot de passe doit contenir : au moins une lettre majuscule, au moins une lettre minuscule, au moins un chiffre, au moins un caractère spécial (@#$%^&?!), et au moins huit (8) caractères.") String password,
            @NotBlank(message = "Le numéro de téléphone ne peut pas être vide") @Pattern(regexp = "^\\+?\\d{10}$", message = "Numéro de téléphone invalide. Veuillez respecter le format \"+33 0 00 00 00\"") String phoneNumber,
            UserProfile userProfile, Subscription subsciption) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userProfile = userProfile;

    }

    public User() {
		super();
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Like> getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(List<Like> senderUser) {
		this.senderUser = senderUser;
	}

	public List<Like> getReceiverUser() {
		return receiverUser;
	}

	public void setReceiverUser(List<Like> receiverUser) {
		this.receiverUser = receiverUser;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

	public Consent getUserConsent() {
		return userConsent;
	}

	public void setUserConsent(Consent userConsent) {
		this.userConsent = userConsent;
	}

	public Consent getConsentEngagement() {
		return consentEngagement;
	}

	public void setConsentEngagement(Consent consentEngagement) {
		this.consentEngagement = consentEngagement;
	}

}
