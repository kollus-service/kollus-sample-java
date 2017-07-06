kollus-sample-java
==================

Description
-
Kollus와의 연동을 위한 Java 기반의 예제를 실행할수 있는 프로젝트입니다. 

적용 예제는 다음과 같습니다.
>
0. One Page Test --> 아래의 모든 기능을 한페이지에서 실행해 볼수 있습니다.
1. Web Token 생성 및  디코딩
2. HTTP Endpoint Upload
3. Video Gateway Query String 생성기
4. Player Controller for javascript
5. Play Callback
6. DRM Callback
7. LMS Callback

Runtime Environment
-
>
- JDK 1.8
- Maven 3.2.3

Configuration Files
-
>
*/src/main/webapp/files/user.properties : Kollus 계정정보 파일
*/src/main/webapp/files/policy/default.json : Kollus callback 정책 설정 파일

How to run
-
embeded-tomcat 8.0 이 구성이 되어 있어 따로 웹서버를 구성하실 필요가 없습니다.

    $ git clone https://github.com/kollus-service/kollus-sample-java.git
    $ mvn package
    $ cd target/bin

- Linux or MacOSX

    $  ./kollus-sample-java
    
- Windows

    $ kollus-sample-java.bat


- Browser
> http://localhost

Cautions
-
> * 다른 어플리케이션에서 80번 포트를 이용할 경우 충돌이 일어날수 있습니다.

