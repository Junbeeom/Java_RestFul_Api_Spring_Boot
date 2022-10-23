package com.example.RestFulApi.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@Entity
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ip;

    @Column(name = "regestered_ts", length = 15)
    @CreationTimestamp
    private Timestamp registeredTs;

    @Column(name = "updated_ts", length = 15)
    @UpdateTimestamp
    private Timestamp updatedTs;

    public Host() {
    }

    public Host(Long id, String name, String ip, Timestamp registeredTs, Timestamp updatedTs) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.registeredTs = registeredTs;
        this.updatedTs = updatedTs;
    }
}
