# Sistema Integrado POO

## Integrantes: 
- Ian de Sousa Pereira

## Descrição:
Sistema de gerenciamento integrado para restaurante, clínica e eventos, desenvolvido em Java com foco em Programação Orientada a Objetos (POO).
O sistema gera dados automaticamente e permite analisar o comportamento de clientes, pacientes e participantes por meio de relatórios interativos.

## Funcionalidades principais:
- Cadastro e gerenciamento de pratos (Dish)
- Criação e acompanhamento de pedidos (Order)
- Pagamentos com diferentes métodos (PaymentMethod), exibindo o método mais usado
- Cadastro de pessoas (Person) e classificação por tipo (Paciente, Cliente, Participante)
- Agendamento de consultas (Appointment) em clínicas
- Registro de eventos (Event) e participações (Participation)
- Relatórios e métricas de decisão
- Ticket médio no restaurante
- Tempo médio de retorno de pacientes que participam de eventos
- Pessoas que completam a jornada completa (consulta + evento + refeição)
- Locais e horários mais populares
- Faixa etária mais ativa
- Fidelidade de participantes de oficinas
- Método de pagamento mais usado em cada área

## Perguntas:
- Pacientes que participam de eventos retornam mais rápido à clínica?
- Vouchers distribuídos em eventos são usados no restaurante e em quanto tempo?
- Descontos da clínica ou vouchers influenciam o ticket médio do restaurante?
- Qual é o perfil da pessoa que completa a jornada completa (consulta + evento + refeição)?
- Qual tipo de lugar é mais usado entre clínica, eventos e restaurante?
- Quais horários de eventos e consultas mais se sobrepõem?
- Participantes de workshops voltam a consumir no restaurante depois?
- Qual faixa etária é mais ativa?
- Método de pagamento mais usado?

## Como executar:
1. Clone o repositório
2. Rode no VSCode
3. Caso não funcione, compile no terminal escrevendo o seguinte:
```bash
javac restaurant/**/*.java
java restaurant.main.Main