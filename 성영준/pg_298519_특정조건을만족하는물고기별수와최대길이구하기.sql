-- 작성 전
서브쿼리 안쓰고 안되나?
했다가

having 절에 avg 로 조건 걸고
조건에 if 문 쓰고
나머진 쉽네

-- 작성 1(시작) 제출 -> 실패
select
    count(*) as FISH_COUNT,
    max(LENGTH) as MAX_LENGTH,
    FISH_TYPE
from
    FISH_INFO
group by
    FISH_TYPE
having
    avg(if(LENGTH < 10, 10, LENGTH)) >= 33
order by
    FISH_TYPE
이제 보니 10cm 이하는 null 로 표기하는 거였네 유유

-- 작성 2(하는 김에 if문으로 하기 보다 null 판별하는 함수 찾아 봄) 제출 -> 성공
select
    count(*) as FISH_COUNT,
    max(LENGTH) as MAX_LENGTH,
    FISH_TYPE
from
    FISH_INFO
group by
    FISH_TYPE
having
    avg(COALESCE(LENGTH, 10)) >= 33
order by
    FISH_TYPE

coalesce 이거 좋네
null 아니면 해당값, null 이면 기재값