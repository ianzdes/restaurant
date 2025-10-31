# Sistema Restaurante POO

## Integrantes
- Ian de Sousa Pereira

## Descrição
Sistema de gerenciamento simplificado para restaurante, clínica e eventos, com:

- Cadastro e gerenciamento de pratos (`Dish`)
- Criação e acompanhamento de pedidos (`Order`)
- Pagamentos com diferentes métodos (`PaymentMethod`)  
  - Mostrando o método de pagamento mais usado no restaurante, na clínica e nos eventos
- Cadastro de pessoas (`Person`) e classificação por tipo (Paciente, Cliente, Participante)
- Agendamento de consultas (`Appointment`) em clínicas
- Registro de eventos (`Event`) e participações (`Participation`)
- Geração de relatórios e métricas de decisão:
  - Ticket médio no restaurante  
  - Pacientes que participam de eventos retornam mais rápido  
  - Número de pessoas que completam jornada completa (consulta + evento + refeição)  
  - Locais e horários mais populares  
  - Faixa etária mais ativa  
  - Fidelidade de participantes de oficinas

## Perguntas
- Pacientes que participam de eventos retornam mais rápido à clínica.
- Vouchers distribuídos em eventos são usados no restaurante e em quanto tempo
- Descontos da clínica ou vouchers influenciam o ticket médio do restaurante.
- Qual é o perfil da pessoa que completa a jornada completa (consulta + evento + refeição).
- Qual tipo de lugar é mais usado entre clínica, eventos e restaurante.
- Quais horários de eventos e consultas mais se sobrepõem.
- Participantes de workshops voltam a consumir no restaurante depois.
- Qual faixa etária é mais ativa.
- Método de pagamento mais usado por área (restaurante, evento, clínica).

## Como executar
1. Compile todos os arquivos Java:
```bash
javac shared/*.java clinic/*.java event/*.java restaurant2/*.java main/*.java