# Used
* Kotlin (jdk 17)
* Spring Boot (v3.3.3)
* H2 Database
* JUnit

<br>

# 빌드 방법
```
$ make build
```

# 실행 방법
```
# IDE에서 실행하는 방법
$ make run

# Terminal에서 실행하는 방법
1. https://github.com/anjeongkyun/product-api/tree/main/jar/product-api-0.0.1-SNAPSHOT.jar 다운로드
2. java -jar product-api-0.0.1-SNAPSHOT.jar
    A. 위 명령 터미널에 실행
```

# API Spec 및 Test 방법
```
1. 위 "실행 방법"을 참고하여 Application을 기동한다
2. http://localhost:8080/swagger 접속
3. Swagger Client로 API Test 진행
```

# 단위 테스트 실행
``` 
$ make test
```

# 구현 내용
### API 명세([스웨거 문서 보러가기](http://localhost:8080/swagger))
- [x] 구현 1) 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
- [x] 구현 2) 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
- [x] 구현 3) 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
- [x] 구현 4) 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API

### 예외 핸들링
- InvariantViolationException의 커스텀 예외를 정의해서 사용하고있어요.
- 예외 발생한 이유에 대한 Key와 Reason을 내려줘요.
  - Key: 예외를 식별할 수 있는 값이 들어가요.
  - Reason: 값은 예외가 발생한 이유에 대해서 Enum으로 관리하고있어요.
  - e.g. 수정 명령의 ID가 존재하지않을 때.
  ```json
    {
      "status": 400,
      "message": "Product with id 99999 not found",
      "className": "InvariantViolationException",
      "errorProperties":[
        {
          "key": "id",
          "reason": "NotFound"
        }
      ]
    }
    ```

<br>

# 시스템 주요 특징
### Database & init sql 파일 구성
  * data.sql, schema.sql을 구성하여 Application 실행 시 과제에 명시된 데이터들이 H2에 자동 세팅되도록 구성해놓았어요.
  * 데이터베이스는 h2를 사용하고있고, http://localhost:8080/h2-console/ 접속하여 삽입된 데이터를 확인할 수 있어요.
  
### API Spec & Test
  * Swagger를 사용하여 API Spec을 관리하고있어요.
  * Swagger Client로 프론트엔드 페이지를 대체했어요.

### 단위 테스트
  * JUnit을 사용하여 단위 테스트를 진행하고있어요.
  * 테스트 실행은 `make test` 명령어로 실행할 수 있어요. 


### 클린 아키텍쳐 기반 설계
* 각 계층(패키지) 별로 책임과 관심사를 분리하여 변경이나 확장이 쉽도록 설계했고, 의존성을 낮추는 것을 목적으로 설계했어요.
  * 각 계층 별 책임과 관심사는 아래를 참고해주세요.  
* domain-model 패키지가 우리의 핵심 비지니스 룰을 담고있는 패키지예요.
  * POJO 형태로, Spring Framework, Library, Database 등의 의존성을 갖고있지않아요.
* 모든 계층의 의존 방향은 domain-model을 향하도록 설계했어요. 
  * 도메인 모델은 외부 프레임워크, 라이브러리 등의 저수준 모듈을 사용할 때 DIP를 이용하여 의존성을 낮추고있어요.
    * e.g. 
      * Aggregate Repository <- JPA Repository
* Entity Service 방식이 아닌 요구사항의 UseCase 별로 독립적인 논리를 담고있어요.

### 현재 아키텍쳐의 문제점과 개선안
[현재 문제점]
* 조회 성능에 있어 무거운 쿼리 발생
  * 현재 구조는 Write Model로 조회를 해야해서 꽤 무거운 쿼리를 조합하여 조회를 하고있어요.
  * 뷰 모델에 특화된 데이터 모델이 없다보니, 조회를 할 때마다 무거운 쿼리가 발생하고, 새로운 유즈케이스가 생기면 구현에 대한 비용도 높다고 생각해요.

[추 후 개선안]
* [CQRS Pattern](https://en.wikipedia.org/wiki/Command_Query_Responsibility_Segregation) 고려
  * 시스템이 더욱 거대해진다면 CQRS를 고려해볼 필요가 있다고 생각해요.
  * 상품 생성/ 수정/ 삭제 명령에 따른 이벤트를 소비하여 적절한 뷰 모델(Materialized View)을 생성하여 조회하는 방식으로의 변경을 생각중이에요.
  * CQRS 패턴을 구성하기 위해 추가 작업과 인프라 비용이 들어갈 순 있지만, 앞으로의 시스템 변경에 대한 확장성과 성능에 대한 이점을 챙길 수 있는 트레이드 오프를 고민해보았을 때 적합하다 판단했어요.
  * 현재 Reader 인터페이스로 정의해두었는데, 이에 대한 구현체는 추 후 읽기에 적합한 Redis, Elasticsearch 등을 사용할 수 있어요. 

<br>

# 패키지 구조 설명

**api**
* Controller, Application Context 관리(Repository, UseCase Bean) 등의 역할을 갖고있는 표현 계층(Presentation)에 속한 모듈이예요. 

**contracts**
* 클라이언트와의 계약 모델을 관리하는 모듈이예요. (e.g. Data Transfer Object)
* 도메인의 명령(Command), 조회(Query)의 계약모델들이 존재해요. 

**domain-model**
* 도메인(비지니스) 논리를 담당하고있는 고수준 모듈이예요.
  * 비지니스 정책의 UseCase 논리들을 담고 있고 집합체(Aggregate)와 값 객체(Value Object)가 존재해요.
    * 애그리거트의 생성에 대한 팩토리 메서드들을 구현하였고, 도메인의 불변식들을 애그리거트에 응집시켜놓았어요.
    * 현재 값 객체인 `Money`는 금액이 0원 미만이 될 수 없는 불변식을 응집시켜놓았어요.
  * [Repository Pattern](https://martinfowler.com/eaaCatalog/repository.html)을 사용하고있고, 데이터 접근에 대한 구현체는 Persistence 패키지에 구현되어있어요.   
  * 도메인 논리를 구현하면서 사용된 외부 프레임워크, 라이브러리 등의 저수준 모듈은 Domain-Model의 인터페이스를 구현하여 사용해요. (DIP)
* 모든 도메인 논리(유즈케이스)들은 test 모듈에서 단위 테스트를 진행하고있어요.

**persistence**
* 데이터 접근 계층에 해당하는 모듈이예요.
  * 데이터 접근 기술은 Spring Data JPA를 사용했어요.
  * 데이터 모델은 데이터 매퍼([Data Mapper](https://martinfowler.com/eaaCatalog/dataMapper.html))를 통해 도메인 모델(Aggregates)로 변환하도록 구성했어요.

**test**
* 어플리케이션의 단위 테스트를 담당하는 모듈이예요.
* Test Database는 H2 In-Memory DB를 이용했어요.
* Test Fixture는 [AutoParams](https://github.com/AutoParams/AutoParams) 사용했어요.
* 도메인 모델에 속한 모든 논리에 대해서 테스트가 작성되어있어요.
