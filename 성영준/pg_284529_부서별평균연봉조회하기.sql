-- 작성 전
round 랑 avg 물어보는 문제네

-- 작성 1(시작) 뇌정지
select
    d.DEPT_ID,
    DEPT_NAME_EN,
    round(avg(SAL)) as AVG_SAL
from
    HR_DEPARTMENT d
        join
    HR_EMPLOYEES e on d.DEPT_ID = e.DEPT_ID
group by
    DEPT_NAME_EN
order by
    AVG_SAL desc

자꾸 여기서 DEPT_ID 가 이상하다고 에러 뜨길래
내가 on 절을 잘못 쓰고 있나 했음
그런데 알고 보니

GPT왈
    쿼리에서 GROUP BY 절에 DEPT_NAME_EN만 포함되어 있습니다. 그러나 SELECT 절에는 DEPT_ID와 DEPT_NAME_EN이 포함되어 있습니다. SQL 표준에 따르면, GROUP BY 절에는 SELECT 절에 나열된 모든 비집계 열이 포함되어야 합니다.

다른 비집계 열이 모두 포함되야 한다고 한다

-- 작성 2(group by 조건 추가) 제출 -> 성공
select
    d.DEPT_ID,
    DEPT_NAME_EN,
    round(avg(SAL)) as AVG_SAL
from
    HR_DEPARTMENT d
        join
    HR_EMPLOYEES e on d.DEPT_ID = e.DEPT_ID
group by
    DEPT_NAME_EN, DEPT_ID
order by
    AVG_SAL desc