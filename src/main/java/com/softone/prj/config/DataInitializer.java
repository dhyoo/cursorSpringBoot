package com.softone.prj.config;

import com.softone.prj.entity.*;
import com.softone.prj.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BoardRepository boardRepository;
    private final MenuRepository menuRepository;
    private final ActivityRepository activityRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("샘플 데이터 초기화를 시작합니다...");

        // Role 데이터 초기화
        initializeRoles();
        
        // User 데이터 초기화
        initializeUsers();
        
        // Menu 데이터 초기화
        initializeMenus();
        
        // Board 데이터 초기화
        initializeBoards();
        
        // Activity 데이터 초기화
        initializeActivities();

        log.info("샘플 데이터 초기화가 완료되었습니다.");
    }

    private void initializeRoles() {
        List<Role> roles = Arrays.asList(
                Role.builder()
                        .name("시스템 관리자")
                        .code("ADMIN")
                        .description("모든 권한을 가진 최고 관리자")
                        .userCount(3)
                        .permissions(Arrays.asList("READ", "WRITE", "DELETE", "ADMIN"))
                        .createdAt(LocalDate.of(2024, 1, 1))
                        .build(),
                Role.builder()
                        .name("일반 관리자")
                        .code("MANAGER")
                        .description("일반 관리 권한을 가진 사용자")
                        .userCount(5)
                        .permissions(Arrays.asList("READ", "WRITE"))
                        .createdAt(LocalDate.of(2024, 1, 5))
                        .build(),
                Role.builder()
                        .name("일반 사용자")
                        .code("USER")
                        .description("기본 읽기 권한만 가진 사용자")
                        .userCount(24)
                        .permissions(Arrays.asList("READ"))
                        .createdAt(LocalDate.of(2024, 1, 10))
                        .build(),
                Role.builder()
                        .name("게스트")
                        .code("GUEST")
                        .description("제한된 읽기 권한")
                        .userCount(12)
                        .permissions(Arrays.asList("READ"))
                        .createdAt(LocalDate.of(2024, 2, 1))
                        .build(),
                Role.builder()
                        .name("에디터")
                        .code("EDITOR")
                        .description("콘텐츠 편집 권한")
                        .userCount(8)
                        .permissions(Arrays.asList("READ", "WRITE"))
                        .createdAt(LocalDate.of(2024, 2, 15))
                        .build(),
                Role.builder()
                        .name("모더레이터")
                        .code("MODERATOR")
                        .description("콘텐츠 관리 권한")
                        .userCount(6)
                        .permissions(Arrays.asList("READ", "WRITE", "DELETE"))
                        .createdAt(LocalDate.of(2024, 3, 1))
                        .build()
        );
        roleRepository.saveAll(roles);
        log.info("{}개의 역할이 생성되었습니다.", roles.size());
    }

    private void initializeUsers() {
        List<User> users = Arrays.asList(
                User.builder()
                        .name("홍길동")
                        .email("hong@example.com")
                        .role("ADMIN")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 1, 15))
                        .lastLogin(LocalDate.of(2024, 11, 3))
                        .build(),
                User.builder()
                        .name("김철수")
                        .email("kim@example.com")
                        .role("USER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 2, 20))
                        .lastLogin(LocalDate.of(2024, 11, 2))
                        .build(),
                User.builder()
                        .name("이영희")
                        .email("lee@example.com")
                        .role("USER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 3, 10))
                        .lastLogin(LocalDate.of(2024, 11, 1))
                        .build(),
                User.builder()
                        .name("박민수")
                        .email("park@example.com")
                        .role("MANAGER")
                        .status("비활성")
                        .createdAt(LocalDate.of(2024, 4, 5))
                        .lastLogin(LocalDate.of(2024, 10, 28))
                        .build(),
                User.builder()
                        .name("정수진")
                        .email("jung@example.com")
                        .role("USER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 5, 12))
                        .lastLogin(LocalDate.of(2024, 11, 3))
                        .build(),
                User.builder()
                        .name("최동현")
                        .email("choi@example.com")
                        .role("ADMIN")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 6, 18))
                        .lastLogin(LocalDate.of(2024, 11, 3))
                        .build(),
                User.builder()
                        .name("강미영")
                        .email("kang@example.com")
                        .role("USER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 7, 22))
                        .lastLogin(LocalDate.of(2024, 11, 2))
                        .build(),
                User.builder()
                        .name("윤태호")
                        .email("yoon@example.com")
                        .role("MANAGER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 8, 30))
                        .lastLogin(LocalDate.of(2024, 11, 1))
                        .build(),
                User.builder()
                        .name("임소연")
                        .email("lim@example.com")
                        .role("USER")
                        .status("비활성")
                        .createdAt(LocalDate.of(2024, 9, 15))
                        .lastLogin(LocalDate.of(2024, 10, 25))
                        .build(),
                User.builder()
                        .name("한지훈")
                        .email("han@example.com")
                        .role("USER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 10, 1))
                        .lastLogin(LocalDate.of(2024, 11, 3))
                        .build(),
                User.builder()
                        .name("조현우")
                        .email("jo@example.com")
                        .role("ADMIN")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 10, 10))
                        .lastLogin(LocalDate.of(2024, 11, 2))
                        .build(),
                User.builder()
                        .name("송지은")
                        .email("song@example.com")
                        .role("USER")
                        .status("활성")
                        .createdAt(LocalDate.of(2024, 10, 20))
                        .lastLogin(LocalDate.of(2024, 11, 3))
                        .build()
        );
        userRepository.saveAll(users);
        log.info("{}명의 사용자가 생성되었습니다.", users.size());
    }

    private void initializeMenus() {
        List<Menu> menus = Arrays.asList(
                Menu.builder()
                        .menuId("MENU_001")
                        .menuName("대시보드")
                        .menuPath("/dashboard")
                        .menuIcon("📊")
                        .parentMenuId(null)
                        .menuOrder(1)
                        .menuLevel(1)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("메인 대시보드")
                        .permissionType("READ")
                        .build(),
                Menu.builder()
                        .menuId("MENU_002")
                        .menuName("사용자 관리")
                        .menuPath("/users")
                        .menuIcon("👥")
                        .parentMenuId(null)
                        .menuOrder(2)
                        .menuLevel(1)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("사용자 관리 메뉴")
                        .permissionType("ADMIN")
                        .build(),
                Menu.builder()
                        .menuId("MENU_003")
                        .menuName("사용자 목록")
                        .menuPath("/users/list")
                        .menuIcon("📋")
                        .parentMenuId("MENU_002")
                        .menuOrder(1)
                        .menuLevel(2)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("사용자 목록 조회")
                        .permissionType("READ")
                        .build(),
                Menu.builder()
                        .menuId("MENU_004")
                        .menuName("사용자 등록")
                        .menuPath("/users/create")
                        .menuIcon("➕")
                        .parentMenuId("MENU_002")
                        .menuOrder(2)
                        .menuLevel(2)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("사용자 등록")
                        .permissionType("WRITE")
                        .build(),
                Menu.builder()
                        .menuId("MENU_005")
                        .menuName("권한 관리")
                        .menuPath("/roles")
                        .menuIcon("🔐")
                        .parentMenuId(null)
                        .menuOrder(3)
                        .menuLevel(1)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("권한 관리 메뉴")
                        .permissionType("ADMIN")
                        .build(),
                Menu.builder()
                        .menuId("MENU_006")
                        .menuName("조직 관리")
                        .menuPath("/organizations")
                        .menuIcon("🏢")
                        .parentMenuId(null)
                        .menuOrder(4)
                        .menuLevel(1)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("조직 관리 메뉴")
                        .permissionType("READ")
                        .build(),
                Menu.builder()
                        .menuId("MENU_007")
                        .menuName("시스템 설정")
                        .menuPath("/settings")
                        .menuIcon("⚙️")
                        .parentMenuId(null)
                        .menuOrder(5)
                        .menuLevel(1)
                        .isActive("Y")
                        .isVisible("Y")
                        .description("시스템 설정")
                        .permissionType("ADMIN")
                        .build()
        );
        menuRepository.saveAll(menus);
        log.info("{}개의 메뉴가 생성되었습니다.", menus.size());
    }

    private void initializeBoards() {
        List<Board> boards = Arrays.asList(
                Board.builder()
                        .title("환영합니다! 게시판 이용 안내")
                        .content("안녕하세요. 게시판에 오신 것을 환영합니다.\n\n게시판 이용 시 다음 사항을 준수해 주세요:\n1. 타인을 존중하는 글 작성\n2. 불법적인 내용 금지\n3. 스팸 및 광고 금지")
                        .author("홍길동")
                        .authorEmail("hong@example.com")
                        .views(156L)
                        .category("공지사항")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 1))
                        .updatedAt(LocalDate.of(2024, 11, 1))
                        .build(),
                Board.builder()
                        .title("Spring Boot 프로젝트 진행 중")
                        .content("현재 Spring Boot와 React를 연동하는 프로젝트를 진행하고 있습니다.\n\n주요 기능:\n- 사용자 관리\n- 권한 관리\n- 게시판 기능")
                        .author("김철수")
                        .authorEmail("kim@example.com")
                        .views(89L)
                        .category("일반")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 2))
                        .updatedAt(LocalDate.of(2024, 11, 2))
                        .build(),
                Board.builder()
                        .title("API 사용법 질문")
                        .content("REST API를 사용하는 방법에 대해 질문이 있습니다.\n\n특히 POST 요청 시 body에 데이터를 어떻게 보내야 하는지 알려주세요.")
                        .author("이영희")
                        .authorEmail("lee@example.com")
                        .views(45L)
                        .category("질문")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 2))
                        .updatedAt(LocalDate.of(2024, 11, 2))
                        .build(),
                Board.builder()
                        .title("프로젝트 회고")
                        .content("이번 프로젝트를 통해 많은 것을 배웠습니다.\n\n특히 React와 Spring Boot의 통신 방법에 대해 깊이 이해할 수 있었습니다.")
                        .author("박민수")
                        .authorEmail("park@example.com")
                        .views(67L)
                        .category("자유게시판")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("Swagger UI 사용 가이드")
                        .content("Swagger UI를 통해 API를 테스트하는 방법을 안내합니다.\n\n1. http://localhost:8080/swagger-ui/index.html 접속\n2. 원하는 API 선택\n3. Try it out 클릭\n4. 필요한 파라미터 입력 후 Execute")
                        .author("정수진")
                        .authorEmail("jung@example.com")
                        .views(123L)
                        .category("일반")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("권한 관리 시스템 개선 제안")
                        .content("현재 권한 관리 시스템을 개선하기 위한 제안입니다.\n\n제안 사항:\n1. 역할 기반 접근 제어 강화\n2. 조직/팀 단위 권한 관리\n3. 동적 권한 변경 기능")
                        .author("최동현")
                        .authorEmail("choi@example.com")
                        .views(34L)
                        .category("질문")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("React Hook 사용 팁")
                        .content("React Hook을 효율적으로 사용하는 방법을 공유합니다.\n\n주요 Hook:\n- useState: 상태 관리\n- useEffect: 사이드 이펙트 처리\n- useMemo: 메모이제이션\n- useCallback: 함수 메모이제이션")
                        .author("강미영")
                        .authorEmail("kang@example.com")
                        .views(234L)
                        .category("일반")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("Spring Boot 성능 최적화")
                        .content("Spring Boot 애플리케이션의 성능을 최적화하는 방법을 소개합니다.\n\n최적화 포인트:\n1. 데이터베이스 쿼리 최적화\n2. 캐싱 전략 수립\n3. 비동기 처리 활용")
                        .author("윤태호")
                        .authorEmail("yoon@example.com")
                        .views(178L)
                        .category("일반")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("TypeScript 타입 안정성 향상")
                        .content("TypeScript를 사용하여 타입 안정성을 높이는 방법을 설명합니다.\n\n주요 기법:\n- Generic 활용\n- Discriminated Union\n- 타입 가드 사용")
                        .author("임소연")
                        .authorEmail("lim@example.com")
                        .views(145L)
                        .category("질문")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("RESTful API 설계 원칙")
                        .content("RESTful API를 설계할 때 따라야 할 원칙들을 정리했습니다.\n\n원칙:\n1. 리소스 기반 URL 설계\n2. HTTP 메서드 적절히 사용\n3. 상태 코드 올바르게 사용\n4. HATEOAS 고려")
                        .author("한지훈")
                        .authorEmail("han@example.com")
                        .views(267L)
                        .category("일반")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("보안 업데이트 안내")
                        .content("시스템 보안을 강화하기 위한 업데이트를 진행합니다.\n\n주요 변경사항:\n- 인증 토큰 만료 시간 조정\n- 비밀번호 정책 강화\n- XSS 방지 강화")
                        .author("조현우")
                        .authorEmail("jo@example.com")
                        .views(312L)
                        .category("공지사항")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("게시판 기능 추가 계획")
                        .content("게시판에 추가될 기능들을 안내합니다.\n\n추가 예정 기능:\n1. 댓글 시스템\n2. 파일 첨부\n3. 좋아요 기능\n4. 검색 기능 강화")
                        .author("송지은")
                        .authorEmail("song@example.com")
                        .views(189L)
                        .category("공지사항")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build(),
                Board.builder()
                        .title("데이터베이스 마이그레이션 가이드")
                        .content("데이터베이스 마이그레이션을 수행하는 방법을 안내합니다.\n\n주요 단계:\n1. 백업 수행\n2. 스키마 변경 스크립트 작성\n3. 데이터 마이그레이션\n4. 검증 및 롤백 계획 수립")
                        .author("홍길동")
                        .authorEmail("hong@example.com")
                        .views(98L)
                        .category("자유게시판")
                        .status("공개")
                        .createdAt(LocalDate.of(2024, 11, 3))
                        .updatedAt(LocalDate.of(2024, 11, 3))
                        .build()
        );
        boardRepository.saveAll(boards);
        log.info("{}개의 게시글이 생성되었습니다.", boards.size());
    }

    private void initializeActivities() {
        List<Activity> activities = Arrays.asList(
                Activity.builder()
                        .user("홍길동")
                        .action("새 사용자 등록")
                        .target("김철수")
                        .time("2분 전")
                        .type("create")
                        .build(),
                Activity.builder()
                        .user("이영희")
                        .action("권한 수정")
                        .target("일반 사용자")
                        .time("15분 전")
                        .type("update")
                        .build(),
                Activity.builder()
                        .user("박민수")
                        .action("사용자 삭제")
                        .target("임시계정")
                        .time("1시간 전")
                        .type("delete")
                        .build(),
                Activity.builder()
                        .user("정수진")
                        .action("역할 생성")
                        .target("에디터")
                        .time("2시간 전")
                        .type("create")
                        .build(),
                Activity.builder()
                        .user("최동현")
                        .action("권한 수정")
                        .target("관리자")
                        .time("3시간 전")
                        .type("update")
                        .build(),
                Activity.builder()
                        .user("강미영")
                        .action("사용자 등록")
                        .target("신규사용자")
                        .time("5시간 전")
                        .type("create")
                        .build(),
                Activity.builder()
                        .user("윤태호")
                        .action("권한 삭제")
                        .target("게스트")
                        .time("1일 전")
                        .type("delete")
                        .build(),
                Activity.builder()
                        .user("임소연")
                        .action("사용자 수정")
                        .target("기존사용자")
                        .time("1일 전")
                        .type("update")
                        .build()
        );
        activityRepository.saveAll(activities);
        log.info("{}개의 활동 로그가 생성되었습니다.", activities.size());
    }
}

