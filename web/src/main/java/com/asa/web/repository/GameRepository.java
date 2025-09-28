package com.asa.web.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.asa.web.dto.GameWithTeamsDto;
// import com.asa.web.model.Game;
// import com.asa.web.model.League;
// import com.asa.web.model.Team;

// @Repository
// public interface GameRepository extends JpaRepository<Game, Long> {

//     List<Game> findBySeason(Integer season);
//     List<Game> findByWeek(String week);
//     List<Game> findByHomeTeam(Team homeTeam);
//     List<Game> findByAwayTeam(Team awayTeam);
//     List<Game> findByHomeTeamAndAwayTeam(Team homeTeam, Team awayTeam);
//     List<Game> findByHomeTeamAndSeason(Team homeTeam, Integer season);
//     List<Game> findByAwayTeamAndSeason(Team awayTeam, Integer season);
//     List<Game> findByHomeTeamAndAwayTeamAndSeason(Team homeTeam, Team awayTeam, Integer season);
//     List<Game> findByHomeTeamAndSeasonAndWeek(Team homeTeam, Integer season, String week);
//     List<Game> findByAwayTeamAndSeasonAndWeek(Team awayTeam, Integer season, String week);
//     List<Game> findByHomeTeamAndAwayTeamAndSeasonAndWeek(Team homeTeam, Team awayTeam, Integer season, String week);
//     List<Game> findBySeasonAndWeek(Integer season, String week);
//     List<Game> findByLeague(League league);
//     List<Game> findByLeagueAndSeason(League league, Integer season);
//     List<Game> findByLeagueAndSeasonAndWeek(League league, Integer season, String week);

//     @Query("""
//         SELECT new com.asa.web.dto.GameWithTeamsDto(
//             g.id,
//             ht.name,
//             at.name,
//             g.gameDate,
//             g.league,
//             g.season,
//             g.week
//         )
//         FROM Game g
//         JOIN g.homeTeam ht
//         JOIN g.awayTeam at
//         WHERE g.league = :league AND g.season = :season
//     """)
//     List<GameWithTeamsDto> findByLeagueAndSeasonWithTeams(@Param("league") League league, @Param("season") Integer season);

//     @Query("""
//         SELECT new com.asa.web.dto.GameWithTeamsDto(
//             g.id,
//             ht.name,
//             at.name,
//             g.gameDate,
//             g.league,
//             g.season,
//             g.week
//         )
//         FROM Game g
//         JOIN g.homeTeam ht
//         JOIN g.awayTeam at
//         WHERE g.id = :id
//     """)
//     Optional<GameWithTeamsDto> findByIdWithTeams(@Param("id") Long id);
    
// }
