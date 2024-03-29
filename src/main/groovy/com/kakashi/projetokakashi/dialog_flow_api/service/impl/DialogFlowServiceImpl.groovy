package com.kakashi.projetokakashi.dialog_flow_api.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.gson.Gson
import com.kakashi.projetokakashi.dialog_flow_api.contract.Intent
import com.kakashi.projetokakashi.dialog_flow_api.contract.MessageInput
import com.kakashi.projetokakashi.dialog_flow_api.contract.MessageOutput
import com.kakashi.projetokakashi.dialog_flow_api.contract.Messages
import com.kakashi.projetokakashi.dialog_flow_api.contract.Parts
import com.kakashi.projetokakashi.dialog_flow_api.contract.QueryInput
import com.kakashi.projetokakashi.dialog_flow_api.contract.ResponseText
import com.kakashi.projetokakashi.dialog_flow_api.contract.Text
import com.kakashi.projetokakashi.dialog_flow_api.contract.TrainingPhrases
import com.kakashi.projetokakashi.dialog_flow_api.service.DialogFlowService
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import com.kakashi.projetokakashi.web_api.contract.TrainingRequest
import com.kakashi.projetokakashi.web_api.handler.MessageOutputHelper
import org.aspectj.bridge.Message
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import java.security.PrivateKey

@Service
class DialogFlowServiceImpl implements DialogFlowService{


    private final static String DEFAULT_LANGUAGE_MESSAGE = "pt_BR"
    private final String USER_ACCOUNT = System.getenv("DIALOG_FLOW_USER_ACCOUNT")
    private String urlDialogFlow = System.getenv("URL_DIALOG_FLOW")
    private String urlDialogFlowBase = System.getenv("URL_DIALOG_FLOW_BASE")
    private String tokenAudienceUrl = "https://dialogflow.googleapis.com/"
    private String credentialsFilePath = System.getenv("CREDENTIALS_FILE_PATH")
    private Gson jsonConverter = new Gson()
    private ObjectMapper objectMapper = new ObjectMapper()

    private final RestTemplate restTemplate

    DialogFlowServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    OutgoingMessage sendMessage(String message, String sessionId) {
        MessageInput messageToDialogFlow = buildDialogFlowInputMessage(message)
        ResponseEntity<String> response = getMessageResponse(messageToDialogFlow, sessionId)
        return MessageOutputHelper.buildOutgoingMessage(objectMapper.readValue(response.body, MessageOutput))
    }

    Intent createTraining(TrainingRequest trainingRequest) {
        Intent request = buildDialogFlowIntent(trainingRequest)
        ResponseEntity<String> response = createTrainingPhrase(request)
        return objectMapper.readValue(response.body, Intent)
    }

    void editTraining(TrainingRequest trainingRequest, String flowId){
        Intent request = buildDialogFlowIntent(trainingRequest)
        editTrainingPhrase(request, flowId)
    }

    void removeTraining(String flowId) {
        removeTrainingPhrase(flowId)
    }

    private Intent buildDialogFlowIntent(TrainingRequest trainingRequest) {
        return new Intent(
                displayName: trainingRequest.question,
                trainingPhrases: [
                        new TrainingPhrases(
                                name: UUID.randomUUID().toString(),
                                parts: [
                                        new Parts(text: trainingRequest.question)
                                ]
                        )
                ],
                messages: [
                        new Messages(
                                text: new ResponseText(
                                        text: [trainingRequest.answer]
                                )
                        )
                ]
        )
    }

    private ResponseEntity<String> getMessageResponse(MessageInput message, String sessionId) {
        String url = this.urlDialogFlow << 'sessions/' << sessionId << ':detectIntent'
        HttpHeaders headers = getHeadersToRequest()
        HttpEntity request = new HttpEntity<>(message, headers)
        this.restTemplate.postForEntity(url, request, String)
    }

    private ResponseEntity<String> createTrainingPhrase(Intent intent) {
        String url = this.urlDialogFlow << '/intents'
        HttpHeaders headers = getHeadersToRequest()
        HttpEntity request = new HttpEntity<>(intent, headers)
        return this.restTemplate.postForEntity(url, request, String)
    }

    private void editTrainingPhrase(Intent intent, String flowId) {
        String url = this.urlDialogFlowBase << flowId
        HttpHeaders headers = getHeadersToRequest()
        HttpEntity request = new HttpEntity<>(intent, headers)
        this.restTemplate.patchForObject(url, request, String)
    }

    private void removeTrainingPhrase(String flowId) {
        String url = this.urlDialogFlowBase << flowId
        HttpHeaders headers = getHeadersToRequest()
        HttpEntity request = new HttpEntity<>(headers)
        this.restTemplate.exchange(url, HttpMethod.DELETE, request, String)
    }

    private HttpHeaders getHeadersToRequest(){
        String tokenAuthenticated = getAccessToken()
        String authenticationHeader = 'Bearer ' << tokenAuthenticated
        HttpHeaders headers = new HttpHeaders()
        headers.add('Authorization', authenticationHeader)
        headers.add('Content-Type', 'application/json; charset=utf-8')
        headers.add('Accept', 'application/json')
        return headers
    }

    private String getAccessToken() {
        GoogleCredential credential =
                GoogleCredential.fromStream(new FileInputStream(credentialsFilePath))
        PrivateKey privateKey = credential.getServiceAccountPrivateKey()
        String privateKeyId = credential.getServiceAccountPrivateKeyId()
        Algorithm algorithm = Algorithm.RSA256(null, privateKey)
        String signedJwt = JWT.create()
                .withKeyId(privateKeyId)
                .withIssuer(USER_ACCOUNT)
                .withSubject(USER_ACCOUNT)
                .withAudience(tokenAudienceUrl)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(generateExpirationTokenTime()))
                .sign(algorithm)
        signedJwt
    }

    private long generateExpirationTokenTime(){
        Integer quantMillisecondsCount = 3600
        Integer quantOneThousandToCount = 1000L
        return System.currentTimeMillis() + quantMillisecondsCount * quantOneThousandToCount
    }

    private MessageInput buildDialogFlowInputMessage(String message) {
        return new MessageInput(new QueryInput(new Text(text: message, languageCode: DEFAULT_LANGUAGE_MESSAGE)))
    }
}
