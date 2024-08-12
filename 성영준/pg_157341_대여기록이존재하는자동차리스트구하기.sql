-- 작성 전(사고 흐름)
간단한 join 에 month 물어보는 건가
distinct 도 물어보네

-- 작성 1(시작) 제출 -> 성공
SELECT
    distinct CAR_ID
from
    CAR_RENTAL_COMPANY_CAR c join CAR_RENTAL_COMPANY_RENTAL_HISTORY r using(CAR_ID)
where
    c.CAR_TYPE = "세단"
    and month(r.START_DATE) = 10
order by
    CAR_ID desc

이거 할 때, using 즉 같은 컬럼명을 사용하는 경우에 alias를 생략해도 되는지 실험했는데 바로 됐음