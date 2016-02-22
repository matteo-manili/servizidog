package com.dogsitter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dogsitter.form.RuoliServizi;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
/**
 * This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Spring Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Spring UserDetails interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "app_user")
@Indexed
@XmlRootElement
public class User extends BaseObject implements Serializable, UserDetails {
    private static final long serialVersionUID = 3832626162173359411L;

    private Long id;
    private String username;                    // required
    private String password;                    // required
    private String confirmPassword;
    private String passwordHint;
    private String firstName;                   // required
    private String lastName;                    // required
    private String email;                       // required; unique
    private String website;
    //private Address address = new Address();
    private Integer version;
    private boolean enabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    
    private Date signupDate;
    private String emailConfirmToken;
    private Date confirmDate;
    
    private String FacebookId;
    

	private Set<Role> roles = new HashSet<Role>();
    private Set<TipoRuoliServiceDog> tipoRuoli = new HashSet<TipoRuoliServiceDog>();
    
    //private String phoneNumber;
    
    
    
 // -------------------RUOLI APP FUSE --------------------------------
    /*	FetchType
     * 
     * 	Le API Java Persistence mettono a disposizione solo due strategie di mapping: lazy o eager.
		Il lazy fetching è l'impostazione di default mentre l'eager fetching è equivalente all'immediate fetching di
		Hibernate. La strategia di fetching viene definita con la proprietà fetch per l'annotazione che definisce il
		mapping dell'associazione impostandone il valore a FetchType.LAZY o FetchType.EAGER.
		
		- Lazy collection fetching: una collezione viene caricata in memoria quando l'applicazione invoca
		un'operazione su quella collezione. Questo è il comportamento di default, il valore dell'attributo
		lazy per l'elemento che definisce il mapping dell'associazione è "true".
		
		- Immediate fetching: un'associazione o collezione viene caricata immediatamente quando viene
		caricato l'oggetto associato. Questa strategia di mapping viene impostata specificando
		lazy="false" nell'elemento che definisce il mapping dell'associazione.
     */
    
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    

    public void addRole(Role role) {
        getRoles().add(role);
    }

    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getName(), role.getName()));
            }
        }
        return userRoles;
    }
    
    
    // -------------------RUOLI SERVIZI DOG--------------------------------
    
	@ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "user_tipo_ruoli",
	    joinColumns = @JoinColumn (name = "user_id")    ,
	    inverseJoinColumns = @JoinColumn(name = "tipo_ruoli_id")
    )
    public Set<TipoRuoliServiceDog> getTipoRuoli() {
		return tipoRuoli;
	}
    
	public void setTipoRuoli(Set<TipoRuoliServiceDog> tipoRuoli) {
		this.tipoRuoli = tipoRuoli;
	}
	
    public void addtipoRuoli(TipoRuoliServiceDog tipoRuoli) {
    	getTipoRuoli().add(tipoRuoli);
    }
    
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public List<LabelValue> getTipoRuoliServiceDogList() {
        List<LabelValue> userRuoliServiziDogList = new ArrayList<LabelValue>();
        if (this.tipoRuoli != null) {
            for (TipoRuoliServiceDog ruoliServiziDog : tipoRuoli) {
                // convert the user's roles to LabelValue Objects
            	userRuoliServiziDogList.add(new LabelValue(ruoliServiziDog.getDescription(), ruoliServiziDog.getName()));
            }
        }
        return userRuoliServiziDogList;
    }
    
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public List<LabelValue> getTipoRuoliServiceDogListPerMenu() {
        List<LabelValue> userRuoliServiziDogList = new ArrayList<LabelValue>();
        if (this.tipoRuoli != null) {
            for (TipoRuoliServiceDog ruoliServiziDog : tipoRuoli) {
                // convert the user's roles to LabelValue Objects
            	String pulisci = ruoliServiziDog.getName().replaceAll("ROLE_","").toLowerCase();
            	userRuoliServiziDogList.add(new LabelValue(pulisci, pulisci));
            }
        }
        return userRuoliServiziDogList;
    }


	
	//---------------------------------------------------------------------------------------------------------
    //----------PROPRIETA PER IL FORM INSERIMENTO RUOLI SERVIZIO (INDIFFERENTI PER IL DATABASE)-----------
	//--------------------------------------------------------------------------------------------------------
    
	
	




	private RuoliServizi ruoliServizi = new RuoliServizi();
    


	@Transient
    public RuoliServizi getRuoliServizi() {
		return ruoliServizi;
	}
    @Transient
	public void setRuoliServizi(RuoliServizi ruoliServizi) {
		this.ruoliServizi = ruoliServizi;
	}
	
    
    
    //---------------PRPRIETA' PER LA CONFERMA EMAIL UTENTE--------------------------------------
    

    
    @Column(name="signup_date" /*, nullable=false**/ )
	public Date getSignupDate() {
		return signupDate;
	}
	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}

	@Column(name="confirm_token", length=32)
	public String getEmailConfirmToken() {
		return emailConfirmToken;
	}

	public void setEmailConfirmToken(String emailConfirmToken) {
		this.emailConfirmToken = emailConfirmToken;
	}

	@Column(name="confirm_date")
	public Date getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {
    }

	/**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 50, unique = true)
    @Field
    public String getUsername() {
        return username;
    }

    @Column(nullable = false)
    @XmlTransient
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    @XmlTransient
    @JsonIgnore
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Column(name = "password_hint")
    @XmlTransient
    public String getPasswordHint() {
        return passwordHint;
    }

    @Column(name = "first_name", nullable = false, length = 50)
    @Field
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", length = 50)
    @Field
    public String getLastName() {
        return lastName;
    }

    @Column(nullable = false, unique = true)
    @Field
    public String getEmail() {
        return email;
    }


    @Field
    public String getWebsite() {
        return website;
    }
    
    @Column(name = "facebook_id", length = 50)
    @Field
    public String getFacebookId() {
		return FacebookId;
	}


    /**
     * Returns the full name.
     *
     * @return firstName + ' ' + lastName
     */
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    /*
    @Embedded
    @IndexedEmbedded
    public Address getAddress() {
        return address;
    }
*/

/*
    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
*/
	/**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    @JsonIgnore // needed for UserApiITest in appfuse-ws archetype
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.addAll(roles);
        return authorities;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    @Column(name = "account_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Column(name = "account_expired", nullable = false)
    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Column(name = "account_locked", nullable = false)
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Column(name = "credentials_expired", nullable = false)
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient //@Transient che consente di evitare che un attributo diventi persistente (non comparirà come campo della tabella)
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public void setFirstName(String firstName) {
    	
    	firstName = StringUtils.strip(firstName);
    	firstName = WordUtils.capitalizeFully(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
    	
    	lastName = StringUtils.strip(lastName);
    	lastName = WordUtils.capitalizeFully(lastName);
        this.lastName = lastName;
    }

    public void setEmail(String email) {
    	
    	email = StringUtils.lowerCase(email);
        this.email = email;
    }


    public void setWebsite(String website) {
    	
    	website = StringUtils.strip(website);
    	website = StringUtils.lowerCase(website);
        this.website = website;
    }
    
    
	public void setFacebookId(String facebookId) {
		FacebookId = facebookId;
	}

    /*
    public void setAddress(Address address) {
        this.address = address;
    }
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        final User user = (User) o;

        return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (username != null ? username.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append("username", this.username)
                .append("enabled", this.enabled)
                .append("accountExpired", this.accountExpired)
                .append("credentialsExpired", this.credentialsExpired)
                .append("accountLocked", this.accountLocked);

        if (roles != null) {
            sb.append("Granted Authorities: ");

            int i = 0;
            for (Role role : roles) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(role.toString());
                i++;
            }
        } else {
            sb.append("No Granted Authorities");
        }
        return sb.toString();
    }
}
