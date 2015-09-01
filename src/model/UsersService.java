package model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import JPAEntities.Users;

public class UsersService {

	@PersistenceContext
	private EntityManager em;

	public Users find(int id) {
		return em.find(Users.class, id);
	}

}
