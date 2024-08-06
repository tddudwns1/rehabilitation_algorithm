-- 작성 전(사고 흐름)
생각보다 어렵네
count 쓰는건 알겠는데
hour은 group by 인가?

-- 작성 1(시작)
SELECT
    # as hour,
    count(*) as count
from
    animal_outs o
group by datetime
여기서 뇌정지

-- 작성 2(group by에 알맞게 작성)
SELECT
    # as hour,
    count(*) as count
from
    animal_outs o
group by hour(datetime)
값이 나오기 시작

-- 작성 3(group by에 맞게 select문 완성하고, having절을 통해 문제 조건 맞춤) 제출 -> 실패
SELECT
    hour(datetime) as hour,
    count(*) as count
from
    animal_outs o
group by hour(datetime)
having hour >= 9
order by hour
알고보니 19시 이하로도 해야했음

-- 작성 4(최종) 제출 -> 성공
SELECT
    hour(datetime) as hour,
    count(*) as count
from
    animal_outs o
group by hour(datetime)
having hour between 9 and 19
order by hour
between도 썼다 호호루