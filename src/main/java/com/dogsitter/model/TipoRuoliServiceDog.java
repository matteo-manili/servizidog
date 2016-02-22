package com.dogsitter.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Entity
@Table(name = "tipo_ruoli")
public class TipoRuoliServiceDog extends BaseObject implements Serializable {
    private static final long serialVersionUID = 2086472938752091333L;
    
	private Long id;
    private String name;
    private String description;
    
    
    /**
     * Default constructor - creates a new instance with no values set.
     */
    public TipoRuoliServiceDog() {
    }
    
	public TipoRuoliServiceDog(final String name) {
        this.name = name;
	}
	

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(length = 20)
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	

	@Column(length = 64)
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}





    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoRuoliServiceDog)) {
            return false;
        }

        final TipoRuoliServiceDog tipoRuoloServiziDog = (TipoRuoliServiceDog) o;

        return !(name != null ? !name.equals(tipoRuoloServiziDog.name) : tipoRuoloServiziDog.name != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString();
    }

}
