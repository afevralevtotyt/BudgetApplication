package me.fevralev.budgetapplication.controllers;

import me.fevralev.budgetapplication.services.BudgetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vocation")
public class VocationController {
    private BudgetService budgetService;

    public VocationController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public int vocationBonus(@RequestParam int vocationDays){
        return budgetService.getVocationBonus(vocationDays);
    }

    @GetMapping("/salary")
    public int salaryWithVocation(@RequestParam int vocationDays, @RequestParam int workingDays, @RequestParam int vocationWorkingDays){
        return budgetService.getSalaryWithVocation(vocationDays, vocationWorkingDays, workingDays);
    }
}
