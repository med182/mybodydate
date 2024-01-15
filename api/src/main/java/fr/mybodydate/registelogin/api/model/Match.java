package fr.mybodydate.registelogin.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`match`")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "user_match", joinColumns = @JoinColumn(name = "match_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "blacklistedMatches")
    private Set<User> blacklistedUsers = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getBlacklistedUsers() {
        return blacklistedUsers;
    }

    public void setBlacklistedUsers(Set<User> blacklistedUsers) {
        this.blacklistedUsers = blacklistedUsers;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
