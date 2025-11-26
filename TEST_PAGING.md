# 페이징 테스트 가이드

## 빌드 & 실행

```bash
# 빌드
.\gradlew.bat clean build -x test

# 실행
.\gradlew.bat bootRun
```

## API 테스트

### 1. JPA 페이징 - 기본
```bash
curl http://localhost:8080/api/boards/paging?page=1&size=5
```

### 2. JPA 페이징 - 조회수 정렬
```bash
curl "http://localhost:8080/api/boards/paging?page=1&size=5&sortBy=views&sortDirection=DESC"
```

### 3. JPA 페이징 - 카테고리 필터
```bash
curl "http://localhost:8080/api/boards/paging?page=1&size=5&category=질문"
```

### 4. MyBatis 페이징 - 기본
```bash
curl http://localhost:8080/api/boards/paging/mybatis?page=1&size=5
```

### 5. MyBatis 페이징 - 검색
```bash
curl "http://localhost:8080/api/boards/paging/mybatis?page=1&size=5&keyword=Spring"
```

### 6. MyBatis 페이징 - 카테고리 + 정렬
```bash
curl "http://localhost:8080/api/boards/paging/mybatis?page=1&size=5&category=공지사항&sortBy=views&sortDirection=DESC"
```

## 예상 결과

```json
{
  "success": true,
  "data": {
    "currentPage": 1,
    "pageSize": 5,
    "totalElements": 13,
    "totalPages": 3,
    "first": true,
    "last": false,
    "empty": false,
    "content": [...]
  },
  "timestamp": "2024-11-06T16:30:00"
}
```


