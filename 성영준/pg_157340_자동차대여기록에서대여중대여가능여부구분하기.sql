-- 작성 전
case

-- 작성 1(시작)
SELECT distinct
    CAR_ID,
    case
        when START_DATE <= '2022-10-16' and '2022-10-16' <= END_DATE then '대여중'
        else '대여 가능'
        end
        as AVAILABILITY
from
    CAR_RENTAL_COMPANY_RENTAL_HISTORY
order by
    CAR_ID desc
성능 평가: 이 쿼리는 각 CAR_ID에 대해 서브쿼리를 실행합니다. EXISTS는 효율적이지만, DISTINCT와 결합된 서브쿼리로 인해 성능이 저하될 수 있습니다.
순위: 3위 (서브쿼리가 포함되어 있어 상대적으로 느림)

-- 작성 2
SELECT
    c.CAR_ID,
    CASE
        WHEN EXISTS (
            SELECT 1
            FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY h
            WHERE h.CAR_ID = c.CAR_ID
              AND '2022-10-16' BETWEEN h.START_DATE AND h.END_DATE
        ) THEN '대여중'
        ELSE '대여 가능'
        END AS AVAILABILITY
FROM
    (SELECT DISTINCT CAR_ID FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY) c
ORDER BY
    c.CAR_ID DESC;
성능 평가: 이 쿼리는 GROUP BY와 함께 사용되며, 각 CAR_ID에 대해 집계 함수인 SUM을 실행합니다. SUM과 CASE의 조합은 효율적이며, 서브쿼리가 없기 때문에 성능이 상대적으로 좋습니다.
순위: 1위 (직관적이고 효율적인 연산)

-- 작성 3
SELECT
    c.CAR_ID,
    CASE
        WHEN SUM(CASE WHEN c.START_DATE <= '2022-10-16' AND c.END_DATE >= '2022-10-16' THEN 1 ELSE 0 END) > 0
            THEN '대여중'
        ELSE '대여 가능'
        END AS AVAILABILITY
FROM
    CAR_RENTAL_COMPANY_RENTAL_HISTORY c
GROUP BY
    c.CAR_ID
ORDER BY
    c.CAR_ID DESC;
성능 평가: 이 쿼리는 LEFT JOIN을 사용하여 각 CAR_ID에 대해 조건에 맞는 데이터를 조인합니다. 서브쿼리가 없지만 LEFT JOIN이 전체 테이블을 스캔하게 되므로 성능이 떨어질 수 있습니다.
순위: 2위 (효율적인 조인이지만, JOIN 비용이 있음)

-- 작성 4
SELECT
    c.CAR_ID,
    CASE
        WHEN r.CAR_ID IS NOT NULL AND r.START_DATE <= '2022-10-16' AND r.END_DATE >= '2022-10-16'
            THEN '대여중'
        ELSE '대여 가능'
        END AS AVAILABILITY
FROM
    (SELECT DISTINCT CAR_ID FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY) c
        LEFT JOIN
    CAR_RENTAL_COMPANY_RENTAL_HISTORY r
    ON c.CAR_ID = r.CAR_ID
        AND r.START_DATE <= '2022-10-16'
        AND r.END_DATE >= '2022-10-16'
ORDER BY
    c.CAR_ID DESC;
문제점:

중복된 조인 결과:

만약 CAR_ID가 여러 개의 대여 기록을 가지고 있을 경우, 이 쿼리는 각 CAR_ID에 대해 여러 행을 반환할 수 있습니다. 이는 특히 JOIN이 조건을 만족하는 여러 행을 가져올 때 발생합니다.
이로 인해 CASE 문이 여러 번 평가되어, 의도하지 않은 결과가 도출될 수 있습니다. 예를 들어, 하나의 CAR_ID에 대해 일부 기록이 "대여중"이고 다른 기록이 "대여 가능"인 경우, 중복된 행이 반환될 수 있습니다.
DISTINCT 사용의 한계:

이 쿼리는 중복된 결과를 제거하기 위해 DISTINCT를 사용하지만, 중복된 결과를 제거하는 것은 비용이 많이 들 수 있으며, JOIN이 많은 경우 예상치 못한 성능 저하를 초래할 수 있습니다.
쿼리의 복잡성:

LEFT JOIN을 사용한 쿼리는 일반적으로 더 복잡하며, 이로 인해 유지보수성과 가독성이 떨어질 수 있습니다. GROUP BY와 같은 간단한 집계 함수가 이러한 문제를 더 효과적으로 해결할 수 있습니다.

-- 최종
-
SELECT
    c.CAR_ID,
    CASE
        WHEN EXISTS (
            SELECT
                1
            FROM
                CAR_RENTAL_COMPANY_RENTAL_HISTORY h
            WHERE
                h.CAR_ID = c.CAR_ID
                AND '2022-10-16' BETWEEN h.START_DATE AND h.END_DATE
        ) THEN '대여중'
        ELSE '대여 가능'
END AS AVAILABILITY
FROM
    (SELECT DISTINCT
        CAR_ID
     FROM
        CAR_RENTAL_COMPANY_RENTAL_HISTORY) c
ORDER BY
    c.CAR_ID DESC;