package com.kakashi.projetokakashi.web_api.service.impl

import com.kakashi.projetokakashi.web_api.contract.BotInfo
import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.contract.Trainings
import com.kakashi.projetokakashi.web_api.service.BotService
import org.springframework.stereotype.Service

@Service
class BotServiceImpl implements BotService{

    @Override
    BotInfo getBotInfo() {
        return null
    }

    @Override
    Trainings getAllTrainings() {
        return null
    }

    @Override
    OutgoingMessage sendMessageToBot(IncomingMessage message) {
        return null
    }

    @Override
    void trainingBot(TrainingRequest trainingRequest) {

    }

    @Override
    void editTrainingBot(TrainingRequest trainingRequest, String id) {

    }

    @Override
    void deleteTraining(String id) {

    }

}
