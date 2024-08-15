-- 작성 전
며칠 전 한 rank 문제네

-- 작성 1(시작) 뇌정지
SELECT
    FOOD_TYPE,
    REST_ID,
    REST_NAME,
    FAVORITES
from
    (
        select
            FOOD_TYPE,
            REST_ID,
            REST_NAME,
            FAVORITES,
            rank()
        from
            REST_INFO
    ) r
where
    r.ranking = 1
order by
    FOOD_TYPE desc

rank 뒤에 over 랑 partition by 나왔던거 같은데 까먹음

-- 작성 2(지난 커밋 확인 후) 제출 -> 성공
SELECT
    FOOD_TYPE,
    REST_ID,
    REST_NAME,
    FAVORITES
from
    (
        select
            FOOD_TYPE,
            REST_ID,
            REST_NAME,
            FAVORITES,
            rank() over (partition by FOOD_TYPE order by FAVORITES desc) as ranking
        from
            REST_INFO
    ) r
where
    r.ranking = 1
order by
    FOOD_TYPE desc

gpt도 제 코드가 더 낫다네요~ 호호루~