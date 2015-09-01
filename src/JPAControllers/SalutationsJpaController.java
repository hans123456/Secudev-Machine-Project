/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPAControllers;

import JPAControllers.exceptions.IllegalOrphanException;
import JPAControllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPAEntities.Genders;
import JPAEntities.Salutations;
import JPAEntities.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hans
 */
public class SalutationsJpaController implements Serializable {

    public SalutationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salutations salutations) {
        if (salutations.getUsersCollection() == null) {
            salutations.setUsersCollection(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genders genderId = salutations.getGenderId();
            if (genderId != null) {
                genderId = em.getReference(genderId.getClass(), genderId.getId());
                salutations.setGenderId(genderId);
            }
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : salutations.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            salutations.setUsersCollection(attachedUsersCollection);
            em.persist(salutations);
            if (genderId != null) {
                genderId.getSalutationsCollection().add(salutations);
                genderId = em.merge(genderId);
            }
            for (Users usersCollectionUsers : salutations.getUsersCollection()) {
                Salutations oldSalutationIdOfUsersCollectionUsers = usersCollectionUsers.getSalutationId();
                usersCollectionUsers.setSalutationId(salutations);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldSalutationIdOfUsersCollectionUsers != null) {
                    oldSalutationIdOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldSalutationIdOfUsersCollectionUsers = em.merge(oldSalutationIdOfUsersCollectionUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salutations salutations) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salutations persistentSalutations = em.find(Salutations.class, salutations.getId());
            Genders genderIdOld = persistentSalutations.getGenderId();
            Genders genderIdNew = salutations.getGenderId();
            Collection<Users> usersCollectionOld = persistentSalutations.getUsersCollection();
            Collection<Users> usersCollectionNew = salutations.getUsersCollection();
            List<String> illegalOrphanMessages = null;
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its salutationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (genderIdNew != null) {
                genderIdNew = em.getReference(genderIdNew.getClass(), genderIdNew.getId());
                salutations.setGenderId(genderIdNew);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            salutations.setUsersCollection(usersCollectionNew);
            salutations = em.merge(salutations);
            if (genderIdOld != null && !genderIdOld.equals(genderIdNew)) {
                genderIdOld.getSalutationsCollection().remove(salutations);
                genderIdOld = em.merge(genderIdOld);
            }
            if (genderIdNew != null && !genderIdNew.equals(genderIdOld)) {
                genderIdNew.getSalutationsCollection().add(salutations);
                genderIdNew = em.merge(genderIdNew);
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Salutations oldSalutationIdOfUsersCollectionNewUsers = usersCollectionNewUsers.getSalutationId();
                    usersCollectionNewUsers.setSalutationId(salutations);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldSalutationIdOfUsersCollectionNewUsers != null && !oldSalutationIdOfUsersCollectionNewUsers.equals(salutations)) {
                        oldSalutationIdOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldSalutationIdOfUsersCollectionNewUsers = em.merge(oldSalutationIdOfUsersCollectionNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = salutations.getId();
                if (findSalutations(id) == null) {
                    throw new NonexistentEntityException("The salutations with id " + id + " no longer exists.");
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
            Salutations salutations;
            try {
                salutations = em.getReference(Salutations.class, id);
                salutations.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salutations with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Users> usersCollectionOrphanCheck = salutations.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salutations (" + salutations + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable salutationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Genders genderId = salutations.getGenderId();
            if (genderId != null) {
                genderId.getSalutationsCollection().remove(salutations);
                genderId = em.merge(genderId);
            }
            em.remove(salutations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salutations> findSalutationsEntities() {
        return findSalutationsEntities(true, -1, -1);
    }

    public List<Salutations> findSalutationsEntities(int maxResults, int firstResult) {
        return findSalutationsEntities(false, maxResults, firstResult);
    }

    private List<Salutations> findSalutationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salutations.class));
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

    public Salutations findSalutations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salutations.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalutationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salutations> rt = cq.from(Salutations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
