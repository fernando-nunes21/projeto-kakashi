package com.kakashi.projetokakashi.web_api.service

import com.kakashi.projetokakashi.web_api.contract.BotInfo
import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.contract.Trainings
import org.springframework.stereotype.Service

@Service
interface BotService {

    BotInfo getBotInfo()

    Trainings getAllTrainings()

    OutgoingMessage sendMessageToBot(IncomingMessage message)

    void trainingBot(TrainingRequest trainingRequest)

    void editTrainingBot(TrainingRequest trainingRequest, String id)

    void deleteTraining(String id)

}