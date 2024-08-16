-- 시작 전
discount_rate가 int 인데 %가 붙어있네 오륜가 진짠가
그럼 %를 어떻게 때야지 끝에서 1자리 남게 때는 게 되는건가

세단이랑 suv인 discount를 어떻게 찾아오지

다 조인해야하나 그럼 엄청 중복되는데

날짜가 포함 안되게 해야하는데
그럼 범위 밖이면 으로 하면 대여 기록이 존재하지 않는 차는 free한데 못 빌리네

-- 작성 1(시작) 뇌정지
SELECT distinct
    c.CAR_ID,
    c.CAR_TYPE,
    case
        when c.CAR_TYPE = '세단' then c.DAILY_FEE * 30
        else c.DAILY_FEE * 30
        end
        as FEE
from
    CAR_RENTAL_COMPANY_CAR c
        right join
    CAR_RENTAL_COMPANY_RENTAL_HISTORY r using(CAR_ID)
        right join
    CAR_RENTAL_COMPANY_DISCOUNT_PLAN d using(CAR_TYPE)
where
    c.CAR_TYPE in ('세단', 'SUV') and
    d.DURATION_TYPE = '30일 이상' and
    r.END_DATE < 2022-11-01 or r.START_DATE > 2022-11-30 and
                               c.DAILY_FEE * 30 * d.DISCOUNT_RATE between 500000 and 2000000
order by
    FEE desc,
    CAR_TYPE,
    CAR_ID desc

뇌정지를 너무 오래해서 다 기억안나고 처음 제출 내역부터

-- 작성 2
SELECT distinct
    c.CAR_ID,
    c.CAR_TYPE,
    round(c.DAILY_FEE * 30 * (100 - d.DISCOUNT_RATE) / 100, 0)
        as FEE
from
    CAR_RENTAL_COMPANY_CAR c
        right join
    CAR_RENTAL_COMPANY_RENTAL_HISTORY r using(CAR_ID)
        right join
    CAR_RENTAL_COMPANY_DISCOUNT_PLAN d using(CAR_TYPE)
where
    c.CAR_TYPE in ('세단', 'SUV') and
    d.DURATION_TYPE = '30일 이상' and
    (c.DAILY_FEE * 30 * (100 - d.DISCOUNT_RATE) / 100) between 500000 and 2000000 and
    not r.END_DATE between 2022-11-01 and 2022-11-30 and
    not r.START_DATE between 2022-11-01 and 2022-11-30
order by
    FEE desc,
    CAR_TYPE,
    CAR_ID desc

NOT 과 BETWEEN 을 사용한 조건이 원하는 대로 작동x

-- 작성 3 제출 -> 성공
SELECT distinct
    c.CAR_ID,
    c.CAR_TYPE,
    round(c.DAILY_FEE * 30 * (100 - d.DISCOUNT_RATE) / 100, 0)
        as FEE
from
    CAR_RENTAL_COMPANY_CAR c
        join
    CAR_RENTAL_COMPANY_DISCOUNT_PLAN d using(CAR_TYPE)
where
    c.CAR_TYPE in ('세단', 'SUV') and
    d.DURATION_TYPE = '30일 이상' and
    c.DAILY_FEE * 30 * (100 - d.DISCOUNT_RATE) / 100 between 500000 and 2000000 and
    not exists (
        select
            *
        from
            CAR_RENTAL_COMPANY_RENTAL_HISTORY r
        where
            c.CAR_ID = r.CAR_ID and
            r.START_DATE <= '2022-11-30' and
            r.END_DATE >= '2022-11-01'
    )
order by
    FEE desc,
    CAR_TYPE,
    CAR_ID desc

-- GPT 질문
    WITH ## 2022-11-01~2022-11-30 까지 대여가능한 차량
ABLE_CAR AS (
SELECT  DISTINCT CAR_ID
  FROM  CAR_RENTAL_COMPANY_RENTAL_HISTORY AS CRH
 WHERE  NOT EXISTS (
    SELECT 1
    FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY AS R
    WHERE R.CAR_ID = CRH.CAR_ID
      AND R.START_DATE <= '2022-11-30'
      AND R.END_DATE >= '2022-11-01')
)
,
##30일 이상 할인율
DIS_PLAN AS(
SELECT  *
  FROM  CAR_RENTAL_COMPANY_DISCOUNT_PLAN
 WHERE  DURATION_TYPE = '30일 이상'
)


SELECT  AC.CAR_ID
     ,C.CAR_TYPE AS CAR_TYPE
     ,ROUND((C.DAILY_FEE * (1 - DP.DISCOUNT_RATE*0.01) * 30),0) AS FEE
FROM  ABLE_CAR AS AC
          JOIN  CAR_RENTAL_COMPANY_CAR AS C
                ON  AC.CAR_ID = C.CAR_ID
          JOIN  DIS_PLAN AS DP
                ON  C.CAR_TYPE = DP.CAR_TYPE
WHERE  C.CAR_TYPE IN ('SUV','세단')
  AND (C.DAILY_FEE * (1 - DP.DISCOUNT_RATE*0.01) * 30) > 500000
  AND (C.DAILY_FEE * (1 - DP.DISCOUNT_RATE*0.01) * 30) < 2000000
ORDER
    BY  FEE DESC, CAR_TYPE ASC, CAR_ID DESC

-- left join 적용
SELECT DISTINCT
                c.car_id,
                c.car_type,
                ROUND(c.daily_fee * 30 * (100 - p.discount_rate) / 100) AS fee
FROM
    CAR_RENTAL_COMPANY_CAR c
        JOIN CAR_RENTAL_COMPANY_DISCOUNT_PLAN p ON c.car_type = p.car_type
        LEFT JOIN CAR_RENTAL_COMPANY_RENTAL_HISTORY h ON c.car_id = h.car_id
        AND (h.start_date <= '2022-11-30' AND h.end_date >= '2022-11-01')
WHERE
    h.car_id IS NULL
  AND p.duration_type = '30일 이상'
  AND c.car_type IN ('세단', 'SUV')
HAVING
    fee < 2000000 AND fee >= 500000
ORDER BY
    fee DESC,
    c.car_type ASC,
    c.car_id DESC;

-- right join 적용
SELECT DISTINCT
    c.CAR_ID,
    c.CAR_TYPE,
    ROUND(c.DAILY_FEE * 30 * (100 - p.DISCOUNT_RATE) / 100, 0) AS FEE
FROM
    CAR_RENTAL_COMPANY_CAR c
        JOIN CAR_RENTAL_COMPANY_DISCOUNT_PLAN p ON c.CAR_TYPE = p.CAR_TYPE
        RIGHT JOIN (
        SELECT r.CAR_ID
        FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY r
        WHERE NOT EXISTS (
            SELECT 1
            FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY h
            WHERE h.CAR_ID = r.CAR_ID
              AND h.START_DATE <= '2022-11-30'
              AND h.END_DATE >= '2022-11-01'
        )
    ) AS filtered_history ON c.CAR_ID = filtered_history.CAR_ID
WHERE
    p.DURATION_TYPE = '30일 이상'
  AND c.CAR_TYPE IN ('세단', 'SUV')
  AND ROUND(c.DAILY_FEE * 30 * (100 - p.DISCOUNT_RATE) / 100) BETWEEN 500000 AND 2000000
ORDER BY
    FEE DESC,
    c.CAR_TYPE ASC,
    c.CAR_ID DESC;



결론:
위의 LEFT JOIN과 IS NULL을 사용하는 쿼리는 일반적으로 가장 빠른 성능을 제공할 가능성이 큽니다. 데이터베이스 엔진이 최적화하기 쉬운 구조이며, 인덱스를 잘 활용할 수 있기 때문입니다. 따라서, 복잡한 RIGHT JOIN과 NOT EXISTS 방식보다 이 방식이 대부분의 상황에서 더 빠르고 효율적일 가능성이 높습니다.

이건 나중에 다시 풀어봐야겠다

-- 다음날 복습 제출 -> 성공
select distinct
    c.CAR_ID,
    c.CAR_TYPE,
    round(c.DAILY_FEE * 30 * (100 - d.DISCOUNT_RATE) / 100, 0) as FEE
from
    CAR_RENTAL_COMPANY_CAR c
        join
    CAR_RENTAL_COMPANY_DISCOUNT_PLAN d
    on c.CAR_TYPE = d.CAR_TYPE
        and DURATION_TYPE = "30일 이상"
        and c.CAR_TYPE in('세단', 'SUV')
        left join
    CAR_RENTAL_COMPANY_RENTAL_HISTORY r
    on c.CAR_ID = r.CAR_ID
        and r.start_date <= '2022-11-30' AND r.end_date >= '2022-11-01'
where
    r.CAR_ID is null
  and round(c.DAILY_FEE * 30 * (100 - d.DISCOUNT_RATE) / 100, 0) between 500000 and 2000000
order by
    FEE desc,
    CAR_TYPE,
    CAR_ID desc

1. 조건을 where 절에 거냐 join 절에 거냐
    어떤 조건을 JOIN 절에 넣느냐, WHERE 절에 넣느냐에 따라 결과가 달라질 수 있습니다. 하지만, LEFT JOIN에서 조건을 WHERE 절에 넣으면 그 조건이 필터링된 후 결과에서 제외될 수 있으므로, 이 점을 유념하여 쿼리를 작성해야 합니다.
    성능 측면: 성능 차이는 대부분의 경우 거의 없으며, 데이터베이스 엔진이 동일한 실행 계획을 생성할 가능성이 큽니다.
    가독성 측면: 필터링 조건이 WHERE 절에 모여 있어 필터링 로직을 쉽게 이해할 수 있습니다.-
2. select 절과 where 절에 같은 계산식을 쓰면 안 느린가?
    대부분의 현대적인 데이터베이스 엔진은 동일한 계산식이 SELECT 절과 WHERE 절에 동시에 존재할 경우, 자동으로 이를 최적화하여 중복 계산을 피합니다.
3. using 과 on 은 차이가 있나?
    USING과 ON 절의 사용 여부는 성능보다는 주로 가독성과 코드의 간결성에 영향을 미칩니다. 성능 최적화를 위해서는 조인에 사용되는 열에 적절한 인덱스를 설정하고, 쿼리 구조를 효율적으로 설계하는 것이 중요합니다.
    따라서, USING 절을 사용하면 코드가 더 깔끔해질 수 있지만, 시간적인 이득은 거의 없다고 볼 수 있습니다.

    using 을 사용하면 뒤에 and 조건이 되나?
    됩니다.