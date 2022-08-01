package com.datsddos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Override
    public void run(String... args){
        logger.info("Controller Application has been started");
    }
}
