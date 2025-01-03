# spring_vote_20th

## 배포 - blue green 무중단 배포
### blue green 배포란?
https://github.com/user-attachments/assets/d603aaa1-c8fc-4ef2-829a-629b2a35b09e

```yaml
spring:
  profiles:
    active: local
    group:
      local: local, common, secret
      blue: blue, common, secret
      green: green, common, secret

---

spring:
  config:
    activate:
    on-profile: blue

server:
  port: 8080
  serverAddress: 13.124.171.94

serverName: blue_server

---

spring:
  config:
    activate:
      on-profile: common
    import: optional:file:.env[.properties]
```
application.yml


### nginx load balancing
- blue server - 8080 port
- green server - 8081 port

http://52.79.122.106 → nginx로 8080 or 8081 port 중 실행 중인 port로 연결


### github action
```yaml


# event trigger
# main브랜치에 pull request가 발생 되었을 때 실행
on:
  pull_request:
    branches: [ "master" ]
  push:
    branches: [ "master" ]

jobs:
  build:
    steps:
      - name: Checkout source

      - name: Setup Java

      - name: Gradle Caching

      - name: Grant execute permission for gradlew

      - name: Build Project with Gradle

      - name: Login to DockerHub
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_TOKEN }}

      - name: Build docker image
        run: docker build --platform linux/amd64 -t ${{ secrets.DOCKER_USERNAME }}/vote .

      - name: Publish image to docker hub
        run: docker push ${{ secrets.DOCKER_USERNAME }}/vote:latest

  deploy:
    steps:
      - name: Set Target IP
        run: |
          STATUS=$(curl -o /dev/null -w "%{http_code}" "${{ secrets.SERVER_IP }}/env")
          echo $STATUS
          if [ $STATUS = 200 ]; then
            CURRENT_UPSTREAM=$(curl -s "${{ secrets.SERVER_IP }}/env")
          else
            CURRENT_UPSTREAM=green
          fi
          echo CURRENT_UPSTREAM=$CURRENT_UPSTREAM >> $GITHUB_ENV
          if [ $CURRENT_UPSTREAM = blue ]; then
            echo "CURRENT_PORT=8080" >> $GITHUB_ENV
            echo "STOPPED_PORT=8081" >> $GITHUB_ENV
            echo "TARGET_UPSTREAM=green" >> $GITHUB_ENV
          else
            echo "CURRENT_PORT=8081" >> $GITHUB_ENV
            echo "STOPPED_PORT=8080" >> $GITHUB_ENV
            echo "TARGET_UPSTREAM=blue" >> $GITHUB_ENV
          fi

      - name: Docker Compose
        with:
          script: |
            export JWT_SECRET_KEY=${{ secrets.JWT_SECRET_KEY }}
            sudo docker pull ${{ secrets.DOCKER_USERNAME }}/vote:latest
            sudo docker-compose -f docker-compose-${{env.TARGET_UPSTREAM}}.yml up -d

      - name: Check deploy server URL
        with:
          url: http://${{ secrets.SERVER_IP }}:${{ env.STOPPED_PORT }}/env

      - name: Change nginx upstream
        with:
          script: |
            sudo docker exec -i nginxserver bash -c 'echo "set \$service_url ${{env.TARGET_UPSTREAM}};" > etc/nginx/conf.d/service-env.inc && nginx -s reload'

      - name: Stop current server
        with:
          script: |
            sudo docker stop ${{ env.CURRENT_UPSTREAM }}
            sudo docker rm ${{ env.CURRENT_UPSTREAM }}

```

### trouble shooting
**JWT_SECRET_KEY를 읽지 못해서 발생하는 빌드 에러**

1. git secrets에 JWT_SECRET_KEY 변수를 추가 → docker-compose 파일을 실행할 때 넣어주려고 했으나 인식하지 못하는 문제가 발생함.
2. CICD.yml 파일 내에서 $GITHUB_ENV에 JWT_SECRET_KEY값을 저장 → 키가 외부에 노출되면 안되기 때문에 보안 문제 발생
3. docker-compse.yml 파일에서 직접 추가 → 성공
    ```yaml
    environment:
      - JWT_SECRET_KEY=
    ```


**보안 규칙**
1. 8080 port, 8081 port를 번갈아가며 사용하기 때문에 두 port에 대한 inbound 규칙 추가