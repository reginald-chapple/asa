package com.asa.web.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.asa.web.model.AppUser;
import com.asa.web.model.Role;
import com.asa.web.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSeederConfig {
    // private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;   

    @Bean
    public CommandLineRunner seedAdminUser() {
        return args -> {

            // Create a new user with the admin role
            userRepository.findByEmail("sudo@local.com")
                .orElseGet(() -> {
                    AppUser adminUser = AppUser.builder()
                        .email("sudo@local.com")
                        .password(passwordEncoder.encode("P@ss1234"))
                        .firstName("Super")
                        .lastName("User")
                        .role(Role.ROLE_ADMIN)
                        .build();
                    return userRepository.save(adminUser);
                });
        };
    }

    // @Bean
    // public CommandLineRunner seedNflTeams() {
    //     return args -> {
    //         if (teamRepository.count() == 0) {
    //             List<Team> teams = new ArrayList<>(
    //                 List.of(
    //                     Team.builder().name("Arizona Cardinals").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Atlanta Falcons").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Baltimore Ravens").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Buffalo Bills").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Carolina Panthers").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Chicago Bears").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Cincinnati Bengals").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Cleveland Browns").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Dallas Cowboys").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Denver Broncos").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Detroit Lions").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Green Bay Packers").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Houston Texans").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Indianapolis Colts").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Jacksonville Jaguars").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Kansas City Chiefs").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Las Vegas Raiders").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Los Angeles Chargers").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Los Angeles Rams").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Miami Dolphins").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Minnesota Vikings").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("New England Patriots").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("New Orleans Saints").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("New York Giants").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("New York Jets").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Philadelphia Eagles").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Pittsburgh Steelers").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("San Francisco 49ers").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Seattle Seahawks").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Tampa Bay Buccaneers").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Tennessee Titans").league(League.NFL).sport(Sport.FOOTBALL).build(),
    //                     Team.builder().name("Washington Commanders").league(League.NFL).sport(Sport.FOOTBALL).build()
    //                 )
    //             );

    //             teamRepository.saveAll(teams);
    //         }
    //     };
    // }
}
