# 🚀 Stranger Things API Explorer

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Feign Client](https://img.shields.io/badge/Feign-Client-orange)
![Tests](https://img.shields.io/badge/Tests-JUnit%20%26%20Mockito-green)

> Projeto desenvolvido com o objetivo de explorar a integração com APIs externas usando Spring Boot, boas práticas de arquitetura (Controller, Service, DTO, Client) e testes unitários.

---

## 🎯 Objetivo

Criar uma API REST em Spring Boot que consome dados da Stranger Things API, expondo informações relevantes sobre os personagens da série.

---

## 🌐 API Externa Utilizada

- Base URL: `https://stranger-things-api.fly.dev/api/v1/`
- Endpoint de personagens: `/characters`

---

## 🧱 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Validation
- OpenFeign
- Lombok
- JUnit 5 + Mockito
- Swagger (Springdoc OpenAPI)
- Maven

---

## 📂 Estrutura do Projeto

src/main/java/com/explorer/strangerthingsapiexplorer
│
├── controller → Camada responsável pelos endpoints REST
├── service → Camada de regras de negócio
├── dto → Data Transfer Objects usados nas respostas
├── client → Feign client para integração com a API externa
└── exception → Tratamento de erros e exceções personalizadas

---

## 📌 Endpoints Disponíveis

### 🔍 Buscar personagens com paginação

**GET** `/characters?page={page}&size={size}`  
Retorna uma lista paginada de personagens com nome, ano de nascimento, episódios, sexo, cor do cabelo e cor dos olhos.

---

### 🔍 Filtrar personagens por nome

**GET** `/characters/search?name={name}`  
Retorna personagens cujo nome contém o texto informado.  
Retorna **404 Not Found** se nenhum personagem for encontrado.

---

### 🔍 Filtrar personagens por gênero

**GET** `/characters/gender?gender={gender}&page={page}&size={size}`  
Filtra personagens por gênero (Male ou Female).  
Retorna **400 Bad Request** para valores inválidos de gênero.

---

### 🏆 Top 5 personagens que mais aparecem

**GET** `/characters/top5`  
Retorna os 5 personagens que aparecem em mais episódios da série.

---

## 🧪 Testes

O projeto contém testes unitários usando JUnit e Mockito.

Camadas testadas:

- `CharacterService`: lógica de negócios
- `CharacterController`: endpoints REST
