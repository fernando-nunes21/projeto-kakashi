package com.kakashi.projetokakashi.web_api.controller

import com.kakashi.projetokakashi.web_api.contract.BotInfo
import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.contract.Trainings
import com.kakashi.projetokakashi.web_api.enums.GenericStatusMessages
import com.kakashi.projetokakashi.web_api.handler.GenericStatusMessage
import com.kakashi.projetokakashi.web_api.service.BotService
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

    private BotService botService

    ApplicationController(BotService botService) {
        this.botService = botService
    }

    @GetMapping
    ResponseEntity getBotInfo() {
        BotInfo botInfo = botService.getBotInfo()
        return new ResponseEntity(botInfo, HttpStatus.OK)
    }

    @GetMapping("/training")
    ResponseEntity getTrainings() {
        Trainings trainings = botService.getAllTrainings()
        return new ResponseEntity(trainings, HttpStatus.OK)
    }

    @PostMapping
    ResponseEntity sendMessage(@RequestBody IncomingMessage message) {
        OutgoingMessage response = botService.sendMessageToBot(message)
        return new ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/training")
    ResponseEntity trainingBot(@RequestBody TrainingRequest trainingRequest) {
        botService.trainingBot(trainingRequest)
        return new ResponseEntity(
                new GenericStatusMessage(HttpStatus.OK.value(), GenericStatusMessages.TRAINED_COMPLETE.message),
                HttpStatus.OK
        )
    }

    @PutMapping("/training/{id}")
    ResponseEntity editTrainingBot(@RequestBody TrainingRequest trainingRequest, @PathVariable String trainingId) {
        botService.editTrainingBot(trainingRequest, trainingId)
        return new ResponseEntity(
                new GenericStatusMessage(HttpStatus.OK.value(), GenericStatusMessages.TRAINED_COMPLETE.message),
                HttpStatus.OK
        )
    }

    @DeleteMapping("/training/{id}")
    ResponseEntity deleteTraining(@PathVariable String trainingId) {
        botService.deleteTraining(trainingId)
        return new ResponseEntity(
                new GenericStatusMessage(HttpStatus.ACCEPTED.value(), GenericStatusMessages.TRAINED_DELETED.message),
                HttpStatus.ACCEPTED
        )
    }

}
