-- 작성 전
진짜 어렵다
max 로 하면 다른 컬럼은 유지가 되나
유지가 되면 쉬운데 아니면 생각도 안 나는데

-- 작성 1(시작) 제출 -> 실패
SELECT
    CATEGORY,
    max(PRICE) as MAX_PRICE,
    PRODUCT_NAME
from
    FOOD_PRODUCT
where
    CATEGORY in('과자', '국', '김치', '식용유')
group by
    CATEGORY
order by
    MAX_PRICE desc

다른 컬럼이 유지되지 않고 다른 컬럼 값이 나옴

-- 작성 2(검색 후)
SELECT
    f.CATEGORY,
    f.PRICE as MAX_PRICE,
    PRODUCT_NAME
from
    FOOD_PRODUCT f
        join (
        select
            max(price) as PRICE,
            category
        from
            FOOD_PRODUCT
        where
            CATEGORY in('과자', '국', '김치', '식용유')
        group by
            CATEGORY
    ) m ON f.CATEGORY = m.CATEGORY AND f.PRICE = m.PRICE
where
    f.CATEGORY in('과자', '국', '김치', '식용유')
group by
    f.CATEGORY
order by
    MAX_PRICE desc

-- 작성 2-2(중복 체크 제거)
SELECT
    f.CATEGORY,
    f.PRICE as MAX_PRICE,
    f.PRODUCT_NAME
FROM
    FOOD_PRODUCT f
        JOIN (
        SELECT
            MAX(price) as PRICE,
            category
        FROM
            FOOD_PRODUCT
        WHERE
            CATEGORY IN ('과자', '국', '김치', '식용유')
        GROUP BY
            CATEGORY
    ) m ON f.CATEGORY = m.CATEGORY AND f.PRICE = m.PRICE
ORDER BY
    MAX_PRICE DESC;

-- 작성 3(다음 날 복습)
select
    f.CATEGORY,
    price as MAX_PRICE,
    f.PRODUCT_NAME
from
    FOOD_PRODUCT f
        join (
        select
            CATEGORY,
            max(PRICE) as MAX_PRICE
        from
            FOOD_PRODUCT
        where
            CATEGORY in ('과자', '국', '김치', '식용유')
        group by
            CATEGORY
    ) m on f.CATEGORY = m.CATEGORY and price = m.MAX_PRICE
order by
    MAX_PRICE desc
테이블 alias로 여러 실험 해봄

-- 작성 4(window 함수 학습 후)
select
    CATEGORY,
    PRICE as MAX_PRICE,
    PRODUCT_NAME
from
    (
        select
            CATEGORY,
            PRICE,
            PRODUCT_NAME,
            rank() over (PARTITION BY CATEGORY order by PRICE desc) as ranking
        from
            FOOD_PRODUCT
        where
            CATEGORY in ('과자', '국', '김치', '식용유')
    ) f
where
    f.ranking < 2
order by
    MAX_PRICE desc

gpt한테 물어봄

효율성 측면 비교:
데이터셋 크기가 클 경우, 윈도우 함수를 사용하는 두 번째 쿼리가 일반적으로 더 효율적입니다. 이유는 데이터셋을 한 번만 스캔하면서 각 파티션 내에서 순위를 매기고, 필요 없는 데이터를 걸러내기 때문입니다.
첫 번째 쿼리는 서브쿼리와 조인을 사용해 결과를 도출하므로, 더 많은 리소스가 필요할 수 있습니다. 데이터 양이 많아질수록, 서브쿼리와 조인 연산에 의해 성능 저하가 발생할 가능성이 높습니다.
결론:
두 번째 쿼리 (RANK() 함수를 사용하는 쿼리)가 일반적으로 더 효율적입니다. 특히 데이터 양이 많거나 복잡한 경우, 윈도우 함수를 사용한 접근이 성능 면에서 더 유리합니다.