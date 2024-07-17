-- 작성 전(사고 흐름)
-- 집계함수 max 쓰는거네 쉽네

-- 작성 1(바로 작성) 제출 -> 통과
SELECT max(price) as max_price
from product p
-- table에 alias 붙이면 집계함수에는 안 붙여도 되는건가?
-- 통과는 됐는데 테이블이 하나라 alias 명시가 필요없었나?