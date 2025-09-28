package com.asa.web.controller;

// import java.time.Year;
// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.asa.web.dto.TeamFormDto;
// import com.asa.web.model.League;
// import com.asa.web.model.Sport;
// import com.asa.web.model.Team;
// import com.asa.web.model.TeamSeasonRecord;
// import com.asa.web.repository.TeamRepository;
// import com.asa.web.repository.TeamSeasonRecordRepository;

// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;

// @Controller
// @RequestMapping("/teams")
// @RequiredArgsConstructor
// public class TeamController {

//     private final TeamRepository teamRepository;
//     private final TeamSeasonRecordRepository teamSeasonRecordRepository;

//     @GetMapping("/league/{league}")
//     public String getTeamsByLeague(@PathVariable League league, Model model) {
//         List<Team> teams = teamRepository.findByLeague(league);
//         model.addAttribute("teams", teams);
//         model.addAttribute("league", league);
//         return "teams/list";
//     }

//     @GetMapping("/add")
//     public String showAddForm(Model model) {
//         model.addAttribute("team", new TeamFormDto());
//         model.addAttribute("leagues", League.values());
//         model.addAttribute("sports", Sport.values());
//         return "teams/add";
//     }

//     @PostMapping("/add")
//     public String addTeam(@ModelAttribute("team") @Valid TeamFormDto dto,
//                              BindingResult result, RedirectAttributes redirectAttributes, Model model) {
//         try {

//             if (result.hasErrors()) {
//                 model.addAttribute("leagues", League.values());
//                 model.addAttribute("sports", Sport.values());
//                 redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
//                 return "teams/add";
//             }

//             Team team = new Team();
//             team.setName(dto.getName());
//             team.setLeague(dto.getLeague());
//             team.setSport(dto.getSport());
//             teamRepository.save(team);

//             TeamSeasonRecord record = new TeamSeasonRecord();
//             record.setTeam(team);
//             record.setSeason(Year.now().getValue());
//             record.setIsCurrent(true);
//             teamSeasonRecordRepository.save(record);

//             redirectAttributes.addFlashAttribute("success", "Team added successfully!");

//             return "redirect:/teams/league/" + dto.getLeague().name();

//         } catch (Exception e) {
//             e.printStackTrace();
//             model.addAttribute("leagues", League.values());
//             model.addAttribute("sports", Sport.values());
//             redirectAttributes.addFlashAttribute("errors", "An error occurred while adding the team.");
//             return "redirect:/teams/add";
//         }
//     }
// }

