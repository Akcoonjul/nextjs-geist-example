package com.example.restaurantmanagement.api;

import io.mironaut.Controller;
import io.mironaut.http.Get;
import io.mironaut.http.Post;
import io.mironaut.http.Put;
import io.mironaut.http.Delete;
import io.mironaut.http.Path;
import io.mironaut.http.RequestBody;

import com.example.restaurantmanagement.model.Table;
import com.example.restaurantmanagement.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Controller
@Path("/tables")
public class TableController {

    @Get
    public List<Table> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Table> query = em.createQuery("SELECT t FROM Table t", Table.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Post
    public Table create(@RequestBody Table table) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(table);
            tx.commit();
            return table;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Put
    @Path("/{id}")
    public Table update(long id, @RequestBody Table table) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Table existing = em.find(Table.class, id);
            if (existing == null) {
                tx.rollback();
                return null;
            }
            existing.setName(table.getName());
            existing.setCapacity(table.getCapacity());
            existing.setAvailable(table.isAvailable());
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
            Table existing = em.find(Table.class, id);
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
