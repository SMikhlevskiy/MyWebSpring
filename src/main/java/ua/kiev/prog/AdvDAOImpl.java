package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AdvDAOImpl implements AdvDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Advertisement> list() {
        Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.deleted=0", Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public List<Advertisement> listDeleted() {
        Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.deleted=1", Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public List<Advertisement> list(String pattern) {
        Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.shortDesc LIKE :pattern", Advertisement.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Advertisement>) query.getResultList();
    }



    @Override
    public void add(Advertisement adv) {
        try {
            adv.setDeleted(0);;
            entityManager.getTransaction().begin();
            entityManager.persist(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
    @Override
    public void undelete(long id) {


            try {
                entityManager.getTransaction().begin();
                Query query = entityManager
                        .createQuery("UPDATE Advertisement a SET a.deleted=:del WHERE a.id=:id");
                query.setParameter("del", 0);
                query.setParameter("id", id);
                query.executeUpdate();
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                entityManager.getTransaction().rollback();
                ex.printStackTrace();
            }
        }

            @Override
    public void delete(long id,boolean prBascket) {
        if (prBascket)
        {
            try {
                entityManager.getTransaction().begin();
                Query query = entityManager
                        .createQuery("UPDATE Advertisement a SET a.deleted=:del WHERE a.id=:id");
                query.setParameter("del", 1);
                query.setParameter("id", id);
                query.executeUpdate();
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                entityManager.getTransaction().rollback();
                ex.printStackTrace();
            }
        } else {
            try {

                entityManager.getTransaction().begin();
                Advertisement adv = entityManager.find(Advertisement.class, id);
                entityManager.remove(adv);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                entityManager.getTransaction().rollback();
                ex.printStackTrace();
            }
        }
    }

    @Override
    public byte[] getPhoto(long id) {
        try {
            Photo photo = entityManager.find(Photo.class, id);
            return photo.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
