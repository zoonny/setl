# Spring Batch

## Meta Tables

- BATCH_JOB_INSTANCE

  - Job Parameter에 따라 생성되는 테이블
  - 같은 Batch Job의 BATCH_JOB_INSTANCE 기록
    - Job Parameter가 다르면 기록
    - Job Parameter가 같다면 기록되지 않음
  - 동일한 파라미터로 연속 실행 시 오류 (단, 실패한 경우 재실행 가능)
    > A job instance already exists and is complete for parameters={requestDate=20180805}.  If you want to run this job again, change the parameters.

- BATCH_JOB_EXECUTION

  - JOB_EXECUTION은 자신의 부모 JOB_INSTACNE가 성공/실패했던 모든 내역 저장
  - 동일한 Job Parameter로 성공한 기록이 있을때만 재수행이 안됨
  - JOB vs JOB_INSTANCE vs JOB_EXECUTION
    > JOB : simpleJob, JOB_INSTANCE : simpleJob 20180807, JOB_EXECUTION : simpleJob 20180807의 첫번째(실패) 두번째(성공)한 시도

- BATCH_JOB_EXECUTION_PARAMS

  - JOB_EXECUTION 실행 시 입력한 JOB_PARAMETER의 내용

- BATCH_STEP_EXECUTION

- BATCH_JOB_EXECUTION_CONTEXT

- BATCH_STEP_EXECUTION_CONTEXT

## Job Flow

> STEP간의 흐름 제어, 참고소스: StepNextJobConfiguration.java

- Next 

  - 순차적으로 STEP을 연결시킬때 사용
  
  ```java
  @Bean
  public Job stepNextJob() {
    return jobBuilderFactory.get("stepNextJob")
        .start(step1())
        .next(step2())
        .next(step3())
        .build();
  }

  public Step step1() {
    return stepBuilderFactory.get("step1")
        .tasklet((contribution, chunkContext) -> {
          log.info(">>>>> This is step1");
          return RepeatStatus.FINISHED;
        })
        .build();
  }  
  ...
  ```
  
  - 지정한 BatchJob만 실행
    - Program arguments로 job.name이 넘어오면 해당 값과 일치한 Job만 실행
    - job.name이 있으면 job.name값을 할당하고, 없으면 NONE을 할당
    - NONE이 할당되면 어떤 배치도 실행하지 않겠다는 의미
    ```bash
    # application.yml
    spring.batch.job.names: ${job.name:NONE}
    ```
    - 실제 운영환경 배치 실행
    ```bash
    java -jar batch-application.jar --job.name=simpleJob
    ```

- 조건별 흐름제어 (Flow)

  - 정상일때는 Step B로, 오류가 났을때는 Step C로 수행, 참고: StepNextConditionalJobConfiguration.java
  
  ```java
  @Bean
  public Job stepNextConditionalJob() {
    return jobBuilderFactory.get("stepNextConditionalJob")
        .start(conditionalJobStep1())
          .on("FAILED") // FAILED 일 경우
          .to(conditionalJobStep3())  // step3으로 이동
          .on("*")  // step3의 결과와 관계 없이
          .end()  // step3로 이동하면 Flow가 종료된다.
        .from(conditionalJobStep1())  // step1으로 부터
          .on("*")  // FAILED 이외 모든 경우
          .to(conditionalJobStep2())  // step2로 이동한다.
          .next(conditionalJobStep3())  // step2가 정상종료되면 step3로 이동한다.
          .on("*")  // step3의 결과와 관계 없이
          .end()  // step3로 이동하면 Flow가 종료된다.
        .end() // Job 종료
        .build();
  }

  @Bean
  public Step conditionalJobStep1() {
    return stepBuilderFactory.get("step1")
        .tasklet((contribution, chunkContext) -> {
          log.info(">>>>> This is stepNextConditionalJob Step1");
          contribution.setExitStatus(ExitStatus.FAILED);

          return RepeatStatus.FINISHED;
        })
        .build();
  }  
  ```
  - 설명
    - .on()
      - 캐치할 ExitStatus 지정
      - *일 경우 모든 ExitStatus가 지정됨
    - .to()
      - 다음으로 이동할 step 지정
    - .from()
      - 일종의 리스너 역할
      - 상태값을 보고 일치하는 상태라면 to()에 포함된 step 호출
      - step1의 이벤트 캐치가 FAILED로 되어 있는 상태에서 추가로 이벤트를 캐치하려면 from을 써야만 함.
    - .end()
      - end는 FlowBuilder를 반환하는 end와 FlowBuilder를 종료하는 end 2개가 있음
      - on("*") 뒤에 있는 end는 FlowBuilder를 반환하는 end
      - build()앞의 end는 FlowBuilder를 종료하는 end
      - FlowBuilder를 반환하는 end는 계고해서 flow를 이어갈 수 있음
    - on이 캐치하는 상태값이 BatchStatus가 아닌 ExitStatus라는 점
    
- BatchStatus vs ExitStatus

  - BatchStatus
    - Job 또는 Step 의 실행 결과를 Spring에서 기록할 때 사용하는 Enum
    - COMPLETED, STARTING, STARTED, STOPPING, STOPPED, FAILED, ABANDONED, UNKNOWN
  - ExitStatus
    - ExitStatus는 Step의 실행 후 상태    
    > public static final ExitStatus COMPLETED = new ExitStatus("COMPLETED");
  - Spring Batch는 기본적으로 ExitStatus의 exitCode는 Step의 BatchStatus와 같도록 설정
  - Custom ExitCode를 정의하기 위해서는 COMPLETED WITH SKIPS exitCode를 반환하는 별도의 로직이 필요
  ```java
  public class SkipCheckingListener extends StepExecutionListenerSupport {

    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) && 
              stepExecution.getSkipCount() > 0) {
            return new ExitStatus("COMPLETED WITH SKIPS");
        }
        else {
            return null;
        }
    }
  }  
  ``` 
  
- Decide

  - Flow의 문제점
    - Step이 담당하는 역할이 2개 이상
      - 실제 해당 Step이 처리해야할 로직외에도 분기처리를 시키기 위해 ExitStatus 조작이 필요
    - 다양한 분기 로직 처리의 어려움
      - ExitStatus를 커스텀하게 고치기 위해선 Listener를 생성하고 Job Flow에 등록하는 등 번거로움이 존재
      
  - Spring Batch에서는 Step들의 Flow속에서 분기만 담당하는 타입 : JobExecutionDecider
  
  ```java
    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(startStep())
                .next(decider()) // 홀수 | 짝수 구분
                .from(decider()) // decider의 상태가
                    .on("ODD") // ODD라면
                    .to(oddStep()) // oddStep로 간다.
                .from(decider()) // decider의 상태가
                    .on("EVEN") // ODD라면
                    .to(evenStep()) // evenStep로 간다.
                .end() // builder 종료
                .build();
    }  
  
    ...
  
    public static class OddDecider implements JobExecutionDecider {

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random rand = new Random();

            int randomNumber = rand.nextInt(50) + 1;
            log.info("랜덤숫자: {}", randomNumber);

            if(randomNumber % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }  
  ```
  
  - ExitStatus가 아닌 FlowExecutionStatus로 상태를 관리
  
## Spring Batch Scope & Job Parameter

- Spring Batch Scope

  - @JobScope & @StepScope
    - @JobScope는 Step 선언문에서 사용 가능하고, @StepScope는 Tasklet이나 ItemReader, ItemWriter, ItemProcessor에서 사용
    - Job Parameter의 타입으로 사용할 수 있는 것은 Double, Long, Date, String
  
  - @JobScope
  ```java
  @Bean
  public Job scopeJob() {
    return jobBuilderFactory.get("simpleJob")
      .start(scopeStep1(null))
      ...
  }

  @Bean
  @JobScope
  public Step scopeStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
    return stepBuilderFactory.get("scopeStep1")
      ...
  }
  ```
  
  - @StepScope
  ```java
  @Bean
  public Step scopeStep2() {
    return stepBuilderFactory.get("scopeStep2")
      .tasklet(scopeStep2Tasklet(null))
      ...
  }

  @Bean
  @StepScope
  public Tasklet scopeStep2Tasklet(@Value("#{jobParameters[requestDate]}") String requestDate) {
    return (contribution, chunkContext) -> {
      ... 
    };
  }
  ```

  - Spring Batch가 Spring 컨테이너를 통해 지정된 Step의 실행시점에 해당 컴포넌트를 Spring Bean으로 생성
  - @JobScpoe는 Job 실행시점에 Bean이 생성
  - Bean의 생성 시점을 지정된 Scope가 실행되는 시점으로 지연
  > 어떻게 보면 MVC의 request scope와 비슷할 수 있겠습니다. 
    request scope가 request가 왔을때 생성되고, response를 반환하면 삭제되는것처럼, JobScope, StepScope 역시 Job이 실행되고 끝날때, Step이 실행되고 끝날때 생성/삭제가 이루어진다고 보시면 됩니다.
  - 장점
    - 첫째로, JobParameter의 Late Binding이 가능
    > 꼭 Application이 실행되는 시점이 아니더라도 Controller나 Service와 같은 비지니스 로직 처리 단계에서 Job Parameter를 할당
    - 두번째로, 동일한 컴포넌트를 병렬 혹은 동시에 사용할때 유용
    > @StepScope 없이 Step을 병렬로 실행시키게 되면 서로 다른 Step에서 하나의 Tasklet을 두고 마구잡이로 상태를 변경하려고 함,
      하지만 @StepScope가 있다면 각각의 Step에서 별도의 Tasklet을 생성하고 관리하기 때문에 서로의 상태를 침범할 일이 없음
      
- JobParameter      

  - Job Parameters는 @Value를 통해서 가능
  ```java
  @Slf4j
  @Component
  @StepScope
  public class SimpleJobTasklet implements Tasklet {
  
    @Value("#{jobParameters[requestDate]}")
    private String requestDate;
  
    public SimpleJobTasklet() {
      log.info(">>>>> tasklet 생성");
    }
  
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
      ...
      return RepeatStatus.FINISHED;
    }
  }
  ```
  - @StepScope, @JobScope Bean을 생성할때만 Job Parameters가 생성
  - JobParameters를 사용하기 위해선 꼭 @StepScope, @JobScope로 Bean을 생성
  
- JobParameter vs 시스템변수

  - 시스템변수 단점
    - 시스템 변수를 사용할 경우 Spring Batch의 Job Parameter 관련 기능을 못쓰게 됨.
      - 중복 실행 방지, 실행 파라미터 테이블 저장
    - Command line이 아닌 다른 방법으로 Job을 실행하기가 어려움
      - Late Binding을 못하므로 Controller등을 통한 동적 파라미터 실행 불가
      
- 주의사항

  - @JobScope, @StepScope는 proxyMode=TARGET_CLASS
  - reader()에서 ItemReader 타입을 리턴할 경우 @StepScope의 proxyMode = ScopedProxyMode.TARGET_CLASS로 인해서 ItemReader 인터페이스의 프록시 객체를 생성하여 리턴
  > o.s.b.c.l.AbstractListenerFactoryBean    : org.springframework.batch.item.ItemReader is an interface.  The implementing class will not be queried for annotation based listener configurations.  If using @StepScope on a @Bean method, be sure to return the implementing class so listner annotations can be used.
  - 번역
  > org.springframework.batch.item.ItemReader는 인터페이스입니다. 구현 클래스는 어노테이션 기반 listner 구성에 대해 실행되지 않습니다. @Bean 메소드에서 @StepScope를 사용하는 경우 listner 어노테이션을 사용할 수 있도록 구현 클래스를 리턴해야합니다.

## Chunk 지향 처리

- Chunk란? 데이터 덩어리로 작업 할 때 각 커밋 사이에 처리되는 row 수
- Chunk 지향 처리란 한 번에 하나씩 데이터를 읽어 Chunk라는 덩어리를 만든 뒤, Chunk 단위로 트랜잭션을 다루는 것
- Reader와 Processor에서는 1건씩 다뤄지고, Writer에선 Chunk 단위로 처리
  ```java
  for(int i=0; i<totalSize; i+=chunkSize){ // chunkSize 단위로 묶어서 처리
      List items = new Arraylist();
      for(int j = 0; j < chunkSize; j++){
          Object item = itemReader.read()
          Object processedItem = itemProcessor.process(item);
          items.add(processedItem);
      }
      itemWriter.write(items);
  }
  ```
- ChunkOrientedTasklet

  - Chunk 지향 처리의 전체 로직을 다루는 것은 ChunkOrientedTasklet 클래스
  - execute()
    - chunkProvider.provider() : Reader에서 Chunk Size 만큼 데이터를 가져옴
    - chunkProcessor.process() : 데이터 Processor 및 Writer

- SimpleChunkProcessor

  - Processor와 Writer 로직을 담고 있는 것은 ChunkProcessor 가 담당
  - 실제 구현체 : SimpleChunkProcessor
  - execute()
    - Chunk<I> inputs : 이 데이터는 앞서 chunkProvider.provide() 에서 받은 ChunkSize만큼 쌓인 item
    - transform() 에서는 전달 받은 inputs을 doProcess()로 전달하고 변환값을 받음
    - transform()을 통해 가공된 대량의 데이터는 write()를 통해 일괄 저장

- Page Size vs Chunk Size

  - Chunk Size는 한번에 처리될 트랜잭션 단위를 얘기하며, Page Size는 한번에 조회할 Item의 양
  - PagingItemReader의 부모 클래스인 AbstractItemCountingItemStreamItemReader
  - 2개 값이 의미하는 바가 다르지만 위에서 언급한 여러 이슈로 2개 값을 일치시키는 것이 보편적으로 좋은 방법이니 꼭 2개 값을 일치시키시길 추천

## ItemReader

- 요약
  - Spring Batch가 Chunk 지향 처리
  - 이를 Job과 Step으로 구성
  - Step은 Tasklet 단위로 처리
  - Tasklet 중에서 ChunkOrientedTasklet을 통해 Chunk를 처리
  - 이를 구성하는 3 요소로 ItemReader, ItemWriter, ItemProcessor

- ItemReader 개요
  - 클래스 구성
    - ItemReader (I)
      - read()
    - ItemStream (I)
      - 주기적으로 상태를 저장하고 오류가 발생하면 해당 상태에서 복원하기 위한 마커 인터페이스
      - 배치 프로세스의 실행 컨텍스트와 연계해서 ItemReader의 상태를 저장하고 실패한 곳에서 다시 실행할 수 있게 해주는 역할
      - open(), close() : 스트림을 열고 닫음
      - update() : Batch 처리의 상태를 업데이트
    - ItemStreamReader implements ItemReader, ItemStream (I)
    - ItemStreamSupport extends ItemStream
    - AbstractItemStreamItemReader extends ItemStreamSupport implements ItemStreamReader
    - AbstractItemCountingItemStreamItemReader extends AbstractItemStreamItemReader
    - InititializeBean (I)
    - AbstractPagingItemReader extends AbstractItemCountingItemStreamItemReader implements InititializeBean
    - JdbcPagingItemReader extends AbstractPagingItemReader implements InititializeBean
    
- Database Reader
  - Cursor 기반 ItemReader 구현체 : 1row, Stream 방식, One Connection
    - JdbcCursorItemReader
    - HibernateCursorItemReader
    - StoredProcedureItemReader
  - Paging 기반 ItemReader 구현체
    - JdbcPagingItemReader
    - HibernatePagingItemReader
    - JpaPagingItemReader
  - IbatisReader는 MyBatis 프로젝트에서 진행 : paging count row, Connection per paging

- CursorItemReader
  - JdbCursorItemReader
    - 참고 : JdbcCursorItemReaderJobConfiguration.java
  - 주의사항
    - CursorItemReader를 사용하실때는 Database와 SocketTimeout을 충분히 큰 값으로 설정
    - Cursor는 하나의 Connection으로 Batch가 끝날때까지 사용
    - Batch 수행 시간이 오래 걸리는 경우에는 PagingItemReader를 사용

- PagingItemReader
  - 페이징을 한다는 것
    - 각 쿼리에 시작 행 번호 (offset) 와 페이지에서 반환 할 행 수 (limit)를 지정
    - Spring Batch에서는 offset과 limit을 PageSize에 맞게 자동으로 생성
    
- JpaPagingItemReader
  - Spring Batch 역시 JPA를 지원하기 위해 JpaPagingItemReader를 공식적으로 지원
  - 현재 Querydsl, Jooq 등을 통한 ItemReader 구현체는 공식 지원하지 않음
  - Hibernate와 비슷하지만 Hibernate 에선 Cursor가 지원되지만 JPA에는 Cursor 기반 Database 접근을 지원 안함
  
- 주의사항
  - PagingItemReader 주의 사항 : 정렬 (Order) 가 무조건 포함되어 있어야 함
  - JpaRepository를 ListItemReader, QueueItemReader에 사용하면 안됨
    - new ListItemReader<>(jpaRepository.findByAge(age)) 로 Reader를 구현하면 Cursor, Paging의 장점이 없어짐
    - 만약 JpaRepository를 써야 한다면, RepositoryItemReader를 사용
      - https://stackoverflow.com/questions/43003266/spring-batch-with-spring-data/43986718#43986718
  - Hibernate, JPA 등 영속성 컨텍스트가 필요한 Reader 사용시 fetchSize와 ChunkSize는 같은 값을 유지
  - Spring Batch Paging Reader 사용시 같은 조건의 데이터를 읽고 수정할 때의 문제
    - 50개의 데이터, 50개의 status: false를 조회해서 status: true로 변경하는 배치
    - chunk_size: 10, paging: 10
    - 1) paing 1-10 조회, 1-10 update true, 11-50 false
    - 2) paging 11-20 조회, but 40개 중 11-20 update true, 1-10은 false, 
    - 3) paging 21-30 조회, but 30개 중 21-30 update true, 1-20은 false, 
    ...
    - 해결책
      - Cursor 사용
      - PagingReader Override
        - 일부러 Page 번호를 계속 변경시키지 않고 0번째로 고정
        ```java
        @Bean
            @StepScope
            public JpaPagingItemReader<Pay> payPagingReader() {
        
                JpaPagingItemReader<Pay> reader = new JpaPagingItemReader<Pay>() {
                    @Override
                    public int getPage() {
                        return 0;
                    }
                };
        
                reader.setQueryString("SELECT p FROM Pay p WHERE p.successStatus = false");
                reader.setPageSize(chunkSize);
                reader.setEntityManagerFactory(entityManagerFactory);
                reader.setName("payPagingReader");
        
                return reader;
            }
        ```

## ItemWriter

- Reader와 Writer는 ChunkOrientedTasklet에서 필수 요소
- Processor는 선택, Processor는 없어도 ChunkOrientedTasklet는 구성 가능
- Reader와 Processor를 거쳐 처리된 Item을 Chunk 단위 만큼 쌓은 뒤 이를 Writer에 전달
  
- Database Writer
  - JDBC와 ORM 모두 Writer를 제공
  - Database의 영속성과 관련해서는 항상 마지막에 Flush를 해야 함
    - entityManager.flush()
    - sessionFactory.getCurrentSession().clear();
  - JdbcBatchItemWriter
  - HibernateItemWriter
  - JpaItemWriter
  
- JdbcBatchItemWriter
  - 처리 방식
    - Query 모으기 (Chunk Size 만큼 쌓임)
    - 모아놓은 Query 한번에 전송
    - Database 받은 쿼리 실행
    - 데이터베이스와 주고받는 횟수를 최소화하여 성능 향상
    - 참고 : JdbcBatchItemWriterJobConfiguration.java
    
  - 설정값
    - assertUpdates : 적어도 하나의 항목이 행을 업데이트하거나 삭제하지 않을 경우 예외를 throw할지 여부를 설정, EmptyResultDataAccessException
    - columnMapped : Map<String,Object> 기반 SQL Value 매핑
    - beanMapped : Pojo 기반 SQL Value 매핑
    - afterPropertiesSet : InitializingBean 인터페이스 에서 갖고 있는 메소드
      각각의 Writer들이 실행되기 위해 필요한 필수값들이 제대로 세팅되어있는지를 체크
      ```java
      @Override
      public void afterPropertiesSet() {
        Assert.notNull(namedParameterJdbcTemplate, "A DataSource...");
        Assert.notNull(sql, "SQL statement...");
        List<String> namedParameters = new ArrayList<>();
        parameterCount = JdbcParameterUtils.countParameterPlaceholders(sql, namedParameters);
        if (namedParameters.size() > 0) {
          if (parameterCount != namedParameters.size()) {
            throw new InvalidDataAccessApiUsageException("...");
          }
          usingNamedParameters = true;
        }
        if (usingNamedParameters) {
          Assert.notNull(itemPreparedStatementSetter, "...");
        }
      }
      ```

- JpaItemWriter

  - 참조: JpaItemWriterJobConfiguration.java
  
- Custom ItemWriter

  - Custom하게 만드는 경우
    - Reader에서 읽어온 데이터를 RestTemplate으로 외부 API로 전달
    - 임시저장을 하고 비교하기 위해 싱글톤 객체에 값을 넣어야할때
    - 여러 Entity를 동시에 save 
    
  - 참고: CustomItemWriterJobConfiguration.java
  
- ItemWriter에 리스트로 전달하기

  - 참고: ItemListJobConfiguration.java
  - Not an entity [class java.util.ArrayList]
  - JpaItemWriter 의 write 메소드
    - List 가 3개가 아닌, 2개,,, ChunkSize: 2
    - 즉, write 메소드에 할당되는 List는 chunk size만큼 T 데이터를 받는 것입니다.
      T는 ArrayList<Tax>가 할당되고, ArrayList는 Entity 클래스가 아니기 때문에 오류가 발생한 것입니다.
      이를 해결하려면 결국 JpaItemWriter의 write메소드를 오버라이딩 하는 수 밖에 없습니다.
      그래서 JpaItemListWriter를 만들어 보겠습니다.
    
- MyBatis    

  - @MapperScan
  ```java
  import org.mybatis.spring.annotation.MapperScan;

  @SpringBootApplication
  @MapperScan(basePackages = "com.kt.spring.batch.mybatis")
  public class MybatisSampleApplication {
  ....  
  ```
  
  - application.yml
  ```properties
  # mybatis 매핑 type을 짧게 쓰기 위한 설정
  mybatis.type-aliases-package=com.kt.spring.batch.mybatis

  # mapper 이하를 로깅 위치로 설정.
  logging.level.net.chndol.study.mybatissample.mapper=TRACE
  ```

  - XML파일과 Interface를 하나의 폴더에서 관리
    - pom.xml
    ```xml
    <project>
    ...    
      <build>
        <resources>
            <resource>
                <filtering>false</filtering>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
      ...
      </build>
    </project>    
    ```
  
  - Logging   
    - build.gradle
    > implementation 'org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16'
    - application.yml
    ```properties
    logging.level.jdbc.sqlonly: INFO
    logging.level.jdbc.sqltiming: DEBUG
    loggine.jdbc.audit: OFF
    loggine.jdbc.resultset: OFF
    loggine.jdbc.resultsettable: INFO
    loggine.jdbc.connection: OFF
    ``` 
    - log4jdbc.log4j2.properties
    ```properties
    log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
    log4jdbc.dump.sql.maxlinelength=0
    ```
    - logback.xml ??
    
 # Lombok
 
   - @Data 사용 금지
     - @Data는 @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor을 한번에 사용
   - Setter의 무분별한 사용 금지
     - Setter는 그 의도가 분명하지 않고 객체를 언제든지 변경할 수 있는 상태가 되어서 객체의 안전성이 보장받기 힘듬
   - ToString으로 인한 양방향 연관관계 순환 참조 문제
     - @OneToMany, @ManyToOne
     - Member 객체와 Coupon 객체가 양방향 영관관계일 경우 ToString을 호출하면 무한 순환 참조가 발생
     - 해결 : Member객체에 @ToString(exclude = "coupons") 선언하여 항목 제 
   - 바람직한 Lombok 사용 방법
   ```java
   @Entity
   @Table(name = "member")
   @ToString(exclude = "coupons")
   @Getter
   @NoArgsConstructor(access = AccessLevel.PROTECTED)
   @EqualsAndHashCode(of = {"id", "email"})
   public class Member {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;
    
    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;
  
    @OneToMany
    @JoinColumn(name = "coupon_id")
    private List<Coupon> coupons = new ArrayList<>();
    
    @Builder
    public Member(String email, String name) {
      this.email = email;
      this.name = name;
    }
    
   }
   ```
   - @NoArgsConstructor 접근 권한을 최소화 
     - JPA에서는 프록시를 생성을 위해서 기본 생성자를 반드시 하나를 생성
     - 이때 접근 권한은 protected
     ```java
     @Builder
     public Product(String name) {
         this.id = UUID.randomUUID().toString();
         this.name = name;
     }
     ```
   - Builder 사용시 매개변수 최소화
     - @Builder를 사용 시 @AllArgsConstructor 어노테이션을 붙인 효과를 발생시켜 모든 멤버 필드에 대해서 매개변수를 받는 기본 생성자를 만듬
     
   - Lombok Annotation
     - @Getter @Setter
     - 생성자 자동 생성
       - @NoArgsConstructor : 파라미터가 없는 기본 생성자를 생성
       - @AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자
       - @RequiredArgsConstructor : final이나 @NonNull인 필드 값만 파라미터로 받는 생성자
     - @ToString(exclude = "password")
     - @EqualsAndHashCode(callSuper = true)
       - callSuper 속성을 통해 equals와 hashCode 메소드 자동 생성 시 부모 클래스의 필드까지 감안할지 안 할지에 대해서 설정
     - @Data  
       - @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한꺼번에 설정
     - @Builder
      - 컬렉션으로 된 필드에는 @Singular 어노테이션을 선언해주면 모든 원소를 한 번에 넘기지 않고 원소를 하나씩 추가
      ```java
      @Builder
      public class User {
        private Long id;
        private String username;
        private String password;
        @Singular
        private List<Integer> scores;
      }
      ```
      ```java
      User user = User.builder()
        .id(1L)
        .username("dale")
        .password("1234")
        .score(70)
        .score(80)
        .build();
      // User(id=1, username=dale, password=1234, scores=[70, 80])
      ```
      - @Log @Slf4j @Log4j2
      - @NonNull : 해당 변수가 null로 넘어온 경우, NullPointerException 예외
      - @Cleanup : 당 자원이 자동으로 닫히는 것이 보장, try-catch-finally 문의 finally 절을 통해서 close() 메소드
      ```java
      @Cleanup Connection con = DriverManager.getConnection(url, user, password);
      ```
      - @SneakyThrows : 예외처리 생략
      ```java
      @SneakyThrows(IOException.class)
      public void printLines() {
          BufferedReader reader = new BufferedReader(...);
          for (String line; (line = reader.readLine()) != null; ) {
              System.out.println(line);
          }
      }
      ```
      - @Synchronized : 어노테이션을 사용하면 가상의 필드 레벨에서 좀 더 안전하게 락
      ```java
      @Synchronized
      public void hello() {
          System.out.println("world");
      }
      ```
      - @Data 대신에 @Value : 불변 객체
     
# Java 1.8

- Optional
  - NPE (Null Pointer Exception) 처리
    - java.util.Optional<T>
    - “존재할 수도 있지만 안 할 수도 있는 객체”
    ```java
    Optional<Order> maybeOrder; // Order 타입의 객체를 감쌀 수 있는 Optional 타입의 변수
    Optional<Member> optMember; // Member 타입의 객체를 감쌀 수 있는 Optional 타입의 변수
    Optional<Address> address; // Address 타입의 객체를 감쌀 수 있는 Optional 타입의 변수
    ```
    - Optional.empty()
    ```java
    // null을 담고 있는, 한 마디로 비어있는 Optional 객체
    Optional<Member> maybeMember = Optional.empty();
    ```
    - Optional.of(value)
    ```java
    // null이 아닌 객체를 담고 있는 Optional 객체를 생성
    Optional<Member> maybeMember = Optional.of(aMember);
    ```
    - Optional.ofNullable(value)
    ```java
    // null인지 아닌지 확신할 수 없는 객체를 담고 있는 Optional 객체를 생성
    ```
    - get()
    비어있는 Optional 객체에 대해서, NoSuchElementException
    - orElse(T other)
    비어있는 Optional 객체에 대해서, 넘어온 인자를 반환
    - orElseGet(Supplier<? extends T> other)
    비어있는 Optional 객체에 대해서, 넘어온 함수형 인자를 통해 생성된 객체를 반환
    비어있는 경우에만 함수가 호출되기 때문에 orElse(T other) 대비 성능상 이점
    - orElseThrow(Supplier<? extends X> exceptionSupplier)
    비어있는 Optional 객체에 대해서, 넘어온 함수형 인자를 통해 생성된 예외
    - 잘못된 사용
      - 객재 존재 여부를 bool 타입으로 반환하는 isPresent()라는 메소드를 통해 null 체크가 필요
      - Optional을 정확히 이해하고 제대로 사용하실 수 있는 개발자라면 첫번째 예제의 코드는 다음과 같이 한 줄의 코드로 작성
      - 기존에 조건문으로 null을 대하던 생각을 함수형 사고로 완전히 새롭게 바꿔야 
      ```java
      int length = Optional.ofNullable(getText()).map(String::length).orElse(0);
      ```
  - Optional을 Optional 답게,,,    
    - NPE
      - Optional을 최대 1개의 원소를 가지고 있는 특별한 Stream    
      - Stream 클래스가 가지고 있는 map()이나 flatMap(), filter()와 같은 메소드를 Optional도 가지고 있음
      ```java
      /* 주문을 한 회원이 살고 있는 도시를 반환한다 */
      public String getCityOfMemberFromOrder(Order order) {
      	return Optional.ofNullable(order)
    			.map(Order::getMember)
    			.map(Member::getAddress)
    			.map(Address::getCity)
    			.orElse("Seoul");
      }
      ```
      - ofNullable() 정적 팩토리 메소드를 호출하여 Order 객체를 Optional로 감쌈. 혹시 Order 객체가 null인 경우를 대비하여 of() 대신에 ofNullable()을 사용
      - 3번의 map() 메소드의 연쇄 호출을 통해서 Optional 객체를 3번 변환
      - 매 번 다른 메소드 레퍼런스를 인자로 넘겨서 Optional에 담긴 객체의 타입을 바꿔줌
      - (Optional<Order> -> Optional<Member> -> Optional<Address> -> Optional<String>)
      - 마무리 작업으로 orElse() 메소드를 호출하여 이 전 과정을 통해 얻은 Optional이 비어있을 경우, 디폴트로 사용할 도시 이름을 세팅
    - filter()로 레벨업
      - filter() 메소드를 사용하면 if 조건문 없이 메소드 연쇄 호출만으로도 좀 더 읽기 편한 코드를 작성
      - if (obj != null && obj.do() ...) 대신
      ```java
      public Optional<Member> getMemberIfOrderWithin(Order order, int min) {
      	return Optional.ofNullable(order)
      			.filter(o -> o.getDate().getTime() > System.currentTimeMillis() - min * 1000)
      			.map(Order::getMember);
      }
      ```
      - filter() 메소드는 넘어온 함수형 인자의 리턴 값이 false인 경우, Optional을 비워버리므로 그 이후 메소드 호출은 의미가 없어짐
      - Stream 클래스의 filter() 메소드와 동작 방식이 동일하지만, Optional의 경우 원소가 하나 밖에 없기 때문에 이런 효과
    - 사용
      - null 반환
      ```java
      Optional<String> maybeCity = Optional.ofNullable(cities.get(4)); // Optional
      int length = maybeCity.map(String::length).orElse(0); // null-safe
      System.out.println("length: " + length);
      ```
      - List의 get()메소드 사용 시, ArrayIndexOutOfBoundsException 처리
      ```java
      public static <T> Optional<T> getAsOptional(List<T> list, int index) {
      	try {
      		return Optional.of(list.get(index));
      	} catch (ArrayIndexOutOfBoundsException e) {
      		return Optional.empty();
      	}
      }
      ```
      ```java
      Optional<String> maybeCity = getAsOptional(cities, 3); // Optional
      int length = maybeCity.map(String::length).orElse(0); // null-safe
      System.out.println("length: " + length);
      ```
      - ifPresent()
        - ifPresent(Consumer<? super T> consumer) 
        - 이 메소드는 특정 결과를 반환하는 대신에 Optional 객체가 감싸고 있는 값이 존재할 경우에만 실행될 로직을 함수형 인자로 넘김
        ```java
        Optional<String> maybeCity = getAsOptional(cities, 3); // Optional
        maybeCity.ifPresent(city -> {
        	System.out.println("length: " + city.length());
        });
        ```
  
- Lamda  
  - 함수형 객체(인터페이스), 클로저
  ```java
  public static void main(String... args) {
    Runnable r2 = () -> System.out.println("Howdy, world!");
    r2.run();
  }
  ```
  - @FunctionalInterface
  ```java
  public static void main(String... args) {
    Comparator<String> c = (String lhs, String rhs) -> lhs.compareTo(rhs);
    int result = c.compare("Hello", "World");
  }
  ```
  - 타입추론
  ```java
  public static void main(String... args) {
    Comparator<String> c = (lhs, rhs) -> {
      System.out.println("I am comparing" + lhs + " to " + rhs);
      return lhs.compareTo(rhs);
    };
    int result = c.compare("Hello", "World");
  }  
  ``` 
  - 변수캡쳐
    - thread 내에서 final 선언 없이 변수 사용 가능
  - 메소드 참조
  ```java
  class Person {
    public String firstName;
    public String lastName;
    public int age;
  
    public final static Comparator<Person> compareFirstName =
      (lhs, rhs) -> lhs.firstName.compareTo(rhs.firstName);
  
    public final static Comparator<Person> compareLastName =
      (lhs, rhs) -> lhs.lastName.compareTo(rhs.lastName);
  
    public Person(String f, String l, int a) {
      firstName = f; lastName = l; age = a;
    }
  
    public String toString() {
      return "[Person: firstName:" + firstName + " " +
        "lastName:" + lastName + " " + "age:" + age + "]";
    }
  } 
  ```
  ```java
  public static void main(String... args) {
    Person[] people = . . .;
  
    // Sort by first name
    Arrays.sort(people, Person.compareFirstName);
    for (Person p : people)
      System.out.println(p);
  }
  ```
  ```java
  Comparator cf = Person::compareFirstNames;
  ```
  - 가상 확장 메소드  
  ```java
  interface Iterator<T> {
  	boolean hasNext();
  	T next();
  	void remove();
  
  	void skip(int i) default {
    	for (; i > 0 && hasNext(); i--) next();
  	}
  }
  ```
- 반복문에서 벗어나기 (Stream)
  - Java가 포함된 첫번째 Article 리턴
  - “Java” 태그를 가지는 모든 기사를 찾기 위해서 filter 오퍼레이션을 사용 후에 그 중 첫 번째 원소를 찾기위해서 findFirst 오퍼레이션을 사용
  ```java
  public Optional<Article> getFirstJavaArticle() {  
      return articles.stream()
          .filter(article -> article.getTags().contains("Java"))
          .findFirst();
  }
  ```  
  - Java가 포함된 모든 Article 리스트 리턴
  - collect 오퍼레이션을 사용하여 결과 스트림을 대상으로 reduction 수행
  ```java
  public List<Article> getAllJavaArticles() {  
      return articles.stream()
          .filter(article -> article.getTags().contains("Java"))
          .collect(Collectors.toList());
  }
  ```
  - Article을 저자 별로 분류
  - groupingBy 오퍼레이션과 getAuthor에 대한 메소드 참조를 사용
  ```java
  public Map<String, List<Article>> groupByAuthor() {  
      return articles.stream()
          .collect(Collectors.groupingBy(Article::getAuthor));
  }
  ```
  - 기사 컬렉션에서 사용된 모든 태그 반환 (Set을 통해 Distinct)
  - flatmap은 태그 리스트들을 하나의 결과 스트림으로 평탄화(flaten)시켜주고, 우리는 세트(set)를 반환하기 위해서 collect를 사용
  ```java
  public Set<String> getDistinctTags() {  
    return articles.stream()
        .flatMap(article -> article.getTags().stream())
        .collect(Collectors.toSet());
  }
  ``` 
  ```java
  fruits.stream().forEach(System.out::println);
  ```
  
## JPA

- @OneToMany
  - 속성
    - targetEntity
      - 관계를 맺을 Entity Class를 정의
    - cascade
      - 현 Entity의 변경에 대해 관계를 맺은 Entity도 변경 전략을 결정
      - ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH
    - fetch
      - 관계 Entity의 데이터 읽기 전략을 결정
      - FetchType.EAGER : EAGER인 경우 관계된 Entity의 정보를 미리 읽음
      - FetchType.LAZY : LAZY는 실제로 요청하는 순간 가져옴
    - mappedBy
      - 양방향 관계 설정시 관계의 주체가 되는 쪽에서 정의
    - orphanRemoval
      - 관계 Entity에서 변경이 일어난 경우 DB 변경을 같이 할지 결정
      - cascade는 JPA 레이어 수준이고 이것은 DB레이어에서 처리
    
  - FetchType.LAZY
    - org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role 오류
    - 해당 오류는 데이터가 영속성(persistence)을 잃었을 경우 발생
    - 영속성이란 데이터를 생성한 프로그램이 실행이 종료되더라도 사라지지 않는 특징
    - 영속성을 잃었다는 것은 데이터를 생성한 프로그램이 종료되면 해당 데이터는 더 이상 추가 적재 및 갱신이안되고 메모리에만 남아있음
    - 현재 LAZY 전략을 취한 경우 Member Entity의 멤버 변수는 살아 있는것처럼 보이지만 @OneToMany로 묶인 Phone Entity는 호출한 이후 영속성을 잃음
    - 하지만 이러한 방법은 경우에 따라 불필요한 조회를 하게 만들고 볼륨이 큰 데이터일 경우 더욱 성능을 나쁘게 만듬
    - 다른 방법은 @Transactional 어노테이션을 메소드에 설정

  - Repository
    ```java
    public interface JpaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>
    public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID>
    public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID>
    public interface Repository<T, ID extends Serializable>
    ```

- Multiple Key
  ```java
  @Embeddable
  public class MemberPK implements Serializable {
  
  	private static final long serialVersionUID = 1L;
  
  	@Column(name="id", nullable=false)
  	private Long id;
  
  	@Column(name="name", nullable=false)
  	private String name;
  
  	public MemberPK(){}
  
  	public MemberPK(long id, String name) {
  		super();
  		this.id = id;
  		this.name = name;
  	}
  }
  ```
  - @Embeddable 어노테이션을 정의
  ```java
  @Entity
  public class Member {
  
  	@EmbeddedId
  	private MemberPK pk;
  
  	@Column
  	private int age;
  
  	public Member() {}
  
  	public Member(MemberPK pk, int age) {
  		this.pk = pk;
  		this.age = age;
  	}
	
	  ...
  }
  ``` 
  - @EmbeddedId 선언

## 정산

  - 배치 프로세스
    - 정산집계(계산) 배치
      - 레퍼런스 로딩
        - 정산항목정보
          - 정산규칙정보
          - 정산항목요율정보
          - 파트너정보(?)
      - 트랜잭션 데이터 조회
      - 정산집계
        - 정산항목집계
          - 정산항목중개상세
      - 정산집계정보저장
      
    - 정산배분 배치
      - 레퍼런스 로딩
        - 파트너정보
          - 파트너배분정보 
      - 정산항목집계 데이터 조회
        - 정산항목집계
        - 정산항목중개상세
      - 배분집계
        - 정산항목배분집계
          - 정산항목배분상세
      - 배분집계정보저장

    - 정산 조정 처리
    
## Build & Run
 
### Build
 
```script
# 아래는 필요 없음
task fatJar(type: Jar) {
	manifest {
		attributes 'Implementation-Title' : 'Setl Jar File',
        'Implentation-Version' : version,
		'Main-Class': 'com.kt.mmp.BatchApplication'
	}
	baseName = project.name + '-all'
	from {
       	configurations.compile.collect {
			it.isDirectory() ? it : zipTree(it)
		}
    }
	with jar
}
```

```script
gradle clean
gradle build -x test
```

- with out test
```script
test {
  ignoreFailures = true
}
```

### Run
```script
java -jar setl-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=postgres --job.name=setlCalcJob setlTgtYm=201902
```