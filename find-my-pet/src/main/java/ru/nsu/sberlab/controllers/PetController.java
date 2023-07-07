package ru.nsu.sberlab.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.sberlab.models.Pet;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/")
    public String pets(@RequestParam(name = "chipId", required = false) String chipId, Principal principal, Model model) {
        model.addAttribute("pets", petService.getPets(chipId));
        model.addAttribute("user", petService.getUserByPrincipal(principal));
        return "pets";
    }

    @PostMapping("/pet/create")
    public String addPet(Pet pet, Principal principal){
        petService.savePet(principal, pet);
        return "redirect:/";
    }

    @GetMapping("/pet/{id}")
    public String petInfo(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.getPetById(id));
        return "pet-info";
    }

    @GetMapping("/pet/find")
    public String findPet(@RequestParam(name = "chipId") String chipId, Model model) {
        Pet pet = petService.getPetByChipId(chipId);
        if (pet != null) return petInfo(pet.getId(), model);

        model.addAttribute("chipId", chipId);
        return "pet-not-found";
    }
}
