package com.kakashi.projetokakashi.web_api.controller

import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/kakashi/bot")
class ApplicationController {

    @GetMapping
    ResponseEntity getBotInfo() {
        return new ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/training")
    ResponseEntity getTrainings() {
        return new ResponseEntity(HttpStatus.OK)
    }

    @PostMapping
    ResponseEntity sendMessage(@RequestBody IncomingMessage message) {
        return new ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/training")
    ResponseEntity trainingBot(@RequestBody TrainingRequest trainingRequest) {
        return new ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/training/{id}")
    ResponseEntity editTrainingBot(@RequestBody TrainingRequest trainingRequest, @PathVariable trainingId) {
        return new ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/training/{id}")
    ResponseEntity deleteTraining(@PathVariable String trainingId) {

    }

}
