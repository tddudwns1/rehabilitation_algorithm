-- 작성 전(사고 흐름)
-- count 집계함수 쓰는거네
-- is null도 쓰네

-- 작성 1(시작) 제출 -> 성공
SELECT count(*) as users
from user_info i
where age is null

-- 작성 2(* 대신 다른 걸 넣어보자)
SELECT count(user_id) as users
from user_info i
where age is null
-- 4가 나옴

-- 작성 3
SELECT count(age) as users
from user_info i
where age is null
-- 0이 나옴

-- 작성 4
SELECT count(age) as users
from user_info i
where age is not null
-- 196이 나옴

-- 결론
-- count는 해당 칼럼이 null이 아닌 값만 카운팅 한다
-- 그래서 해당 컬림이 null일 경우에 해당 컬럼을 기준으로 둔다면 늘 0이 나올 것이고,
-- 또한 user_id가 만약 key값이 아니며 null이라면 해당 필드는 세지 못하게 된다
-- 그러니 * 혹은 1을 넣어줘야 안전할 것 같다