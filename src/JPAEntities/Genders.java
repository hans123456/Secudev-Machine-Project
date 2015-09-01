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
@Table(name = "genders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genders.findAll", query = "SELECT g FROM Genders g"),
    @NamedQuery(name = "Genders.findById", query = "SELECT g FROM Genders g WHERE g.id = :id"),
    @NamedQuery(name = "Genders.findByGender", query = "SELECT g FROM Genders g WHERE g.gender = :gender")})
public class Genders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "gender")
    private String gender;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genderId")
    private Collection<Salutations> salutationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genderId")
    private Collection<Users> usersCollection;

    public Genders() {
    }

    public Genders(Integer id) {
        this.id = id;
    }

    public Genders(Integer id, String gender) {
        this.id = id;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlTransient
    public Collection<Salutations> getSalutationsCollection() {
        return salutationsCollection;
    }

    public void setSalutationsCollection(Collection<Salutations> salutationsCollection) {
        this.salutationsCollection = salutationsCollection;
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
        if (!(object instanceof Genders)) {
            return false;
        }
        Genders other = (Genders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPAEntities.Genders[ id=" + id + " ]";
    }
    
}
