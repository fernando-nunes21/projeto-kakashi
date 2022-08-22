# Projeto-Kakashi
Um bot para comunicação de fácil integração e boa base de respostas e auto aprendizado (Projeto para Serviços WEB)

## Como funciona esse bot?
O bot Kakashi é basicamente uma API com endpoints que serve para conversar com o bot, criar treinos, editar treinos, deletar treinos e pegar
informações sobre a atual versão do bot. 

Basicamente ele é integrado com o DialogFlow do Google para cuidar da parte de comunicação do bot. Cada treino de conversa 
e todo processado no DialogFlow e a conversa também e integrada por lá. Mais a frente entro em detalhes sobre a integração e os endpoints. 

## Me ajuda a rodar isso pelo amor de deus? 
Para rodar o projeto, é necessário configurar algumas variáveis de ambiente primeiro (relacionadas a integração do DialogFlow)

CREDENTIALS_FILE_PATH -> Essa vai ser onde fica o arquivo json com as credencias da Cloud do Google já com as permissões pro DialogFlow. 

DIALOG_FLOW_USER_ACCOUNT -> Esse vai ser o e-mail account que ta vinculado as credenciais (Gerado ou não pelo google)

URL_DIALOG_FLOW=https://dialogflow.googleapis.com/v2/projects/{Altere pro nome do projeto}/agent/;

URL_DIALOG_FLOW_BASE=https://dialogflow.googleapis.com/v2/  -> Esse pode deixar dessa forma. 

Tendo essas variáveis de ambiente ok, verifiquei só mais um detalhe, dessa vez referente ao banco de dados:
O Arquivo application.yml contem as config do database. Por gentileza, deixe ajustado para o username ser root ou o que você deseja
e o password seja o correto para não explodir na hora de subir (flyway faz o restante com as tabelas e pre inserções)

## Sobre os endpoints

Da uma olhada na controller que tu descobre. 
Ta eu te ajudo.

Basicamente vai usar o seu endereço padrão, provavelmente localhost e a base do url dessa API é "/v1/kakashi/bot"

Para buscar informações do bot, basta fazer a chamada usando GET

Para buscar todos os treinos cadastrados, basta adicionar /training na chamada e fazer uma chamada GET

Para mandar mensagem para o bot, basta usar a url base e o método POST.
Segue o contrato para troca de mensagem:

Ex:{"message":"Olá bot","senderId":"Fernando-id001"}

Para criar treinos, basta adicionar /training na chamada e fazer uma chamada POST
Segue o contrato para criação de treino.

Ex: {"question":"Como vai você?","answer":"Eu vou bem"}

Para editar treino, basta adicionar /training/{id} onde o ID vai ser o salvo no nosso banco (pode obter pegando os treinos)
e fazer uma chamada PUT onde o body terá as modificações. Segue o exemplo:

Ex: {"question":"Como vai você?","answer":"Eu vou bem"} -- Sim é o mesmo do criar.

Para deletar o treino, basta adicionar /training/{id} onde o ID vai ser o salvo no nosso banco (pode obter pegando os treinos)
e fazer uma chamada DELETE. 


## Duvidas, choros e reclamações? 

Abre o issue e a gente resolve! 

Dale. 
