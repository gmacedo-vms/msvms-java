package pe.com.msvms.springwebflux.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import pe.com.msvms.springwebflux.operators.util.Persona;
import pe.com.msvms.springwebflux.operators.util.Personas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MostUsed implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(MostUsed.class);
  private static List<String> platos = new ArrayList<>();

  public static void main(String[] args) {

    platos.add("Hamburguesa");
    platos.add("Pizza");
    platos.add("Soda");
    SpringApplication.run(MostUsed.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    crearMono();
    crearFlux();
    doOnNext();
    map();
    flatMap();
    range();
    dealyElement();
    zipWith();
    merge();
    filter();
    takeLast();
    take();
    defaultIfEmpty();
    onErrorReturn();
    retry();
    fluxToMono();
  }

  /**
   *
   */
  public void crearMono() {

    log.info("*****************************************************");
    Mono<Integer> monoNumero = Mono.just(7);
    monoNumero.subscribe(x -> log.info("Numero: " + x));
  }

  public void crearFlux() {

    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.subscribe(p -> log.info(p));

    fxPlatos.collectList().subscribe(lista -> log.info(lista.toString()));

    List<Persona> personas = new Personas().getPersonas();

    Flux.fromIterable(personas).subscribe(persona -> log.info("[Flux]: ".concat(persona.toString())));
  }

  public void fluxToMono() {

    List<Persona> personas = new Personas().getPersonas();

    Flux<Persona> personaFlux = Flux.fromIterable(personas);
    personaFlux.collectList().subscribe(personaFluxMono -> log.info("[FluxToMono]: ".concat(personaFluxMono.toString())));
  }

  public void doOnNext() {

    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.doOnNext(p -> log.info(p)).subscribe();
  }

  public void map() {

    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.map(p -> "Plato: " + p).subscribe(p -> log.info(p));
  }

  public void flatMap() {

    log.info("*****************************************************");
    Mono.just("Jaime").flatMap(x -> Mono.just(29)).subscribe(p -> log.info(p.toString()));
  }

  public void range() {

    log.info("*****************************************************");
    Flux<Integer> fx1 = Flux.range(0, 10);
    fx1.map(x -> {
      // líneas de código
      return x + 1;
    }).subscribe(x -> log.info("N: " + x));
  }

  public void dealyElement() throws InterruptedException {

    log.info("*****************************************************");
    Flux.range(0, 10).delayElements(Duration.ofSeconds(2)).doOnNext(integer -> log.info(integer.toString())).subscribe();

    Thread.sleep(20000);
  }

  public void zipWith() {

    log.info("*****************************************************");
    List<String> clientes = new ArrayList<>();
    clientes.add("Jaime");
    clientes.add("Code");

    Flux<String> fxPlatos = Flux.fromIterable(platos);
    Flux<String> fxClientes = Flux.fromIterable(clientes);

    fxPlatos.zipWith(fxClientes, (p, c) -> String.format("Flux 1: %s, Flux 2; %s", p, c)).subscribe(s -> log.info(s));
  }

  public void merge() {

    log.info("*****************************************************");
    List<String> clientes = new ArrayList<>();
    clientes.add("Jaime");
    clientes.add("Code");

    Flux<String> fxPlatos = Flux.fromIterable(platos);
    Flux<String> fxClientes = Flux.fromIterable(clientes);

    Flux.merge(fxPlatos, fxClientes).subscribe(s -> log.info(s));
  }

  public void filter() {
    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.filter(s -> {
      return s.startsWith("H");
    }).subscribe(s -> log.info(s));
  }

  public void takeLast() {
    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.takeLast(2).subscribe(s -> log.info(s));
  }

  public void take() {
    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.take(2).subscribe(s -> log.info(s));
  }

  public void defaultIfEmpty() {

    // platos = new ArrayList<>();
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    log.info("*****************************************************");
    fxPlatos.defaultIfEmpty("Lista vacía").subscribe(s -> log.info(s));
  }

  public void onErrorReturn() {

    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.doOnNext(s -> {
          throw new ArithmeticException("Instancia de Exception");
        }).onErrorMap(ex -> new Exception("Error Map"))
        // .onErrorReturn("Error return")
        .subscribe(s -> log.info(s));
  }

  public void retry() {

    log.info("*****************************************************");
    Flux<String> fxPlatos = Flux.fromIterable(platos);
    fxPlatos.doOnNext(s -> {
      log.info("Intento de lectura...");
      throw new ArithmeticException("Instancia de Exception");
    }).retry(3).onErrorReturn("Error!").subscribe(s -> log.info(s));
  }
}
