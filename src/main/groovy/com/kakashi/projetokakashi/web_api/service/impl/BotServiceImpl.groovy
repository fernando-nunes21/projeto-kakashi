package com.kakashi.projetokakashi.web_api.service.impl

import com.kakashi.projetokakashi.dialog_flow_api.contract.Intent
import com.kakashi.projetokakashi.dialog_flow_api.service.DialogFlowService
import com.kakashi.projetokakashi.web_api.contract.BotInfo
import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.contract.Trainings
import com.kakashi.projetokakashi.web_api.contract.entity.BotConfig
import com.kakashi.projetokakashi.web_api.contract.entity.TrainingsEntity
import com.kakashi.projetokakashi.web_api.helper.TrainingConverter
import com.kakashi.projetokakashi.web_api.repository.BotConfigRepository
import com.kakashi.projetokakashi.web_api.repository.TrainingRepository
import com.kakashi.projetokakashi.web_api.service.BotService
import org.springframework.stereotype.Service

@Service
class BotServiceImpl implements BotService{

    private DialogFlowService dialogFlowService
    private BotConfigRepository botConfigRepository
    private TrainingRepository trainingRepository

    BotServiceImpl(BotConfigRepository botConfigRepository,
                   TrainingRepository trainingRepository,
                   DialogFlowService dialogFlowService) {
        this.botConfigRepository = botConfigRepository
        this.trainingRepository = trainingRepository
        this.dialogFlowService = dialogFlowService
    }

    @Override
    BotInfo getBotInfo() {
        List<BotConfig> botConfig = this.botConfigRepository.findAll()
        List<Trainings> trainings = this.trainingRepository.findAll()
        return buildBotInfo(botConfig, trainings)
    }

    @Override
    Trainings getAllTrainings() {
        List<TrainingsEntity> trainings = this.trainingRepository.findAll()
        return buildBotTrainings(trainings)
    }

    @Override
    OutgoingMessage sendMessageToBot(IncomingMessage message) {
        OutgoingMessage responseMessage = dialogFlowService.sendMessage(message.message, message.senderId)
        BotInfo botInfo = getBotInfo()
        responseMessage.responseBotId = botInfo.botId
        responseMessage.originalSenderId = message.senderId
        return responseMessage
    }

    @Override
    void trainingBot(TrainingRequest trainingRequest) {
        Intent response = dialogFlowService.createTraining(trainingRequest)
        trainingRepository.save(TrainingConverter.converterTrainingRequestToTraining(trainingRequest, response))
    }

    @Override
    void editTrainingBot(TrainingRequest trainingRequest, Integer id) {
        TrainingsEntity training = getTrainingEntityById(id)
        this.dialogFlowService.editTraining(trainingRequest, training.flowId)
        training.question = trainingRequest.question
        training.answer = trainingRequest.answer
        this.trainingRepository.save(training)
    }

    @Override
    void deleteTraining(Integer id) {
        TrainingsEntity training = getTrainingEntityById(id)
        this.dialogFlowService.removeTraining(training.flowId)
        this.trainingRepository.delete(training)
    }

    private TrainingsEntity getTrainingEntityById(Integer id) {
        Optional<TrainingsEntity> dbTrainingReturning = this.trainingRepository.findById(id)
        if (dbTrainingReturning.isPresent()) {
            return dbTrainingReturning.get()
        } else {
            throw new Exception()
        }
    }

    private BotInfo buildBotInfo(List<BotConfig> botConfigs, List<TrainingsEntity> trainings) {
        return new BotInfo(
                botId: botConfigs[0].id,
                botName: botConfigs[0].name,
                botVersion: botConfigs[0].version,
                allTrainings: buildBotTrainings(trainings),
                sendMessagesToBotQuantity: botConfigs[0].allMessagesSend
        )
    }

    private Trainings buildBotTrainings(List<TrainingsEntity> trainings) {
        return new Trainings(trainings: trainings)
    }

}
