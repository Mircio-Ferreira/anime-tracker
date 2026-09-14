

Projeto: "AnimeTracker" (Catálogo e Acompanhamento de Animes)

App web full-stack pra substituir o bloco de notas que uso hoje pra acompanhar os animes da temporada (nome + dia da semana de exibição). Domínio simples e pessoal, mas que exercita a stack inteira.

Entidades: Usuário, Anime, Temporada e Acompanhamento, com relacionamentos entre si.

Funcionalidades:
• Catálogo de animes e temporadas, com manutenção restrita a administradores)
• Acompanhamento pessoal por usuário (status assistindo/completo/dropado, progresso de episódios)
• Grade semanal mostrando o que passa em cada dia
• Listagem com paginação e filtros (por status, dia da semana, temporada)
• Validação de dados
• Autenticação com rotas protegidas (usuário comum vs. administrador)
• Front React consumindo a API Java
• Ambiente dockerizado (app + banco)

Como cada trilha é coberta:
• Java → modelagem do domínio (OO: encapsulamento, enums, hierarquia de exceções de negócio)
• Banco (SQL & JPA) → modelagem relacional (relação N-N entre Anime e Temporada, entidade de associação entre Usuário e Anime), ORM Hibernate, relacionamentos, JPQL
• Spring Boot → API REST, DTOs como Records, Spring Data JPA, paginação, Bean Validation, soft delete, autenticação/autorização com Spring Security
• Docker → Criação de um ambiente para executar a aplicação.
• React → SPA em TypeScript, hooks, consumo de API, React Router com rotas protegidas por papel

Fora do escopo: migrations formais (Flyway) — o Hibernate gera o schema automaticamente por enquanto.

