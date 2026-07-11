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