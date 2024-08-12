-- 작성 전
date_format, month, is not null 할 줄 아는 지 물어보는 문제

-- 작성 1(시작) 제출 -> 실패
SELECT
    MEMBER_ID,
    MEMBER_NAME,
    GENDER,
    date_format(DATE_OF_BIRTH, "%Y-%m-%d") as DATE_OF_BIRTH
from
    MEMBER_PROFILE
where
    month(DATE_OF_BIRTH) = 3
  and TLNO is not null
order by
    MEMBER_ID

한참 봤는데 여성을 빼먹었네요 ㅋㅋ

-- 작성 2(여성 추가) 제출 -> 성공
SELECT
    MEMBER_ID,
    MEMBER_NAME,
    GENDER,
    date_format(DATE_OF_BIRTH, "%Y-%m-%d") as DATE_OF_BIRTH
from
    MEMBER_PROFILE
where
    month(DATE_OF_BIRTH) = 3
  and GENDER = "W"
  and TLNO is not null
order by
    MEMBER_ID