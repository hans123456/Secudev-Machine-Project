/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPAControllers;

import JPAControllers.exceptions.IllegalOrphanException;
import JPAControllers.exceptions.NonexistentEntityException;
import JPAEntities.Genders;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPAEntities.Salutations;
import java.util.ArrayList;
import java.util.Collection;
import JPAEntities.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hans
 */
public class GendersJpaController implements Serializable {

    public GendersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genders genders) {
        if (genders.getSalutationsCollection() == null) {
            genders.setSalutationsCollection(new ArrayList<Salutations>());
        }
        if (genders.getUsersCollection() == null) {
            genders.setUsersCollection(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Salutations> attachedSalutationsCollection = new ArrayList<Salutations>();
            for (Salutations salutationsCollectionSalutationsToAttach : genders.getSalutationsCollection()) {
                salutationsCollectionSalutationsToAttach = em.getReference(salutationsCollectionSalutationsToAttach.getClass(), salutationsCollectionSalutationsToAttach.getId());
                attachedSalutationsCollection.add(salutationsCollectionSalutationsToAttach);
            }
            genders.setSalutationsCollection(attachedSalutationsCollection);
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : genders.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            genders.setUsersCollection(attachedUsersCollection);
            em.persist(genders);
            for (Salutations salutationsCollectionSalutations : genders.getSalutationsCollection()) {
                Genders oldGenderIdOfSalutationsCollectionSalutations = salutationsCollectionSalutations.getGenderId();
                salutationsCollectionSalutations.setGenderId(genders);
                salutationsCollectionSalutations = em.merge(salutationsCollectionSalutations);
                if (oldGenderIdOfSalutationsCollectionSalutations != null) {
                    oldGenderIdOfSalutationsCollectionSalutations.getSalutationsCollection().remove(salutationsCollectionSalutations);
                    oldGenderIdOfSalutationsCollectionSalutations = em.merge(oldGenderIdOfSalutationsCollectionSalutations);
                }
            }
            for (Users usersCollectionUsers : genders.getUsersCollection()) {
                Genders oldGenderIdOfUsersCollectionUsers = usersCollectionUsers.getGenderId();
                usersCollectionUsers.setGenderId(genders);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldGenderIdOfUsersCollectionUsers != null) {
                    oldGenderIdOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldGenderIdOfUsersCollectionUsers = em.merge(oldGenderIdOfUsersCollectionUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genders genders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genders persistentGenders = em.find(Genders.class, genders.getId());
            Collection<Salutations> salutationsCollectionOld = persistentGenders.getSalutationsCollection();
            Collection<Salutations> salutationsCollectionNew = genders.getSalutationsCollection();
            Collection<Users> usersCollectionOld = persistentGenders.getUsersCollection();
            Collection<Users> usersCollectionNew = genders.getUsersCollection();
            List<String> illegalOrphanMessages = null;
            for (Salutations salutationsCollectionOldSalutations : salutationsCollectionOld) {
                if (!salutationsCollectionNew.contains(salutationsCollectionOldSalutations)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salutations " + salutationsCollectionOldSalutations + " since its genderId field is not nullable.");
                }
            }
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its genderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Salutations> attachedSalutationsCollectionNew = new ArrayList<Salutations>();
            for (Salutations salutationsCollectionNewSalutationsToAttach : salutationsCollectionNew) {
                salutationsCollectionNewSalutationsToAttach = em.getReference(salutationsCollectionNewSalutationsToAttach.getClass(), salutationsCollectionNewSalutationsToAttach.getId());
                attachedSalutationsCollectionNew.add(salutationsCollectionNewSalutationsToAttach);
            }
            salutationsCollectionNew = attachedSalutationsCollectionNew;
            genders.setSalutationsCollection(salutationsCollectionNew);
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            genders.setUsersCollection(usersCollectionNew);
            genders = em.merge(genders);
            for (Salutations salutationsCollectionNewSalutations : salutationsCollectionNew) {
                if (!salutationsCollectionOld.contains(salutationsCollectionNewSalutations)) {
                    Genders oldGenderIdOfSalutationsCollectionNewSalutations = salutationsCollectionNewSalutations.getGenderId();
                    salutationsCollectionNewSalutations.setGenderId(genders);
                    salutationsCollectionNewSalutations = em.merge(salutationsCollectionNewSalutations);
                    if (oldGenderIdOfSalutationsCollectionNewSalutations != null && !oldGenderIdOfSalutationsCollectionNewSalutations.equals(genders)) {
                        oldGenderIdOfSalutationsCollectionNewSalutations.getSalutationsCollection().remove(salutationsCollectionNewSalutations);
                        oldGenderIdOfSalutationsCollectionNewSalutations = em.merge(oldGenderIdOfSalutationsCollectionNewSalutations);
                    }
                }
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Genders oldGenderIdOfUsersCollectionNewUsers = usersCollectionNewUsers.getGenderId();
                    usersCollectionNewUsers.setGenderId(genders);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldGenderIdOfUsersCollectionNewUsers != null && !oldGenderIdOfUsersCollectionNewUsers.equals(genders)) {
                        oldGenderIdOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldGenderIdOfUsersCollectionNewUsers = em.merge(oldGenderIdOfUsersCollectionNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genders.getId();
                if (findGenders(id) == null) {
                    throw new NonexistentEntityException("The genders with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genders genders;
            try {
                genders = em.getReference(Genders.class, id);
                genders.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Salutations> salutationsCollectionOrphanCheck = genders.getSalutationsCollection();
            for (Salutations salutationsCollectionOrphanCheckSalutations : salutationsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genders (" + genders + ") cannot be destroyed since the Salutations " + salutationsCollectionOrphanCheckSalutations + " in its salutationsCollection field has a non-nullable genderId field.");
            }
            Collection<Users> usersCollectionOrphanCheck = genders.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genders (" + genders + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable genderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genders> findGendersEntities() {
        return findGendersEntities(true, -1, -1);
    }

    public List<Genders> findGendersEntities(int maxResults, int firstResult) {
        return findGendersEntities(false, maxResults, firstResult);
    }

    private List<Genders> findGendersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genders.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Genders findGenders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genders.class, id);
        } finally {
            em.close();
        }
    }

    public int getGendersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genders> rt = cq.from(Genders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
