package fr.mybodydate.registelogin.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_like")
public class Like {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", nullable = false)
    private User receiverUser;

    public Like() {
    }

    public Long getId() {
        return id;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }

}
