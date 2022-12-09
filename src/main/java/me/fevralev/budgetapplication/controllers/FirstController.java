package me.fevralev.budgetapplication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String StartPage(){
        return "Приложение запущено! ";
    }
    @GetMapping("/info")
    public String Description(){
        return "<center>Студент Февралев Антон.\r\n" +"<br>"+
                "Проект \"Книга рецептов бабы Любы\".\n" +
                "Проект создан 08.12.2022.\n"+
                "Данный проект предназначен для начинающих кулинаров.\n"+
                "Постоен на фреймворке Spring. Язык программирования Java v17. Собран сборщиком Maven";
    }
}
