# ✅ 페이징 기능 구현 완료

## 📋 구현 내용

### 1. 공통 DTO 생성

#### PageRequest.java
- **위치**: `src/main/java/com/softone/prj/dto/PageRequest.java`
- **기능**:
  - 페이지 번호 (page, 1부터 시작)
  - 페이지 크기 (size, 기본 10)
  - 정렬 필드 (sortBy)
  - 정렬 방향 (sortDirection, ASC/DESC)
  - JPA Pageable 변환 메서드
  - MyBatis용 offset/limit 계산 메서드
  - 유효성 검증

#### PageResponse.java
- **위치**: `src/main/java/com/softone/prj/dto/PageResponse.java`
- **기능**:
  - 현재 페이지 번호
  - 페이지 크기
  - 전체 항목 수
  - 전체 페이지 수
  - 첫/마지막 페이지 여부
  - 데이터 목록
  - JPA Page 객체 변환
  - MyBatis 결과 변환

---

## 2. MyBatis 설정

### BoardMapper Interface
- **위치**: `src/main/java/com/softone/prj/mapper/BoardMapper.java`

```java
@Mapper
public interface BoardMapper {
    // 전체 목록 페이징
    List<BoardDto> selectBoardsWithPaging(PageRequest pageRequest);
    long countBoards();
    
    // 카테고리 필터 페이징
    List<BoardDto> selectBoardsByCategoryWithPaging(@Param("category") String category, @Param("pageRequest") PageRequest pageRequest);
    long countBoardsByCategory(@Param("category") String category);
    
    // 검색 페이징
    List<BoardDto> searchBoardsWithPaging(@Param("keyword") String keyword, @Param("pageRequest") PageRequest pageRequest);
    long countSearchBoards(@Param("keyword") String keyword);
}
```

### BoardMapper.xml
- **위치**: `src/main/resources/mapper/BoardMapper.xml`
- **기능**:
  - 동적 ORDER BY 절
  - LIMIT/OFFSET 처리
  - 검색 쿼리 (LIKE)

---

## 3. JPA Repository 확장

### BoardRepository
- **위치**: `src/main/java/com/softone/prj/repository/BoardRepository.java`

```java
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 카테고리 페이징
    Page<Board> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);
    
    // 검색 페이징
    Page<Board> findByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);
    Page<Board> findByContentContainingOrderByCreatedAtDesc(String content, Pageable pageable);
    Page<Board> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(
            String title, String content, Pageable pageable);
}
```

---

## 4. Service Layer

### BoardService 메서드

#### JPA 페이징
```java
// 전체 목록
PageResponse<BoardDto> getBoardsWithPagingJPA(PageRequest pageRequest)

// 카테고리 필터
PageResponse<BoardDto> getBoardsByCategoryWithPagingJPA(String category, PageRequest pageRequest)
```

#### MyBatis 페이징
```java
// 전체 목록
PageResponse<BoardDto> getBoardsWithPagingMyBatis(PageRequest pageRequest)

// 카테고리 필터
PageResponse<BoardDto> getBoardsByCategoryWithPagingMyBatis(String category, PageRequest pageRequest)

// 검색
PageResponse<BoardDto> searchBoardsWithPaging(String keyword, PageRequest pageRequest)
```

---

## 5. Controller API

### BoardController

#### JPA 페이징 엔드포인트
```http
GET /api/boards/paging

Query Parameters:
- page: 페이지 번호 (기본값: 1)
- size: 페이지 크기 (기본값: 10)
- sortBy: 정렬 필드 (createdAt, title, views, updatedAt)
- sortDirection: 정렬 방향 (ASC, DESC, 기본값: DESC)
- category: 카테고리 필터 (선택사항)
```

#### MyBatis 페이징 엔드포인트
```http
GET /api/boards/paging/mybatis

Query Parameters:
- page: 페이지 번호 (기본값: 1)
- size: 페이지 크기 (기본값: 10)
- sortBy: 정렬 필드 (createdAt, title, views, updatedAt)
- sortDirection: 정렬 방향 (ASC, DESC, 기본값: DESC)
- category: 카테고리 필터 (선택사항)
- keyword: 검색 키워드 (선택사항, 제목/내용/작성자)
```

---

## 📡 API 사용 예제

### 1. 기본 페이징 조회 (JPA)
```bash
GET http://localhost:8080/api/boards/paging?page=1&size=10
```

### 2. 정렬 조회
```bash
GET http://localhost:8080/api/boards/paging?page=1&size=10&sortBy=views&sortDirection=DESC
```

### 3. 카테고리 필터 (JPA)
```bash
GET http://localhost:8080/api/boards/paging?page=1&size=10&category=질문
```

### 4. 검색 (MyBatis)
```bash
GET http://localhost:8080/api/boards/paging/mybatis?page=1&size=10&keyword=Spring
```

### 5. 카테고리 + 정렬 (MyBatis)
```bash
GET http://localhost:8080/api/boards/paging/mybatis?page=1&size=10&category=공지사항&sortBy=createdAt&sortDirection=DESC
```

---

## 📊 응답 형식

```json
{
  "success": true,
  "data": {
    "currentPage": 1,
    "pageSize": 10,
    "totalElements": 13,
    "totalPages": 2,
    "first": true,
    "last": false,
    "empty": false,
    "content": [
      {
        "id": 1,
        "title": "Spring Boot 시작하기",
        "content": "Spring Boot는...",
        "author": "홍길동",
        "authorEmail": "hong@example.com",
        "views": 150,
        "category": "공지사항",
        "status": "공개",
        "createdAt": "2024-01-15",
        "updatedAt": "2024-01-15"
      }
      // ... 더 많은 게시글
    ]
  },
  "timestamp": "2024-11-06T16:30:00"
}
```

---

## 🔧 주요 기능

### 1. 페이징
✅ 페이지 번호 (1-based)  
✅ 페이지 크기 (최대 100개 제한)  
✅ 전체 항목 수  
✅ 전체 페이지 수  
✅ 첫/마지막 페이지 여부  

### 2. 정렬
✅ 다중 필드 정렬 (createdAt, title, views, updatedAt)  
✅ ASC/DESC 방향  
✅ 기본 정렬: createdAt DESC  

### 3. 필터링
✅ 카테고리 필터  
✅ 검색 (제목, 내용, 작성자)  

### 4. 유효성 검증
✅ 페이지 번호 최소값 1  
✅ 페이지 크기 1~100  
✅ 정렬 방향 ASC/DESC 검증  

---

## 🎯 JPA vs MyBatis 비교

| 특징 | JPA | MyBatis |
|------|-----|---------|
| **구현 방식** | Repository 메서드 | XML SQL 쿼리 |
| **페이징** | Pageable 자동 처리 | LIMIT/OFFSET 수동 |
| **정렬** | Sort 객체 | ORDER BY 동적 생성 |
| **성능** | 단순 쿼리에 최적화 | 복잡한 쿼리에 유리 |
| **유지보수** | 메서드 이름으로 쿼리 생성 | SQL 직접 제어 |
| **학습 곡선** | 쉬움 | 보통 |

---

## 💡 사용 권장 사항

### JPA 사용 권장
- 단순 CRUD
- 표준 정렬/필터링
- 빠른 개발

### MyBatis 사용 권장
- 복잡한 JOIN
- 동적 쿼리
- 성능 최적화 필요
- 레거시 SQL 활용

---

## 🚀 테스트 방법

### 1. Swagger UI 접속
```
http://localhost:8080/swagger-ui/index.html
```

### 2. Board API 섹션 찾기

### 3. JPA 페이징 테스트
- **GET /api/boards/paging** 선택
- Try it out 클릭
- 파라미터 입력:
  - page: 1
  - size: 5
  - sortBy: createdAt
  - sortDirection: DESC
- Execute 클릭

### 4. MyBatis 페이징 테스트
- **GET /api/boards/paging/mybatis** 선택
- Try it out 클릭
- 파라미터 입력:
  - page: 1
  - size: 5
  - keyword: "질문"
- Execute 클릭

---

## ✨ 장점

1. **범용성**: JPA와 MyBatis 모두 지원
2. **재사용성**: PageRequest/PageResponse 공통 DTO
3. **확장성**: 다른 Entity에도 쉽게 적용 가능
4. **유효성 검증**: 자동 파라미터 검증
5. **문서화**: Swagger에 자동 표시

---

## 📝 다른 Entity에 적용 방법

### 1. Mapper 인터페이스 생성
```java
@Mapper
public interface UserMapper {
    List<UserDto> selectUsersWithPaging(PageRequest pageRequest);
    long countUsers();
}
```

### 2. Mapper.xml 작성
```xml
<select id="selectUsersWithPaging" parameterType="PageRequest" resultType="UserDto">
    SELECT * FROM users
    ORDER BY created_at ${sortDirection}
    LIMIT #{limit} OFFSET #{offset}
</select>
```

### 3. Service 메서드 추가
```java
public PageResponse<UserDto> getUsersWithPaging(PageRequest pageRequest) {
    pageRequest.validate();
    List<UserDto> users = userMapper.selectUsersWithPaging(pageRequest);
    long total = userMapper.countUsers();
    return PageResponse.of(users, total, pageRequest);
}
```

### 4. Controller 엔드포인트 추가
```java
@GetMapping("/paging")
public ResponseEntity<ApiResponse<PageResponse<UserDto>>> getUsersWithPaging(
    @RequestParam(defaultValue = "1") Integer page,
    @RequestParam(defaultValue = "10") Integer size
) {
    PageRequest pageRequest = PageRequest.builder()
            .page(page)
            .size(size)
            .build();
    return ResponseEntity.ok(ApiResponse.success(userService.getUsersWithPaging(pageRequest)));
}
```

---

## 🎊 완료!

**JPA와 MyBatis 모두에서 사용 가능한 범용 페이징 시스템이 구현되었습니다!**

### 즉시 사용 가능:
✅ GET /api/boards/paging (JPA)  
✅ GET /api/boards/paging/mybatis (MyBatis)  

### Swagger로 테스트:
👉 http://localhost:8080/swagger-ui/index.html


