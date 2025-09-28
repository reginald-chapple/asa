package com.asa.web.model;

// import java.util.Set;
// import java.util.HashSet;

// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "teams", uniqueConstraints = {
//     @UniqueConstraint(columnNames = {"name", "league"})
// })
// @EqualsAndHashCode(callSuper = true)
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class Team extends BaseEntity {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "name", nullable = false)
//     private String name;

//     @Enumerated(EnumType.STRING)
//     @Column(name = "league", nullable = false)
//     private League league;

//     @Column(name = "sport", nullable = false)
//     @Enumerated(EnumType.STRING)
//     private Sport sport;

//     @OneToMany(mappedBy = "homeTeam", cascade = CascadeType.ALL)
//     @Builder.Default
//     private Set<Game> homeGames = new HashSet<>();

//     @OneToMany(mappedBy = "awayTeam", cascade = CascadeType.ALL)
//     @Builder.Default
//     private Set<Game> awayGames = new HashSet<>();

//     @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
//     @Builder.Default
//     private Set<TeamSeasonRecord> seasonRecords = new HashSet<>();
// }
