package com.kakashi.projetokakashi.web_api.enums

enum GenericStatusMessages {

    TRAINED_COMPLETE("O bot foi treinado com sucesso"), TRAINED_DELETED("O treino foi deletado com sucesso")

    String message

    GenericStatusMessages(String message){
        this.message = message
    }
}