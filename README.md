# Project-TDL
스프링 부트를 이용한 To Do List 프로젝트

## 개발환경
|도구|버전|
|:---:|:---:|
|Spring|Spring Boot 2.1.3.RELEASE
|운영체제|Mac OS X|
|개발 툴|IntelliJ IDEA Ultimate 2018.3|
|JDK|JDK 8|
|데이터베이스|MySQL 8.0.3|
|빌드 툴|Gradle 5.2.1|

### 1일차
- [프로젝트 생성 오류](./img/1.jpeg) / 해결 : IntelliJ 2018.3 다운로드
- TDL 클래스 설계
  1. 키(`idx`) - Integer
  2. 내용(`description`) - String
  3. 완료 여부(`status`) - Boolean
  4. 생성 시간(`createdDate`) - LocalDateTime
  5. 완료 시간(`completedDate`) - LocalDateTime
- 프로젝트 생성

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

### 3일차
- [View 수정](./img/6.png)
