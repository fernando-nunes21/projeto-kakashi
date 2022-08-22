package com.kakashi.projetokakashi.web_api.helper

import com.kakashi.projetokakashi.dialog_flow_api.contract.Intent
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.contract.entity.TrainingsEntity

class TrainingConverter {
    static TrainingsEntity converterTrainingRequestToTraining (TrainingRequest trainingRequest, Intent dialogFlowIntent) {
        return new TrainingsEntity(
                question: trainingRequest.question,
                answer: trainingRequest.answer,
                flowId: dialogFlowIntent.name
        )
    }

    static TrainingRequest converterTrainingToTrainingRequest (TrainingsEntity trainings) {
        return new TrainingRequest(
                question: trainings.question,
                answer: trainings.answer,
                flowId: trainings.flowId
        )
    }
}
