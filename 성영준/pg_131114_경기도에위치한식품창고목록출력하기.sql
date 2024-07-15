-- 작성 전(사고 흐름)
그냥 switch문이네

-- 작성 1(뇌 정지)
SELECT
    warehouse_id as warehouse_id,
    warehouse_name as warehouse_name,
    address as address,
    swtich
    freezer_yn as freezer_yn
from food_warehouse
where address like "경기도%"
order by warehouse_id;
어캐 쓰지

-- 작성 2(case문 검색 후) 제출 -> 성공
SELECT
    warehouse_id as warehouse_id,
    warehouse_name as warehouse_name,
    address as address,
    case
        when freezer_yn is null then 'N'
        else freezer_yn
        end as freezer_yn
from food_warehouse
where address like "경기도%"
order by warehouse_id;