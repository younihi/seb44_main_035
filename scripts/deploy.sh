#!/bin/bash

REPOSITORY=/home/ubuntu
PROJECT_NAME=seb44_main_035

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"
git pull

echo "> project build start"
./gradlew build

echo "> directory로 이동"
cd $REPOSITORY

echo "> build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/server/build/libs/server-0.0.1-SNAPSHOT.jar $REPOSITORY/

echo "> 삭제할 파일 확인 및 삭제"
FILE_TO_DELETE=$REPOSITORY/$PROJECT_NAME/server/gradlew.bat
if [ -f $FILE_TO_DELETE ]; then
  echo "> $FILE_TO_DELETE 파일이 존재하여 삭제합니다."
  rm $FILE_TO_DELETE
fi

echo "> 현재 실행중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -t $REPOSITORY/*.jar | head -n 1)

echo "> Jar Name: $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
