package pe.com.msvms.springwebflux.operators.util;

import java.util.ArrayList;
import java.util.List;

public class Personas {

    private List<Persona> personas;

    public Personas() {

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1, "Roberto", 30));
        personas.add(new Persona(3, "Juan", 31));
        personas.add(new Persona(3, "Pedro", 32));

        this.personas = personas;
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}
