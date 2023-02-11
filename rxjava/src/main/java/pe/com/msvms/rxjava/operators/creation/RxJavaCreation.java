package pe.com.msvms.rxjava.operators.creation;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import pe.com.msvms.rxjava.operators.util.Persona;

public class RxJavaCreation implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(RxJavaCreation.class);

    public static void main(String[] args) {

        SpringApplication.run(RxJavaCreation.class, args);
    }
    @Override
    public void run(String... args) throws Exception {

        rxjava2();
    }

    public void rxjava2() {
        Observable.just(new Persona(1, "Mito", 29))
                .doOnNext(persona -> log.info("[RxJava2] Persona DoOnNext: " + persona))
                .subscribe(persona -> log.info("[RxJava2] Persona: " + persona));
    }
}
