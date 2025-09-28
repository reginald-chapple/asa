package com.asa.web.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.asa.web.dto.TeamWithRecordDTO;
// import com.asa.web.model.League;
// import com.asa.web.model.Sport;
// import com.asa.web.model.Team;

// @Repository
// public interface TeamRepository extends JpaRepository<Team, Long> {

//     List<Team> findByLeague(League league);
//     List<Team> findBySport(Sport sport);
//     List<Team> findByLeagueAndSport(League league, Sport sport);
//     @Query("""
//         SELECT new com.asa.web.dto.TeamWithRecordDTO(
//             t.id, r.id, t.name, t.league, t.sport,
//             r.season, r.wins, r.losses, r.ties
//         )
//         FROM Team t
//         JOIN t.seasonRecords r
//         WHERE t.league = :league
//           AND r.isCurrent = true
//     """)
//     List<TeamWithRecordDTO> findTeamsByLeagueWithCurrentRecord(@Param("league") League league);
//     Optional<Team> findByNameAndLeague(String name, League league);
//     @Query("SELECT new com.asa.web.dto.TeamWithRecordDTO(t.id, r.id, t.name, t.league, t.sport, r.season, r.wins, r.losses, r.ties) " +
//            "FROM Team t " +
//            "JOIN t.seasonRecords r " +
//            "WHERE t.id = :teamId AND r.isCurrent = true")
//     Optional<TeamWithRecordDTO> findTeamWithCurrentRecord(@Param("teamId") Long teamId);
//     boolean existsByNameAndLeague(String name, League league);
//     boolean existsByNameAndSport(String name, Sport sport);
//     boolean existsByNameAndLeagueAndSport(String name, League league, Sport sport);

// }
