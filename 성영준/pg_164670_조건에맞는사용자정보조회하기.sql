-- 작성 전
컬럼 합치고 하는건 첨이네
문자열 합치고 문자열 자르고
이거 말고는 쉬운데
subquery 없이 해봐야지

-- 작성 1(시작) 제출 -> 성공
SELECT
    USER_ID,
    NICKNAME,
    concat(CITY, ' ', STREET_ADDRESS1, ' ', STREET_ADDRESS2) as 전체주소,
    concat(left(TLNO, 3), '-', substr(TLNO, 4, 4), '-', right(TLNO, 4)) as 전화번호
from
    USED_GOODS_USER u
        right join USED_GOODS_BOARD b on u.USER_ID = b.WRITER_ID
group by
    b.WRITER_ID
having
    count(*) >= 3
order by
    u.USER_ID desc

-- GPT 질문 시작
SELECT
    U.USER_ID,
    U.NICKNAME,
    CONCAT(U.CITY, ' ', U.STREET_ADDRESS1, ' ', U.STREET_ADDRESS2) AS 전체주소,
    CONCAT(SUBSTR(U.TLNO, 1, 3), '-', SUBSTR(U.TLNO, 4, 4), '-', SUBSTR(U.TLNO, 8)) AS 전화번호
FROM
    USED_GOODS_USER U
        JOIN
    (SELECT WRITER_ID
     FROM USED_GOODS_BOARD
     GROUP BY WRITER_ID
     HAVING COUNT(*) >= 3) B
    ON
        U.USER_ID = B.WRITER_ID
ORDER BY
    U.USER_ID DESC;

-- Q 비교
-- A
1. 조인 방식:
주어진 쿼리: RIGHT JOIN을 사용하여 USED_GOODS_BOARD를 기준으로 USED_GOODS_USER 테이블과 조인합니다. 즉, 게시글이 있는 모든 사용자를 포함하되, USED_GOODS_USER에 해당하는 정보가 없는 경우라도 게시글 정보가 있으면 결과에 포함됩니다.
이 경우 RIGHT JOIN을 사용했기 때문에, USER_ID가 없는 경우에도 USED_GOODS_BOARD에 존재하는 모든 레코드가 결과에 포함될 수 있지만, 이는 의미상 불필요한 조인입니다.
2. GROUP BY와 HAVING:
주어진 쿼리: GROUP BY를 사용하여 작성자 ID(b.WRITER_ID)별로 그룹화하고, HAVING count(*) >= 3 조건을 통해 게시글이 3건 이상인 사용자만 필터링합니다.
GROUP BY에서 b.WRITER_ID를 기준으로 그룹화하고 있지만, 선택된 컬럼 중에서 USER_ID, NICKNAME, CITY, STREET_ADDRESS1, STREET_ADDRESS2, TLNO가 그룹화되지 않은 채 사용되고 있습니다. 이는 SQL에서 일반적으로 허용되지 않으며, 특정 데이터베이스에서는 오류를 발생시킬 수 있습니다. SQL 표준에 따라 GROUP BY에 포함되지 않은 모든 선택된 컬럼은 집계 함수로 감싸야 합니다.

-- Q 서브쿼리 쓰기 싫어
-- A
서브쿼리를 사용하지 않고 동일한 결과를 얻기 위해서는 윈도우 함수를 활용할 수 있습니다. 아래와 같이 ROW_NUMBER()와 COUNT()를 사용하여 필요한 데이터를 필터링할 수 있습니다.

SELECT
    u.USER_ID,
    u.NICKNAME,
    CONCAT(u.CITY, ' ', u.STREET_ADDRESS1, ' ', IFNULL(u.STREET_ADDRESS2, '')) AS 전체주소,
    CONCAT(LEFT(u.TLNO, 3), '-', SUBSTR(u.TLNO, 4, 4), '-', RIGHT(u.TLNO, 4)) AS 전화번호
FROM
    USED_GOODS_USER u
        JOIN
    USED_GOODS_BOARD b ON u.USER_ID = b.WRITER_ID
GROUP BY
    u.USER_ID, u.NICKNAME, u.CITY, u.STREET_ADDRESS1, u.STREET_ADDRESS2, u.TLNO
HAVING
    COUNT(b.BOARD_ID) >= 3
ORDER BY
    u.USER_ID DESC;

설명:
JOIN:
USED_GOODS_USER와 USED_GOODS_BOARD를 USER_ID와 WRITER_ID로 조인합니다. 이 조인을 통해 각 사용자가 작성한 게시글 정보를 결합합니다.

GROUP BY:
USER_ID를 기준으로 그룹화하여 각 사용자가 작성한 게시글 수를 집계합니다. 모든 SELECT 절에 나열된 컬럼(u.USER_ID, u.NICKNAME, u.CITY, u.STREET_ADDRESS1, u.STREET_ADDRESS2, u.TLNO)을 GROUP BY에 포함시켜야 합니다.

HAVING COUNT(b.BOARD_ID) >= 3:
그룹화된 결과에서 게시글 수(COUNT(b.BOARD_ID))가 3건 이상인 사용자만 필터링합니다.

결론:
이 접근 방식은 서브쿼리를 사용하지 않으면서도 그룹화(GROUP BY)와 집계 함수(COUNT)를 사용하여 원하는 결과를 도출할 수 있습니다.
이 쿼리는 간결하고 성능 측면에서도 효율적이며, SQL 표준을 준수합니다.
이 방법은 서브쿼리를 사용하지 않기를 원하는 상황에 적합하며, 동일한 기능을 제공하면서도 코드의 가독성을 유지합니다.

-- Q group by b.WRITER_ID 이건 별로라고?
-- A
GROUP BY b.WRITER_ID가 별로라고 언급한 이유는 SQL 표준에 맞지 않을 수 있는 상황을 피하기 위해서입니다. 아래에 그 이유와 함께 설명드리겠습니다.

1. SQL 표준과 GROUP BY:
    SQL 표준에서는 GROUP BY 절에 포함되지 않은 컬럼은 SELECT 절에서 직접 사용할 수 없습니다. 만약 해당 컬럼들이 GROUP BY에 포함되지 않은 상태로 사용된다면, 이로 인해 예기치 않은 결과가 나올 수 있거나, 일부 데이터베이스에서는 오류가 발생할 수 있습니다.

예를 들어, 다음과 같은 쿼리에서:
SELECT
    USER_ID,
    NICKNAME,
    CITY,
    STREET_ADDRESS1,
    STREET_ADDRESS2,
    TLNO
FROM
    USED_GOODS_USER u
        JOIN
    USED_GOODS_BOARD b ON u.USER_ID = b.WRITER_ID
GROUP BY
    b.WRITER_ID;
GROUP BY b.WRITER_ID만 사용하면 USER_ID, NICKNAME, CITY, STREET_ADDRESS1, STREET_ADDRESS2, TLNO가 GROUP BY에 포함되지 않기 때문에 이러한 컬럼들이 올바르게 집계되지 않을 수 있습니다. 이를 방지하기 위해 모든 SELECT 절에 있는 컬럼들이 GROUP BY 절에 포함되거나, 집계 함수로 묶어야 합니다.

2. SQL 표준 준수와 명확성:
명확성: 모든 컬럼을 명확히 GROUP BY 절에 포함시키는 것이 SQL 표준을 준수하며, 데이터베이스에 따라 예기치 않은 결과를 방지할 수 있습니다.
    안정성: 이렇게 하면 쿼리가 더 명확해지고, 유지보수하기도 쉬워집니다. 특히 다양한 데이터베이스 플랫폼에서 동일한 결과를 보장받을 수 있습니다.

-- 최종 코드
-
SELECT
    USER_ID,
    NICKNAME,
    concat(CITY, ' ', STREET_ADDRESS1, ' ', STREET_ADDRESS2) as 전체주소,
    concat(left(TLNO, 3), '-', substr(TLNO, 4, 4), '-', right(TLNO, 4)) as 전화번호
from
    USED_GOODS_USER u
    join USED_GOODS_BOARD b on u.USER_ID = b.WRITER_ID
group by
    u.USER_ID, u.NICKNAME, u.CITY, u.STREET_ADDRESS1, u.STREET_ADDRESS2, u.TLNO
having
    count(*) >= 3
order by
    u.USER_ID desc