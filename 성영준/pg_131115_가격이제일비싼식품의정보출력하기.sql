-- 작성 전(사고 과정)
-- 그냥 limit네
-- alias나 연습해야지

-- 작성 1(그냥 함) 제출 -> 성공
SELECT
    f.product_id as product_id,
    f.product_name as product_name,
    f.product_cd as product_cd,
    f.category as category,
    f.price as price
from food_product f
order by price desc
    limit 1;