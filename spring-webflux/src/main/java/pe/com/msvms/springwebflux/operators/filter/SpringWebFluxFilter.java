package pe.com.msvms.springwebflux.operators.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import pe.com.msvms.springwebflux.operators.util.Persona;
import pe.com.msvms.springwebflux.operators.util.Personas;
import reactor.core.publisher.Flux;

import java.util.List;

public class SpringWebFluxFilter implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringWebFluxFilter.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringWebFluxFilter.class);
    }

    @Override
    public void run(String... args) throws Exception {

        filter();
        distinct();
        take();
        takeLast();
        skip();
        skipLast();
    }

    public void filter() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() > 30)
                .subscribe(persona -> log.info("Filter: ".concat(persona.toString())));
    }

    /**
     * El metodo distinct compara las referencias en memoria. Si desea que distinct trabaje por un
     * solo valor, debe generar equals and hashcode para el atributo a distinguir.
     */
    public void distinct() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .distinct()
                .subscribe(persona -> log.info("Distinct: ".concat(persona.toString())));

        Flux.fromIterable(List.of(1, 1, 2, 2)).distinct().subscribe(item -> log.info(item.toString()));
    }

    /**
     * El metodo take realiza un corte en el flujo de datos hasta el valor que se le indique, inicia
     * desde el primer elemento del flujo de datos.
     */
    public void take() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .take(2)
                .subscribe(persona -> log.info("Take: ".concat(persona.toString())));
    }

    /**
     * El metodo takeLast realiza un corte en el flujo de datos hasta el valor que se le indique, inicia
     * desde el último elemento del flujo de datos.
     */
    public void takeLast() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .takeLast(2)
                .subscribe(persona -> log.info("TakeLast: ".concat(persona.toString())));
    }

    /**
     * El metodo skip omite flujo de datos hasta el valor que se le indique, inicia
     * desde el primer elemento del flujo de datos entrante.
     */
    public void skip() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .skip(2)
                .subscribe(persona -> log.info("Skip: ".concat(persona.toString())));
    }

    /**
     * El metodo skipLast omite flujo de datos hasta el valor que se le indique, inicia
     * desde el último elemento del flujo de datos entrante.
     */
    public void skipLast() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .skipLast(2)
                .subscribe(persona -> log.info("SkipLast: ".concat(persona.toString())));
    }
}
