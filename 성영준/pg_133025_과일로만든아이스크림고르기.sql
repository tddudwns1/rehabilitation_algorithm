-- 작성 전
쉽네

-- 작성 1(시작) 제출 -> 성공
SELECT
    FLAVOR
from
    FIRST_HALF
        join ICECREAM_INFO using(FLAVOR)
where
    INGREDIENT_TYPE = "fruit_based"
  and TOTAL_ORDER > 3000
order by
    TOTAL_ORDER desc