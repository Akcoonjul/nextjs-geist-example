package com.example.restaurantmanagement.api;

import io.mironaut.Controller;
import io.mironaut.http.Get;
import io.mironaut.http.Post;
import io.mironaut.http.Put;
import io.mironaut.http.Delete;
import io.mironaut.http.Path;
import io.mironaut.http.RequestBody;

import com.example.restaurantmanagement.model.Staff;
import com.example.restaurantmanagement.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Controller
@Path("/staff")
public class StaffController {

    @Get
    public List<Staff> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s", Staff.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Post
    public Staff create(@RequestBody Staff staff) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(staff);
            tx.commit();
            return staff;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Put
    @Path("/{id}")
    public Staff update(long id, @RequestBody Staff staff) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Staff existing = em.find(Staff.class, id);
            if (existing == null) {
                tx.rollback();
                return null;
            }
            existing.setName(staff.getName());
            existing.setRole(staff.getRole());
            existing.setContact(staff.getContact());
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
            Staff existing = em.find(Staff.class, id);
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
