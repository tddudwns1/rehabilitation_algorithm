-- 시작 전


-- 작성 1(시작) 제출 -> 실패
select
    YEAR(YM) as YEAR,
    round(avg(PM_VAL1), 3) as 'PM10',
    round(avg(PM_VAL2), 3) as 'PM2.5'
from
    AIR_POLLUTION
where
    LOCATION2 = '수원'
group by
    YEAR
order by
    YEAR
알고 보니 round 함수가
적은 자릿수로 반올림을 하는 거라
2를 적어야 했다

-- 작성 2(round 수정) 제출 -> 성공
select
    YEAR(YM) as YEAR,
    round(avg(PM_VAL1), 2) as 'PM10',
    round(avg(PM_VAL2), 2) as 'PM2.5'
from
    AIR_POLLUTION
where
    LOCATION2 = '수원'
group by
    YEAR
order by
    YEAR

-- GPT
SELECT
    TO_CHAR(YM, 'YYYY') AS YEAR,
    ROUND(AVG(PM_VAL1), 2) AS PM10,
    ROUND(AVG(PM_VAL2), 2) AS PM2.5
FROM
    AIR_POLLUTION
WHERE
    LOCATION1 = '경기도' AND LOCATION2 = '수원'
GROUP BY
    TO_CHAR(YM, 'YYYY')
ORDER BY
    YEAR ASC;

-- 비교
호환성: 첫 번째 쿼리 (TO_CHAR(YM, 'YYYY'))는 더 많은 데이터베이스 시스템에서 호환되며, 일반적으로 더 안전한 선택입니다. 그러나 MySQL에서만 사용되는 경우라면 두 번째 쿼리 (YEAR(YM))도 완전히 유효하며, 간결하고 이해하기 쉬운 방법입니다.
표준성: 첫 번째 쿼리에서는 별칭을 지정할 때 작은따옴표 대신 표준적으로 큰따옴표나 그냥 별칭을 사용하는 것이 더 나은 방법입니다. 두 번째 쿼리에서 작은따옴표 대신 백틱(``)을 사용하거나 그냥 별칭을 사용하는 것이 더 일반적입니다.
MySQL을 사용하는 경우, YEAR(YM)을 사용하는 두 번째 쿼리가 조금 더 효율적일 수 있습니다. 이는 함수가 간단하고, 인덱스 사용 가능성을 높일 수 있기 때문입니다.
하지만 실제로는 두 쿼리 간의 성능 차이는 데이터 양이 매우 큰 경우가 아니면 거의 무시할 수 있을 정도로 미미합니다.
따라서 성능이 아주 중요한 상황이 아니라면, 가독성과 SQL 표준을 고려하여 쿼리를 선택하는 것이 좋습니다. MySQL에서 YEAR(YM) 함수를 사용하면 코드가 간결해지고, MySQL에 최적화된 성능을 얻을 수 있습니다.
