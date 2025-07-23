package com.example.restaurantmanagement.api;

import io.mironaut.Controller;
import io.mironaut.http.Get;
import io.mironaut.http.Post;
import io.mironaut.http.Put;
import io.mironaut.http.Delete;
import io.mironaut.http.Path;
import io.mironaut.http.RequestBody;

import com.example.restaurantmanagement.model.MenuItem;
import com.example.restaurantmanagement.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Controller
@Path("/menuitems")
public class MenuItemController {

    @Get
    public List<MenuItem> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<MenuItem> query = em.createQuery("SELECT m FROM MenuItem m", MenuItem.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Post
    public MenuItem create(@RequestBody MenuItem menuItem) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(menuItem);
            tx.commit();
            return menuItem;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Put
    @Path("/{id}")
    public MenuItem update(long id, @RequestBody MenuItem menuItem) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MenuItem existing = em.find(MenuItem.class, id);
            if (existing == null) {
                tx.rollback();
                return null;
            }
            existing.setName(menuItem.getName());
            existing.setDescription(menuItem.getDescription());
            existing.setPrice(menuItem.getPrice());
            em.merge(existing);
            tx.commit();
            return existing;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Delete
    @Path("/{id}")
    public boolean delete(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MenuItem existing = em.find(MenuItem.class, id);
            if (existing == null) {
                tx.rollback();
                return false;
            }
            em.remove(existing);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
