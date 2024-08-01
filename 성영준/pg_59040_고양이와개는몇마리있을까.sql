-- 작성 전(사고 흐름)
그냥 group by 문제네

-- 작성 1(시작) 제출 -> 실패
SELECT
    i.animal_type as animal_type,
    count(*) as count
from
    animal_ins i
group by
    i.animal_type
-- 이유
    이때 고양이를 개보다 먼저 조회해주세요. 가 있었는데
그냥 실행해도 고양이가 먼저 나오길래
'아 그냥 알아서 cat dog 순으로 해주는구나 한번 테스트 해봐야지'
했는데 그냥 바로 틀렸습니다

-- 작성 2(order by 추가) 제출 -> 성공
SELECT
    i.animal_type as animal_type,
    count(*) as count
from
    animal_ins i
group by
    i.animal_type
order by
    i.animal_type
쉽다쉬워