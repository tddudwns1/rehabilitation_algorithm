-- 작성 전
group by 하고 count 해서 2 이상인거 하면 되나?

-- 작성 1(시작) 제출 -> 실패
select
    USER_ID,
    PRODUCT_ID
from
    ONLINE_SALE
group by
    USER_ID, PRODUCT_ID
order by
    USER_ID,
    PRODUCT_ID desc
완전 바보같이 함
이렇게 하면 오히려 중복 구매 내역을 지우는 꼴

-- 작성 2(수정) 제출 -> 실패
select
    s.USER_ID,
    s.PRODUCT_ID
from
    ONLINE_SALE s
        join
    (
        select
            USER_ID,
            PRODUCT_ID,
            count(*) as cnt
        from
            ONLINE_SALE
        group by
            USER_ID, PRODUCT_ID
        having
            cnt > 1
    ) c on s.USER_ID = c.USER_ID and s.PRODUCT_ID = c.PRODUCT_ID
order by
    USER_ID,
    PRODUCT_ID desc

이렇게 하니까 join 되는 컬럼이 2개 이상으로 나옴
당연함 2개 이상인 경우랑 join 했으니

-- 작성 3(distinct 추가) 제출 -> 성공
select distinct
    s.USER_ID,
    s.PRODUCT_ID
from
    ONLINE_SALE s
        join
    (
        select
            USER_ID,
            PRODUCT_ID,
            count(*) as cnt
        from
            ONLINE_SALE
        group by
            USER_ID, PRODUCT_ID
        having
            cnt > 1
    ) c on s.PRODUCT_ID = c.PRODUCT_ID and s.USER_ID = c.USER_ID
order by
    USER_ID,
    PRODUCT_ID desc
되긴 했음

-- gpt 질문
select
    USER_ID,
    PRODUCT_ID
from
    ONLINE_SALE
group by
    USER_ID,
    PRODUCT_ID
having
    count(*) > 1
order by
    USER_ID,
    PRODUCT_ID desc
그냥 이게 맞아서 할말이 없네
having 에서 직접 count 호출 가능한 지 몰랐습니다
무조건 select에 써야 하는줄 알고
일부러 서브쿼리 쓴건데
유유

하나 배워갑니다
having 에도 집계 함수를 쓸 수 있다