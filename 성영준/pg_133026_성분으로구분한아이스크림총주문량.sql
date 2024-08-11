-- 작성 전(사고 흐름)
join 에 sum 이네

-- 작성 1(시작) 제출 -> 성공
SELECT
    i.INGREDIENT_TYPE as INGREDIENT_TYPE,
    sum(f.TOTAL_ORDER) as TOTAL_ORDER
from
    FIRST_HALF f
        join ICECREAM_INFO i using(FLAVOR)
group by
    i.INGREDIENT_TYPE
order by
    TOTAL_ORDER

gpt도 같은 답 나왔네