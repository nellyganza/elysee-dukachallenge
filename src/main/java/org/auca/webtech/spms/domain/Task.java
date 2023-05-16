package com.elysee.springapp.dukachallenge.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Task implements Serializable {
    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    private TaskOwner ownedBy;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

}
