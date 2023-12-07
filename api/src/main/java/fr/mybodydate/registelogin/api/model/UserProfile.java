package fr.mybodydate.registelogin.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String accessPosition;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = true)
    private String dateOfBirth;

    @Column(nullable = true)
    private String size;

    @Column(nullable = true)
    private String languages;

    @Column(nullable = true)
    private String maritalStatus;

    @Column(nullable = true)
    private String orientation;

    @Column(nullable = true)
    private String searchPreference1;

    @Column(nullable = true)
    private String searchPreference2;

    @Column(nullable = true)
    private String affinities;

    @Column(nullable = true)
    private String lifestyle1;

    @Column(nullable = true)
    private String lifestyle2;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String pseudo;

    private String imagePath;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userProfile")
    private User user;

    public UserProfile() {

    }

    public UserProfile(String city, String accessPosition, String gender, String dateOfBirth, String size,
            String languages, String maritalStatus, String orientation, String searchPreference1,
            String searchPreference2, String affinities, String lifestyle1, String lifestyle2, String firstName,
            String pseudo, String imagePath, User user) {
        this.city = city;
        this.accessPosition = accessPosition;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.size = size;
        this.languages = languages;
        this.maritalStatus = maritalStatus;
        this.orientation = orientation;
        this.searchPreference1 = searchPreference1;
        this.searchPreference2 = searchPreference2;
        this.affinities = affinities;
        this.lifestyle1 = lifestyle1;
        this.lifestyle2 = lifestyle2;
        this.firstName = firstName;
        this.pseudo = pseudo;
        this.imagePath = imagePath;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccessPosition() {
        return accessPosition;
    }

    public void setAccessPosition(String accessPosition) {
        this.accessPosition = accessPosition;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getSearchPreference1() {
        return searchPreference1;
    }

    public void setSearchPreference1(String searchPreference1) {
        this.searchPreference1 = searchPreference1;
    }

    public String getSearchPreference2() {
        return searchPreference2;
    }

    public void setSearchPreference2(String searchPreference2) {
        this.searchPreference2 = searchPreference2;
    }

    public String getAffinities() {
        return affinities;
    }

    public void setAffinities(String affinities) {
        this.affinities = affinities;
    }

    public String getLifestyle1() {
        return lifestyle1;
    }

    public void setLifestyle1(String lifestyle1) {
        this.lifestyle1 = lifestyle1;
    }

    public String getLifestyle2() {
        return lifestyle2;
    }

    public void setLifestyle2(String lifestyle2) {
        this.lifestyle2 = lifestyle2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
