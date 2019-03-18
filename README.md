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

### 4일차
- [View 수정](./img/7.png)

### 5일차
- footer 생성
- 폼 생성 및 등록 함수 생성(`postTDL`)
  1. input text 값 받아오기(`@RequestBody`, `MultiValueMap`)
  2. 서비스 호출 및 등록 로직 처리(`toDoListService.postList()`)
  3. DataBase 저장 및 redirect

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
