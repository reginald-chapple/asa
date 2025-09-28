package com.asa.web.controller;

// import java.time.Year;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.asa.web.dto.GameFormDto;
// import com.asa.web.model.Game;
// import com.asa.web.model.League;
// import com.asa.web.repository.GameRepository;
// import com.asa.web.repository.TeamRepository;

// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;

// @Controller
// @RequestMapping("/games")
// @RequiredArgsConstructor
// public class GameController {

//     private final GameRepository gameRepository;
//     private final TeamRepository teamRepository;

//     @GetMapping("/league/{league}")
//     public String leagueTeams(@PathVariable League league, Model model) {
//         model.addAttribute("games", gameRepository.findByLeagueAndSeasonWithTeams(league, Year.now().getValue()));
//         model.addAttribute("league", league);
//         return "games/league";
//     }

//     @GetMapping("/league/{league}/add")
//     public String addGameForm(@PathVariable League league, Model model) {
//         model.addAttribute("league", league);
//         model.addAttribute("teams", teamRepository.findByLeague(league));
//         model.addAttribute("game", new GameFormDto());
//         return "games/add";
//     }

//     @PostMapping("/league/{league}/add")
//     public String addGameSubmit(@PathVariable League league, @ModelAttribute("game") @Valid GameFormDto dto,
//                             BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        
//         if (result.hasErrors()) {
//             model.addAttribute("league", league);
//             model.addAttribute("teams", teamRepository.findByLeague(league));
//             redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
//             return "games/add";
//         }

//         Game game = new Game();
//         game.setHomeTeam(teamRepository.findById(dto.getHomeTeamId()).get());
//         game.setAwayTeam(teamRepository.findById(dto.getAwayTeamId()).get());
//         game.setGameDate(dto.getGameDate());
//         game.setWeek(dto.getWeek());
//         game.setSeason(dto.getGameDate().getYear());
//         game.setLeague(league);
//         gameRepository.save(game);
//         redirectAttributes.addFlashAttribute("success", "Game added successfully!");

//         return "redirect:/games/league/" + league;

//     }

//     @GetMapping("{id}")
//     public String gameView(@PathVariable Long id, Model model) {
//         model.addAttribute("game", gameRepository.findByIdWithTeams(id).get());
//         return "games/view";
//     }

// }
