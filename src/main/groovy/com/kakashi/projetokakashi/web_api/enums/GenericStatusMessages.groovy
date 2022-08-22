package com.kakashi.projetokakashi.web_api.enums

enum GenericStatusMessages {

    TRAINING_COMPLETED("O bot foi treinado com sucesso"),
    TRAINING_EDITED("O treino do bot foi editado com sucesso"),
    TRANINIG_DELETED("O treino foi deletado com sucesso")

    String message

    GenericStatusMessages(String message){
        this.message = message
    }
}