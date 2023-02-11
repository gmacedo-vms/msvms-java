package pe.com.msvms.springwebflux.operators.creation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import pe.com.msvms.springwebflux.operators.util.Persona;
import pe.com.msvms.springwebflux.operators.util.Personas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SpringWebFluxCreation implements CommandLineRunner {

    public static final Logger log = LoggerFactory.getLogger(SpringWebFluxCreation.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringWebFluxCreation.class);
    }
    @Override
    public void run(String... args) throws Exception {

        justFrom();
        empty();
        range();
        repeat();
    }

    public void justFrom() {

        Mono.just(new Persona(1, "Mito", 29))
                .subscribe(persona -> log.info("[Mono]: ".concat(persona.toString())));

        Mono.just(new Personas().getPersonas())
                .subscribe(personas -> log.info("[Mono]: ".concat(personas.toString())));

        Flux.fromIterable(new Personas().getPersonas())
                .subscribe(persona -> log.info("Flux: ".concat(persona.toString())));
    }

    public void empty() {

        Mono.empty();
        Flux.empty();
    }

    public void range() {
        Flux.range(0, 3).doOnNext(valor -> log.info("El valor es: " + valor)).subscribe();
    }

    public void repeat() {
        Flux.fromIterable(new Personas().getPersonas())
                .repeat(3)
                .subscribe(persona -> log.info("Repeat-Flux: ".concat(persona.toString())));

        Mono.just(new Personas().getPersonas())
                .repeat(3)
                .subscribe(persona -> log.info("Repeat-Mono: ".concat(persona.toString())));
    }


}
