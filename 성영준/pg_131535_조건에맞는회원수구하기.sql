-- 작성 전(사고 흐름)
-- 어렵다 뭐부터 해야하나
-- date_format부터 넣고 하면 되려나
-- 효율 떨어질 거 같은데
-- 일단 되려나

-- 작성 1(시작) 제출 -> 성공
SELECT
    count(*) as users
from
    user_info
where
    age >= 20 and age <= 29 and
    date_format(joined, "%Y-%m-%d") >= "2021-01-01" and
    date_format(joined, "%Y-%m-%d") <= "2021-12-31"
-- 바로 성공해서 일단 좋음
-- 근데 코드 수준은 진짜 처참한거 같아서 gpt 물어봐야징

-- 작성 2(gpt 답안) 제출 -> 성공
SELECT COUNT(*) AS USERS
FROM USER_INFO
WHERE YEAR(JOINED) = 2021
  AND AGE BETWEEN 20 AND 29;
-- 진짜 코드 확 다르네 유유
-- 1. year은 type이 date인 데이터의 년도 정보를 빼온다
-- 2. between 기억하자

-- Q. 만약 joined 데이터타입이 string이었다면?
-- A.
SELECT COUNT(*) AS USERS
FROM USER_INFO
WHERE LEFT(JOINED, 4) = '2021'
  AND AGE BETWEEN 20 AND 29;
-- 천재가 따로없다

-- Q. 내 코드는 느리겠지?
-- A.
-- 네, DATE_FORMAT 함수를 사용하면
-- JOINED 컬럼의 모든 값을 날짜 형식으로 변환해야 하므로,
-- 이로 인해 성능이 저하될 수 있습니다.
--
-- 유유

-- Q. 그럼 left랑 date_format이랑 뭐가 더 효율적이야?
-- A.
-- LEFT 함수와 DATE_FORMAT 함수의 성능을 비교할 때,
-- 일반적으로 LEFT 함수가 더 효율적입니다.
-- 그 이유는 LEFT 함수는 단순히 문자열의 앞 몇 자리를 추출하는 데 반해,
-- DATE_FORMAT 함수는 문자열을 날짜 형식으로 변환한 후
-- 원하는 형식으로 다시 변환하는 작업을 수행하기 때문입니다.

SELECT COUNT(*) AS USERS
FROM USER_INFO
WHERE AGE BETWEEN 20 AND 29
  AND DATE_FORMAT(JOINED, '%Y') = '2021';
-- 심지어 이렇게도 가능하네 유유