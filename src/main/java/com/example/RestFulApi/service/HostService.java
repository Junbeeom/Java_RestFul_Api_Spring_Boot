package com.example.RestFulApi.service;

import com.example.RestFulApi.domain.Host;
import com.example.RestFulApi.repository.JpaHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostService {

    private final JpaHostRepository jpaHostRepository;

    @Transactional
    public Long create(Host createParams) {

        validateDuplicateName(createParams);
        validateDuplicateIp(createParams);
        countHost();

        jpaHostRepository.create(createParams);

        return  createParams.getId();
    }

    private void validateDuplicateIp(Host createHost) {
        List<Host> findIps = jpaHostRepository.findByIp(createHost.getIp());

        if(!findIps.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 ip입니다");
        }
    }

    private void validateDuplicateName(Host createHost) {
        List<Host> findIps = jpaHostRepository.findByName(createHost.getName());

        if(!findIps.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 name입니다");
        }
    }

    private void countHost() {
        Long count = jpaHostRepository.countHost();

        if(count > 100) {
            throw new IllegalStateException("host 최대갯수를 초과했습니다.");
        }
    }

    public Host findOne(Long hostId) {
        return jpaHostRepository.findOne(hostId);
    }

    public List<Host> findAll() {
        return jpaHostRepository.findAll();
    }

    //수정
    @Transactional
    public void update(Long hostId, String name, String ip) {
        Host host = jpaHostRepository.findOne(hostId);
        host.setName(name);
        host.setIp(ip);
    }
}
