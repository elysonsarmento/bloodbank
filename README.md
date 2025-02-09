# Projeto Backend em Java

Bem-vindo ao projeto backend em Java! Este repositório contém uma aplicação Java com suporte a Docker e Docker Compose. Siga as instruções abaixo para configurar e executar o projeto.

## Requisitos
Antes de iniciar, certifique-se de ter instalado os seguintes requisitos:

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Instalação

1. Clone o repositório:
   ```sh
   git clone https://github.com/elysonsarmento/bloodbank
   cd bloodbank

## Executando o Projeto

### Com Docker Compose
1. Suba os containers com:
   ```sh
   docker-compose up -d
   ```
2. Para verificar os logs da aplicação:
   ```sh
   docker-compose logs -f app
   ```
3. Para derrubar os containers:
   ```sh
   docker-compose down
   ```

### Executando Localmente
1. Inicie a aplicação sem Docker:
   ```sh
   mvn spring-boot:run
   ```
