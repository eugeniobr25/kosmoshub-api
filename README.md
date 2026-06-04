# 🌌 KósmosHub API

> *O Universo Ordenado da Astronomia Amadora.*

Na astrofotografia e na astronomia visual, o registo em si é apenas uma parte da jornada; a "receita do bolo" (os dados de aquisição e equipamentos) é o que realmente permite que a comunidade aprenda e evolua. 
O grande desafio enfrentado pelos hobbystas de hoje é que essas informações valiosas — como métricas de captura, especificações de telescópios, uso de filtros e condições de observação — muitas vezes perdem-se, 
ficam isoladas em blocos de notas ou dispersas em redes sociais não otimizadas para isso.

É exatamente para solucionar esse gargalo que o projeto ganhou o seu nome. A palavra **"Kósmos"** (κόσμος) originalmente significa "ordem", "bom arranjo" e, por extensão, o "universo" ordenado. 
É a metáfora perfeita para uma aplicação que visa pegar nos dados de equipamentos e observações (que hoje estão caóticos e espalhados) e organizá-los num repositório central.

## 🎯 Visão do Produto

O **KósmosHub** vai muito além de um simples diário de observação. Ele posiciona-se como uma verdadeira rede de conhecimento para a comunidade, desenhado para:

- 🔭 **Centralizar o Conhecimento:** Criar um espaço onde informações técnicas, desde configurações avançadas de câmaras até especificações de projetos no estilo "Faça Você Mesmo" (DIY), fiquem documentadas e estruturadas.
- 📊 **Estruturar os Dados:** Transformar anotações descentralizadas e textos livres num repositório dinâmico, padronizado e filtrável.
- 🤝 **Fomentar o Compartilhamento:** Permitir que outros astrónomos amadores utilizem estes dados atrelados aos registos visuais para replicar configurações e alcançar resultados semelhantes nas suas próprias sessões.

A nossa missão é pegar no vasto universo de dados gerados no campo e trazer a ordem necessária para que todos possam **observar mais, fotografar melhor e partilhar sempre**.

---

## ⚙️ A Engenharia por Trás da API (Backend)

Este repositório contém a **API RESTful** que alimenta o ecossistema KósmosHub. Foi construído com foco em escalabilidade, integridade de dados e alta performance, utilizando o estado da arte do ecossistema Java.

### 🛠️ Stack Tecnológico
- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.5.14 (Spring Web, Spring Data JPA)
- **Base de Dados:** PostgreSQL 17.5
- **Ferramentas:** Lombok, Maven

### 📐 Destaques da Arquitetura (Domain Driven)
Para suportar a complexidade dos dados astronómicos sem onerar a base de dados, implementámos soluções de engenharia avançadas:

1. **Flexibilidade de Metadados (`JSONB`):** Configurações de telescópios e câmaras variam drasticamente de utilizador para utilizador.
2. Em vez de criar dezenas de colunas estáticas, os metadados de equipamento são armazenados de forma nativa como objetos `jsonb` no PostgreSQL,
3. permitindo buscas dinâmicas de alta performance.
4. **Relacionamentos Polimórficos:** O sistema de Interações (Comentários/Votos) e Mídias (Fotos/Diagramas) utiliza um motor polimórfico (`entity_id` e `entity_type`).
5. Isso permite que uma única tabela gira ficheiros e comentários tanto de postagens de observação quanto de tutoriais DIY, mantendo a base de dados limpa (DRY - *Don't Repeat Yourself*).
6. **Resiliência e Auditoria Leve (Shadow Columns):** Implementámos um padrão de *1-Step Undo* diretamente no ciclo de vida do JPA (`@PreUpdate`).
7. Sempre que um utilizador edita informações críticas (como o texto de um tutorial ou os parâmetros de uma câmara), a versão anterior é automaticamente espelhada numa *shadow column* (`previous_content`).
8. Isto garante segurança contra perdas acidentais de dados sem a necessidade de tabelas pesadas de histórico global.

## 🚀 Próximos Passos (Roadmap)
- [x] **Fase 1:** Modelação de Domínio e Arquitetura da Base de Dados Relacional.
- [x] **Fase 2:** Construção da Camada de Serviços, Repositórios e Controladores REST.
- [ ] **Fase 3:** Segurança (Autenticação JWT) e Validações.
- [ ] **Fase 4:** Consumo da API pelo Frontend (Angular).

---
