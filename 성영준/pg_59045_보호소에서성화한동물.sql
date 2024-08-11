-- 작성 전(사고 흐름)
1. 조인을 하기 전에 where 을 통해 거른 두 테이블을 inner join 해서 정렬한다
2. 그냥 inner join 후 where 을 통해 거르고 정렬한다
중에서 1번이 걸러진 테이블끼리 join 해서 더 좋을 것 같아서 진행

-- 작성 1(시작) 제출 -> 성공
SELECT
    i.ANIMAL_ID as ANIMAL_ID,
    i.ANIMAL_TYPE as ANIMAL_TYPE,
    i.NAME as NAME
from
    (
        select
            i.ANIMAL_ID as ANIMAL_ID,
            i.ANIMAL_TYPE as ANIMAL_TYPE,
            i.NAME as NAME
        from
            ANIMAL_INS i
        where
            SEX_UPON_INTAKE like "Intact%"
    ) i
        join
    (
        select
            o.ANIMAL_ID as ANIMAL_ID
        from
            ANIMAL_OUTS o
        where
            SEX_UPON_OUTCOME not like "Intact%"
    ) o
    using(animal_id)
order by
    i.ANIMAL_ID

1번 로직 그대로 진행

이거 적으면서 생각해보니까 join 하고 where 을 하는게 맞을 것 같은 게
한 쪽 테이블에서 걸러진 정보가
다른 쪽 테이블에선 알 수 없으니
중복 체크 할 듯 유유

-- 작성 2(join 후 where 진행) 제출 -> 성공
SELECT
    i.ANIMAL_ID as ANIMAL_ID,
    i.ANIMAL_TYPE as ANIMAL_TYPE,
    i.NAME as NAME
from
    ANIMAL_INS i
        join ANIMAL_OUTS o using(animal_id)
where
    i.SEX_UPON_INTAKE like "Intact%"
  and o.SEX_UPON_OUTCOME not like "Intact%"
order by
    i.ANIMAL_ID

이게 코드도 깔끔하고 더 좋고
아마 이게 맞을 듯

-- gpt 질문
Q. 둘 중 뭐가 더 좋아?
A.
두 번째 쿼리가 더 효율적입니다. 이유는 서브쿼리를 사용하지 않고, 조인 후에 필터링을 수행하므로 데이터베이스 엔진이 인덱스를 더 효과적으로 활용할 수 있으며, 불필요한 중간 결과를 줄일 수 있기 때문입니다.
또한, 두 번째 쿼리는 더 간결하고 이해하기 쉽기 때문에 유지보수 측면에서도 더 유리합니다.

성장했다