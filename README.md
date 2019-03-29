# Project-TDL
- 참여인원 : [민경환](https://www.github.com/ber01), [박동현](https://www.github.com/pdh6547), [신무곤](https://www.github.com/mkshin96), [신재홍](https://www.github.com/woghd9072), [양기석](https://www.github.com/yks095), [엄태균](https://www.github.com/etg6550), [임동훈](https://www.github.com/dongh9508), [최광민](https://www.github.com/rhkd4560), [하상엽](https://www.github.com/hagome0)

## 목차
1. [목적](#목적)
2. [개발환경](#개발환경)
3. [학습과정](#학습과정)
4. [주요기능](#주요기능)

## 목적
- Spring Boot & JPA 학습 및 Web Application(To Do List) 개발
- Spring Security 학습 및 로그인, 회원가입 구현

## 개발환경
|도구|버전|
|:---:|:---:|
|Spring|Spring Boot 2.1.3.RELEASE
|운영체제|Mac OS X|
|개발 툴|IntelliJ IDEA Ultimate 2018.3|
|JDK|JDK 8|
|데이터베이스|MySQL 8.0.3|
|빌드 툴|Gradle 5.2.1|

## 학습과정
### 1일차
- [프로젝트 생성 오류](./img/1.jpeg) / 해결 : IntelliJ 2018.3 다운로드
- TDL 클래스 설계
  1. 키(`idx`) - Integer
  2. 내용(`description`) - String
  3. 완료 여부(`status`) - Boolean
  4. 생성 시간(`createdDate`) - LocalDateTime
  5. 완료 시간(`completedDate`) - LocalDateTime
- 프로젝트 생성
---
### 2일차
- 도메인 클래스, 저장소 생성(`ToDoList.java`, `ToDoListRepository.java`)
- MySQL 연결 및 테스트(`build.gradle`, `application.yml`)
  1. Schema(`tdl_db`), User(`tdl_user`) 생성
  2. TimeZone 설정 : `url: jdbc:mysql://127.0.0.1:3306/tdl_db?serverTimezone=Asia/Seoul`
  3. 데이터 삽입 및 [확인](./img/2.png)
- 컨트롤러 생성(`ToDoListController.java`)
  - 서비스 호출 및 View 생성(`list.html`)
- 서비스 생성(`ToDoListService.java`)
  - 저장소 호출 및 데이터 반환
- [View 확인](./img/3.png)
- [View 꾸미기](./img/4.png) : Boot Strap 활용
  1. css 적용 시, 경로는 `/static/css`가 아니라 `/css`로 경로 설정
  2. `<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>`
  3. `<link rel="stylesheet" href="/css/bootstrap.min.css"/>`
- [IntelliJ, MySQL 연동](./img/5.png)
---
### 3일차
- [View 수정](./img/6.png)
---
### 4일차
- [View 수정](./img/7.png)
---
### 5일차
- footer 생성
- 폼 생성 및 등록 함수 생성(`postTDL`)
  1. input text 값 받아오기(`@RequestBody`, `MultiValueMap`)
  2. 서비스 호출 및 등록 로직 처리(`toDoListService.postList()`)
  3. DataBase 저장 및 redirect
---
### 6일차
- `idx` 속성 값 지정
  1. `delete`, `update`, `status` 버튼 idx value 지정
  2. `<script>` 코드 작성
      1. 반복되는 버튼 이므로 `class` 선택자 사용
      2. 현재 객체의 idx 값을 받아야 하므로 `$(this).val()` 사용
      3. `DELETE` 타입 설정
- 삭제 함수 생성(`deleteTdl`)
  1. idx값 받아오기(`@PathVariable`)
  2. 서비스 호출 및 삭제 함수 생성(`deleteList`)
      - 저장소 호출, 해당 idx 삭제
- 완료 함수 생성(`statusTdl`)
  1. idx값 받아오기(`@PathVariable`)
  2. 서비스 호출 및 완료 함수 생성(`completeList`)
      1. idx값에 해당하는 `ToDoList` 객체 불러오기
      2. 객체 업데이트 함수 생성(`updateStatus`)
          - `status` 및 `completedDate` 업데이트
- [View 완성](./img/8.png)
---
### 7일차
- Ajax 통신 객체 생성
  1. url : Controller의 `@PutMapping` 경로
  2. type : `PUT`
  3. data : 수정 할 `description`
  4. contentType: `application/json`
  5. dataType : `text`
- 수정 함수 생성(`updateTdl`)
  1. idx값 받아오기(`@PathVariable`)
  2. 수정 할 description 받아오기(`@RequestBody`)
  3. 서비스 호출 및 수정 함수 생성(`updateList`)
      1. idx값에 해당하는 `ToDoList` 객체 불러오기
      2. 객체 업데이트 함수 생성(`update`)
          - `description` 업데이트
---
### 8일차
- User, TDL - 1:N관계 설정하기
- User 클래스 생성
  1. 키(`idx`) - Integer
  2. 아이디(`id`) - String
  3. 비밀번호(`password`) - String
  4. 이메일(`email`) - String
  6. `OneToMany` : ToDoList(`toDoList`) - ToDoList
- TDL 클래스 User 필드 추가
  - `ManyToOne` : 유저(`user`) - user
- 빌드 실패
---
### 9일차
- User, TDL - 1:1관계 설정 해보기
  1. ToDoList 클래스 User필드 추가
  ~~~java
  @OneToOne(fetch = FetchType.LAZY)
  private User user;
  ~~~
  2. User 객체 생성 후 DB 삽입(`CommandLineRunner`)
- User Controller, Repository, Service 생성
- ToDoListController 클래스 수정
  1. 현재 User의 정보에 대한 User 필드 생성
  `private User user;`
  2. list() 함수 실행 시 User가 null 이면 : User 객체 불러오기
  3. 서비스 호출 및 현재 User와 등록 할 ToDoList 객체 넘기기
- [User 객체 저장](./img/20.png)
---
### 10일차
- 로그인
  1. `LoginController` 생성
  2. [로그인 View(`login.html`)](./img/21.png) 생성
- 회원가입
  1. `RegisterController`생성
  2. [회원가입 View(`register.html`)](./img/22.png) 생성
  3. `id`, `email`, `pwd`를 포함한 Ajax 객체 생성, 회원가입 유저 DB 저장
- 로그인 확인
  1. `id`, `pwd` Ajax 객체 생성 후 데이터 전송
  2. 서비스 호출 및 `id`, `pwd` 체크 함수 생성(`loginCheck`)
  3. 로그인 확인
      1. `id`가 없을 경우 : 실패
      2. `id`가 있을 경우, `pwd` 확인
          1. `pwd` 일치 : 성공(`redirect list`)
          2. `pwd` 불일치 : 실패
---
### 11일차
- 로그인 성공 여부에 따라 `redirect` 설정
- 현재 유저에 따라 `to_do_list` 테이블에 `user_idx` [저장](./img/23.png)
- 로그인한 유저가 작성한 `ToDoList` 출력
---
### 12일차
- `ManyToOne`, `OneToMany` 설정
  - `FK`를 들고 있는(소유권이 있는) 클래스에 `@ManyToOne` 어노테이션
  - 소유권이 없는 클래스에 `@OneToMany` 어노테이션 및 `mappedBy` 소유권이 있는 클래스의 변수명
  - 소유권이 없는 클래스는 `Collections`로 지정
- `User` 클래스가 `FK`로 `ToDoList`를 포함할 때

  |idx|email|id|pwd|to_do_list_idx|
  |:---:|:---:|:---:|:---:|:---:|

  ~~~java
  public class User{

    // more field

    @ManyToOne
    private ToDoList toDoList;
  }
  ~~~

  ~~~java
  public Class ToDoList{

    // more filed

    @OneToMany(mappedBy = "toDoList");
    private List<User> user;
  }
  ~~~

  `User`가 `FK`로 `ToDoList`를 소유하고 있을 경우 `ToDo`가 등록됨에 따라 불필요하게 중복된 `User`의 정보가 삽입된다.

  `User_Table`

  |idx|email|id|pwd|to_do_list_idx|
  |:---:|:---:|:---:|:---:|:---:|
  |1|test@ks.ac.kr|test1|12345|1|
  |1|test@ks.ac.kr|test1|12345|2|
  |...|...|...|...|...|
  |1|test@ks.ac.kr|test1|12345|200|

  `ToDoList_Table`

  |idx|completedDate|createdDate|description|status|
  |:---:|:---:|:---:|:---:|:---:|
  |1|-|Time()|description1|false|
  |2|Time()|Time()|description2|true|
  |...|...|...|...|...|
  |200|-|Time()|description200|false|

  `User` 클래스가 `FK`로 `ToDoList`를 포함하는 것은 적합하지 않다.
- `ToDoList` 클래스가 `FK`로 `User`를 포함 할 때

  |idx|completedDate|createdDate|description|status|user_idx|
  |:---:|:---:|:---:|:---:|:---:|:---:|

  ~~~java
  public class User{

    // more field

    @OneToMany(mappedBy = "user")
    private List<ToDoList> toDoLists;
  }
  ~~~
  ~~~java
  public Class ToDoList{

    // more filed

    @ManyToOne
    private User user;
  }
  ~~~

  `ToDoList`가 `FK`로 `User`를 소유하고 있을 경우 `ToDo`가 등록되어도 하나의 `User` 정보를 유지할 수 있다.

  `User_Table`

  |idx|email|id|pwd|
  |:---:|:---:|:---:|:---:|
  |1|test1@ks.ac.kr|test1|12345|
  |2|test2@ks.ac.kr|test2|12345|
  |3|test3@ks.ac.kr|test3|12345|

  `ToDoList_Table`

  |idx|completedDate|createdDate|description|status|user_idx|
  |:---:|:---:|:---:|:---:|:---:|:---:|
  |1|-|Time()|description1|false|1|
  |2|Time()|Time()|description2|true|1|
  |...|...|...|...|...|...|
  |199|Time()|Time()|description199|true|2|
  |200|-|Time()|description200|false|3|

  `ToDoList` 클래스가 `FK`로 `User`를 포함하여야 한다.
---
### 13일차  
- 빌더 패턴을 사용하지 않기 때문에 `@Builder` 및 생성자 삭제
- `ToDoList` 테이블에 `user_idx` 값만 들어가는 것이 아니라 현재 유저 객체(`this.user`)가 자신이 작성한 `ToDoList`를 가지고 있어야 한다.
- `FetchType`을 `LAZY`에서 `EAGER`로 변경한다.
- `List<ToDoList>`에 값을 추가하기 위하여 새로운 리스트를 생성한다.(`new ArrayList<>()`)
- `User` 객체의 `List<ToDoList>` 필드에 저장할 `ToDoList` 를 `add` 한다.
  ~~~java
  public Class User{

  // more field

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ToDoList> toDoList = new ArrayList<>();

    public void add(ToDoList toDoList){
        toDoList.setUser(this);
        getToDoList().add(toDoList);
      }
    }
    ~~~
- `tdl/list`로 `redirect`될 때 현재 유저가 가지고 있는 `ToDoList`객체 출력
---
### 14일차
`Spring Security` 적용해보기
- Security 테스트 프로젝트 생성
- `config` 패키지 및 `SecurityConfig` 클래스 생성
- Spring Security 의존성 추가
    ~~~
    dependencies {
        compile 'org.springframework.security:spring-security-web:4.2.7.RELEASE'
        compile 'org.springframework.security:spring-security-config:4.2.7.RELEASE'
        implementation 'org.springframework.boot:spring-boot-starter-web'
        implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
    }
    ~~~
- `WebSecurityConfigurerAdapter` 상속 및 `configure()` 메소드 오버라이딩
- `AuthenticationManagerBuilder` 클래스를 이용하여 인메모리 인증 코드 작성
- `login.html` `index.html` `user/index.html` 추가
- `application.yml` 설정
  ~~~
  server:
    port: 8080

  logging:
    level:
      root: WARN
      org.springframework.web: INFO
      org.springframework.security: INFO

  spring:
    thymeleaf:
      cache: false
  ~~~
- 실패
---
### 15일차
`Spring Security` 적용 재도전 - [참고 사이트](https://spring.io/guides/gs/securing-web/)
- 프로젝트 생성(Security)
- 의존성 설정(`build.gradle`)
  ~~~
  dependencies {
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("junit:junit")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.springframework.security:spring-security-test")
  }
  ~~~
- html 삽입(`home.html`, `hello.html`)
- View Controller 생성(`MvcConfig`) - url에 따라 View(`html`)를 뿌리는 역할
    1. `registry.addViewController("/home").setViewName
    ("home");`
        - `"/home"`일 때, `home.html`을 반환
    2. `registry.addViewController("/").setViewName("home");`
        - `/`일 때, `home.html`을 반환
    3. `registry.addViewController("/hello").setViewName("hello");`
        - `/hello`일 때, `hello.html`을 반환
    4. `registry.addViewController("/login").setViewName("login");`
        - `/login`일 때, `login.html`을 반환
        - `loing.html`을 아직 생성하지 않았기 때문에 반환하지 않고 오류 발생
- 로그인을 하지 않은 사용자가 `hello.html`을 볼 수 없게 설정
  1. `home.html` 에서 링크 클릭 시 login 페이지 이동
  2. 로그인이 된 사용자일 경우 `hello.html` 반환
- Security 사용을 위한 의존성 설정(`build.gradle`) 및 실행
  ~~~
  dependencies {

    // more dependencies
    compile("org.springframework.boot:spring-boot-starter-security")
  }
  ~~~
  1. 인증이 되지 않았기에 모든 url에 인증을 요청하게 됨
  2. 모든 url요청에 대해 Spring Security에서 기본 [로그인 페이지](./img/25.png) 반환
- `WebSecurityConfig` 클래스 생성 후 커스터마이징
  1. `WebSecurityConfigurerAdapter` 상속 및 `configure(HttpSecurity http)` 메소드 오버라이딩
      1. `.authorizeRequests()` : 요청에 대하여 보안을 어떻게 적용할 것인지 설정
      2. `.antMatchers()` : 특정한 패턴에 대해서
          - `antMatchers("/", "/home").permitAll()` : 두 가지 요청에 대해서는 인증을 하지 않아도 뷰를 보여줌
          - 일반적인 프로그램은 `css`, `js`등 도 존재하기에 이에 대한 요청도 허용 하여야 한다.
      3. `.anyRequest()` : 나머지 모든 요청들에 대해서는
      4. `.authenticated()` : 인증이 필요함을 명시
      5. `.formLogin()` : form 로그인과 관련된 설정
          - `.loginPage("/login")` : 로그인 페이지로 가는 `request` 설정
          - `.loginPage("/login").permitAll()` : 모든 사용자가 인증 없이 로그인 페이지로 이동 가능
      6. `.logout()` : 로그아웃에 관한 설정
  2. `userDetailsService()` 메소드 오버라이딩
      1. `UserDetails user` : 유저 객체 생성
          - `.withDefaultPasswordEncorder()` : 패스워드 인코딩 설정
          - `.userName()` : 이름 설정
          - `.password()` : 패스워드 설정
          - `.roles()` : 권한 설정
          - `new InMemoryUserDetailsManager(user)` 인메모리 방식으로 유저 저장
      2. `login.html` 생성
      3. `hello.html` 변경 : 로그인(인증) 사용자에 대한 정보 표시, 로그아웃 버튼 추가
- 출력 화면      
  1. `"/"`, `"/home"` [화면](./img/26.png)
  2. `"/login"` [화면](./img/27.png)
  3. 로그인(인증) 후 `"/hello"` [화면](./img/28.png)
  4. 로그아웃 후 `"/login?logout"` [화면](./img/29.png)
---
## 주요기능
### 1. 로그인 화면
![로그인](./img/21.png)
### 2. 회원가입 화면
![회원가입](./img/22.png)
### 3. 초기 화면
![초기1](./img/9.png)
### 4. To Do 등록
![등록1](./img/10.png)
![등록2](./img/11.png)
![등록3](./img/12.png)
### 5. To Do 완료
![완료1](./img/13.png)
### 6. To Do 삭제
![삭제1](./img/14.png)
![삭제2](./img/15.png)
### 7. To Do 수정
![수정1](./img/16.png)
![수정2](./img/17.png)
![수정3](./img/18.png)
![수정4](./img/19.png)
