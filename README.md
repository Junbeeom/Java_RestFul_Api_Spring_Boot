# # Java_RestFul_Api_Spring_Boot

# 1.프로젝트 개요
### 1.1 프로젝트 목적
- 호스트들의 Alive 상태 체크 및 모니터링 API 서버 개발


# 2. 개발 환경
- IntelliJ IDEA(Ultimate Edition), GitHub


# 3. 사용기술
- Java 11, Spring Boot, MariaDB, JPA

# 4.프로젝트 설계

### 4.1 HostApiController
```java

```


# 5.기본 기능
- 등록 
- 조회 
- 수정




# 6.핵심 기능

### 6.1 등록 api

CreatedHostrequest, CreatedHostresponse의 객체 생성하여 dto로 사용 하였으며 등록 api 호출시 service에 작된 validateDuplicateIp, validateDuplicateName, countHost method를 호출하고
유효성을 체크합니다. try catch을 활용하여 ResponseEntity 객체의 상태 코드와 data를 return 할 수 있도록 구현 했습니다. 

1)
<img width="711" alt="스크린샷 2022-10-24 오전 12 45 17" src="https://user-images.githubusercontent.com/103010985/197401817-64c90fb6-4d2e-499b-a7e7-37825584b63f.png">

2)
<img width="726" alt="스크린샷 2022-10-24 오전 12 44 27" src="https://user-images.githubusercontent.com/103010985/197401780-9bd707b6-5868-47e7-af43-e13d840f6f06.png">

3)
<img width="732" alt="스크린샷 2022-10-24 오전 12 43 55" src="https://user-images.githubusercontent.com/103010985/197401759-2cfd55b3-927f-48af-9d28-dbad8dbf8423.png">

```java

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

```



### 6.2 조회 api

 Entity를 DTO로 변환해서 사용하였고 추가로 Result 클래스로 컬렉션을 감싸사 향후 필요한 필드를 추가 할 수 있도록 구현했습니다.

```java
//조회 api
    @GetMapping("/api/hosts")
    public Result host() {
        List<Host> findHost = hostService.findAll();
        List<HostDto> collect = findHost.stream()
                .map(m -> new HostDto(m.getName(), m.getIp(), m.getRegisteredTs(), m.getUpdatedTs()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class HostDto {
        private String name;
        private String ip;
        private Timestamp registeredTs;
        private Timestamp updatedTs;
    }
```

1)
```java
{
    "data": [
        {
            "name": "조준범",
            "ip": "133",
            "registeredTs": "2022-10-23T08:13:56.013+00:00",
            "updatedTs": "2022-10-23T08:14:07.494+00:00"
        },
        {
            "name": "민해주",
            "ip": "1",
            "registeredTs": "2022-10-23T08:35:26.929+00:00",
            "updatedTs": "2022-10-23T08:35:26.929+00:00"
        },
        {
            "name": "test",
            "ip": "3",
            "registeredTs": "2022-10-23T10:13:34.991+00:00",
            "updatedTs": "2022-10-23T10:13:34.991+00:00"
        },
        {
            "name": null,
            "ip": null,
            "registeredTs": "2022-10-23T10:22:40.523+00:00",
            "updatedTs": "2022-10-23T10:22:40.523+00:00"
        },
        {
            "name": "test1",
            "ip": "test1",
            "registeredTs": "2022-10-23T11:27:33.918+00:00",
            "updatedTs": "2022-10-23T11:27:33.919+00:00"
        },
        {
            "name": "test21",
            "ip": "35",
            "registeredTs": "2022-10-23T13:48:36.179+00:00",
            "updatedTs": "2022-10-23T13:48:36.179+00:00"
        },
        {
            "name": "test22122",
            "ip": "1000",
            "registeredTs": "2022-10-23T14:09:54.349+00:00",
            "updatedTs": "2022-10-23T14:09:54.349+00:00"
        },
        {
            "name": "test22112122",
            "ip": "10010",
            "registeredTs": "2022-10-23T14:09:59.740+00:00",
            "updatedTs": "2022-10-23T14:09:59.740+00:00"
        }
    ]
}
```

### 6.3 수정 api
UpdateHostRequest, UpdateHostRespose 객체를 생성하여 DTO로 사용하였고 sevice 로직의 transaction이 일어날때 jpa의 영속성 컨텍스트 개념을 활용하여 host을 변경사항을 set 할 수 있도록 구현 하였습니다. 서비스에서 member를 반환하게되면 영속 상태에서 끊긴 상태에서 반환이 되고, 커멘드랑 쿼리를 철저하게 분리하기 위해 변경감지를 통한 commit이 되는 시점 이후 별도의 findOne 메소드를 호출하여 객체를 return 받을 수 있도록 구현 했습니다. return 받은 객체와 ResponseEntity 객체의 상태코드를 활용하여 상태코드와 data를 return 하였습니다. 

1)
<img width="742" alt="스크린샷 2022-10-24 오전 1 14 24" src="https://user-images.githubusercontent.com/103010985/197403311-c944c2a0-ffa3-4b0c-a9d7-e680892edf46.png">

```java
//수정 api
    @PutMapping("/api/hosts/{id}")
    public ResponseEntity updateHost(@PathVariable("id") Long id, @RequestBody @Valid UpdateHostRequest request) {
        hostService.update(id, request.getName(), request.getIp());
        Host findHost = hostService.findOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(new UpdateHostResponse(findHost.getId(), findHost.getName(), findHost.getIp(), findHost.getRegisteredTs(), findHost.getUpdatedTs()));
    }

    @Data
    static class UpdateHostRequest {
        private String name;
        private String ip;

    }
    @Data
    @AllArgsConstructor
    static class UpdateHostResponse {
        private Long id;
        private String name;
        private String ip;
        private Timestamp createdTs;
        private Timestamp updatedTs;

    }
```

