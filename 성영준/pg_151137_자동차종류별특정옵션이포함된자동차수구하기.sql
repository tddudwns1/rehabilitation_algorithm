-- 작성 전(사고 흐름)
like문 여러번말고 한번에 하는 방식은 없나

-- 작성 1(시작) 제출->성공
SELECT
    c.car_type as car_type,
    count(*) as cars
from
    CAR_RENTAL_COMPANY_CAR c
where
    c.options like "%통풍시트%" or
    c.options like "%열선시트%" or
    c.options like "%가죽시트%"
group by
    c.car_type
order by
    c.car_type
쉬운데 이게 맞나

GPT가 맞다네요 호호루