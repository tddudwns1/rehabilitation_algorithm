-- 작성 전
date_format year 할 줄 아는지 물어보는 문제네

-- 작성 1(시작) 제출 -> 성공
SELECT
    BOOK_ID,
    date_format(PUBLISHED_DATE, "%Y-%m-%d") as date_format
from
    BOOK
where
    year(PUBLISHED_DATE) = 2021
  and CATEGORY = '인문'
order by
    PUBLISHED_DATE