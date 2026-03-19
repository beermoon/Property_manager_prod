# 부동산 관리 프로그램

지도 기반으로 부동산 매물을 조회·등록·관리할 수 있는 프로그램입니다.  
기존 엑셀 중심의 매물 관리 방식에서 벗어나, 위치와 매물 정보를 더 직관적으로 확인할 수 있도록 구현했습니다.

---

## 1. 프로젝트 개요

기존에는 부동산 매물 정보를 엑셀로 관리하고 있어, 위치와 정보를 직관적으로 확인하기 어렵다는 불편이 있었습니다.  
이를 개선하기 위해 지도 기반으로 매물 정보를 조회하고 관리할 수 있는 프로그램을 개발했습니다.

### 개발 목적
- 엑셀 중심의 매물 관리 방식 개선
- 지도 기반으로 매물 위치와 정보를 직관적으로 확인
- 매물 등록, 수정, 삭제를 포함한 관리 기능 제공

---

## 2. 주요 기능

- **지도 기반 매물 조회**
  - 네이버 지도 API를 활용해 지도 위에서 매물 위치와 정보를 확인할 수 있습니다.

- **매물 등록 / 수정 / 삭제**
  - 부동산 매물 정보를 등록·수정·삭제할 수 있습니다.

- **사진 업로드 및 관리**
  - 매물 사진을 업로드하고 관리할 수 있습니다.

- **메모 및 옵션 관리**
  - 매물별 메모와 옵션 정보를 함께 저장하고 확인할 수 있습니다.

- **조건 기반 매물 확인**
  - 거래 유형, 상태, 옵션 등의 조건을 기반으로 원하는 매물을 구분할 수 있습니다.

---

## 3. 기술 스택

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- HTML
- CSS
- JavaScript

### Database
- SQLite
- PostgreSQL

### Infra / Tools
- AWS RDS
- Git
- GitHub

### External API
- Naver Maps JavaScript API
- Geocoding API

---

## 4. 아키텍처

> 전체 아키텍처 이미지를 여기에 추가

예시 흐름:

- 사용자 → 브라우저
- Frontend (Thymeleaf / HTML / CSS / JavaScript)
- Backend (Spring Boot)
- Database
  - SQLite (실사용 로컬 환경)
  - PostgreSQL + AWS RDS (배포 테스트 환경)
- External API
  - Naver Maps API
  - Geocoding API
- File Storage
  - Property Photos

---

## 5. 실행 방법

### 1) 저장소 클론
```bash
git clone [레포지토리 주소]
cd [프로젝트 폴더명]
