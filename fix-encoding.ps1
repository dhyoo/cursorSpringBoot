# UTF-8 인코딩 및 한글 복구 스크립트
$ErrorActionPreference = "Continue"

$files = @(
    "src\main\java\com\softone\prj\service\UserService.java",
    "src\main\java\com\softone\prj\service\RoleService.java",
    "src\main\java\com\softone\prj\service\MenuService.java",
    "src\main\java\com\softone\prj\service\BoardService.java",
    "src\main\java\com\softone\prj\mapper\EntityMapper.java",
    "src\main\java\com\softone\prj\exception\ResourceNotFoundException.java",
    "src\main\java\com\softone\prj\exception\GlobalExceptionHandler.java",
    "src\main\java\com\softone\prj\entity\User.java",
    "src\main\java\com\softone\prj\entity\Board.java",
    "src\main\java\com\softone\prj\dto\UserDto.java",
    "src\main\java\com\softone\prj\dto\DashboardStatsDto.java",
    "src\main\java\com\softone\prj\dto\BoardDto.java",
    "src\main\java\com\softone\prj\controller\UserController.java",
    "src\main\java\com\softone\prj\controller\RoleController.java",
    "src\main\java\com\softone\prj\controller\MenuController.java",
    "src\main\java\com\softone\prj\controller\DashboardController.java",
    "src\main\java\com\softone\prj\controller\BoardController.java",
    "src\main\java\com\softone\prj\controller\ActivityController.java",
    "src\main\java\com\softone\prj\config\SwaggerConfig.java",
    "src\main\java\com\softone\prj\config\SecurityConfig.java",
    "src\main\java\com\softone\prj\config\CorsConfig.java",
    "src\main\java\com\softone\prj\config\DataInitializer.java"
)

$replacements = @{
    '?ъ슜??'                        = '사용자'
    '????'                           = '역할'
    '寃뚯\?\?\?'                     = '게시글'
    '寃뚯떆湲?'                      = '게시글'
    '\?대\?'                         = '이미'
    '議댁옱'                         = '존재'
    '?대찓'                          = '이메일'
    '?덈떎'                          = '입니다'
    '?쒖꽦'                          = '활성'
    '?대\? 議댁옱?섎뒗 ?대찓?쇱엯?덈떎' = '이미 존재하는 이메일입니다'
    '\?깃났'                         = '성공'
    '?섎せ'                          = '잘못된'
    '?붿껌'                          = '요청'
    '由ъ냼??'                        = '리소스를'
    '李얠쓣 ???놁쓬'                  = '찾을 수 없음'
    '?쒕쾭'                          = '서버'
    '?대\?'                          = '내부'
    '?ㅻ쪟'                          = '오류'
}

$count = 0
foreach ($file in $files) {
    if (Test-Path $file) {
        $content = Get-Content $file -Raw -Encoding Default
        
        foreach ($key in $replacements.Keys) {
            $content = $content -replace [regex]::Escape($key), $replacements[$key]
        }
        
        # UTF-8 without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file, $content, $utf8NoBom)
        
        $count++
        Write-Host "$count. $file - 수정 완료" -ForegroundColor Green
    }
}

Write-Host "`n총 $count개 파일 수정 완료!" -ForegroundColor Cyan


