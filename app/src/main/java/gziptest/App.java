package gziptest;

import java.util.Iterator;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("logging!!");

        Iterator<Integer> it = IntStream.range(-5, 5).boxed().iterator();
        it.forEachRemaining(System.out::println);
    }    
}
