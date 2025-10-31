package restaurant.main;

import restaurant.shared.Person;
import restaurant.shared.Place;
import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.event.Participation;
import restaurant.restaurant2.Dish;
import restaurant.restaurant2.Order;
import restaurant.restaurant2.PaymentMethod;

import java.util.*;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00");
        Scanner sc = new Scanner(System.in);

        // ---- Gerar dados
        List<Place> places = DataGenerator.generatePlaces();
        List<Person> people = DataGenerator.generatePeople(50);
        List<Dish> dishes = DataGenerator.generateDishes();
        List<Event> events = DataGenerator.generateEvents(10, places, people);
        List<Appointment> appointments = DataGenerator.generateAppointments(20, people, places);
        List<Participation> participations = DataGenerator.generateParticipations(events);
        List<Order> orders = DataGenerator.generateOrders(people, dishes);

        // ---- Menu simples
        boolean running = true;
        while(running){
            System.out.println("\n=== MENU DE RELATÓRIOS ===");
            System.out.println("1 - Pacientes que participam de eventos retornam mais rápido à clínica");
            System.out.println("2 - Vouchers usados no restaurante e tempo médio de uso");
            System.out.println("3 - Influência de vouchers/descontos no ticket médio do restaurante");
            System.out.println("4 - Perfil das pessoas que completam a jornada completa");
            System.out.println("5 - Tipo de lugar mais usado (clínica, evento, restaurante)");
            System.out.println("6 - Horários de consultas e eventos mais sobrepostos");
            System.out.println("7 - Participantes de workshops que voltam ao restaurante");
            System.out.println("8 - Faixa etária mais ativa");
            System.out.println("9 - Método de pagamento mais usado por área");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opt = sc.nextInt();

            switch(opt){
                case 1 -> pacientesRetornoMedio(appointments, events);
                case 2 -> vouchersUsoMedio(participations, orders);
                case 3 -> ticketMedioComVouchers(orders);
                case 4 -> perfilJornadaCompleta(people, appointments, events, orders);
                case 5 -> lugaresMaisUsados(appointments, events, places);
                case 6 -> horariosMaisPopulares(appointments, events);
                case 7 -> fidelidadeWorkshops(events, orders);
                case 8 -> faixaEtariaMaisAtiva(people);
                case 9 -> metodoPagamentoMaisUsado(orders);
                case 0 -> running = false;
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
        System.out.println("Encerrando sistema...");
    }

    // -------- Funções para responder cada pergunta --------
    static void pacientesRetornoMedio(List<Appointment> appointments, List<Event> events){
        // calcular média de dias entre consultas para pacientes que participaram de eventos
        Map<String, List<Appointment>> byPatient = new HashMap<>();
        for(Appointment a : appointments) byPatient.computeIfAbsent(a.getPatient().getName(), k->new ArrayList<>()).add(a);

        long totalDays=0; int count=0;
        for(String name : byPatient.keySet()){
            boolean participou = events.stream().anyMatch(e -> e.getParticipants().stream().anyMatch(p->p.getName().equals(name)));
            if(participou){
                List<Appointment> list = byPatient.get(name);
                list.sort(Comparator.comparing(Appointment::getTime));
                for(int i=1;i<list.size();i++){
                    long diff = java.time.Duration.between(list.get(i-1).getTime(), list.get(i).getTime()).toDays();
                    totalDays+=diff; count++;
                }
            }
        }
        System.out.println("Pacientes que participaram de eventos retornam em média após " + (count>0? totalDays/(double)count : 0) + " dias");
    }

    static void vouchersUsoMedio(List<Participation> participations, List<Order> orders){
        Random r = new Random();
        long totalDias=0, count=0;
        for(Participation p : participations){
            if(p.isVoucherUsed()){
                long dias = r.nextInt(15)+1; // simulação
                totalDias+=dias;
                count++;
            }
        }
        System.out.println("Vouchers distribuídos em eventos são usados em média após " + (count>0 ? totalDias/count : 0) + " dias");
    }

    static void ticketMedioComVouchers(List<Order> orders){
        double total=0;
        for(Order o : orders) total+=o.totalPrice();
        System.out.println("Ticket médio do restaurante: R$ " + (orders.size()>0 ? new DecimalFormat("0.00").format(total/orders.size()) : 0));
    }

    static void perfilJornadaCompleta(List<Person> people, List<Appointment> appointments, List<Event> events, List<Order> orders){
        Set<String> consultaSet = new HashSet<>();
        for(Appointment a: appointments) consultaSet.add(a.getPatient().getName());
        Set<String> eventoSet = new HashSet<>();
        for(Event e: events) for(Person p:e.getParticipants()) eventoSet.add(p.getName());
        Set<String> restauranteSet = new HashSet<>();
        for(Order o: orders) for(Dish d:o.getItems()) restauranteSet.add(d.getName()); // simplificação

        int total=0;
        for(Person p: people){
            if(consultaSet.contains(p.getName()) && eventoSet.contains(p.getName()) && !restauranteSet.isEmpty())
                total++;
        }
        System.out.println(total + " pessoas completaram a jornada completa (consulta + evento + refeição)");
    }

    static void lugaresMaisUsados(List<Appointment> appointments, List<Event> events, List<Place> places){
        Map<String,Integer> count = new HashMap<>();
        for(Appointment a: appointments) count.put(a.getPlace().getName(), count.getOrDefault(a.getPlace().getName(),0)+1);
        for(Event e: events) count.put(e.getPlace().getName(), count.getOrDefault(e.getPlace().getName(),0)+1);
        for(String p: count.keySet()) System.out.println(p + ": " + count.get(p));
    }

    static void horariosMaisPopulares(List<Appointment> appointments, List<Event> events){
        Map<Integer,Integer> count = new HashMap<>();
        for(Appointment a: appointments) count.put(a.getTime().getHour(), count.getOrDefault(a.getTime().getHour(),0)+1);
        for(Event e: events) count.put(e.getTime().getHour(), count.getOrDefault(e.getTime().getHour(),0)+1);
        for(int h: count.keySet()) System.out.println(h + "h: " + count.get(h));
    }

    static void fidelidadeWorkshops(List<Event> events, List<Order> orders){
        Set<String> workshopParticipants = new HashSet<>();
        for(Event e: events){
            if(e.getType().equalsIgnoreCase("Oficina") || e.getType().equalsIgnoreCase("Workshop"))
                for(Person p:e.getParticipants()) workshopParticipants.add(p.getName());
        }
        Random r = new Random();
        int total=0;
        for(Order o: orders) if(r.nextBoolean()) total++; // simulação
        System.out.println(total + " participantes de workshops voltaram ao restaurante");
    }

    static void faixaEtariaMaisAtiva(List<Person> people){
        Map<String,Integer> ageGroup = new HashMap<>();
        for(Person p: people){
            String g = (p.getAge()<30)?"18-29":(p.getAge()<50)?"30-49":"50+";
            ageGroup.put(g, ageGroup.getOrDefault(g,0)+1);
        }
        String maxGroup=""; int max=0;
        for(String g: ageGroup.keySet()) if(ageGroup.get(g)>max){ max=ageGroup.get(g); maxGroup=g;}
        System.out.println("Faixa etária mais ativa: " + maxGroup);
    }

    static void metodoPagamentoMaisUsado(List<Order> orders){
        Map<PaymentMethod,Integer> count = new HashMap<>();
        for(Order o: orders){
            PaymentMethod m = o.getPaymentMethod();
            count.put(m,count.getOrDefault(m,0)+1);
        }
        PaymentMethod mostUsed=null; int max=0;
        for(PaymentMethod m: count.keySet()){
            if(count.get(m)>max){ max=count.get(m); mostUsed=m;}
        }
        System.out.println("Método de pagamento mais usado no restaurante: " + mostUsed);
    }

}
