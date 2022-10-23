package com.example.RestFulApi.repository;

import com.example.RestFulApi.domain.Host;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class JpaHostRepository {

    @PersistenceContext
    private final EntityManager em;

    public void create(Host host) {
        if(host.getId() == null) {
            em.persist(host);
        } else {
            em.merge(host);
        }
    }

    public void update(Long hostId, String name, String ip) {
        Host findHost = em.find(Host.class, hostId);
        findHost.setName(name);
        findHost.setIp(ip);
    }


    public List<Host> findByIp(String ip) {
        return em.createQuery("select h from Host h where h.ip = :ip", Host.class)
                .setParameter("ip", ip)
                .getResultList();
    }

    public Host findOne(Long id) {
        return em.find(Host.class, id);
    }

    public List<Host> findByName(String name) {
        return em.createQuery("select h from Host h where h.name = :name", Host.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Long countHost() {
        Query query = em.createQuery("SELECT count(h) FROM Host h");

        long count = (long) query.getSingleResult();

        return count;
    }

    public List<Host> findAll() {
        String jpql = "select h from Host h";

        List<Host> result = em.createQuery(jpql, Host.class)
                .getResultList();

        return result;
    }
}
