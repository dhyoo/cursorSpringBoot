# 빠른 시작 스크립트
Write-Host "================================" -ForegroundColor Cyan
Write-Host "  Spring Boot 빌드 & 실행" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# 현재 실행 중인 Java 프로세스 확인 및 종료
Write-Host "[1/4] 기존 Java 프로세스 종료 중..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# 빌드
Write-Host "[2/4] 프로젝트 빌드 중..." -ForegroundColor Yellow
.\gradlew.bat clean build -x test

if ($LASTEXITCODE -eq 0) {
    Write-Host "[3/4] 빌드 성공!" -ForegroundColor Green
    
    # 실행
    Write-Host "[4/4] 애플리케이션 시작 중..." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  Swagger UI: http://localhost:8080/swagger-ui/index.html" -ForegroundColor Green
    Write-Host "  H2 Console: http://localhost:8080/h2-console" -ForegroundColor Green  
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    
    .\gradlew.bat bootRun
} else {
    Write-Host "[3/4] 빌드 실패!" -ForegroundColor Red
    Write-Host "에러 로그를 확인하세요." -ForegroundColor Red
    exit 1
}


