$ErrorActionPreference = "Stop"

Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$baseScratch = "C:\Users\_CEYLA~1\.gemini\antigravity\scratch"
$sdkDir = "$baseScratch\tools\android-sdk"
$buildTools = "$sdkDir\build-tools\34.0.0"
$androidJar = "$sdkDir\platforms\android-34\android.jar"
$jdkBin = "C:\Program Files\Microsoft\jdk-17.0.20.101-hotspot\bin"

$projDir = "$baseScratch\pipitalk"

Write-Host ">>> [1/6] Compiling Android resources with aapt2..."
if (Test-Path "$projDir\build\res.zip") { Remove-Item "$projDir\build\res.zip" -Force }
& "$buildTools\aapt2.exe" compile --dir "$projDir\res" -o "$projDir\build\res.zip"

Write-Host ">>> [2/6] Linking APK with aapt2..."
if (Test-Path "$projDir\build\unaligned.apk") { Remove-Item "$projDir\build\unaligned.apk" -Force }
& "$buildTools\aapt2.exe" link -o "$projDir\build\unaligned.apk" `
    -I "$androidJar" `
    --manifest "$projDir\AndroidManifest.xml" `
    --java "$projDir\build\gen" `
    "$projDir\build\res.zip"

Write-Host ">>> [3/6] Compiling Java classes with javac..."
if (-not (Test-Path "$projDir\build\obj")) { New-Item -ItemType Directory -Path "$projDir\build\obj" -Force | Out-Null }
& "$jdkBin\javac.exe" -encoding UTF-8 -source 1.8 -target 1.8 `
    -bootclasspath "$androidJar" `
    -classpath "$androidJar" `
    -d "$projDir\build\obj" `
    "$projDir\build\gen\com\ceylan\pipitalk\R.java" `
    "$projDir\src\com\ceylan\pipitalk\MainActivity.java"

Write-Host ">>> [4/6] Converting class files to DEX with d8..."
if (-not (Test-Path "$projDir\build\dex")) { New-Item -ItemType Directory -Path "$projDir\build\dex" -Force | Out-Null }
$classFiles = Get-ChildItem -Path "$projDir\build\obj" -Filter "*.class" -Recurse | ForEach-Object { $_.FullName }
& "$jdkBin\java.exe" -cp "$buildTools\lib\d8.jar" com.android.tools.r8.D8 `
    --lib "$androidJar" `
    --min-api 21 `
    --output "$projDir\build\dex" `
    $classFiles

Write-Host ">>> [5/6] Injecting classes.dex and web assets into APK..."
$zip = [System.IO.Compression.ZipFile]::Open("$projDir\build\unaligned.apk", [System.IO.Compression.ZipArchiveMode]::Update)
$existingDex = $zip.GetEntry("classes.dex")
if ($existingDex) { $existingDex.Delete() }
[System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile($zip, "$projDir\build\dex\classes.dex", "classes.dex") | Out-Null

$uiFiles = Get-ChildItem -Path "$projDir\assets" -Recurse -File
foreach ($f in $uiFiles) {
    $rel = $f.FullName.Substring("$projDir\assets\".Length).Replace('\', '/')
    $entryName = "assets/" + $rel
    $existing = $zip.GetEntry($entryName)
    if ($existing) { $existing.Delete() }
    [System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile($zip, $f.FullName, $entryName) | Out-Null
    Write-Host "Added APK asset: $entryName"
}
$zip.Dispose()

Write-Host ">>> [6/6] Zipaligning & Signing APK with Ceylan55 Play Store key..."
if (Test-Path "$projDir\build\aligned.apk") { Remove-Item "$projDir\build\aligned.apk" -Force }
& "$buildTools\zipalign.exe" -f 4 "$projDir\build\unaligned.apk" "$projDir\build\aligned.apk"

$keystore = "$projDir\keystore\pipitalk-release.jks"
$finalApk = "$projDir\PipiTalk_Release.apk"
$desktopApk = "C:\Users\_CEYLA~1\Desktop\PipiTalk_Release.apk"

& "$jdkBin\java.exe" -jar "$buildTools\lib\apksigner.jar" sign `
    --ks $keystore `
    --ks-key-alias "ceylan55" `
    --ks-pass pass:ceylan55pipitalk `
    --key-pass pass:ceylan55pipitalk `
    --out $finalApk `
    "$projDir\build\aligned.apk"

try {
    Copy-Item $finalApk $desktopApk -Force -ErrorAction SilentlyContinue
    Write-Output "Masaustune kopyalandi: $desktopApk"
} catch {}
Write-Output "APK DERLEME BASARILI! Dosya: $finalApk, Boyut: $((Get-Item $finalApk).Length) bytes"
