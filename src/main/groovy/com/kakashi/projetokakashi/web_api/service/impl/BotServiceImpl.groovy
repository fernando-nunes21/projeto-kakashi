package com.kakashi.projetokakashi.web_api.service.impl

import com.kakashi.projetokakashi.dialog_flow_api.service.DialogFlowService
import com.kakashi.projetokakashi.web_api.contract.BotInfo
import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.contract.Trainings
import com.kakashi.projetokakashi.web_api.contract.entity.BotConfig
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
        List<Trainings> trainings = this.trainingRepository.findAll()
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

    }

    @Override
    void editTrainingBot(TrainingRequest trainingRequest, String id) {

    }

    @Override
    void deleteTraining(String id) {

    }


    private BotInfo buildBotInfo(List<BotConfig> botConfigs, List<Trainings> trainings) {
        return new BotInfo(
                botId: botConfigs[0].id,
                botName: botConfigs[0].name,
                botVersion: botConfigs[0].version,
                allTrainings: buildBotTrainings(trainings),
                sendMessagesToBotQuantity: botConfigs[0].allMessagesSend
        )
    }

    private Trainings buildBotTrainings(List<Trainings> trainings) {
        return new Trainings(
                trainings.forEach{
                    new TrainingRequest(question: it.trainings.question, answer: it.trainings.answer)
                }
        )
    }

}
