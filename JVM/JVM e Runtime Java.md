Em Java o compilador não gera um executável, ele gera um arquivo `*.class`*  em bytecode que vai ser utilizado pela máquina virtual do Java para executar o programa. Dessa maneira o bytecode consegue ser entendido pelo JVM correto que entende a arquitetura da SO que está sendo usada. Além disso o JVM é altamente performático, porque consegue utilizar o JIT Compiler para otimizar blocos de código que são usados com mais frequência e realizar a tarefa com mais rapidez. 

## Gerenciamento de Memória

**Heap →** É a memória compartilhada por todo o sistema e é reservada quando o programa começa a rodar pelo JVM. Você pode ter instâncias jovens ou velhas, as jovens são as que acabaram de ser criadas e o Garbage Collector provavelmente logo vai destruir para liberar memória. As velhas são as que sobreviveram aos clicos do GC.

**Stack** → É a memória do método, ali ele guarda todas as variáveis locais e realiza as operações matemáticas e lógicas daquele método, sendo a chamada da pilha na memória para cada thread recursiva. Quando um método é chamado por outro método, a JVM criar um stack frame que é empilhado no topo da stack até resolver aquele conjunto de operações. Assim ele retorna o resultado daquele método e sai da pilha pelo padrão LIFO.

**Metaspace** → Utiliza a memória nativa da máquina e não é gerenciado pela JVM ele guarda os metadados das classes que são responsáveis por dizer ao JVM as informações para processar um escopo. o ClassLoader é o responsável por entregar ao JVM os metadados da classe.

## Garbage collection

Garbage collection → O garbage collector serve para limpar a memória HEAP em tempo de execução de objetos que não precisam mais existir (inalcançáveis) criando mais espaço livre na memória para ser ocupado pelo que mais importa.

Problemas do Gargabage collection → Pausas excessivas - Por que ele interrompe as threads para fazer a limpeza, caso sejam muitos ciclos de GC ele pode interromper todas causando travamentos; Alto uso de CPU; OutOfMemory - O GC não consegue limpar a memória a tempo causando esgotamento.

Throughput → Quantidade de trabalho em determinado período. Ex: 100 MB/s

## Classloader

O Classloader é o responsável por encontrar e transformar os bytes de uma classe em memória da JVM.

Ciclo de vida → O ciclo de vida de uma classe passa por três fases:

1 - Loading → Onde o Classloader é chamado e os bytes da classe são carregados no Metaspace.

2 - Linking:

2.1 → Verificação: Para ver se os bytes lidos seguem o padrão estrutural correto.

2.2 → Preparação → Aloca e inicializa as variáveis da classe com 0, null etc.

**2.3 - Resolução (Ajuste aqui):** Em vez de dizer que a resolução "determina onde os bytes vão morar", é mais preciso dizer que **a JVM traduz um texto (referência simbólica) para um endereço físico real (referência direta) na memória.** Ela procura na *Constant Pool* o nome do método que você quer chamar e troca por algo como um endereço numérico (ex: `0x00007FFB3`), para que o processador saiba onde achar aquele código no Metaspace.

3 - Inicialization → É a execução da classe, onde os valores reais determinados para as variáveis são definidos.

## Threads e Java Memory Model (JMM)

**Threads →** São processos que permitem que um processo pai funcione com tarefas assincronas. Elas compartilham o espaço de memória e os arquivos abertos do processo, porém possue sua própria Stack e variáveis.

Visibilidade em Java → É a solução para um problema comum, onde uma informação de uma thread possa sair da heap pelo algoritmo de otimização do JAVA, onde todas as threads tem acesso e ir parar em um registrador do processador para acesso mais rápido, o que causa a perda de informação de uma thread. Uma thread então poderia modificar o valor da variável que as outras não iriam saber, podendo causar um travamento por looping infinito da aplicação.

**JMM →** É o conjunto de regras que permite que as ferramentas de visibilidade no compartilhamento de memória entre threads funcione em qualquer SO. Assim como a JVM permite que o JAVA rode em qualquer SO. 

**Volatile →** É a palavra reservada que permite que uma flag booleana seja visível por todas as threads que utilizem a variável. Ela impede que o valor vá para os registradores do processador e fique na heap onde as threads sempre tem acesso.