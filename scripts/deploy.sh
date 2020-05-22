#!/usr/bin/env bash

echo 'Building angular app..'

cd frontend

ng build --prod

cd ..

echo 'Coping angular app into string app..'

rm -R backend/src/main/resources/public/
cp -R frontend/dist/frontend/. backend/src/main/resources/public/

echo 'Packaging string app...'

cd backend
mvn clean
mvn package -DskipTests
cd ..

echo 'Coping files ...'

scp -r ~/.ssh/droplet_project11 \
	backend/target/corporate.jar \
	tolstov_m_v@161.35.88.48:/home/tolstov_m_v

echo 'Restart server..'

ssh -i ~/.ssh/droplet_project11 tolstov_m_v@161.35.88.48 << EOF

pgrep java | xargs kill -9
nohup java -jar corporate.jar > log.txt &

EOF

echo 'End'
