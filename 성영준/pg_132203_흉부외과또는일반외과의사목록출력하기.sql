-- 작성 전(사고 회로)
-- in문이라고 해야하나 그거 쓰는거구나

-- 작성 1(시작) 제출 -> 실패
SELECT
    dr_name as dr_name,
    dr_id as dr_id,
    mcdp_cd as mcdp_cd,
    hire_ymd as hire_ymd
from
    doctor
where
    mcdp_cd in ("cs", "gs")
order by
    hire_ymd desc,
    dr_name;
-- 주의사항
-- 날짜 포맷은 예시와 동일하게 나와야합니다.
-- 를 못 보고 제출했다

-- 작성 2(날짜 포맷 삽질)
SELECT
    dr_name as dr_name,
    dr_id as dr_id,
    mcdp_cd as mcdp_cd,
    date_format(hire_ymd, "YY-mm-dd") as hire_ymd
from
    doctor
where
    mcdp_cd in ("cs", "gs")
order by
    hire_ymd desc,
    dr_name;
-- YY-mm-dd
-- 그냥 이렇게 나옴

-- 작성 3(검색) 제출 -> 성공
SELECT
    dr_name as dr_name,
    dr_id as dr_id,
    mcdp_cd as mcdp_cd,
    date_format(hire_ymd, "%Y-%m-%d") as hire_ymd
from
    doctor
where
    mcdp_cd in ("cs", "gs")
order by
    hire_ymd desc,
    dr_name;
-- 유유 다 왔는데
-- 뭔가 2개 적힌 거만 기억나서
-- 아깝네요