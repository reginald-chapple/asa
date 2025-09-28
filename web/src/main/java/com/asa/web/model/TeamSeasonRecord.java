package com.asa.web.model;

// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "team_season_records")
// @EqualsAndHashCode(callSuper = true)
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class TeamSeasonRecord extends BaseEntity {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "season", nullable = false)
//     private Integer season;

//     @Column(name = "wins", nullable = false)
//     @Builder.Default
//     private Integer wins = 0;

//     @Column(name = "losses", nullable = false)
//     @Builder.Default
//     private Integer losses = 0;

//     @Column(name = "ties", nullable = false)
//     @Builder.Default
//     private Integer ties = 0;

//     @Column(name = "is_current", nullable = false)
//     @Builder.Default
//     private Boolean isCurrent = false;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "team_id", nullable = false)
//     private Team team;

// }
