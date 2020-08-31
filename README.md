# GSMin < Android-app >

광주소프트웨어마이스터고등학교 취업 커뮤니티 사이트

## 기술 스택

- Java
- JsonTask
- Android API

## APK 설치 방법

QR코드와 구글 드라이브를 이용함

​	![gsmin_qr](C:\dev\GSMin\app\release\gsmin_qr.jpg)

# 기능

### 1. SplashActivity

앱이 시작하기 전 보여지는 Splash 페이지

![image-20200831171616077.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831171616077.png?raw=true)

##### FILE

```
SplashActivity.java # 스플레쉬 이벤트 처리
Login.java # 자동 로그인을 위한 데이터 처리
```

### 2. Sign in & up

로그인과 회원가입이 가능함. 회원가입은 학생의 고유 이메일을 통해 인증 후 가능

![image-20200831171020514.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831171020514.png?raw=true)

##### FILE

```
StartActivity.java # 로그인과 회원가입을 선택
LoginActivity.java # 로그인을 진행하는 페이지
TitleActivity.java # 학생 이메일 인증 페이지
InfoActivity.java # 회원가입 정보를 서버와 통신
Login.java # 로그인 데이터를 JsonTask로 서버와 통신
Data.java # 로그인한 사용자의 정보를 담음
```

### 3. Get/Write Board

JsonTask를 활용하여 서버에서 게시글 데이터를 가져와 RecyclerView로 뿌려줌

![image-20200831171232275.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831171232275.png?raw=true)

##### FILE

```
MainActivity.java # Fragment 총괄
HomeFragment.java # 게시판 타입 선택
BoardActivity.java # 게시글 선택
WriteActivity.java # 게시글을 쓰고 서버로 전달
```

### 4.  Bulletin

게시글 클릭 후 보이는 게시글 세부 화면

![image-20200831173014308.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831173014308.png?raw=true)

##### FILE

```
BulletinActivity.java # 게시글 세부 정보
```



### 5. MyWrite

내가 쓴 게시글들을 불러와 보여줌

![image-20200831173029410.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831173029410.png?raw=true)



##### FILE

```
MainActivity.java # Fragment 총괄
MyWriteFragment.java # 내가 쓴 게시물 보여줌
HomeRecyclerViewAdapter.java # 리사이클러뷰
```



### 6. MyChat

내가 쓴 댓글을 불러와 보여줌

![image-20200831173042166.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831173042166.png?raw=true)

##### FILE

```
MainActivity.java # Fragment 총괄
MyChatFragment.java # 내가 쓴 댓글 보여줌
HomeRecyclerViewAdapter.java # 리사이클러뷰
```



### 7. Setting

닉네임을 재설정할 수 있는 페이지

![image-20200831173052152.png](https://github.com/hangyeol0531/GSMin/blob/GSMin_App/README/image-20200831173052152.png?raw=true)

##### FILE

```
MainActivity.java # Fragment 총괄
SettingFragment.java # 닉네임 설정
HomeRecyclerViewAdapter.java # 리사이클러뷰
```



## etc API

```
Sweet Alert Dialog
Singleton
SharedPreference
RecyclerView
swiperefreshlayout
MarkdownViewSupport
```
