-- 작성 전(사고 흐름)
클났따 switch문인데 뭐라쓰드라

-- 작성 1(시작)
SELECT
    as pt_name,
    as pt_no,
    as gend_cd,
    as age,

    when p.tlno is null then "NONE"
    else p.tlno
end tlno
from
    patient p
where
    p.age <= 12
    and p.gend_cd = "W"
order by
    age,
    p.pt_name desc
여기서 뇌정지

-- 작성 1.1(생각 남)
SELECT
    p.pt_name as pt_name,
    p.pt_no as pt_no,
    p.gend_cd as gend_cd,
    p.age as age,
    case
        when p.tlno is null then "NONE"
        else p.tlno
        end tlno
from
    patient p
where
    p.age <= 12
  and p.gend_cd = "W"
order by
    age desc,
    p.pt_name
그리고 select절에 컬럼 명을 적지 않았고
정렬 기준도 틀렸음