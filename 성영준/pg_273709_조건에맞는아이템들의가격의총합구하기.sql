-- 작성 전(사고 과정)
sum 집계함수 물어보는거네

-- 작성 1(시작) 제출 -> 성공
select
    sum(i.price) as total_price
from
    item_info i
where
    i.rarity = "LEGEND"