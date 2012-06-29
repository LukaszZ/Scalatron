@echo off
echo Deploy To scalatron
echo %1
copy ./target/ScalatronBot.jar %1


rem cd %1
rem java -jar 