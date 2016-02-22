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
@Table(name = "gestione_applicazione")
public class GestioneApplicazione extends BaseObject implements Serializable {
    private static final long serialVersionUID = 2086472938752091333L;
    
	private Long id;
    private String name;
    private int paginaInizio;
    private int paginaFine;
    private boolean attivo;
    private int numeroMesiAnnuncioAttivo;
    
    /**
     * Default constructor - creates a new instance with no values set.
     */
    public GestioneApplicazione() {
    }
    
	public GestioneApplicazione(final String name) {
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

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="pagina_inizio")
	public int getPaginaInizio() {
		return paginaInizio;
	}

	public void setPaginaInizio(int paginaInizio) {
		this.paginaInizio = paginaInizio;
	}
	
	@Column(name="pagina_fine")
	public int getPaginaFine() {
		return paginaFine;
	}

	public void setPaginaFine(int paginaFine) {
		this.paginaFine = paginaFine;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	@Column(name="numero_mesi_annuncio_attivo")
	public int getNumeroMesiAnnuncioAttivo() {
		return numeroMesiAnnuncioAttivo;
	}

	public void setNumeroMesiAnnuncioAttivo(int numeroMesiAnnuncioAttivo) {
		this.numeroMesiAnnuncioAttivo = numeroMesiAnnuncioAttivo;
	}

	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestioneApplicazione)) {
            return false;
        }

        final GestioneApplicazione tipoRuoloServiziDog = (GestioneApplicazione) o;

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
