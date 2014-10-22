package com.seven.mavenbiblioteca.dao;

import com.seven.mavenbiblioteca.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.seven.mavenbiblioteca.modelo.Emprestimo;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.util.Date;
import javax.persistence.TypedQuery;

public class EmprestimoJpaController implements Serializable {

    public EmprestimoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Emprestimo emprestimo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(emprestimo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Emprestimo> emprestimosAbertosUsuario(Usuario u){
                
        EntityManager em = getEntityManager();
        try{
            String pesq = " select e.* from EMPRESTIMO e, USUARIO u where e.USUARIO_ID = u.ID and u.CPF = '" +u.getCpf() + "' and e.DATA_DEVOLUCAO = '1969-12-31'";
            TypedQuery<Emprestimo> query = (TypedQuery<Emprestimo>) em.createNativeQuery(pesq, Emprestimo.class);
            
            return query.getResultList();
            
        }catch (Exception e){
            return null;
        }
    }
    
    public List<Emprestimo> emprestimosFechadosUsuario(Usuario u, Date hoje){
                
        EntityManager em = getEntityManager();
        try{
            String pesq = "select e.* from EMPRESTIMO e, USUARIO u where e.USUARIO_ID = u.ID and u.CPF = '" +u.getCpf() + "' and e.DATA_DEVOLUCAO = '"+hoje+"'";
            TypedQuery<Emprestimo> query = (TypedQuery<Emprestimo>) em.createNativeQuery(pesq, Emprestimo.class);
            
            return query.getResultList();
            
        }catch (Exception e){
            return null;
        }
    }

    public void edit(Emprestimo emprestimo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            emprestimo = em.merge(emprestimo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = emprestimo.getId();
                if (findEmprestimo(id) == null) {
                    throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Emprestimo emprestimo;
            try {
                emprestimo = em.getReference(Emprestimo.class, id);
                emprestimo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emprestimo with id " + id + " no longer exists.", enfe);
            }
            em.remove(emprestimo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Emprestimo> findEmprestimoEntities() {
        return findEmprestimoEntities(true, -1, -1);
    }

    public List<Emprestimo> findEmprestimoEntities(int maxResults, int firstResult) {
        return findEmprestimoEntities(false, maxResults, firstResult);
    }

    private List<Emprestimo> findEmprestimoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Emprestimo.class));
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

    public Emprestimo findEmprestimo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Emprestimo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmprestimoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Emprestimo> rt = cq.from(Emprestimo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
