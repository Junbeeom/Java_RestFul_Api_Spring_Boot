//package com.example.RestFulApi.service;
//
//import com.example.RestFulApi.domain.CreateHost;
//import com.example.RestFulApi.repository.JpaHostRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Fail.fail;
//import static org.junit.Assert.assertEquals;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//class HostServiceTest {
//
//    @Autowired
//    HostService hostService;
//    @Autowired
//    JpaHostRepository jpaHostRepository;
//
//    @Test
//    void 호스트등록() throws Exception {
//        //given
//        CreateHost createHost =  new CreateHost();
//
//        createHost.setIp("test");
//
//        //when
//        Long savedId = hostService.create(createHost);
//
//        //then
//        //Assertions.assertThat(createHost.getId()).isEqualTo(jpaHostRepository.findOne(savedId));
//        assertEquals(createHost.getId(), jpaHostRepository.findOne(savedId).getId());
//    }
//
//    @Test
//    void 중복_ip_체크() throws Exception {
//        CreateHost ip1 = new CreateHost();
//        ip1.setIp("test1");
//
//        CreateHost ip2 = new CreateHost();
//        ip2.setIp("test1");
//
//        jpaHostRepository.create(ip1);
//        try {
//            hostService.create(ip2);
//        } catch(IllegalStateException e) {
//            //then
//            fail("예외가 발생해야 한다.");
//            return;
//        }
//
//
//
//
//    }
//
//    @Test
//    void update() {
//    }
//}