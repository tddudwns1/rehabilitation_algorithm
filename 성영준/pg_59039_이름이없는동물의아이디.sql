-- 작성 전(사고 흐름)
-- 그냥 is null 문이네

-- 작성 1(시작) 제출 -> 성공
SELECT
    i.animal_id as animal_id
from
    animal_ins i
where
    name is null
order by
    i.animal_id