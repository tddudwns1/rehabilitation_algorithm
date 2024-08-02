-- 작성 전(사고 흐름)
case문이랑 like문이네

-- 작성 1(시작) 제출 -> 성공
SELECT
    i.ANIMAL_ID as ANIMAL_ID,
    i.NAME as NAME,
    case
        when i.SEX_UPON_INTAKE like "Neutered%"
            then "O"
        when i.SEX_UPON_INTAKE like "Spayed%"
            then "O"
        else "X"
        end as 중성화
from
    ANIMAL_INS i

그런데 이제보니까 '아이디 순으로 조회하는 SQL문을 작성해주세요.'를 안 했는데 맞았음

그리고 자바처럼 when으로 묶는거 시도해봤는데 그건 안됐음
SELECT
    i.ANIMAL_ID as ANIMAL_ID,
    i.NAME as NAME,
    case
        when i.SEX_UPON_INTAKE like "Neutered%"
        when i.SEX_UPON_INTAKE like "Spayed%"
            then "O"
        else "X"
        end as 중성화
from
    ANIMAL_INS i

SQL 실행 중 오류가 발생하였습니다.
You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'when i.SEX_UPON_INTAKE like "Spayed%"
        then "O"
        else "X"
    end ' at line 6

-- 작성 2(gpt 질문)
SELECT
    ANIMAL_ID,
    NAME,
    CASE
        WHEN SEX_UPON_INTAKE LIKE '%Neutered%' OR SEX_UPON_INTAKE LIKE '%Spayed%' THEN 'O'
        ELSE 'X'
        END AS 중성화
FROM
    ANIMAL_INS
ORDER BY
    ANIMAL_ID;
유유 or문이 있네
어제도 between문 할때 and 봤었는데 생각이 안나네