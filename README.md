# 🌌 KósmosHub API

> *O Universo Ordenado da Astronomia Amadora.*

Na astrofotografia e na astronomia visual, o registo em si é apenas uma parte da jornada; a "receita do bolo" (os dados de aquisição e equipamentos) é o que realmente permite que a comunidade aprenda e evolua. O grande desafio enfrentado pelos hobbystas de hoje é que essas informações valiosas — como métricas de captura, especificações de telescópios, uso de filtros e condições de observação — muitas vezes perdem-se, ficam isoladas em blocos de notas ou dispersas em redes sociais genéricas não otimizadas para este nicho.

É exatamente para solucionar este gargalo que o projeto ganhou o seu nome. A palavra **"Kósmos"** (κόσμος) originalmente significa "ordem", "bom arranjo" e, por extensão, o "universo" ordenado. É a metáfora perfeita para uma aplicação que visa resgatar os dados de equipamentos e observações (atualmente caóticos e espalhados) e organizá-los num repositório centralizado, estruturado e escalável.

---

## 🎯 Visão do Produto

O **KósmosHub** vai muito além de um simples diário de observação. Posiciona-se como uma verdadeira rede de conhecimento para a comunidade, desenhada para:

- 🔭 **Centralizar o Conhecimento:** Criar um espaço onde informações técnicas, desde configurações avançadas de câmaras até especificações de projetos no estilo "Faça Você Mesmo" (DIY), fiquem rigorosamente documentadas.
- 📊 **Estruturar os Dados:** Transformar anotações descentralizadas e textos livres num repositório dinâmico, padronizado e facilmente filtrável.
- 🤝 **Fomentar a Partilha:** Permitir que outros astrónomos amadores utilizem dados atrelados a registos visuais para replicar configurações e alcançar resultados de excelência nas suas próprias sessões.

A nossa missão é pegar no vasto universo de dados gerados no campo e trazer a ordem necessária para que todos possam **observar mais, fotografar melhor e partilhar sempre**.

---

## ⚙️ A Engenharia por Trás da API (Backend)

Este repositório contém a **API RESTful** que alimenta o ecossistema KósmosHub. Foi construída com foco absoluto em escalabilidade, segurança *Zero Trust* e alta performance, utilizando o estado da arte do ecossistema Java.

### 🛠️ Stack Tecnológico
- **Core:** Java 21 | Spring Boot 3.5.14 (Web, Data JPA, Validation)
- **Base de Dados:** PostgreSQL 17.5 | Flyway (Migrações)
- **Segurança:** Spring Security | JWT (JSON Web Tokens) | BCrypt
- **Documentação:** OpenAPI 3 (Swagger UI)
- **Ferramentas:** Lombok, Maven

---

### 📐 Destaques da Arquitetura & Boas Práticas

Para suportar a complexidade dos dados astronómicos garantindo resiliência e performance, a arquitetura foi desenhada com padrões *Enterprise*:

#### 1. Segurança & Controlo de Acesso (Zero Trust)
- **Autenticação Stateless:** Implementação de JWT (Bearer Token) interceptado via Filtros de Segurança customizados, eliminando o uso de sessões na memória do servidor.
- **Blindagem de Payload (Padrão DTO):** Utilização intensiva de *Java Records* como *Data Transfer Objects*. As entidades de base de dados nunca são expostas aos controladores, prevenindo ataques de *Mass Assignment* e vazamento de dados sensíveis (como hashes de senhas).
- **Tratamento Global de Erros (`@ControllerAdvice`):** O utilizador (ou aplicação cliente) nunca recebe *stacktraces* do servidor. Todas as exceções de negócio ou de validação são capturadas globalmente e traduzidas em respostas JSON limpas e padronizadas.

#### 2. Modelagem de Dados & Flexibilidade
- **Metadados Dinâmicos (`JSONB`):** Como as configurações de telescópios e câmaras variam drasticamente, os metadados de equipamento são armazenados de forma nativa como objetos `jsonb` no PostgreSQL, permitindo flexibilidade sem comprometer a capacidade de busca indexada.
- **Relacionamentos Polimórficos:** O sistema de Interações (Comentários/Votos) e Mídias (Fotos) utiliza um motor polimórfico (`entity_id` e `entity_type`). Uma única tabela centraliza ficheiros e interações de Postagens de Observação, Planos e Tutoriais DIY, respeitando o princípio DRY (*Don't Repeat Yourself*).
- **Congelamento de Schema (Flyway):** A base de dados é estritamente versionada. O schema é determinístico, previsível e construído através de scripts SQL de migração.

#### 3. Performance & Escalabilidade
- **Prevenção do Paradoxo N+1 Queries:** Uso estratégico de `@EntityGraph` para garantir que dados relacionados sejam carregados através de JOINs otimizados, preservando a memória RAM.
- **Paginação de Alto Desempenho:** Todos os endpoints que retornam listas utilizam a interface `Pageable` do Spring, garantindo respostas em milissegundos independentemente do crescimento da base de dados.
- **Indexação Estratégica:** Criação de Índices B-Tree para *Foreign Keys* e índices compostos em tabelas polimórficas para evitar *Full Table Scans*.

#### 4. Resiliência e Auditoria Leve (Shadow Columns)
Implementámos um padrão de *1-Step Undo* diretamente no ciclo de vida do JPA (`@PreUpdate`). Sempre que um utilizador edita informações críticas (como o texto de um tutorial), a versão anterior é automaticamente espelhada numa *shadow column* (`previous_content`), garantindo segurança contra perdas acidentais de forma leve.

---

## 📚 Documentação da API

A API encontra-se totalmente documentada e testável através do **Swagger UI** (OpenAPI 3).
Para explorar os endpoints, rodar a aplicação e aceder localmente a:
`http://localhost:8080/swagger-ui/index.html`

*(Nota: O Swagger está configurado para suportar injeção de Bearer Token JWT para testes em rotas protegidas).*

---

## 🚀 Roadmap do Projeto

- [x] **Fase 1:** Modelação de Domínio (DDD) e Arquitetura da Base de Dados Relacional.
- [x] **Fase 2:** Construção da Camada de Serviços, Repositórios e Controladores REST.
- [x] **Fase 3:** Segurança (Spring Security + Autenticação JWT) e Validações.
- [x] **Fase 4:** Auditoria Arquitetural (DTOs, Flyway, CORS, Tratamento Global de Erros, Índices e Paginação).
- [ ] **Fase 5:** Início do Desenvolvimento do **Frontend (Cliente)**.
    - *Stack Alvo:* Angular 17+ (Standalone Components, Signals), Tailwind CSS (focado num UI/UX "Red Mode" para preservação de visão noturna).