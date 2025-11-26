# 빌드 및 실행 가이드

## ✅ 준비 사항

1. JDK 17 설치 확인
2. 프로젝트 디렉토리로 이동: `cd C:\project\cursorSpringBoot`

## 🔧 빌드 방법

### 1. Clean Build (추천)
```bash
.\gradlew.bat clean build -x test
```

### 2. 컴파일만
```bash
.\gradlew.bat compileJava
```

### 3. 의존성 새로고침
```bash
.\gradlew.bat --refresh-dependencies clean build -x test
```

## 🚀 실행 방법

### 1. Gradle로 실행
```bash
.\gradlew.bat bootRun
```

### 2. JAR 파일로 실행
```bash
java -jar build\libs\cursorSpringBoot-0.0.1-SNAPSHOT.jar
```

## 🌐 접속 URL

애플리케이션이 실행되면 다음 URL로 접속:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **H2 Console**: http://localhost:8080/h2-console
- **API Base URL**: http://localhost:8080/api

## 🧪 페이징 API 테스트

### JPA 페이징
```bash
# 기본 페이징
curl http://localhost:8080/api/boards/paging?page=1&size=10

# 정렬
curl "http://localhost:8080/api/boards/paging?page=1&size=5&sortBy=views&sortDirection=DESC"

# 카테고리 필터
curl "http://localhost:8080/api/boards/paging?page=1&size=5&category=질문"
```

### MyBatis 페이징
```bash
# 기본 페이징
curl http://localhost:8080/api/boards/paging/mybatis?page=1&size=10

# 검색
curl "http://localhost:8080/api/boards/paging/mybatis?page=1&size=5&keyword=Spring"

# 카테고리 + 정렬
curl "http://localhost:8080/api/boards/paging/mybatis?page=1&size=5&category=공지사항&sortBy=views&sortDirection=DESC"
```

## 🔍 에러 확인

빌드 에러가 발생하면:

```bash
# 자세한 로그 확인
.\gradlew.bat clean build -x test --stacktrace

# 디버그 모드
.\gradlew.bat clean build -x test --debug
```

## 📝 H2 데이터베이스 접속 정보

```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (비워두기)
Driver Class: org.h2.Driver
```

## 🎯 주요 포트

- **애플리케이션**: 8080
- **H2 Console**: 8080/h2-console

## 💡 문제 해결

### 포트 충돌
```bash
# 8080 포트 사용 중인 프로세스 확인
netstat -ano | findstr :8080

# 프로세스 종료
taskkill /PID [프로세스ID] /F
```

### Java 프로세스 확인 및 종료
```powershell
# Java 프로세스 확인
Get-Process -Name java -ErrorAction SilentlyContinue

# Java 프로세스 모두 종료
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
```

### Gradle Daemon 정리
```bash
.\gradlew.bat --stop
```

## 🎊 성공 확인

애플리케이션이 성공적으로 시작되면 로그에 다음 메시지가 표시됩니다:

```
Started CursorSpringbootApplication in X.XXX seconds
샘플 데이터 초기화를 시작합니다...
샘플 데이터 초기화가 완료되었습니다.
```

이제 Swagger UI로 접속하여 API를 테스트할 수 있습니다!


