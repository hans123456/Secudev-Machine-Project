/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPAEntities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hans
 */
@Entity
@Table(name = "salutations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salutations.findAll", query = "SELECT s FROM Salutations s"),
    @NamedQuery(name = "Salutations.findById", query = "SELECT s FROM Salutations s WHERE s.id = :id"),
    @NamedQuery(name = "Salutations.findBySalutation", query = "SELECT s FROM Salutations s WHERE s.salutation = :salutation")})
public class Salutations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "salutation")
    private String salutation;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Genders genderId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salutationId")
    private Collection<Users> usersCollection;

    public Salutations() {
    }

    public Salutations(Integer id) {
        this.id = id;
    }

    public Salutations(Integer id, String salutation) {
        this.id = id;
        this.salutation = salutation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public Genders getGenderId() {
        return genderId;
    }

    public void setGenderId(Genders genderId) {
        this.genderId = genderId;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salutations)) {
            return false;
        }
        Salutations other = (Salutations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPAEntities.Salutations[ id=" + id + " ]";
    }
    
}
