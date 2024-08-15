-- 작성 전
right join 하면 되겠는데

-- 작성 1(시작) 제출 -> 성공
SELECT
    ANIMAL_ID,
    o.NAME
from
    ANIMAL_INS i
        right join ANIMAL_OUTS o using(ANIMAL_ID)
where
    i.INTAKE_CONDITION is null
order by
    ANIMAL_ID

gpt도 같은 코드 줌