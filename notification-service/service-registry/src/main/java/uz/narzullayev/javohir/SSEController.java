package uz.narzullayev.javohir;/* 
 @author: Javohir
  Date: 6/10/2022
  Time: 3:23 AM*/

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class SSEController {

    private List<String> stockPriceList = new ArrayList<>();


    @PostConstruct
    public void initializeStockObjects() {

        stockPriceList.add("HDFC BANK");
        stockPriceList.add("RELIANCE");
        stockPriceList.add("KOTAK");
    }

    @GetMapping(value = "/stockprice", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<String>> getStockPrice() {

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(3)).log();
        //interval.subscribe((i) -> stockPriceList.get(Math.toIntExact(i)));

        Flux<List<String>> transactionFlux = Flux.fromStream(Stream.generate(() -> stockPriceList)).log();
        return Flux.zip(interval, transactionFlux).map(Tuple2::getT2);
    }

}
