-- 작성 전
subquery 안 쓰고 할 수 있나

-- 작성 1
select
    year(DIFFERENTIATION_DATE) as YEAR,       # 분화된 연도
    (
        select
            max(SIZE_OF_COLONY)
        from
            ECOLI_DATA
        where
            YEAR = year(DIFFERENTIATION_DATE)
        group by
            year(DIFFERENTIATION_DATE)
    ) - SIZE_OF_COLONY as YEAR_DEV,   # 분화된 연도별 대장균 크기의 편차: 분화된 연도별 가장 큰 대장균의 크기 - 각 대장균의 크기
    ID              # 대장균 개체의 ID
from
    ECOLI_DATA      # 실험실에서 배양한 대장균들의 정보
order by            # 연도에 대해 오름차순, 대장균 크기의 편차에 대해 오름차순
    YEAR,
    YEAR_DEV
subquery가 있어서 마음에 안 듦

-- 작성 2
SELECT
    YEAR(DIFFERENTIATION_DATE) AS YEAR,
    (
        MAX(SIZE_OF_COLONY) OVER (PARTITION BY YEAR(DIFFERENTIATION_DATE)) - SIZE_OF_COLONY
    ) AS YEAR_DEV,
    ID
FROM
    ECOLI_DATA
ORDER BY
    YEAR ASC,
    YEAR_DEV ASC,
    ID ASC;
window 함수 사용한 건데 익숙해져야겠다

-- 작성 3
SELECT
    e.YEAR,
    (m.MAX_SIZE - e.SIZE_OF_COLONY) AS YEAR_DEV,
    e.ID
FROM
    (SELECT
         ID,
         SIZE_OF_COLONY,
         YEAR(DIFFERENTIATION_DATE) AS YEAR
     FROM
         ECOLI_DATA) e
        JOIN
    (SELECT
         YEAR(DIFFERENTIATION_DATE) AS YEAR,
         MAX(SIZE_OF_COLONY) AS MAX_SIZE
     FROM
         ECOLI_DATA
     GROUP BY
         YEAR(DIFFERENTIATION_DATE)) m
    ON
        e.YEAR = m.YEAR
ORDER BY
    e.YEAR ASC,
    YEAR_DEV ASC,
    e.ID ASC;

-- 작성 4
SELECT
    YEAR(e.DIFFERENTIATION_DATE) AS "YEAR",
    (m.max - e.SIZE_OF_COLONY) AS "YEAR_DEV",
    e.ID
FROM
    ECOLI_DATA e
    JOIN
    (SELECT
    YEAR(DIFFERENTIATION_DATE) AS "y",
    MAX(SIZE_OF_COLONY) AS "max"
    FROM
    ECOLI_DATA
    GROUP BY
    YEAR(DIFFERENTIATION_DATE)
    ) m
ON
    YEAR(e.DIFFERENTIATION_DATE) = m.y
ORDER BY
    1, 2;

성능 비교
작성 2 (Window 함수 사용)
    일반적으로 가장 빠릅니다. 데이터베이스가 전체 테이블을 한 번 스캔하여 필요한 모든 계산을 수행할 수 있으므로 효율적입니다.
작성 3/4 (Join 사용)
    두 번째로 빠릅니다. 특히 큰 데이터셋에서는 JOIN 연산이 많은 데이터를 처리하는 데 추가적인 오버헤드가 발생할 수 있습니다.
작성 1 (Subquery 사용)
    가장 느립니다. 각 행마다 서브쿼리를 실행해야 하므로 성능이 가장 떨어집니다.