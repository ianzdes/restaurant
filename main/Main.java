package restaurant.main;

import restaurant.shared.*;
import restaurant.clinic.*;
import restaurant.restaurant2.*;
import restaurant.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Alice", 30, "alice@email.com", "99999-9999", "Paciente");
        Person p2 = new Person("Bob", 25, "bob@email.com", "98888-8888", "Participante");

        Place clinicaLocal = new Place("Clinica Vida", "Rua A, 100", 20);
        Place eventoLocal = new Place("Auditorio Central", "Av B, 200", 100);
        Place restauranteLocal = new Place("Restaurante Fit", "Rua C, 50", 50);

        
    }
}
