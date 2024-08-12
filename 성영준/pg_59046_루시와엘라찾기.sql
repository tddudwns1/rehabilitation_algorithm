-- 작성 전
그냥 in 쓸줄 아나 물어보는 문제

-- 작성 1(시작) 제출 -> 성공
SELECT
    ANIMAL_ID,
    NAME,
    SEX_UPON_INTAKE
from
    ANIMAL_INS
where
    NAME in ("Lucy", "Ella", "Pickle", "Rogan", "Sabrina", "Mitty")
order by
    ANIMAL_ID

gpt 한테 물어보고 나서야 order by 가 빠진 채로 제출하고 성공이 뜬 것을 알았습니다
집중하겠습니다