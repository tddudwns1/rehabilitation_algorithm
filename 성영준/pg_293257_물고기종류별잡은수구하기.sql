-- 작성 전
너무 쉽네

-- 작성 1(시작) 뇌정지
select
    count(*) as FISH_COUNT,
    FISH_NAME
from
    FISH_INFO i
        join
    FISH_NAME_INFO n
    on i.FISH_TYPE = n.FISH_TYPE
group by
    FISH_TYPE
order by
    FISH_COUNT desc

분명 fish_type으로 join 해서 유일한 컬럼인데 안됨
그리고 alias를 붙여도 안됨

gpt 왈
SQL 표준에 따르면 GROUP BY 절에 포함된 모든 열 또는 그 열의 집합 함수가 아닌 다른 열은 SELECT 절에 포함될 수 없습니다. 즉, SELECT 절에서 그룹화되지 않은 열(FISH_NAME)을 직접 선택하려면 SQL 엔진은 어떻게 해당 값을 선택할지 명확하지 않게 됩니다.

-- 작성 2(피드백 반영) 제출 -> 성공
-
select
    count(*) as FISH_COUNT,
    FISH_NAME
from
    FISH_INFO i
        join
    FISH_NAME_INFO n
    on i.FISH_TYPE = n.FISH_TYPE
group by
    FISH_NAME
order by
    FISH_COUNT desc