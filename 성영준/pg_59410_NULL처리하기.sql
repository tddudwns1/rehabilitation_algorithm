-- 작성 전(사고 흐름)
-- switch문이네

-- 작성 1(시작)
SELECT
    i.animal_type as animal_type,
    when name is null then "No name"
    default name
    as name,
    i.sex_upon_intake
from
    animal_ins i
order by
    i.animal_id
--     SQL 실행 중 오류가 발생하였습니다.
--     You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'when name is null then "No name"
--     default name
--     as name,
--     i.sex_upon_in' at line 3

-- switch였나 뭐가 앞에 붙었는데 까먹었으니까 검색해봐야겠다

-- 작성 2(검색 후) 제출 -> 성공
SELECT
    i.animal_type as animal_type,
    case
        when name is null then "No name"
        else name
    end as name,
    i.sex_upon_intake
from
    animal_ins i
order by
    i.animal_id
-- default도 틀렸네