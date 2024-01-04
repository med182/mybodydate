package fr.mybodydate.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "consent")
public class Consent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String userConsent;

	private String consentEngagement;

	public Consent() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserConsent() {
		return userConsent;
	}

	public void setUserConsent(String userConsent) {
		this.userConsent = userConsent;
	}

	public String getConsentEngagement() {
		return consentEngagement;
	}

	public void setConsentEngagement(String consentEngagement) {
		this.consentEngagement = consentEngagement;
	}

}
