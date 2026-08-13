# Mapa de Conhecimentos — Backend Web / Engenharia de Software

> Currículo permanente de estudos para desenvolver senioridade em sistemas backend web.
>
> **Objetivo:** dominar fundamentos de computação, backend, bancos de dados, sistemas distribuídos, arquitetura, segurança, infraestrutura, observabilidade, IA e engenharia de software.
>
> **Princípio:** não estudar apenas tecnologias. Buscar entender **conceitos, mecanismos, trade-offs e aplicações práticas**.

---

## Como usar este mapa

Para cada tópico, buscar progressivamente:

- **Fundamentos:** consigo explicar o conceito?
- **Prática:** consigo implementar?
- **Diagnóstico:** consigo investigar quando algo dá errado?
- **Decisão:** consigo escolher entre alternativas e explicar os trade-offs?

### Hierarquia de fontes

1. **Livros-texto** — teoria e fundamentos.
2. **Cursos universitários** — explicação estruturada e exercícios.
3. **Documentação oficial** — tecnologias e APIs.
4. **RFCs / padrões** — protocolos e especificações.
5. **OWASP / NIST / fontes oficiais** — segurança.
6. **Papers** — pesquisa e fundamentos de IA/sistemas.
7. **Projetos e laboratórios** — validação prática.

---

# 1. Fundamentos de Computação

## 1.1 Algoritmos e complexidade

### Conteúdos

- Big O, Big Ω, Big Θ
- Complexidade de tempo
- Complexidade de espaço
- Análise assintótica
- Recursão
- Iteração
- Correção de algoritmos
- Trade-offs entre tempo e memória

### Estruturas de dados

- Array
- Linked List
- Stack
- Queue
- Hash Table
- Set
- Tree
- Binary Search Tree
- Heap
- Priority Queue
- Trie
- Graph

### Algoritmos

- Binary Search
- Sorting
- Two Pointers
- Sliding Window
- Recursion
- Backtracking
- BFS
- DFS
- Greedy
- Dynamic Programming
- Shortest Path
- Topological Sort

### Referências principais

- **MIT 6.006 — Introduction to Algorithms**
- **Introduction to Algorithms — Cormen, Leiserson, Rivest & Stein (CLRS)**

[MIT 6.006](https://ocw.mit.edu/courses/6-006-introduction-to-algorithms-fall-2011/)

---

# 2. Programação e Linguagens

## 2.1 Fundamentos de programação

- Abstração
- Encapsulamento
- Composição
- Polimorfismo
- Imutabilidade
- Funções puras
- Tratamento de erros
- Generics
- Interfaces
- Tipagem estática/dinâmica
- Runtime vs compile time
- Memory management
- Garbage Collection

## 2.2 TypeScript

- Type system
- Generics
- Union/intersection types
- Type narrowing
- Type guards
- Utility types
- Conditional types
- Mapped types
- Modules
- Decorators
- Async/await

### Referência

- **TypeScript Handbook**

https://www.typescriptlang.org/docs/handbook/intro.html

## 2.3 Node.js

- Event Loop
- libuv
- Async I/O
- Promises
- Streams
- Buffers
- Workers
- Processes
- Signals
- Graceful shutdown
- Networking
- Performance

### Referência

- **Node.js Documentation**

https://nodejs.org/docs/latest/api/

---

# 3. Sistemas Operacionais

## 3.1 Processos e threads

- Process
- Thread
- Context switching
- Multithreading
- Concurrency
- Parallelism
- Race conditions
- Deadlocks
- Starvation
- Synchronization

## 3.2 Memória

- Stack
- Heap
- Virtual memory
- Paging
- Memory allocation
- Memory leaks
- Garbage Collection

## 3.3 Linux

- Filesystem
- Permissions
- Users/groups
- Processes
- Signals
- Services
- systemd
- Environment variables
- Logs
- Shell scripting

## 3.4 Ferramentas

- ps
- top
- htop
- free
- df
- du
- lsof
- ss
- curl
- grep
- awk
- sed
- find
- journalctl
- systemctl
- strace

### Referência principal

- **Operating Systems: Three Easy Pieces — OSTEP**

https://pages.cs.wisc.edu/~remzi/OSTEP/

### Referência complementar

- **Linux Kernel Documentation**

https://docs.kernel.org/

---

# 4. Redes de Computadores

## 4.1 Fundamentos

- OSI
- TCP/IP
- LAN/WAN
- Routing
- NAT
- Firewall
- Ports
- Sockets

## 4.2 TCP/IP

- IPv4
- IPv6
- TCP
- UDP
- Handshake
- Connection termination
- Retransmission
- Congestion control

## 4.3 DNS

- DNS resolution
- A
- AAAA
- CNAME
- MX
- TXT
- TTL
- DNS caching

## 4.4 HTTP

- HTTP/1.1
- HTTP/2
- HTTP/3
- Methods
- Status codes
- Headers
- Cookies
- Sessions
- Keep-alive
- Compression
- Content negotiation
- Caching

## 4.5 TLS

- HTTPS
- Certificates
- Certificate Authorities
- Public/private keys
- TLS handshake
- Encryption

## 4.6 Infraestrutura de rede

- Reverse proxy
- Load balancer
- CDN
- API Gateway
- Service discovery

### Referência principal

- **Computer Networking: A Top-Down Approach — Kurose & Ross**

### Referências online

- **MDN Web Docs — HTTP**

https://developer.mozilla.org/en-US/docs/Web/HTTP

- **RFC Editor**

https://www.rfc-editor.org/

---

# 5. Backend Web

## 5.1 APIs

- REST
- RPC
- GraphQL
- WebSockets
- Server-Sent Events
- Webhooks

## 5.2 REST

- Resources
- HTTP semantics
- Idempotency
- Pagination
- Filtering
- Sorting
- Versioning
- Error handling
- Rate limiting
- Backward compatibility
- API documentation

## 5.3 Autenticação

- Sessions
- Cookies
- JWT
- OAuth 2.0
- OpenID Connect
- Access tokens
- Refresh tokens
- API keys
- MFA

## 5.4 Backend frameworks

Conhecer profundamente pelo menos um ecossistema:

- Node.js / TypeScript
- Java / Spring
- Go
- C#

### Prioridade pessoal

**TypeScript → Node.js → posteriormente Java/Spring ou Go**

---

# 6. Bancos de Dados

## 6.1 Relacional

### SQL

- SELECT
- JOIN
- GROUP BY
- HAVING
- Subqueries
- CTE
- Window Functions
- Views
- Constraints

### Modelagem

- Entidades
- Relacionamentos
- Cardinalidade
- Normalização
- Desnormalização
- Primary Keys
- Foreign Keys
- Constraints

## 6.2 PostgreSQL

- Indexes
- B-tree
- Composite indexes
- Partial indexes
- Query planner
- EXPLAIN
- EXPLAIN ANALYZE
- VACUUM
- WAL
- Replication

## 6.3 Transações

- ACID
- Atomicity
- Consistency
- Isolation
- Durability
- Isolation levels
- Locks
- Deadlocks
- MVCC

### Referência principal

- **PostgreSQL Documentation**

https://www.postgresql.org/docs/current/

## 6.4 Internals

- Storage engines
- B-Trees
- LSM Trees
- Indexes
- Transactions
- WAL
- Replication
- Query processing

### Referência principal

- **Database Internals — Alex Petrov**

---

# 7. Bancos Não Relacionais

## MongoDB

- Document modeling
- Embedding
- Referencing
- Indexes
- Aggregation
- Transactions
- Replication
- Sharding

## Outros modelos

- Key-value
- Document
- Columnar
- Graph

### Objetivo

Entender **quando escolher cada modelo**, não decorar tecnologias.

---

# 8. Caching

- Cache hit/miss
- TTL
- Cache invalidation
- Cache-aside
- Write-through
- Write-behind
- Local cache
- Distributed cache
- Cache stampede
- Cache consistency

## Redis

- Strings
- Lists
- Sets
- Sorted Sets
- Hashes
- Pub/Sub
- Streams
- Transactions
- Distributed locks

---

# 9. Mensageria e Sistemas Assíncronos

## Conceitos

- Message Queue
- Producer
- Consumer
- Broker
- Acknowledgement
- Retry
- Dead Letter Queue
- Ordering
- At-most-once
- At-least-once
- Exactly-once semantics
- Idempotency

## Tecnologias

- RabbitMQ
- Kafka

## Arquiteturas

- Event-driven architecture
- Eventual consistency
- Event sourcing
- CQRS
- Outbox Pattern
- Saga

---

# 10. Concorrência e Sistemas Distribuídos

## Concorrência

- Race conditions
- Mutex
- Semaphore
- Atomic operations
- Optimistic locking
- Pessimistic locking
- Thread safety

## Sistemas distribuídos

- CAP theorem
- Consistency
- Availability
- Partition tolerance
- Replication
- Sharding
- Leader election
- Consensus
- Distributed locks
- Distributed transactions
- Eventual consistency
- Clock synchronization

## Falhas

- Duplicate requests
- Network failure
- Partial failure
- Retry storms
- Split brain
- Cascading failure

### Referência principal

- **Designing Data-Intensive Applications — Martin Kleppmann & Chris Riccomini**

https://www.oreilly.com/library/view/designing-data-intensive-applications/9781098119058/

---

# 11. Engenharia de Software

## Princípios

- SOLID
- DRY
- KISS
- YAGNI
- Separation of Concerns
- High cohesion
- Low coupling
- Composition over inheritance
- Dependency inversion

## Design Patterns

- Factory
- Builder
- Strategy
- Adapter
- Decorator
- Observer
- Command
- State
- Repository

### Referências principais

- **Clean Code — Robert C. Martin**
- **Refactoring — Martin Fowler**
- **Patterns of Enterprise Application Architecture — Martin Fowler**
- **Clean Architecture — Robert C. Martin**

https://martinfowler.com/books/eaa.html

---

# 12. Domain-Driven Design

## Strategic DDD

- Domain
- Subdomain
- Core Domain
- Supporting Subdomain
- Generic Subdomain
- Bounded Context
- Context Map

## Tactical DDD

- Entity
- Value Object
- Aggregate
- Aggregate Root
- Repository
- Domain Service
- Application Service
- Domain Event
- Factory

## Modelagem

- Ubiquitous Language
- Invariants
- Business rules
- Domain boundaries

### Referências principais

1. **Domain-Driven Design — Eric Evans**
2. **Implementing Domain-Driven Design — Vaughn Vernon**
3. **Domain-Driven Design Distilled — Vaughn Vernon**

---

# 13. Testes

## Tipos

- Unit tests
- Integration tests
- End-to-end tests
- Contract tests
- Load tests
- Smoke tests

## Conceitos

- Test doubles
- Mock
- Stub
- Fake
- Spy
- Fixtures
- Test isolation
- Testability
- Test pyramid

## Estratégia

- O que testar?
- Onde testar?
- Como evitar testes frágeis?
- Como testar integrações?
- Como testar contratos?

### Referências

- **Test-Driven Development — Kent Beck**
- **xUnit Test Patterns — Gerard Meszaros**

---

# 14. Segurança Web

> Segurança deve ser estudada continuamente e integrada ao desenvolvimento, não tratada como etapa final.

## 14.1 Fundamentos

- CIA Triad
- Authentication
- Authorization
- Least privilege
- Defense in depth
- Threat modeling
- Attack surface

## 14.2 Vulnerabilidades

- Broken Access Control
- Security Misconfiguration
- Injection
- SQL Injection
- XSS
- CSRF
- SSRF
- Path Traversal
- Command Injection
- File Upload vulnerabilities
- Insecure Deserialization
- Authentication failures

## 14.3 OWASP

- OWASP Top 10
- OWASP Web Security Testing Guide
- OWASP ASVS
- OWASP Cheat Sheet Series

### Referências principais

- **OWASP Top 10**

https://owasp.org/www-project-top-ten/

- **OWASP Web Security Testing Guide**

https://owasp.org/www-project-web-security-testing-guide/

- **PortSwigger Web Security Academy**

https://portswigger.net/web-security

---

# 15. Criptografia

- Hash
- Salt
- Encryption
- Symmetric encryption
- Asymmetric encryption
- Digital signatures
- HMAC
- Key exchange
- Certificates
- TLS
- Randomness

## Algoritmos/conceitos

- SHA-2/SHA-3
- AES
- RSA
- ECC
- Argon2
- bcrypt

### Referências

- **Serious Cryptography — Jean-Philippe Aumasson**
- **Cryptography Engineering — Ferguson, Schneier & Kohno**

---

# 16. Segurança de Infraestrutura

- Linux security
- File permissions
- SSH
- Firewall
- Network segmentation
- Secrets management
- Container security
- IAM
- Cloud security
- Dependency vulnerabilities
- Supply chain security

## DevSecOps

- SAST
- DAST
- Dependency scanning
- Container scanning
- Secret scanning
- Security CI/CD

---

# 17. Performance

## Backend

- Profiling
- CPU
- Memory
- Garbage Collection
- Async operations
- Connection pools
- Thread pools

## Banco

- Slow queries
- Indexes
- Query plans
- N+1
- Locks
- Connection pooling

## Web

- Latency
- Throughput
- Bandwidth
- Compression
- CDN
- Caching

## Load testing

- k6
- JMeter
- autocannon

---

# 18. Observabilidade

## Logs

- Structured logging
- Log levels
- Correlation IDs
- Request IDs

## Metrics

- Counters
- Gauges
- Histograms
- Percentiles
- p50
- p95
- p99

## Tracing

- Distributed tracing
- Span
- Trace
- Context propagation

## Ferramentas

- OpenTelemetry
- Prometheus
- Grafana

## SRE

- SLI
- SLO
- SLA
- Error budget
- Incident response
- Postmortems
- Capacity planning

### Referências principais

- **Site Reliability Engineering — Google**
- **The Site Reliability Workbook — Google**
- **Building Secure & Reliable Systems — Google**

https://sre.google/books/

---

# 19. DevOps e Infraestrutura

## Linux

- Processes
- Networking
- Filesystem
- Permissions
- Services
- Monitoring

## Docker

- Images
- Layers
- Containers
- Volumes
- Networks
- Docker Compose
- Multi-stage builds
- Container security

### Referência

https://docs.docker.com/

## CI/CD

- Build pipelines
- Automated tests
- Deployment
- Rollback
- Blue/green deployment
- Canary deployment

## Kubernetes

- Pod
- Deployment
- Service
- Ingress
- ConfigMap
- Secret
- StatefulSet
- Job
- CronJob
- Probes
- HPA
- Scheduling

### Referência

https://kubernetes.io/docs/

## Cloud

Escolher uma cloud principal e aprofundar.

### Recomendação

**AWS**

Estudar:

- EC2
- S3
- RDS
- VPC
- IAM
- CloudFront
- Load Balancer
- Lambda
- SQS
- SNS
- CloudWatch

### Referência

https://docs.aws.amazon.com/

---

# 20. Infrastructure as Code

- Terraform
- State
- Modules
- Variables
- Providers
- Resource dependencies
- Secrets
- Environment management

### Referência

https://developer.hashicorp.com/terraform/docs

---

# 21. System Design

## Projetos para estudar

- URL Shortener
- Chat
- E-commerce
- Payment system
- Notification system
- File storage
- Search system
- Social network
- Video streaming
- Ride sharing

## Processo de design

1. Requirements
2. Functional requirements
3. Non-functional requirements
4. Capacity planning
5. Architecture
6. Data model
7. Communication
8. Consistency
9. Scaling
10. Failure handling
11. Security
12. Observability
13. Cost

### Referências

- **Designing Data-Intensive Applications**
- **Designing Distributed Systems — Brendan Burns**
- **System Design Interview — Alex Xu**

---

# 22. Inteligência Artificial — Fundamentos

## 22.1 Conceitos gerais

- Artificial Intelligence
- Machine Learning
- Deep Learning
- Neural Networks
- Supervised learning
- Unsupervised learning
- Reinforcement learning

## 22.2 Matemática

- Álgebra linear
- Vetores
- Matrizes
- Probabilidade
- Estatística
- Derivadas
- Gradiente

### Referências

- **Stanford CS229 — Machine Learning**
- **Deep Learning — Goodfellow, Bengio & Courville**

https://cs229.stanford.edu/

https://www.deeplearningbook.org/

---

# 23. Neural Networks e Deep Learning

- Neuron
- Weights
- Bias
- Activation functions
- Forward propagation
- Backpropagation
- Gradient descent
- Loss functions
- Optimization
- Regularization
- Overfitting
- Underfitting
- CNNs
- RNNs
- Transformers

---

# 24. Transformers e LLMs

## Conceitos

- Tokenization
- Tokens
- Embeddings
- Attention
- Self-attention
- Multi-head attention
- Positional encoding
- Transformer blocks
- Context window

## Treinamento

- Pre-training
- Fine-tuning
- Instruction tuning
- RLHF
- Preference optimization

## Inferência

- Temperature
- Sampling
- Context management
- Latency
- Token usage
- Model selection

## Limitações

- Hallucination
- Context limitations
- Bias
- Model uncertainty
- Knowledge cutoff
- Prompt injection

### Referências

- **Attention Is All You Need**

https://arxiv.org/abs/1706.03762

- **The Illustrated Transformer — Jay Alammar**

https://jalammar.github.io/illustrated-transformer/

---

# 25. AI Engineering / LLM Applications

## Prompting

- Clear instructions
- Context
- Constraints
- Few-shot
- Zero-shot
- Structured output
- Examples
- Evaluation

## RAG

- Embeddings
- Vector databases
- Chunking
- Retrieval
- Reranking
- Semantic search
- Grounding

## Tool use

- Function calling
- Tool schemas
- Agents
- MCP
- External APIs

## Avaliação

- Accuracy
- Precision/recall
- Groundedness
- Hallucination rate
- Evaluation datasets
- Regression tests

### Referências

- **OpenAI Developer Documentation**

https://platform.openai.com/docs/

- **OpenAI Cookbook**

https://cookbook.openai.com/

---

# 26. IA aplicada ao desenvolvimento de software

## Usar IA para

- Code generation
- Refactoring
- Debugging
- Testing
- Documentation
- Migration
- Code review
- Architecture analysis
- Requirements analysis
- Security analysis
- Learning

## Fluxo recomendado

```text
Problem
   ↓
Context
   ↓
Instructions
   ↓
Constraints
   ↓
Tools
   ↓
AI output
   ↓
Verification
   ↓
Testing
   ↓
Human decision
```

## Competência essencial

Não basta saber obter respostas da IA.

É necessário saber:

- verificar respostas;
- detectar alucinações;
- fornecer contexto suficiente;
- identificar código incorreto;
- questionar decisões arquiteturais;
- validar segurança;
- validar performance;
- comparar alternativas.

---

# 27. Engenharia de Requisitos

- Requirements gathering
- Functional requirements
- Non-functional requirements
- Constraints
- Acceptance criteria
- User stories
- Edge cases
- Domain modeling
- Stakeholder communication

## Objetivo

Transformar:

> "Precisamos de um sistema para controlar obras."

em:

- requisitos;
- regras de negócio;
- entidades;
- casos de uso;
- restrições;
- critérios de aceitação;
- requisitos não funcionais.

---

# 28. Design de APIs

- API contracts
- OpenAPI
- Versioning
- Backward compatibility
- Pagination
- Idempotency
- Error models
- Rate limiting
- Authentication
- Authorization
- Webhooks
- Event APIs

---

# 29. Git e GitHub

## Git

- Git internals
- Branching
- Merge
- Rebase
- Cherry-pick
- Bisect
- Reflog
- Hooks
- Conflict resolution

## GitHub

- Pull Requests
- Code review
- Branch protection
- CODEOWNERS
- GitHub Actions
- Releases
- CI/CD

---

# 30. Comunicação e Liderança Técnica

## Comunicação

- Escrita técnica
- Documentação
- RFCs
- ADRs
- Architecture diagrams

## Code Review

- Bugs
- Arquitetura
- Segurança
- Performance
- Manutenibilidade

## Liderança

- Mentoring
- Delegation
- Technical decision making
- Conflict resolution
- Estimation
- Prioritization

## Senioridade

Ser capaz de responder:

> "Qual é a solução tecnicamente correta?"

e também:

> "Qual é a solução correta dadas as restrições do negócio?"

---

# 31. Conhecimento de Negócio

- Custo
- Receita
- Usuários
- Métricas
- ROI
- Risco
- SLA
- Compliance
- Prioridades do negócio
- Technical debt

## Objetivo

Entender que uma solução tecnicamente sofisticada nem sempre é a solução correta para o negócio.

---

# 32. Matemática e Estatística

- Lógica
- Álgebra
- Probabilidade
- Estatística
- Média
- Mediana
- Variância
- Distribuições
- Percentis
- Correlação
- Regressão básica

### Aplicações

- Performance
- Observabilidade
- Capacity planning
- Machine Learning
- Análise de dados

---

# 33. Arquitetura de Computadores

- CPU
- RAM
- Registers
- CPU cache
- L1/L2/L3
- Memory hierarchy
- Instruction execution
- Storage
- SSD/HDD
- I/O
- GPU

## Objetivo

Entender por que determinados programas são rápidos ou lentos.

---

# Biblioteca-base recomendada

Se fosse necessário reduzir toda esta trilha a uma biblioteca central:

## Fundamentos

1. **Introduction to Algorithms — CLRS**
2. **Operating Systems: Three Easy Pieces**
3. **Computer Networking: A Top-Down Approach**

## Backend e dados

4. **Database Internals — Alex Petrov**
5. **Designing Data-Intensive Applications — Martin Kleppmann & Chris Riccomini**

## Engenharia

6. **Refactoring — Martin Fowler**
7. **Clean Architecture — Robert C. Martin**
8. **Patterns of Enterprise Application Architecture — Martin Fowler**

## Domínio

9. **Domain-Driven Design — Eric Evans**
10. **Implementing Domain-Driven Design — Vaughn Vernon**
11. **Domain-Driven Design Distilled — Vaughn Vernon**

## Segurança

12. **Serious Cryptography — Jean-Philippe Aumasson**
13. **Cryptography Engineering — Ferguson, Schneier & Kohno**
14. **OWASP Top 10**
15. **OWASP Web Security Testing Guide**
16. **PortSwigger Web Security Academy**

## SRE

17. **Site Reliability Engineering — Google**
18. **The Site Reliability Workbook — Google**
19. **Building Secure & Reliable Systems — Google**

## IA

20. **Deep Learning — Goodfellow, Bengio & Courville**
21. **Attention Is All You Need**
22. **Stanford CS229**
23. **The Illustrated Transformer**

---

# Fontes oficiais para tecnologias

| Tecnologia | Fonte |
|---|---|
| TypeScript | https://www.typescriptlang.org/docs/ |
| Node.js | https://nodejs.org/docs/ |
| PostgreSQL | https://www.postgresql.org/docs/ |
| MongoDB | https://www.mongodb.com/docs/ |
| Redis | https://redis.io/docs/ |
| Docker | https://docs.docker.com/ |
| Kubernetes | https://kubernetes.io/docs/ |
| AWS | https://docs.aws.amazon.com/ |
| Terraform | https://developer.hashicorp.com/terraform/docs |
| OpenTelemetry | https://opentelemetry.io/docs/ |
| Prometheus | https://prometheus.io/docs/ |
| Git | https://git-scm.com/doc |
| GitHub | https://docs.github.com/ |
| HTTP/Web | https://developer.mozilla.org/ |
| RFCs | https://www.rfc-editor.org/ |
| OWASP | https://owasp.org/ |
| OpenAI | https://platform.openai.com/docs/ |

---

# Regra geral de estudo

Para qualquer assunto novo:

```text
                    CONCEITO
                       ↓
                FUNDAMENTOS
                       ↓
                    TEORIA
                       ↓
                IMPLEMENTAÇÃO
                       ↓
                 EXPERIMENTAÇÃO
                       ↓
                  PROBLEMAS
                       ↓
                  DIAGNÓSTICO
                       ↓
                  TRADE-OFFS
                       ↓
               APLICAÇÃO REAL
```

O objetivo final não é:

> "Eu estudei PostgreSQL."

É:

> "Eu entendo como PostgreSQL funciona, consigo modelar dados, escrever queries eficientes, analisar planos de execução, diagnosticar problemas de concorrência e decidir quando PostgreSQL é ou não a escolha adequada."

---

# Princípio final

**Tecnologias mudam. Fundamentos permanecem.**

Priorizar:

1. Fundamentos de computação
2. Redes
3. Sistemas operacionais
4. Banco de dados
5. Engenharia de software
6. Backend
7. Sistemas distribuídos
8. Segurança
9. Infraestrutura
10. Observabilidade
11. System Design
12. IA
13. Negócio e liderança técnica

E usar documentação oficial, livros, RFCs, padrões, papers e prática como fontes complementares — em vez de depender de um único curso ou criador de conteúdo.
