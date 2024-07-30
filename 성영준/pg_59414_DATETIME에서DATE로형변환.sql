-- 작성 전(사고 흐름)
-- dateformat이네
-- 근데 어디에 어떻게 써야지

-- 작성 1-1(시작)
SELECT
    i.animal_id as animal_id,
    i.name as name,
    i.datetime as "날짜"
from
    animal_ins i
order by
    i.animal_id
-- 여기서 뇌정지
-- dateformat을 써야하는 건 알겠는데 어떻게 바꾸는지 모르겠었음

-- 작성 1-2(select 내부에 삽입)
SELECT
    i.animal_id as animal_id,
    i.name as name,
    dateformat(i.datetime) as "날짜"
from
    animal_ins i
order by
    i.animal_id
-- 뭔가 안 됨
-- 원래 %Y 이런거 넣어야했는데 저 형식이 기억이 안나서 2차 뇌정지

-- 작성 1-3(뒤에 형식 넣고, dateformat 사이에 _ 넣음) 제출 -> 성공
SELECT
    i.animal_id as animal_id,
    i.name as name,
    date_format(i.datetime, "%Y-%m-%d") as "날짜"
from
    animal_ins i
order by
    i.animal_id