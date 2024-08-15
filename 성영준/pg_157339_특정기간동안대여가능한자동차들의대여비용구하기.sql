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