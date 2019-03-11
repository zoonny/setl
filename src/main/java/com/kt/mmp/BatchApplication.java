package com.kt.mmp;

import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableBatchProcessing
@EnableJpaAuditing
@SpringBootApplication  // @Configuration @EnableAutoConfiguration @ComponentScan
//@MapperScan(basePackages = "com.kt.mmp")
public class BatchApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(BatchApplication.class,
        ArrayUtils.add(args, "baseDt=" + LocalDateTime.now()));
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {
      String[] beanNames = ctx.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        log.debug(">>>>> {}", beanName);
      }
    };
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
    log.info("NonOptionArgs: {}", args.getNonOptionArgs());
    log.info("OptionNames: {}", args.getOptionNames());

    for (String name : args.getOptionNames()){
      log.info("arg-" + name + "=" + args.getOptionValues(name));
    }

    boolean containsOption = args.containsOption("setlTgtYm");
    log.info("Contains person.name: " + containsOption);
  }

}

