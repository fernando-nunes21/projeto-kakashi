package com.kakashi.projetokakashi.dialog_flow_api.service

import com.kakashi.projetokakashi.dialog_flow_api.contract.Intent
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import org.springframework.stereotype.Service

@Service
interface DialogFlowService {

    OutgoingMessage sendMessage(String message, String sessionId)

    Intent createTraining(TrainingRequest trainingRequest)

    void editTraining(TrainingRequest trainingRequest, String flowId)

    void removeTraining(String flowId)

}