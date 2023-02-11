package pe.com.msvms.springwebflux.operators.transformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import pe.com.msvms.springwebflux.operators.util.Persona;
import pe.com.msvms.springwebflux.operators.util.Personas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class SpringWebfluxTransformation implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringWebfluxTransformation.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringWebfluxTransformation.class);
    }

    @Override
    public void run(String... args) throws Exception {

        map();
        flatMap();
        groupBy();
    }

    public void map() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .map(
                        persona -> {
                            persona.setEdad(persona.getEdad() + 10);
                            return persona;
                        })
                .subscribe(persona -> log.info("Map: ".concat(persona.toString())));

        Flux<Integer> flux = Flux.range(0, 10);
        Flux<Integer> fluxNew = flux.map(item -> item + 10);
        fluxNew.subscribe(item -> log.info("[Map-range] x: " + item));

    }

    public void flatMap() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .flatMap(
                        persona -> {
                            persona.setEdad(persona.getEdad() + 10);
                            return Mono.just(persona);
                        })
                .subscribe(persona -> log.info("FlatMap: ".concat(persona.toString())));
    }

    public void groupBy() {

        List<Persona> personas = new Personas().getPersonas();

        Flux.fromIterable(personas)
                .groupBy(Persona::getIdPersona)
                .flatMap(fluxNew -> fluxNew.collectList())
                .subscribe(flux -> log.info(flux.toString()));
    }

}
