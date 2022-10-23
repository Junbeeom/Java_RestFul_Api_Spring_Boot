package com.example.RestFulApi.api;

import com.example.RestFulApi.domain.Host;
import com.example.RestFulApi.service.HostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HostApiController {
    private final HostService hostService;

    @RequestMapping("/")
    public String home() {
        log.info("home controller");

        return "home";
    }

    //등록 api
    @PostMapping("/api/hosts")
    public ResponseEntity createHost(@RequestBody @Valid CreateHostRequest request) {

        Host host = new Host();
        host.setName(request.getName());
        host.setIp(request.getIp());

        Long id = null;

        try {
            id = hostService.create(host);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateHostResponse(id));
    }

    @Data
    static class CreateHostRequest {
        private String name;
        private String ip;
    }

    @Data
    static class CreateHostResponse {
        private Long id;

        public CreateHostResponse(Long id) {
            this.id = id;
        }
    }

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


//    //삭제 api
//    @DeleteMapping("/api/host/{id}")
//    public DeleteHostResponse deleteHost(@PathVariable("id") Long id, @RequestBody @Valid DeleteHostRequest request) {
//        hostService.delete(id);
//
//    }
//
//    static class DeleteHostRequest {
//
//    }
//
//    static class DeleteHostResponse {
//
//    }


}
