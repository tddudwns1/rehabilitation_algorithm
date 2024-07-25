-- 작성 전(사고 흐름)
-- like문이네

-- 작성 1(시작) 제출 -> 성공
SELECT
    f.factory_id as factory_id,
    f.factory_name as factory_name,
    f.address as address
from
    food_factory f
where
    f.address like "강원도%"
order by
    f.factory_id