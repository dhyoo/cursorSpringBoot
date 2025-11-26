Set-Location C:\project\cursorSpringBoot
.\gradlew.bat clean compileJava 2>&1 | Out-File -FilePath "build-output.txt" -Encoding UTF8
Get-Content "build-output.txt"


