package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/visit")
public class VisitController {
    @Autowired
    private VisitRepository visitRepository;

}
