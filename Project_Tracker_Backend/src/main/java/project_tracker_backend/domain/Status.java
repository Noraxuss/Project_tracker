package project_tracker_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "statuses")
@Getter
@Setter
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPurpose statusPurpose;

    @OneToMany(mappedBy = "status")
    private List<Task> tasks;

    @OneToMany(mappedBy = "status")
    private List<Project> projects;



}
