package com.example.restaurantmanagement.api;

import io.mironaut.Controller;
import io.mironaut.http.Get;
import io.mironaut.http.Post;
import io.mironaut.http.Put;
import io.mironaut.http.Delete;
import io.mironaut.http.Path;
import io.mironaut.http.RequestBody;

import com.example.restaurantmanagement.model.Order;
import com.example.restaurantmanagement.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Controller
@Path("/orders")
public class OrderController {

    @Get
    public List<Order> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Post
    public Order create(@RequestBody Order order) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            tx.commit();
            return order;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Put
    @Path("/{id}")
    public Order update(long id, @RequestBody Order order) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order existing = em.find(Order.class, id);
            if (existing == null) {
                tx.rollback();
                return null;
            }
            existing.setTableId(order.getTableId());
            existing.setMenuItemIds(order.getMenuItemIds());
            existing.setStatus(order.getStatus());
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
            Order existing = em.find(Order.class, id);
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
