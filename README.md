# Guia de Estudos: Engenharia de Software e IA para Desenvolvedores Backend

---

## Premissa

Este guia não tem tempo como fator. O objetivo é ser uma referência que você consulta, revisita e aprofunda conforme vai acumulando experiência real. Leitura sem projeto concreto ao lado não entra de verdade — cada bloco deste guia tem mais valor quando você está sentindo a dor que ele resolve.

A progressão sugerida existe, mas não é linear. Você pode entrar por onde a dor aparecer primeiro.

---

## Parte 1 — Fundamentos que sempre importam

### O modelo mental do Next (server, client e as fronteiras entre eles)

Next.js não é "React com rotas". É um framework que decide, por você, onde cada pedaço de código roda: no build, no servidor a cada request, ou no navegador. Se você não internaliza essa decisão de fronteira, cada bug vira mistério. Se você internaliza, a maioria dos bugs fica óbvia antes de abrir o debugger.

### As três perguntas que todo arquivo/componente responde:

- Onde esse código executa? — build time, request time (servidor), ou runtime do navegador?
- Quando ele executa? — uma vez por deploy, uma vez por request, ou toda vez que o usuário interage?
- O que ele pode acessar? — segredos de ambiente e banco de dados (servidor) ou window, localStorage, eventos de clique (cliente)?

Toda confusão clássica de Next — "por que minha env var é undefined", "por que esse hook não funciona aqui", "por que a página não atualiza" — é uma dessas três perguntas respondida errado.

**React Server Components (RSC):** o divisor real
**Server Components (default no App Router):** renderizam no servidor, não vão para o bundle do cliente, podem await direto num banco de dados, não têm estado nem useEffect.
**Client Components ("use client"):** hidratam no navegador, viram JavaScript que o usuário baixa, têm estado, eventos, hooks.
A árvore é mista por padrão: Server Components podem renderizar Client Components (passando props serializáveis), mas Client Components não podem importar Server Components diretamente — só recebê-los como children/slot.
O que isso resolve: reduzir o JS enviado ao cliente e permitir acesso direto a dados no servidor sem uma camada de API intermediária.
O que isso custa: um modelo mental novo de "fronteira serializável" — tudo que atravessa de Server para Client Component precisa ser serializável (sem funções, sem classes com métodos, sem Date sem cuidado).

Exercício concreto: pegue uma página sua e desenhe, à mão, onde fica a fronteira "use client". Se você não consegue desenhar isso rápido, você não sabe o que está rodando onde.

Renderização: os quatro modelos e o que cada um resolve
SSR (Server-Side Rendering): HTML gerado a cada request no servidor. Resolve conteúdo que muda por usuário/sessão sem sacrificar SEO/first paint.
SSG (Static Site Generation): HTML gerado no build, servido como arquivo estático. Resolve conteúdo que não muda entre requests — o mais rápido e barato possível.
ISR (Incremental Static Regeneration): estático, mas com revalidação em background após um tempo (revalidate) ou sob demanda (revalidateTag/revalidatePath). Resolve o meio-termo: conteúdo que muda, mas não a cada request.
Streaming SSR: o servidor manda o HTML em pedaços conforme fica pronto (via Suspense), em vez de esperar tudo. Resolve TTFB alto quando uma parte da página depende de dado lento.

A pergunta certa não é "qual é melhor" — é "qual é o padrão de mudança dos dados desta página". Dado que nunca muda → SSG. Dado por usuário → SSR. Dado que muda, mas tolera atraso → ISR. Página com uma seção rápida e uma lenta → Streaming + Suspense.

### Cache: a parte que mais gera bug em produção

Next tem múltiplas camadas de cache simultâneas, cada uma com regra própria de invalidação. A maioria dos "bugs estranhos de produção que não reproduzem local" é uma dessas camadas fazendo exatamente o que foi projetada pra fazer, mas não o que você esperava.

As camadas (entenda cada uma separadamente):

Request memoization: dentro de um único request de servidor, chamadas fetch idênticas são deduplicadas automaticamente. Vive e morre com o request — não persiste entre requests diferentes.
Data Cache: cache persistente de resultados de fetch no servidor, entre requests e até entre deploys, até ser invalidado ou expirar. Controlado por cache/next.revalidate na chamada de fetch.
Full Route Cache: cache do HTML/RSC payload de rotas estáticas, gerado no build ou revalidado via ISR.
Router Cache (client-side): cache no navegador de payloads de RSC já visitados, pra navegação instantânea entre páginas. Some quando a aba recarrega ou após tempo configurado.

A dor que isso resolve: navegação instantânea e menos chamadas redundantes ao backend. O custo: você precisa saber exatamente qual camada invalidar quando um dado muda — invalidar a errada (ou nenhuma) é a causa raiz mais comum de "dado velho aparecendo pra usuário depois que ele salvou algo".

Padrão prático: para cada Server Action ou mutação que você escreve, pergunte explicitamente "qual cache isso precisa invalidar, e com revalidatePath ou revalidateTag?". Se a resposta for "nenhum", verifique se está certo — normalmente não está.

Referência principal: documentação oficial do Next sobre caching (nextjs.org/docs). Esta é uma área que muda de comportamento entre versões majors — trate como referência viva, não como conhecimento fixo que você aprende uma vez.

### Runtime: Node vs. Edge, e Middleware
Node runtime: ambiente completo, acesso a todas as APIs Node, cold start mais lento, roda nas regiões configuradas do seu provedor.
Edge runtime: subconjunto de Web APIs (sem APIs nativas do Node), inicia quase instantaneamente, roda geograficamente perto do usuário.
Trade-off real: Edge é ótimo pra lógica leve e latência-sensível (auth check, redirect, feature flag). Não serve pra tudo que precisa de bibliotecas Node completas (drivers de banco tradicionais, processamento pesado).
Middleware roda antes da rota ser resolvida, sempre em Edge runtime, em toda request que casa com o matcher — inclusive assets, se você não filtrar bem. É bom pra redirecionamento e checagem leve de sessão; ruim pra lógica de negócio pesada ou acesso direto a banco relacional tradicional.
Hidratação e o modelo de concorrência do lado cliente

Hidratação é o processo de o React "religar" os event listeners no HTML que já veio do servidor. Erros de hidratação acontecem quando o HTML gerado no servidor difere do que o cliente renderiza na primeira passada — datas com timezone diferente, Math.random(), checagem malfeita de typeof window. Regra prática: nada não-determinístico pode influenciar a primeira renderização sem tratamento explícito (ex: aplicar só depois de montar no cliente).

No servidor, o modelo de concorrência do Node é single-threaded com event loop: I/O é não-bloqueante, mas processamento pesado de CPU dentro de uma Server Action ou Route Handler bloqueia o loop inteiro e afeta todas as outras requisições sendo servidas ao mesmo tempo. Entender isso é o equivalente, no mundo Next, a entender threads e memória compartilhada em runtimes multi-thread — é o que evita que você trave o servidor sem perceber.

Referências principais: documentação oficial em nextjs.org/docs e react.dev — tratem como fonte primária, dado o ritmo de mudança do framework. Learning React (Banks & Porcello) para fundamentos de React sem acoplar a versões específicas de API.

---

## Parte 2 — Design de Código

### Princípios Fundamentais

Estes princípios existem para gerir uma coisa só: **complexidade**. Cada um é uma heurística, não uma lei. Entenda o motivo antes de aplicar.

**SOLID**
- Single Responsibility: uma classe tem um motivo pra mudar
- Open/Closed: aberto para extensão, fechado para modificação
- Liskov Substitution: subtipos devem ser substituíveis pelo tipo base sem quebrar o programa
- Interface Segregation: interfaces pequenas e específicas
- Dependency Inversion: dependa de abstrações, não de implementações concretas

**Outros princípios essenciais**
- DRY (Don't Repeat Yourself) — e quando repetição deliberada é melhor que abstração prematura
- YAGNI (You Aren't Gonna Need It) — não construa o que não existe como necessidade hoje
- KISS (Keep It Simple, Stupid) — complexidade é o inimigo padrão
- Lei de Deméter — um objeto só fala com seus vizinhos diretos
- Composição sobre herança — herança cria acoplamento profundo

### Gestão de Complexidade

O conceito central aqui é o de **módulo profundo** (deep module): um módulo ideal tem interface pequena e implementação grande. A complexidade fica escondida atrás de uma abstração simples. O oposto — módulo raso — expõe complexidade sem entregar valor equivalente.

Conceitos do *A Philosophy of Software Design*:
- Complexidade acidental vs. complexidade essencial
- Módulos profundos vs. módulos rasos
- Complexidade que vaza (information leakage)
- Abstrações que mentem
- Comentários que explicam o "porquê", não o "o quê"

**Referência principal:** *A Philosophy of Software Design* (Ousterhout) — o livro mais denso e honesto sobre design de código que existe. Leitura obrigatória.

### Design Patterns

Não decorar o catálogo — entender os problemas que cada família resolve.

**Criacionais:** como instanciar objetos sem acoplar ao tipo concreto (Factory, Builder, Singleton — e por que Singleton é problema)

**Estruturais:** como compor objetos e classes (Adapter, Decorator, Proxy, Facade, Composite)

**Comportamentais:** como objetos colaboram e distribuem responsabilidade (Strategy, Observer, Command, Template Method, Chain of Responsibility)

O ponto real: patterns são vocabulário. Quando você fala "isso é um Strategy", o time inteiro entende a estrutura sem precisar de mais explicação.

**Referência principal:** *Design Patterns* (GoF — Gang of Four) — referência canônica, linguagem densa. *Head First Design Patterns* — mais acessível para começar. *Refactoring to Patterns* (Kerievsky) — mostra como patterns emergem de refactoring, não de design upfront.

### Refactoring

- Code smells: o que eles são, o que indicam (não são problemas — são sintomas)
- Técnicas de refactoring: extract method, extract class, move method, replace conditional with polymorphism
- Refactoring seguro: apoiado em testes, em passos pequenos
- Quando refatorar vs. quando reescrever

**Referência principal:** *Refactoring* (Fowler) — o livro. O catálogo em refactoring.com como referência rápida.

---

## Parte 3 — Modelagem de Domínio

### Por que modelagem vem antes de arquitetura

Você não sabe que estrutura arquitetural proteger se não sabe o que é o domínio. Arquitetura existe a serviço do domínio — não ao contrário. Estudar estilos antes de saber modelar domínio é decorar respostas sem ter a pergunta.

### DDD Estratégico

Esta é a metade mais importante e menos estudada. Não requer framework, pattern tático, nem livro para começar — requer conversa com quem conhece o negócio.

**Conceitos centrais:**

*Linguagem Ubíqua (Ubiquitous Language)*
A linguagem do negócio usada literalmente no código. Não "User" quando o cliente chama de "Paciente". Não "process()" quando a operação tem um nome preciso no domínio. A linguagem ubíqua é o elo entre desenvolvedor e especialista de domínio — quando os dois usam as mesmas palavras para as mesmas coisas, os bugs semânticos somem.

*Bounded Context (Contexto Delimitado)*
Um limite dentro do qual um modelo de domínio tem significado coerente. O mesmo conceito pode ter representações diferentes em contextos diferentes — "Cliente" no contexto de Vendas tem atributos distintos de "Cliente" no contexto de Suporte. Forçar um único modelo para tudo cria a "big ball of mud".

*Context Map*
O mapa das relações entre bounded contexts. Como eles se comunicam, quem lidera a integração, quais são os padrões de relacionamento (Partnership, Customer-Supplier, Conformist, Anti-Corruption Layer, Open Host Service, Published Language).

*Subdomínios*
- Core domain: onde o negócio se diferencia; aqui vai o esforço de modelagem mais rico
- Supporting subdomain: necessário mas não diferenciador; pode ser construído com menos sofisticação
- Generic subdomain: resolve problema genérico; comprar ou usar open source é melhor que construir

**Exercício concreto:** Para qualquer projeto real, escreva o glossário dos termos do cliente nas palavras *dele*, depois mapeie quais termos têm sentido diferente em contextos diferentes. Você está fazendo modelagem estratégica.

**Event Storming**
Técnica de workshop para descobrir o domínio colaborativamente com especialistas. Usa post-its de eventos de domínio (algo que aconteceu, passado), comandos (o que dispara o evento), e atores (quem dispara o comando). Não requer código. Produz um mapa do fluxo do negócio em horas.

### DDD Tático

*Atenção: aplique esses padrões quando sentir a dor que eles resolvem, não antes.*

**Entidade:** objeto com identidade própria que persiste ao longo do tempo. Dois objetos com os mesmos atributos não são o mesmo se têm IDs diferentes.

**Value Object:** objeto definido apenas por seus atributos, sem identidade própria. Imutável. "Dinheiro" é um value object — R$ 10,00 é sempre R$ 10,00, independente de qual instância.

**Agregado:** cluster de entidades e value objects tratado como unidade de consistência. Toda mudança que precisa ser atômica mora dentro de um agregado. A raiz do agregado é a única entrada — nada de fora referencia entidades internas diretamente.

**Repositório:** abstração que simula uma coleção em memória para recuperar e persistir agregados. O domínio não sabe que existe banco de dados.

**Serviço de Domínio:** operação de domínio que não pertence naturalmente a nenhuma entidade ou value object. Não é service do Spring — é um conceito do domínio que envolve múltiplos objetos.

**Evento de Domínio:** algo significativo que aconteceu no domínio, no passado. "PedidoConfirmado", "PagamentoRecusado". Usados para comunicação entre bounded contexts e para registrar histórico.

**Factory:** responsabilidade de criar objetos complexos ou agregados, quando a criação envolve lógica de negócio.

### Referências de Modelagem

- *Learning Domain-Driven Design* (Vlad Khononov) — a melhor porta de entrada moderna. Cobre estratégico e tático com exemplos práticos e linguagem acessível
- *Implementing Domain-Driven Design* (Vaughn Vernon) — referência funda para o DDD tático com exemplos completos
- *Domain-Driven Design* (Eric Evans) — o original canônico. Linguagem densa, melhor como referência do que como introdução
- *Domain Modeling Made Functional* (Scott Wlaschin) — perspectiva funcional que ilumina conceitos mesmo para quem não usa F#

---

## Parte 4 — Arquitetura de Software

### O que arquitetura é (e o que não é)

Arquitetura são as decisões caras de reverter depois. Onde ficam as fronteiras entre componentes, quem depende de quem, o que fica junto e o que fica separado, como as partes conversam.

Arquitetura não é sobre qual framework usar. Framework é detalhe — a arquitetura é o que você *protege* do framework.

Toda decisão arquitetural é um trade-off. Quem promete "a melhor arquitetura" sem perguntar o contexto está vendendo moda.

### Qualidades de Software (as "-ilidades")

A arquitetura não existe por elegância — existe para favorecer certas qualidades. Você escolhe uma estrutura porque ela serve as qualidades que o seu contexto exige, pagando o custo nas qualidades que você sacrifica.

- **Manutenibilidade:** custo de entender e modificar o sistema ao longo do tempo
- **Testabilidade:** facilidade de testar componentes isolados sem dependências concretas
- **Escalabilidade:** capacidade de crescer sob carga (vertical e horizontal)
- **Disponibilidade:** quanto tempo o sistema funciona; como ele se comporta sob falha
- **Desempenho:** latência e throughput; onde estão os gargalos
- **Observabilidade:** capacidade de entender o estado interno do sistema a partir de sua saída
- **Segurança:** proteção contra acesso indevido e manipulação
- **Implantabilidade:** facilidade e risco de colocar o sistema em produção
- **Elasticidade:** capacidade de escalar e desescalar dinamicamente
- **Interoperabilidade:** facilidade de integrar com outros sistemas

Toda decisão arquitetural melhora algumas dessas qualidades e piora outras. Documentar esses trade-offs é parte do trabalho de quem toma as decisões.

### Lei de Conway

"As organizações que projetam sistemas são constrangidas a produzir sistemas que são cópias das estruturas de comunicação dessas organizações."

Na prática: se dois times não conversam bem, o sistema deles vai ter uma fronteira mal definida naquele ponto. Antes de redesenhar a arquitetura, pergunte se você precisa redesenhar a comunicação do time.

### Arquitetura Interna

**Layered (em Camadas)**
Controller → Service → Repository. O default da maioria das aplicações Spring. Simples, conhecido por todos.
Problema central: o domínio tende a vazar para a infraestrutura com o tempo. A lógica de negócio acaba espalhada em services anêmicos, e o banco de dados vira o modelo de domínio de fato.

**Hexagonal (Ports & Adapters)**
O domínio fica no centro e não conhece o mundo externo. O banco, a API REST, a fila, a UI viram "adaptadores" que implementam "portas" definidas pelo domínio. A regra de ouro é única: *as dependências apontam para dentro*. O domínio não importa nada de fora.

Benefício principal: você testa a regra de negócio sem subir banco, sem servidor, sem nada de infraestrutura. Isso é arquitetura a serviço de testabilidade.

Custo: mais cerimônia. Para um CRUD simples, é overkill. Vale quando o domínio é rico — quando as regras de negócio existem de verdade.

**Clean Architecture e Onion Architecture**
São essencialmente a mesma ideia que a hexagonal com nomes diferentes e algumas distinções de camada. O princípio central é o mesmo: dependências apontam para o centro (o domínio).

**Referência:** *Clean Architecture* (Robert Martin) — ler com olhar crítico. Ele é dogmático. Parte do aprendizado é discordar com fundamentação depois de entender o porquê das regras.

### Arquitetura de Sistema

**Monólito**
Uma aplicação única, um deploy só. Ainda é a escolha certa para a maioria dos projetos e para todos os projetos nascentes. O mercado redescobriu isso depois de anos de hype contrário. Não é arquitetura ruim — é a arquitetura mais simples que funciona para um determinado tamanho.

**Monólito Modular**
Um deploy único, mas internamente dividido em módulos com fronteiras bem definidas e acoplamento explicitamente controlado. Você ganha a clareza de fronteiras (e a capacidade de testar módulos isolados) sem o custo operacional de distribuir. É o consenso pragmático que emergiu depois da ressaca de microsserviços. Para a maioria dos projetos de pequenas empresas e startups, é o ponto de chegada ideal — não o ponto de partida para microsserviços.

**Microsserviços**
Serviços independentes, cada um com seu próprio deploy e (idealmente) seu próprio banco. Resolve um problema real: autonomia de times grandes, escala independente de componentes com cargas distintas.

Custo real: rede (latência, falha de rede vira falha de negócio), observabilidade (você agora precisa rastrear uma requisição por N serviços), consistência distribuída (não existe transação global), complexidade de deploy. Adotar sem ter o problema que resolve é o erro mais caro da última década em arquitetura.

Regra de bolso: comece monólito modular. Extraia um serviço quando sentir uma dor concreta e específica que justifique o custo — não antes.

**Event-Driven Architecture**
Componentes se comunicam por mensagens ou eventos em vez de chamadas diretas síncronas. Aparece combinada com os outros estilos. Brilha quando você precisa desacoplar no tempo (processar depois, reagir a algo que aconteceu), garantir resiliência (o produtor não precisa esperar o consumidor), ou integrar sistemas heterogêneos.

Custo: rastrear o fluxo de execução fica mais difícil; consistência eventual exige lidar com estados intermediários; ordering e entrega garantida adicionam complexidade.

### Referências de Arquitetura

- *Fundamentals of Software Architecture* (Mark Richards & Neal Ford) — o melhor panorama do mercado. Catálogo de estilos, vocabulário de trade-offs, como pensar em decisões arquiteturais
- *Get Your Hands Dirty on Clean Architecture* (Tom Hombergs) — implementa hexagonal em Java/Spring passo a passo. A ponte mais direta entre conceito e código real
- *Software Architecture: The Hard Parts* (Ford, Richards, Sadalage, Dehghani) — para quando você estiver encarando decisões de sistema distribuído de verdade. Não antes
- *Building Microservices* (Sam Newman) — a referência sobre microsserviços. Leia depois de ter sentido a dor que eles resolvem

---

## Parte 5 — Sistemas Distribuídos

### Por que importa para backend

Quando o seu sistema tem mais de um processo, ou usa banco de dados, ou usa fila, ou chama uma API externa — ele já é distribuído. Os problemas desta seção não são teóricos.

### Conceitos Fundamentais

**Consistência, Disponibilidade, Tolerância a Partição (CAP)**
O teorema CAP diz que um sistema distribuído pode garantir no máximo dois dos três ao mesmo tempo. Na prática, partições de rede acontecem — então a escolha real é entre consistência e disponibilidade quando a rede falha.

**Níveis de Consistência**
- Strong consistency: toda leitura vê a escrita mais recente
- Eventual consistency: as réplicas convergem, mas pode haver janela de divergência
- Read-your-writes, monotonic reads, causal consistency — níveis intermediários com garantias específicas

**Replicação**
Como os dados chegam em múltiplas máquinas. Síncrona (mais lenta, mais segura) vs. assíncrona (mais rápida, risco de perda). Leader-follower, multi-leader, leaderless.

**Particionamento (Sharding)**
Como dividir dados entre nós. Por range, por hash, por diretório. Trade-offs de hotspots, rebalanceamento, queries cruzadas.

### Padrões de Integração

**Idempotência**
Uma operação é idempotente se executá-la múltiplas vezes produz o mesmo resultado que executar uma vez. É o requisito mínimo para qualquer sistema que pode retentar operações. Se o seu endpoint de pagamento não é idempotente, você vai cobrar o cliente duas vezes quando a rede falhar no momento errado.

**Outbox Pattern**
Garantia de que um evento é publicado se e somente se a transação de banco for efetivada. Escreve o evento numa tabela "outbox" na mesma transação, e um processo separado publica para a fila. Resolve o problema clássico de "salvar no banco E publicar na fila" sem transação distribuída.

**Saga**
Padrão para manter consistência em transações que cruzam múltiplos serviços sem transação global. Coreografada (cada serviço escuta eventos e age) ou orquestrada (um orchestrator central coordena os passos e as compensações em caso de falha).

**Circuit Breaker**
Interrompe chamadas a um serviço que está falhando, em vez de deixar a falha se propagar e consumir threads. Três estados: fechado (normal), aberto (rejeita chamadas), meio-aberto (testa recuperação).

**Bulkhead**
Isola recursos por funcionalidade para que a falha de uma não esgote recursos das outras. Análogo às divisórias de um navio.

### Mensageria e Filas

- Diferença entre filas (ponto a ponto) e tópicos (pub/sub)
- Garantias de entrega: at-most-once, at-least-once, exactly-once
- Ordering: global, por partição, sem garantia
- Consumer groups, offsets, replay
- Dead letter queues

**Referência principal:** *Designing Data-Intensive Applications* (Martin Kleppmann) — o livro. Cobre replicação, particionamento, transações, streams e consistência com profundidade e honestidade rara. Leitura de carreira, não de sprint.

---

## Parte 6 — Qualidade e Testes

### Testes como design

Testes não são apenas verificação — são feedback de design. Código difícil de testar é código com problemas de design. A dificuldade de escrever o teste é o sinal; o design acoplado é o problema.

**Pirâmide de Testes**
- Unitários (base): testam uma unidade isolada, rápidos, muitos
- Integração (meio): testam a interação entre componentes reais (ex: repositório com banco)
- End-to-end (topo): testam o sistema completo, lentos, poucos

A forma de pirâmide importa: muitos unitários baratos na base, poucos E2E caros no topo. Inversão da pirâmide (muitos E2E, poucos unitários) é sinal de problema de design — ninguém confia nos unitários porque o domínio está acoplado à infraestrutura.

**Test-Driven Development (TDD)**
Escrever o teste antes do código. O ciclo: Red (escreve teste que falha) → Green (escreve o mínimo para passar) → Refactor (melhora sem quebrar).

O benefício real não é cobertura — é design. Quando você escreve o teste primeiro, você pensa na interface do código antes da implementação. Você descobre acoplamentos desnecessários antes de construí-los.

**Testando o domínio isolado**
Com arquitetura hexagonal, os testes de regra de negócio não precisam de Spring, banco, ou qualquer infraestrutura. São testes unitários puros, milissegundos de execução, zero dependência externa. Esse é o dividendo arquitetural mais concreto da hexagonal.

**Testes de contrato**
Verificam que um produtor (API) honra o contrato que seus consumidores esperam. Consumer-Driven Contract Testing com Pact é o padrão em microsserviços. Substitui (com vantagens) uma parte dos testes de integração end-to-end.

**Referências de testes:**
- *Test-Driven Development: By Example* (Kent Beck) — o fundador do TDD explica o método
- *Unit Testing: Principles, Practices and Patterns* (Vladimir Khorikov) — o melhor livro moderno sobre testes unitários bem feitos

---

## Parte 7 — Estudar e Usar IA

### Entender como LLMs funcionam

Você não precisa implementar um transformer para usar bem a IA — mas precisa ter uma intuição de como ela funciona para entender seus limites.

**Conceitos fundamentais:**

*Tokens*
LLMs não processam palavras — processam tokens (fragmentos de texto, aproximadamente 3-4 caracteres em média para texto em inglês, menos eficiente para português). Isso importa porque o limite de contexto é em tokens, não em palavras, e porque o modelo "pensa" em unidades que não são as suas.

*Janela de contexto*
Tudo que o modelo pode considerar ao gerar uma resposta. É finita. Em conversas longas, o início começa a sair da janela. Isso explica por que o modelo "esquece" instruções dadas no começo de uma conversa muito longa.

*Temperature e sampling*
Temperature controla o quão "criativo" (ou randômico) o modelo é ao escolher o próximo token. Temperature 0 é quase determinístico; temperature alta produz variação maior. Para código, temperature baixa tende a ser melhor.

*Por que o modelo alucina*
O modelo não "sabe" coisas — ele prediz tokens plausíveis dado o contexto. Quando não tem base no treinamento, ele prediz tokens que *parecem* plausíveis mas são inventados. Isso não é bug — é a natureza do mecanismo. Você precisa verificar o que ele produz.

*Por que o modelo é bom em código*
Código é um dos formatos mais estruturados e abundantes nos dados de treinamento. Padrões se repetem. A IA interpola muito bem dentro de padrões conhecidos — e falha nos casos fora da distribuição (código que combina restrições incomuns, APIs obscuras, lógica de domínio específica).

**Para ir mais fundo:**
- Artigo "Attention Is All You Need" (Vaswani et al.) — o paper original do transformer
- Série de vídeos *Neural Networks: Zero to Hero* (Andrej Karpathy) — constrói um GPT do zero com explicação detalhada
- *Understanding Deep Learning* (Prince) — livro gratuito, cobre os fundamentos matematicamente acessíveis

### Prompt Engineering

Não é mágica — é especificação. As mesmas habilidades de escrever requisitos claros se transferem diretamente.

**Princípios que funcionam:**

*Seja específico sobre o contexto.* "Escreva um service em Java com Spring Boot que valida se um CPF é único, usando repositório JPA, lançando exceção de domínio customizada, sem lógica de negócio no controller" produz resultado muito melhor que "escreva um service Java que valida CPF".

*Use exemplos positivos e negativos.* Mostrar o que você quer E o que você não quer elimina ambiguidade de forma eficiente.

*Peça raciocínio antes da resposta.* "Pense passo a passo antes de responder" melhora resultados em perguntas que exigem raciocínio encadeado. O modelo produz tokens de raciocínio que influenciam os tokens da resposta.

*Estruture com XML ou delimitadores claros.* `<contexto>`, `<requisitos>`, `<restrições>` tornam a entrada mais parseável pelo modelo.

*Especifique o formato da saída.* "Responda apenas com o código, sem explicação, sem markdown" elimina ruído quando você só quer o código.

*System prompt vs. user prompt.* Em APIs, o system prompt define o papel e as restrições permanentes. O user prompt é o input. Separar as responsabilidades produz resultados mais consistentes.

**Técnicas avançadas:**

- Chain-of-thought: pedir ao modelo para raciocinar em voz alta antes de concluir
- Few-shot: dar exemplos de input/output antes da pergunta real
- Role prompting: "Você é um arquiteto de software sênior revisando este código..."
- Self-consistency: gerar múltiplas respostas e comparar (útil para decisões importantes)

**Referência:** Documentação de prompt engineering da Anthropic em docs.claude.ai — direto da fonte, atualizado.

### Padrões de Uso Responsável

A diferença entre quem fica mais forte e quem fica dependente não está em *usar ou não* IA — está na *ordem* das operações.

**Padrão: Decidir antes de delegar**
Tome a decisão de design *antes* de abrir a IA. Qual é a estrutura? Quais são as fronteiras? Qual abordagem? *Depois* use a IA para implementar ou criticar o que você já decidiu. Inverter essa ordem (perguntar pra IA o que fazer antes de pensar) é o que atrofia o julgamento.

**Padrão: Verificação crítica**
Nunca aceite código gerado sem ler. Especificamente: verifique complexidade, verifique casos de borda, verifique o que o código *não* faz, verifique se a API usada realmente existe (LLMs alucinam APIs com frequência).

**Padrão: Especificação iterativa**
Comece com uma especificação escrita do que você quer construir — não em linguagem vaga, mas com os edge cases, as restrições, os contratos de entrada e saída. Use a IA para refinar a especificação antes de gerar código. Você aprende mais e produz melhor.

**Padrão: Usar IA para revisar, não só para gerar**
"Revise este código e aponte problemas de design, possíveis bugs, e o que não está coberto pelos testes" é um uso poderoso que muita gente ignora. A IA como par de revisão.

**Padrão: Manter fundamentos ativos**
Reserve tempo para resolver problemas sem IA — por hábito, não por punição. O objetivo não é não usar a ferramenta; é manter o músculo que te deixa capaz de julgá-la.

### Ferramentas e Ecossistema

**IDEs e assistentes de código**
- Cursor — IDE baseado em VS Code com integração nativa de LLM; bom para trabalho de código com contexto de codebase
- GitHub Copilot — integração em IDEs populares; mais inline completion
- JetBrains AI — integração nativa no IntelliJ; útil se você já usa IntelliJ para Java

**Linha de comando e agentes**
- Claude Code — agente de linha de comando para tarefas de código que envolvem múltiplos arquivos, refactoring, geração de testes
- Aider — alternativa open source para edição de código por chat no terminal

**APIs e construção**
- API da Anthropic (Claude), OpenAI (GPT), Google (Gemini) — bases para construir aplicações com LLM
- LangChain / LangGraph — frameworks para orquestrar fluxos com LLMs; avalie se a abstração compensa antes de adotar
- LlamaIndex — especializado em RAG (conectar LLMs a documentos e bases de conhecimento)

### Construindo Aplicações com LLM

Esta é a fronteira mais prática: usar LLMs como componente de uma aplicação real, não apenas como assistente de chat.

**RAG (Retrieval-Augmented Generation)**
Padrão para conectar LLMs a bases de conhecimento privadas. O fluxo: documento → chunking → embedding → vector store → na query, recuperar chunks relevantes → incluir no contexto → gerar resposta fundamentada.

Conceitos que precisam ser entendidos:
- Embeddings: representações vetoriais de texto que capturam similaridade semântica
- Vector databases: Chroma, Pinecone, pgvector, Qdrant
- Chunking strategies: como dividir documentos afeta muito a qualidade da recuperação
- Reranking: um segundo passo para melhorar a relevância dos chunks recuperados

**Padrões de agentes**
- Tool use / function calling: o LLM pode chamar funções externas (buscar dados, executar código, chamar APIs)
- ReAct (Reason + Act): o modelo raciocina, age, observa o resultado, repete
- Multi-agent: múltiplos agentes com papéis distintos colaborando
- Human-in-the-loop: o agente pausa e pede confirmação humana em pontos críticos

**Aspectos de engenharia que importam:**
- Latência: chamadas de LLM são lentas (~1-5s); design para isso desde o início
- Custo por token: faz diferença em sistemas com volume; otimizar contexto não é prematuro
- Streaming: resposta token a token melhora percepção de latência
- Fallback e error handling: LLMs falham, retornam mal-formatado, ultrapassam limites
- Observabilidade: logar inputs, outputs e tokens usados é mínimo para debugar
- Testes de LLM: não são unitários convencionais; evals (avaliações com conjunto de casos) são o equivalente

**Referências para construção com LLM:**
- Documentação oficial da Anthropic (docs.anthropic.com) — cobre prompt engineering, tool use, e padrões de agentes
- *AI Engineering* (Chip Huyen) — o livro mais completo sobre engenharia de sistemas com LLM
- *Building LLMs for Production* (Louis-François Bouchard) — mais prático, cobre RAG, fine-tuning, avaliação
- Curso *Deep Learning Specialization* (Andrew Ng, Coursera) — para quem quer entender a base matemática dos modelos

---

## Síntese: Como as partes se conectam

A modelagem de domínio define *o que* o sistema precisa representar.
A arquitetura define *como* proteger essa representação da infraestrutura.
O design de código define *como* organizar a implementação para que ela seja entendida e modificada.
Os fundamentos definem o julgamento para avaliar se as três camadas acima estão corretas.
A IA acelera a produção das três camadas — e exige que o julgamento das três camadas seja seu, não dela.

Estudar nessa ordem faz sentido porque cada camada responde a uma pergunta que a anterior levantou. Mas na prática, você vai entrar por onde a dor aparecer — e tudo bem. O guia existe para você saber onde estacionar o que aprendeu.

---

## Referências Consolidadas

| Livro | Autor | Para quê |
|---|---|---|
| Fundamentals of Software Architecture | Richards & Ford | Panorama de estilos e trade-offs |
| A Philosophy of Software Design | Ousterhout | Gestão de complexidade no código |
| Get Your Hands Dirty on Clean Architecture | Hombergs | Hexagonal em Java/Spring, mãos na massa |
| Learning Domain-Driven Design | Khononov | Melhor porta de entrada para DDD moderno |
| Implementing Domain-Driven Design | Vernon | DDD tático com profundidade |
| Domain-Driven Design | Evans | Referência canônica; consultar, não começar |
| Clean Architecture | R. Martin | Ler com olhar crítico |
| Software Architecture: The Hard Parts | Ford, Richards et al. | Decisões em sistemas distribuídos |
| Building Microservices | Newman | Referência de microsserviços |
| Designing Data-Intensive Applications | Kleppmann | Sistemas distribuídos, consistência, escala |
| Refactoring | Fowler | Técnicas de refactoring e code smells |
| Design Patterns | GoF | Catálogo de patterns; referência |
| Java Concurrency in Practice | Goetz et al. | Concorrência em Java |
| Test-Driven Development: By Example | Kent Beck | TDD na prática |
| Unit Testing: Principles, Practices and Patterns | Khorikov | Testes unitários modernos |
| AI Engineering | Chip Huyen | Engenharia de sistemas com LLM |

---

*Este documento é um mapa, não um checklist. Cada item aprofunda os outros. O critério para avançar não é "terminei o livro" — é "senti a dor que isso resolve e agora entendo por quê".*
