-- 초기화용
-- DROP DATABASE IF EXISTS `final_pjt`;

CREATE DATABASE IF NOT EXISTS `final_pjt` CHAR SET utf8mb4;
USE `final_pjt`;

CREATE TABLE IF NOT EXISTS `user`(
	-- 회원 가입 시 필수 요소만 NOT NULL 조건
	-- UNIQUE로 중복 방지
    -- 표준 최대값 255
	`name` VARCHAR(20) NOT NULL,
	id VARCHAR(60) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(60) NOT NULL,
    -- 0~255로 제한
    age TINYINT NOT NULL,
    img VARCHAR(255),
    -- M or F
    sex CHAR(1) NOT NULL,
    -- 최대 5글자, 소수점 아래 2자리까지 (프론트에서 제한 걸어야 함. 오류 방지)
    goalWeight DOUBLE(5, 2),
    nowWeight DOUBLE(5, 2),
    height DOUBLE(5,2),
    isadmin BOOLEAN DEFAULT FALSE NOT NULL
);

-- 게시판
CREATE TABLE IF NOT EXISTS `postboard`(
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

-- 게시글 분류
CREATE TABLE IF NOT EXISTS `category`(
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- 게시판 넘버
    postboard_id INT NOT NULL,
    -- 말머리 넘버
    category TINYINT NOT NULL,
    -- 말머리
    `name` VARCHAR(100) NOT NULL,
    
    FOREIGN KEY (postboard_id) REFERENCES `postboard`(id),
    UNIQUE KEY (postboard_id, category)
);

-- 게시글
CREATE TABLE IF NOT EXISTS `board`(
	id INT AUTO_INCREMENT PRIMARY KEY,
    -- 게시글 종류 (말머리) 기본값 1 (일반 게시글)
    category TINYINT DEFAULT 1 NOT NULL,
    postboard_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content MEDIUMTEXT NOT NULL,
    writer VARCHAR(60) NOT NULL,
    `like` INT DEFAULT 0 NOT NULL,
    regDate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    viewCnt INT DEFAULT 0 NOT NULL,
    FOREIGN KEY (writer) REFERENCES `user`(id),
    FOREIGN KEY (postboard_id) REFERENCES `postboard`(id)
);


-- 댓글
CREATE TABLE IF NOT EXISTS `reply`(
	id INT PRIMARY KEY AUTO_INCREMENT,
    board_id INT NOT NULL,
    writer VARCHAR(60) NOT NULL,
    content MEDIUMTEXT NOT NULL,
    `like` INT DEFAULT 0 NOT NULL,
    regDate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (board_id) REFERENCES `board`(id),
    FOREIGN KEY (writer) REFERENCES `user`(id)
);

-- 대댓글
CREATE TABLE IF NOT EXISTS `rereply`(
	id INT PRIMARY KEY AUTO_INCREMENT,
    reply_id INT NOT NULL,
    board_id INT NOT NULL,
    writer VARCHAR(60) NOT NULL,
    content MEDIUMTEXT NOT NULL,
    regDate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (board_id) REFERENCES `board`(id),
    FOREIGN KEY (writer) REFERENCES `user`(id),
    FOREIGN KEY (reply_id) REFERENCES `reply`(id)
);

-- 게시글 좋아요
CREATE TABLE IF NOT EXISTS `board_likes` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    board_id INT NOT NULL,
    user_id VARCHAR(60) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES `board`(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    UNIQUE KEY (board_id, user_id) -- 한 사용자가 한 게시글에 한 번만 추천할 수 있도록 유니크 제약 추가
);

-- 댓글 좋아요
CREATE TABLE IF NOT EXISTS `reply_likes` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reply_id INT NOT NULL,
    user_id VARCHAR(60) NOT NULL,
    FOREIGN KEY (reply_id) REFERENCES `reply`(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    UNIQUE KEY (reply_id, user_id) -- 한 사용자가 한 댓글에 한 번만 추천할 수 있도록 유니크 제약 추가
);

-- 운동
CREATE TABLE IF NOT EXISTS `exercise` (
	id INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(60) NOT NULL,
    `check` INT NOT NULL,
    cal INT NOT NULL,
    `time` INT NOT NULL
);

-- 트리거
DELIMITER //
CREATE TRIGGER before_insert_category
BEFORE INSERT ON `category`
FOR EACH ROW
BEGIN
    DECLARE max_category TINYINT;
    
    -- 현재 postboard_id 그룹에서 가장 큰 category 값을 찾는다.
    SELECT IFNULL(MAX(category), 0) INTO max_category
    FROM `category`
    WHERE postboard_id = NEW.postboard_id;

    -- 새로 삽입될 row의 category 값을 설정한다.
    SET NEW.category = max_category + 1;
END;
//
DELIMITER ;

-- 테스트용 데이터 입력
INSERT INTO `user` (name, id, email, password, age, sex) VALUES 
-- 암호화 전 비밀번호 : test
('테스트싸피', 'test', 'test@sfy.com', '$2a$10$AG3BLKDrxeZaN5RMiHtZpeWEgCISlvI1diNLEojDReuHx6M/f9vY.', 20, 'M');

-- delete from user where id = 'ssafy';

INSERT INTO `postboard` (title) VALUES ('게시판1'), ('게시판2');

INSERT INTO `category` (postboard_id, name) VALUES
(1, '일반'), (1, '공지'), (1, '질문'), (2, '일반'), (2, '공지');

-- INSERT INTO `board` (category, postboard_id, title, content, writer, `like`) VALUES
-- (1, 1, "좋아요 테스트3", "좋아요 눌러주세요!!", "test", 150);

INSERT INTO `board` (category, postboard_id, title, content, writer) VALUES
( 2, 1, "게시판1 공지사항입니다.", "ㅎㅇㅎㅇ~", "test"),
( 1, 1, "안녕하세요~~", "ㅎㅇㅎㅇ~", "test"),
( 1, 1, "ㅎㅇㅎㅇ", "하이하이", "test"),
( 1, 1, "점심메뉴추천좀", "ㅎㅇㅎㅇ~", "test"),
( 1, 1, "123456789", "ㅎㅇㅎㅇ~", "test"),
( 1, 1, "일반게시판입니다!", "ㅎㅇㅎㅇ~", "test"),
( 1, 1, "ㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹ", "ㅎㅇㅎㅇ~", "test"),
( 3, 1, "질문있어요!!", "ㅎㅇㅎㅇ~", "test"),
( 2, 2, "게시판2 공지사항입니다.", "ㅎㅇㅎㅇ~", "test"),
( 1, 2, "안녕하세요~", "ㅎㅇㅎㅇ~", "test"),
( 1, 2, "ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ", "ㅎㅇㅎㅇ~", "test"),
( 1, 2, "일반 게시판입니다!", "ㅎㅇㅎㅇ~", "test"),
( 1, 2, "안 녕 하 세 요", "ㅎㅇㅎㅇ~", "test"),
( 1, 2, "123456789", "ㅎㅇㅎㅇ~", "test");

-- delete from `board` where id = 1;

SELECT * FROM `user`;
SELECT * FROM `postboard`;
SELECT * FROM `category`;
SELECT * FROM `board`;
SELECT * FROM `reply`;
SELECT * FROM `rereply`;
SELECT * FROM `board_likes`;
SELECT * FROM `reply_likes`;
SELECT * FROM `exercise`;